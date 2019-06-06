package com.teee.referencestation.api.sysmanage.controller;

import com.teee.referencestation.api.sysmanage.model.DictionaryQueryVo;
import com.teee.referencestation.api.sysmanage.service.DictionaryService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JsonUtil;
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
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dicService;

    @ApiOperation(value = "查询数据词典", notes = "数据词典")
    @PostMapping("/loadItemByCode")
    public RestResponse loadItemByCode(@RequestBody DictionaryQueryVo request) {
        return dicService.loadItemByCode(JsonUtil.vo2map(request));
    }
}
