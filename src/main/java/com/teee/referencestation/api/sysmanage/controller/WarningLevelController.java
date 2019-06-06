package com.teee.referencestation.api.sysmanage.controller;

import com.teee.referencestation.api.sysmanage.model.WarningLevelVo;
import com.teee.referencestation.api.sysmanage.service.WarningLevelService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.ObjUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhanglei
 */
@RestController
@RequestMapping("/warningLevel")
public class WarningLevelController {

    @Autowired
    private WarningLevelService levelService;

    @ApiOperation(value = "获取告警级别", notes = "查询")
    @PostMapping("/loadLevelDropdown")
    public RestResponse loadLevelDropdown() {
        List<WarningLevelVo> levelVoList = levelService.loadAllLevel();
        return ObjUtil.isEmpty(levelVoList) ? RestResponse.error("没有初始数据") : RestResponse.success(levelVoList);
    }
}
