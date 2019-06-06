package com.teee.referencestation.api.user.controller;

import com.teee.referencestation.api.user.model.PermissionDelVo;
import com.teee.referencestation.api.user.model.PermissionGrantVo;
import com.teee.referencestation.api.user.model.RoleIdVo;
import com.teee.referencestation.api.user.service.PermissionService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JsonUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取当前角色所有权限", notes = "菜单信息")
    @PostMapping("/loadAllPermission")
    public RestResponse loadAllPermission(@RequestBody RoleIdVo idVo) {
        return permissionService.loadAllPermission(JsonUtil.vo2map(idVo));
    }

    @ApiOperation(value = "赋予用户权限", notes = "授权")
    @PostMapping("/grantPermission")
    public RestResponse grantPermission(@RequestBody PermissionGrantVo grantVo) {
        return permissionService.grantPermission(grantVo);
    }

    @ApiOperation(value = "删除用户权限", notes = "授权")
    @PostMapping("/deletePermission")
    public RestResponse deletePermission(@RequestBody PermissionDelVo delVo) {
        return permissionService.deletePermission(JsonUtil.vo2map(delVo));
    }
}
