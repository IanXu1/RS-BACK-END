package com.teee.referencestation.api.statistics.controller;

import com.teee.referencestation.api.statistics.model.WarningInfoPageQueryVo;
import com.teee.referencestation.api.statistics.service.WarningInfoService;
import com.teee.referencestation.api.user.model.SysUser;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.DateUtil;
import com.teee.referencestation.utils.ExcelUtils;
import com.teee.referencestation.utils.JsonUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@Controller
@RequestMapping("/warningInfo")
public class WarningInfoController {

    @Autowired
    private WarningInfoService warningInfoService;

    @ApiOperation(value = "获取告警信息列表", notes = "告警信息")
    @PostMapping("/loadWarningInfoList")
    @RequiresPermissions("warningInfo")
    @ResponseBody
    public RestResponse loadWarningInfoList(@RequestBody WarningInfoPageQueryVo queryVo) {
        return warningInfoService.findWarningInfoList(JsonUtil.vo2map(queryVo), queryVo.getPageNum(),
                queryVo.getPageSize());
    }

    @ApiOperation(value = "获取告警信息列表", notes = "告警信息")
    @PostMapping("/loadWarningInfoList4admin")
    @RequiresPermissions("warningInfo")
    @ResponseBody
    public RestResponse loadWarningInfoList4admin(@RequestBody WarningInfoPageQueryVo queryVo) {
        return warningInfoService.loadWarningInfoList4admin(JsonUtil.vo2map(queryVo), queryVo.getPageNum(),
                queryVo.getPageSize());
    }

    @ApiOperation(value = "导出告警内容列表", notes = "暂不可用")
    @RequestMapping("/exportWarningInfo")
    @RequiresPermissions("warninginfo:export")
    public void exportWarningInfo(@RequestBody WarningInfoPageQueryVo queryVo, HttpServletResponse response) throws IOException {
        ExcelUtils excelUtils = new ExcelUtils();
        List<Map<String, Object>> list = warningInfoService.loadExportInfo(JsonUtil.vo2map(queryVo));
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String fileName = "告警信息-" + DateUtil.getCurrentTime() + ".xlsx";
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Access-Control-Expose-Headers", "content-disposition");
            OutputStream out = response.getOutputStream();
            excelUtils.exportXSSExcel("告警信息","告警信息",
                    new String[] {"序号", "告警类型", "告警级别", "基站名称", "发生时间", "告警内容"},
                    new String[] {"seq", "typeName", "levelName", "stationName", "occurredTime", "content"},
                    list, out ,null);
        } catch (IOException e) {
        }
    }

    @PostMapping("/push")
    @ResponseBody
    public RestResponse push(@RequestBody Map<String, String> request) {
        for (Map.Entry<String, String> entry : request.entrySet()) {
            System.out.println("========warning push=========");
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        return warningInfoService.pushWarning2User(request);
    }
}
