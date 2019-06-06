package com.teee.referencestation.api.statistics.controller;

import com.teee.referencestation.api.statistics.model.AuthLogQueryVo;
import com.teee.referencestation.api.statistics.service.AuthLogService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JsonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 */
@RestController
@RequestMapping("/authLog")
public class AuthLogController {

    @Autowired
    private AuthLogService authService;

    @PostMapping("/loadAuthLogList")
    @RequiresPermissions("authLog")
    public RestResponse loadAuthLogList(@RequestBody AuthLogQueryVo request) {
        return authService.findAuthLogList(JsonUtil.vo2map(request), request.getPageNum(), request.getPageSize());
    }
}
