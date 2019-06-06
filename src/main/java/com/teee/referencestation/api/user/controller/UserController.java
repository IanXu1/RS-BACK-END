package com.teee.referencestation.api.user.controller;

import com.teee.referencestation.api.user.model.*;
import com.teee.referencestation.api.user.service.UserService;
import com.teee.referencestation.common.base.controller.BaseController;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JsonUtil;
import com.teee.referencestation.utils.ObjUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhanglei
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询用户列表信息", notes = "用户列表")
    @PostMapping("/list")
    @RequiresPermissions("userInfo")
    public RestResponse list(@RequestBody UserPageQueryVo queryVo) {
        return userService.findUserList(JsonUtil.vo2map(queryVo), queryVo.getPageNum(), queryVo.getPageSize());
    }

    @ApiOperation(value = "添加用户信息", notes = "添加用户")
    @PostMapping("/userAdd")
    @RequiresPermissions("user:add")
    public RestResponse userAdd(@RequestBody UserAddVo userAddVo) {
        return userService.addUser(userAddVo);
    }

    @ApiOperation(value = "用户名查重", notes = "存在返回true，不存在返回false")
    @PostMapping("/isUsernameExists")
    @RequiresPermissions("userInfo")
    public RestResponse isUsernameExists(@RequestBody UsernameVo usernameVo) {
        return RestResponse.success(ObjUtil.isNotEmpty(userService.findUserByUsername(usernameVo.getUsername())));
    }

    @ApiOperation(value = "根据用户ID查询用户信息", notes = "查询用户信息")
    @PostMapping("/loadUserById")
    @RequiresPermissions("userInfo")
    public RestResponse loadUserById(@RequestBody UserIdVo idVo) {
        return RestResponse.success(userService.findUserById(idVo.getId()));
    }

    @ApiOperation(value = "用户信息修改", notes = "修改用户信息")
    @PostMapping("/userModify")
    @RequiresPermissions("user:modify")
    public RestResponse userModify(@RequestBody UserModifyVo modifyVo) {
        return userService.modifyUser(modifyVo);
    }

    @ApiOperation(value = "根据ID删除用户", notes = "删除用户")
    @PostMapping("/deleteUser")
    @RequiresPermissions("user:del")
    public RestResponse deleteUser(@RequestBody UserIdVo idVo) {
        return userService.deleteUser(idVo);
    }

    @ApiOperation(value = "根据用户ID数组批量删除用户", notes = "批量删除")
    @PostMapping("/deleteUserBatch")
    @RequiresPermissions("user:del")
    public RestResponse deleteUserBatch(@RequestBody UserIdListVo idListVo) {
        return userService.deleteUserBatch(idListVo);
    }

    @ApiOperation(value = "用户解锁", notes = "用户解锁")
    @PostMapping("/unLockedUser")
    @RequiresPermissions("userLocked")
    public RestResponse unLockedUser(@RequestBody UserIdVo idVo) {
        return userService.unLockedUser(idVo.getId());
    }

    @ApiOperation(value = "用户批量解锁", notes = "用户批量解锁")
    @PostMapping("/unLockedUserBatch")
    @RequiresPermissions("userLocked")
    public RestResponse unLockedUserBatch(@RequestBody UserIdListVo idListVo) {
        return userService.unLockedUserBatch(idListVo);
    }

    @ApiOperation(value = "获取被锁定用户列表", notes = "被锁定用户列表")
    @PostMapping("/loadLockedUserList")
    @RequiresPermissions("userLocked")
    public RestResponse loadLockedUserList(@RequestBody UserPageQueryVo queryVo) {
        return userService.loadLockedUserList(JsonUtil.vo2map(queryVo), queryVo.getPageNum(), queryVo.getPageSize());
    }

    @ApiOperation(value = "修改用户密码", notes = "修改密码")
    @PostMapping("/pwdModify")
    public RestResponse pwdModify(@RequestBody Map request) {
        return userService.modifyPwd(request);
    }

    /**
     * 获取登录用户的基础，角色，权限，菜单信息
     * @return
     */
    @ApiOperation(value = "获取用户基础、角色、权限、菜单等信息", notes = "用户信息")
    @GetMapping("/info")
    public RestResponse userInfo() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return userService.getUserInfo();
        }
        return new RestResponse(401, "not login", null);
    }

    @ApiOperation(value = "检查原始密码是否正确", notes = "需要登录状态")
    @PostMapping("/isPwdRight")
    public RestResponse isPwdRight(@RequestBody Map request) {
        return RestResponse.success(userService.isPwdRight(request));
    }

    @ApiOperation(value = "判断用户名和密码是否重复", notes = "需要登录状态")
    @PostMapping("/isPwdEqualUsername")
    public RestResponse isPwdEqualUsername(@RequestBody Map request) {
        return RestResponse.success(userService.isPwdEqualUsername(request));
    }
}
