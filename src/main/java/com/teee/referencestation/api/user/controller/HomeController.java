package com.teee.referencestation.api.user.controller;

import com.teee.referencestation.api.user.model.SysPermission;
import com.teee.referencestation.api.user.service.PermissionService;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@RestController
public class HomeController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "根据当前登录用户获取菜单信息", notes = "菜单信息")
    @GetMapping(value = "/loadMenuInfo")
    public RestResponse home() {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        long userId = Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID));
        Map<String, List<SysPermission>> menuInfo = permissionService.loadMenuInfoByUserId(userId);
        return RestResponse.success(menuInfo);
    }
}
