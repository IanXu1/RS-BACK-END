package com.teee.referencestation.api.upgrade.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teee.referencestation.api.upgrade.model.UpgradeFile;
import com.teee.referencestation.api.upgrade.model.UploadFile;
import com.teee.referencestation.api.user.model.SysUser;
import com.teee.referencestation.api.user.service.UserService;
import com.teee.referencestation.utils.Md5EncryptionUtil;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.*;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UpgradeFileService extends BaseService {

    @Value("${teee.upgrade.temp}")
    private String tempPath;
    @Value("${teee.upgrade.store}")
    private String storePath;
    @Autowired
    private UserService userService;

    public RestResponse findUpgradeFileList(UpgradeFile file, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> resultList = session.selectList("upgradeFile.getDataList", file);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo(convert2vo(resultList));
        return RestResponse.success((pageInfo));
    }

    private List<Map<String, Object>> convert2vo(List<Map<String, Object>> resultList) {
        for (Map <String, Object> file : resultList) {
            long createdBy = Long.valueOf(file.get("createdBy").toString());
            SysUser createdUser = userService.findUserById(createdBy);
            file.put("createdBy", ObjUtil.isEmpty(createdUser) ? "" : createdUser.getRealName());
            long lastModifiedBy = Long.valueOf(file.get("lastModifiedBy") == null ? "-1" : file.get("lastModifiedBy").toString());
            SysUser modifiedUser = userService.findUserById(lastModifiedBy);
            file.put("lastModifiedBy", ObjUtil.isEmpty(modifiedUser) ? "" : modifiedUser.getRealName());
        }
        return resultList;
    }

    public RestResponse addUpgradeFile(UploadFile uploadFile) {
        UpgradeFile file = new UpgradeFile();
        String filePath = storeFile(uploadFile);
        if (filePath == null) {
            return RestResponse.error("文件保存出错");
        }
        file.setFilePath(filePath);
        try {
            byte[] bytes = FileUtil.getBytesByFile(AESUtil.getInstance().decrypt(filePath));
            handleFileAttr(bytes, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取审计信息
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        file.setState(0);
        file.setDeleted(0);
        file.setCreatedBy(Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
        int effects = session.insert("upgradeFile.insertUpgradeFile", file);

        return effects > 0 ? RestResponse.success() : RestResponse.error("升级文件添加失败");
    }

    private void handleFileAttr(byte[] bytes, UpgradeFile file) {
        // 解析文件名字
        byte[] fileNameBytes = FileUtil.subBytes(bytes, 0, 20);
        file.setFileName(new String(fileNameBytes).trim());
        // 解析md5
        byte[] md5Bytes = FileUtil.subBytes(bytes, 40, 16);
        file.setMd5(Hex.encodeToString(md5Bytes).toLowerCase());
        // 解析文件类型
        byte[] fileTypeBytes = FileUtil.subBytes(bytes, 56, 4);
        file.setFileType(Hex.encodeToString(fileTypeBytes));
        // 解析文件大小(描述信息中为带文件头的长度)
        file.setFileSize(bytes.length);
        // 解析res
        byte[] resBytes = FileUtil.subBytes(bytes, 64, 36);
        System.out.println(new String(resBytes));
        // 解析文件内容
        byte[] fileContent = FileUtil.subBytes(bytes, 100, bytes.length - 100);
        System.out.println(Md5EncryptionUtil.md5WithNoSalt(fileContent));
    }

    private String storeFile(UploadFile file) {
        try {
            File original = new File(tempPath + file.getId());
            String newPath = storePath + DateUtil.getCurrentTimeMinus() + File.separator + file.getName();
            FileUtils.copyFile(original, new File(newPath));
            FileUtils.forceDelete(original);
            return AESUtil.getInstance().encrypt(newPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UpgradeFile findUpgradeFileById(long id) {
        return session.selectOne("upgradeFile.findUpgradeFileById", id);
    }

    public RestResponse deleteUpgradeFile(UpgradeFile upgradeFile) {
        if (upgradeFile.getId() <= 0) {
            return RestResponse.error("删除失败 ");
        }

        int effects = session.update("upgradeFile.deleteUpgradeFileById", upgradeFile.getId());
        return effects > 0 ? RestResponse.success("删除成功") : RestResponse.error("删除失败");
    }

    public RestResponse loadAvailableFile(Map request) {
        PageHelper.startPage(Integer.valueOf(request.get("pageNum").toString()), Integer.valueOf(request.get("pageSize").toString()));
        List<Map<String, Object>> resultList = session.selectList("upgradeFile.loadAvailableFile", request);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo(convert2vo(resultList));
        return RestResponse.success(pageInfo);
    }


    public int modifyFileOnly(UpgradeFile file) {
        return session.update("upgradeFile.updateUpgradeFile", file);
    }

    public RestResponse deleteUpgradeFileBatch(Map request) {
        int effects = session.update("upgradeFile.deleteUpgradeFileBatch", request);
        return effects > 0 ? RestResponse.success() : RestResponse.error("批量删除失败");
    }

    public UpgradeFile findUpgradeFileByMd5(String md5) {
        return session.selectOne("upgradeFile.loadUpgradeFileByMd5", md5);
    }

    public boolean hasFileExists(Map request) {
        String path = tempPath + request.get("id").toString();
        byte[] bytes = FileUtil.getBytesByFile(path);
        // 解析md5
        byte[] md5Bytes = FileUtil.subBytes(bytes, 40, 16);
        UpgradeFile file = session.selectOne("upgradeFile.loadUpgradeFileByMd5", Hex.encodeToString(md5Bytes));
        return ObjUtil.isNotEmpty(file);
    }

    public boolean md5Check(Map request) {
        String path = tempPath + request.get("id").toString();
        byte[] bytes = FileUtil.getBytesByFile(path);
        // 解析md5
        byte[] md5Bytes = FileUtil.subBytes(bytes, 40, 16);
        // 解析文件长度
        int fileLength = FileUtil.bytesToInt(bytes, 60);
        // 解析文件内容md5
        String md5 = Md5EncryptionUtil.md5WithNoSalt(FileUtil.subBytes(bytes, 100, fileLength));
        return md5.equalsIgnoreCase(Hex.encodeToString(md5Bytes));
    }
}
