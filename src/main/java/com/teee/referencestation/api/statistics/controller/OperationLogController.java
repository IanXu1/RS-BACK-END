package com.teee.referencestation.api.statistics.controller;

import com.teee.referencestation.api.statistics.service.OperationLogService;
import com.teee.referencestation.common.http.RestResponse;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/operationLog")
public class OperationLogController {

    @Autowired
    private OperationLogService operationService;

    @ApiOperation(value = "查询操作日志列表", notes = "操作日志列表")
    @PostMapping("/loadOperationLogList")
    @RequiresPermissions("operateLog")
    public RestResponse loadOperationLogList(@RequestBody Map request) {
        return operationService.findOperationLogList(request, Integer.valueOf(request.get("pageNum").toString()),
                Integer.valueOf(request.get("pageSize").toString()));
    }
}
