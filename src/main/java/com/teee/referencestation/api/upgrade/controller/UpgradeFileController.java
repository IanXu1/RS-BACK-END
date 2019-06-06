package com.teee.referencestation.api.upgrade.controller;

import com.teee.referencestation.api.upgrade.model.UpgradeFile;
import com.teee.referencestation.api.upgrade.model.UploadFile;
import com.teee.referencestation.api.upgrade.service.UpgradeFileService;
import com.teee.referencestation.common.base.controller.BaseController;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.AESUtil;
import com.teee.referencestation.utils.BeanUtil;
import com.teee.referencestation.utils.JsonUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author zhanglei
 */
@Controller
@RequestMapping("/upgradeFile")
public class UpgradeFileController extends BaseController {

    @Autowired
    private UpgradeFileService fileService;

    @Value("${teee.upgrade.temp}")
    private String tempPath;

    @PostMapping("/loadUpgradeFileList")
    @RequiresPermissions("uploadFile")
    @ResponseBody
    public RestResponse loadUpgradeFileList(@RequestBody Map request) {
        return fileService.findUpgradeFileList(BeanUtil.toBean(request, UpgradeFile.class), Integer.valueOf(request.get("pageNum").toString()),
                Integer.valueOf(request.get("pageSize").toString()));
    }

    @PostMapping("/upgradeFileAdd")
    @RequiresPermissions("uploadFile:add")
    @ResponseBody
    public RestResponse upgradeFileAdd(@RequestBody Map<String, Object> param) {
        List<LinkedHashMap<String, Object>> map = (List<LinkedHashMap<String, Object>>) param.get("fileInfo");
        UploadFile uploadFile = JsonUtil.parseObject(JsonUtil.toJSONString(map.get(0)), UploadFile.class);
        return fileService.addUpgradeFile(uploadFile);
    }

    @RequestMapping("/downloadFile")
    public void downLoadFile(@RequestBody Map request, HttpServletResponse response) throws Exception {
        UpgradeFile file = fileService.findUpgradeFileById(Long.valueOf(request.get("id").toString()));

        String realPath = AESUtil.getInstance().decrypt(file.getFilePath());


        try( ServletOutputStream out =response.getOutputStream();
             FileInputStream inputStream = new FileInputStream(realPath)) {

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/force-download");
            response.setContentLength((int) new File(realPath).length());

            String downLoadFileName = file.getFileName();
            downLoadFileName = URLEncoder.encode(downLoadFileName, "UTF-8");
            downLoadFileName = downLoadFileName.replace("+", "%20");
            response.setHeader("content-disposition", "attachment; filename=" + downLoadFileName);
            response.setHeader("Access-Control-Expose-Headers", "content-disposition");

            byte[] b = new byte[1024];
            while (inputStream.read(b) != -1) {
                out.write(b);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/deleteUpgradeFile")
    @RequiresPermissions("uploadFile:del")
    @ResponseBody
    public RestResponse deleteUpgradeFile(HttpServletRequest request) {
        return fileService.deleteUpgradeFile(getParameter(request, UpgradeFile.class));
    }

    @PostMapping("/deleteUpgradeFileBatch")
    @RequiresPermissions("uploadFile:del")
    @ResponseBody
    public RestResponse deleteUpgradeFileBatch(@RequestBody Map request) {
        return fileService.deleteUpgradeFileBatch(request);
    }

    @PostMapping("/hasFileExists")
    @RequiresPermissions("uploadFile:add")
    @ResponseBody
    public RestResponse hasFileExists(@RequestBody Map request) {
        try {
            boolean isExists = fileService.hasFileExists(request);
            return RestResponse.success(isExists);
        } catch (Exception e) {
            return RestResponse.error("未知文件，请上传正确的文件");
        } catch (Error e) {
            return RestResponse.error("未知文件，请上传正确的文件");
        }
    }

    @PostMapping("/md5Check")
    @ResponseBody
    public RestResponse md5Check(@RequestBody Map request) {
        try {
            boolean check = fileService.md5Check(request);
            return RestResponse.success(check);
        } catch (Exception e) {
            return RestResponse.error("未知文件，请上传正确的文件");
        } catch (Error e) {
            return RestResponse.error("未知文件，请上传正确的文件");
        }
    }

    @PostMapping("/loadAvailableFile")
    @RequiresPermissions("versionIssue:issue")
    @ResponseBody
    public RestResponse loadAvailableFile(@RequestBody Map request) {
        return fileService.loadAvailableFile(request);
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public UploadFile upload(@RequestParam(value = "uploadFile", required = false) MultipartFile file) {
        if (file == null) {
            return null;
        }
        File f1 = new File(tempPath);
        if (!f1.exists()) {
            f1.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        //0. 处理fileName, IE和Google的fileName 会不同，ie会携带路径
        if (!StringUtils.isBlank(fileName) && fileName.contains(File.separator)) {
            int index = fileName.lastIndexOf(File.separator);
            fileName = fileName.substring(index + 1);
        }
        try {
            String newFileName = UUID.randomUUID().toString().replaceAll("-", "");
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(tempPath + newFileName));
            UploadFile uploadFile = new UploadFile();
            uploadFile.setName(fileName);
            uploadFile.setId(newFileName);
            return uploadFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
