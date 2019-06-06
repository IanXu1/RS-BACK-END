package com.teee.referencestation.api.test.controller;

import com.teee.referencestation.api.test.service.IceService;
import com.teee.referencestation.common.http.RestResponse;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/test/api")
public class IceController {

    @Autowired
    private IceService iceService;

    @ApiOperation(value = "添加地基站信息", notes = "仅供内部测试")
    @PostMapping("/addBaseStation")
    public RestResponse addBaseStation(@RequestBody Map request) {
        return iceService.addBaseStation(request);
    }

    @ApiOperation(value = "添加接入账户信息", notes = "仅供内部测试")
    @PostMapping("/addTerminal")
    public RestResponse addTerminal(@RequestBody Map request) {
        return iceService.addTerminal(request);
    }

    @ApiOperation(value = "情况地基站和接入账户所有数据", notes = "仅供内部测试")
    @PostMapping("/flushDB")
    public RestResponse flushDB() {
        return iceService.flushDB();
    }
}
