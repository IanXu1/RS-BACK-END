package com.teee.referencestation.api.user.controller;

import com.teee.referencestation.api.user.model.*;
import com.teee.referencestation.api.user.service.RoleService;
import com.teee.referencestation.common.base.controller.BaseController;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.BeanUtil;
import com.teee.referencestation.utils.JsonUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhanglei
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色信息列表", notes = "角色信息列表")
    @PostMapping("/loadRoleList")
    @RequiresPermissions("roleManage")
    public RestResponse loadRoleList(@RequestBody RoleInfoQueryVo queryVo) {
        return roleService.loadRoleList(JsonUtil.vo2map(queryVo), queryVo.getPageNum(), queryVo.getPageSize());
    }

    @ApiOperation(value = "用户与角色绑定", notes = "角色绑定")
    @PostMapping("/assignUser")
    @RequiresPermissions("role:assign")
    public RestResponse assignUser(@RequestBody RoleAssignVo assignVo) {
        return roleService.assignUser(assignVo);
    }

    @ApiOperation(value = "用户与角色解绑", notes = "角色解绑")
    @PostMapping("/unbindUser")
    @RequiresPermissions("role:assign")
    public RestResponse unbindUser(@RequestBody RoleUnbindVo unbindVo) {
        return roleService.unbindUser(unbindVo);
    }

    @ApiOperation(value = "获取当前角色所绑定的用户信息", notes = "角色绑定")
    @PostMapping("/loadAssignedUserByRoleId")
    @RequiresPermissions("role:assign")
    public RestResponse loadAssignedUserByRoleId(@RequestBody RoleIdVo idVo) {
        return roleService.loadAssignedUserByRoleId(idVo.getRoleId());
    }

    @ApiOperation(value = "添加角色", notes = "添加角色")
    @PostMapping("/roleAdd")
    @RequiresPermissions("role:add")
    public RestResponse roleAdd(@RequestBody RoleAddVo addVo) {
        return roleService.addRole(addVo);
    }

    @ApiOperation(value = "编辑角色", notes = "编辑角色")
    @PostMapping("/roleModify")
    @RequiresPermissions("role:modify")
    public RestResponse roleModify(@RequestBody RoleModifyVo modifyVo) {
        return roleService.modifyRole(modifyVo);
    }

    @ApiOperation(value = "角色名称查重", notes = "角色名称查重")
    @PostMapping("/isRoleNameExists")
    @RequiresPermissions("roleManage")
    public RestResponse isRoleNameExists(@RequestBody RoleNameVo nameVo) {
        return RestResponse.success(roleService.isRoleNameExists(JsonUtil.vo2map(nameVo)));
    }

    @ApiOperation(value = "根据角色ID查询角色信息", notes = "查询角色信息")
    @PostMapping("/loadRoleById")
    @RequiresPermissions("roleManage")
    public RestResponse loadRoleById(@RequestBody RoleIdVo idVo) {
        return roleService.loadRoleById(idVo.getRoleId());
    }

    @ApiOperation(value = "根据角色ID删除角色信息", notes = "删除角色信息")
    @PostMapping("/deleteRole")
    @RequiresPermissions("role:del")
    public RestResponse deleteRole(@RequestBody RoleIdVo idVo) {
        return roleService.deleteRole(idVo.getRoleId());
    }

    @ApiOperation(value = "根据角色ID数组批量删除角色信息", notes = "删除角色信息")
    @PostMapping("/deleteRoleBatch")
    @RequiresPermissions("role:del")
    public RestResponse deleteRoleBatch(@RequestBody RoleIdListVo idListVo) {
        return roleService.deleteRoleBatch(idListVo);
    }
}
