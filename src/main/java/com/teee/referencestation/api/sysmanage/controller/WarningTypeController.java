package com.teee.referencestation.api.sysmanage.controller;

import com.teee.referencestation.api.sysmanage.model.*;
import com.teee.referencestation.api.sysmanage.service.WarningTypeService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JsonUtil;
import com.teee.referencestation.utils.ObjUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@RestController
@RequestMapping("/warningType")
public class WarningTypeController {

    @Autowired
    private WarningTypeService typeService;

    @ApiOperation(value = "查询告警配置信息", notes = "告警推送配置列表")
    @PostMapping("/list")
    @RequiresPermissions("warningType")
    public RestResponse list(@RequestBody WarningTypePageQueryVo queryVo) {
        return typeService.findWarningTypeList(JsonUtil.vo2map(queryVo), queryVo.getPageNum(), queryVo.getPageSize());
    }

    @ApiOperation(value = "查询告警类型级联", notes = "查询告警类型级联")
    @PostMapping("/cascade")
    public RestResponse loadCascadeData() {
        List<WarningTypeVo> cascade = typeService.loadCascadeData();
        return ObjUtil.isEmpty(cascade) ? RestResponse.error("没有初始化数据") : RestResponse.success(cascade);
    }

    @ApiOperation(value = "根据ID查询告警类型", notes = "查询数据")
    @PostMapping("/loadWarningTypeById")
    @RequiresPermissions("warningType")
    public RestResponse loadWarningTypeById(@RequestBody IdVo idVo) {
        Map<String, Object> typeVo = typeService.loadWarningTypeById(idVo.getId());
        return ObjUtil.isEmpty(typeVo) ? RestResponse.error("没有相关数据") : RestResponse.success(typeVo);
    }

    @ApiOperation(value = "更新数据", notes = "更新")
    @PostMapping("/update")
    @RequiresPermissions("warningType:modify")
    public RestResponse update(@RequestBody WarningType type) {
        int effects = typeService.updateType(type);
        return effects > 0 ? RestResponse.success() : RestResponse.error("无效更新");
    }

    @PostMapping("/loadPushUsers")
    public RestResponse loadPushUsers(@RequestBody PushUserPageQueryVo pageQueryVo) {
        return typeService.loadPushUsers(pageQueryVo);
    }

    @PostMapping("/bindPushUser")
    public RestResponse bindPushUser(@RequestBody WarningUserMappingVo mappingVo) {
        return typeService.bindPushUser(mappingVo);
    }

    @PostMapping("/unbindPushUser")
    public RestResponse unbindPushUser(@RequestBody WarningUserMappingVo mappingVo) {
        return typeService.unbindPushUser(mappingVo);
    }
}
