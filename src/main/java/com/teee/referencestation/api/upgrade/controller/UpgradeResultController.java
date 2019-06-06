package com.teee.referencestation.api.upgrade.controller;

import com.teee.referencestation.api.upgrade.service.UpgradeResultService;
import com.teee.referencestation.common.http.RestResponse;
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
@RequestMapping("/upgradeResult")
public class UpgradeResultController {

    @Autowired
    private UpgradeResultService resultService;

    @PostMapping("/loadUpgradeResultList")
    @RequiresPermissions("versionIssue:result")
    public RestResponse loadUpgradeResultList(@RequestBody Map request) {
        return resultService.findUpgradeVersionList(request, Integer.valueOf(request.get("pageNum").toString()),
                Integer.valueOf(request.get("pageSize").toString()));
    }
}
