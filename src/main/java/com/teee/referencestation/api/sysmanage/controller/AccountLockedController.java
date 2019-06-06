package com.teee.referencestation.api.sysmanage.controller;

import com.teee.referencestation.api.sysmanage.model.AccountLocked;
import com.teee.referencestation.api.sysmanage.model.AccountLockedBatchVo;
import com.teee.referencestation.api.sysmanage.model.AccountLockedVo;
import com.teee.referencestation.api.sysmanage.service.AccountLockedService;
import com.teee.referencestation.common.base.controller.BaseController;
import com.teee.referencestation.common.base.model.BasePaginationVo;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JsonUtil;
import io.swagger.annotations.ApiImplicitParam;
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
@RequestMapping("/accountLocked")
public class AccountLockedController extends BaseController {

    @Autowired
    private AccountLockedService lockedService;

    @ApiOperation(value = "获取终端账户锁定列表", notes = "终端账户锁定列表")
    @PostMapping("/loadAccountLockedList")
    @RequiresPermissions("accountLocked")
    public RestResponse loadAccountLockedList(@RequestBody BasePaginationVo request) {
        return lockedService.findAccountLockedList(JsonUtil.vo2map(request), request.getPageNum(), request.getPageSize());
    }

    @ApiOperation(value = "解锁终端账户", notes = "解锁终端账户")
    @PostMapping("/unLockedAccount")
    @RequiresPermissions("accountLocked")
    public RestResponse unLockedAccount(@RequestBody AccountLockedVo lockedVo) {
        return lockedService.unLockedAccount(lockedVo);
    }

    @ApiOperation(value = "批量解锁终端账户", notes = "批量解锁终端账户")
    @PostMapping("/unLockedAccountBatch")
    @RequiresPermissions("accountLocked")
    public RestResponse unLockedAccountBatch(@RequestBody AccountLockedBatchVo request) {
        return lockedService.unLockedAccountBatch(request.getAccountLockedList());
    }
}
