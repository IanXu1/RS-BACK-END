package com.teee.referencestation.api.terminalaccount.controller;

import com.teee.referencestation.api.terminalaccount.model.*;
import com.teee.referencestation.api.terminalaccount.service.TerminalAccountService;
import com.teee.referencestation.common.base.controller.BaseController;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.rpc.ice.teee.TerminalAccount;
import com.teee.referencestation.utils.BeanUtil;
import com.teee.referencestation.utils.JsonUtil;
import com.teee.referencestation.utils.ObjUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhanglei
 */
@RestController
@RequestMapping("/terminalAccount")
public class TerminalAccountController extends BaseController {

    @Autowired
    private TerminalAccountService accountService;

    @PostMapping("/loadTerminalAccountList")
    @RequiresPermissions("terminalInfo")
    public RestResponse loadTerminalAccountList(@RequestBody TerminalQueryVo queryVo) {
        return accountService.findAccountList(JsonUtil.vo2map(queryVo), queryVo.getPageNum(), queryVo.getPageSize());
    }

    @PostMapping("/terminalAccountAdd")
    @RequiresPermissions("terminalInfo:add")
    public RestResponse terminalAccountAdd(@RequestBody TerminalAddVo addVo) {
        return accountService.addAccount(BeanUtil.toBean(JsonUtil.vo2map(addVo), TerminalAccount.class),
                ObjUtil.isNotEmpty(addVo.getExpireStartTime()) ? addVo.getExpireStartTime() : "",
                ObjUtil.isNotEmpty(addVo.getExpireEndTime()) ? addVo.getExpireEndTime() : "");
    }

    @PostMapping("/isUsernameExists")
    @RequiresPermissions("terminalInfo")
    public RestResponse isUsernameExists(@RequestBody TerminalUsernameVo usernameVo) {
        return RestResponse.success(accountService.findAccountByUsername(usernameVo.getUsername()));
    }


    @PostMapping("/loadTerminalAccountById")
    @RequiresPermissions("terminalInfo")
    public RestResponse loadTerminalAccountById(@RequestBody TerminalIdVo idVo) {
        return accountService.loadTerminalAccountById(idVo.getId());
    }

    @PostMapping("/terminalAccountModify")
    @RequiresPermissions("terminalInfo:modify")
    public RestResponse terminalAccountModify(@RequestBody TerminalModifyVo modifyVo) {
        return accountService.modifyAccount(BeanUtil.toBean(JsonUtil.vo2map(modifyVo), TerminalAccount.class),
                ObjUtil.isNotEmpty(modifyVo.getExpireStartTime()) ? modifyVo.getExpireStartTime() : "",
                ObjUtil.isNotEmpty(modifyVo.getExpireEndTime()) ? modifyVo.getExpireEndTime() : "");
    }

    @PostMapping("/deleteTerminalAccount")
    @RequiresPermissions("terminalInfo:del")
    public RestResponse deleteTerminalAccount(@RequestBody TerminalIdVo idVo) {
        return accountService.deleteTerminalAccount(idVo.getId());
    }

    @PostMapping("/deleteTerminalAccountBatch")
    @RequiresPermissions("terminalInfo:del")
    public RestResponse deleteTerminalAccountBatch(@RequestBody TerminalIdListVo idListVo) {
        return accountService.deleteTerminalAccountBatch(idListVo);
    }
}
