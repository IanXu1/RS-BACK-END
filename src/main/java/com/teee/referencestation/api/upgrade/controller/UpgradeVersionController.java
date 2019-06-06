package com.teee.referencestation.api.upgrade.controller;

import com.teee.referencestation.api.upgrade.model.UpgradeVersion;
import com.teee.referencestation.api.upgrade.service.UpgradeVersionService;
import com.teee.referencestation.common.base.controller.BaseController;
import com.teee.referencestation.common.http.RestResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@RestController
@RequestMapping("/upgradeVersion")
public class UpgradeVersionController extends BaseController {

    @Autowired
    private UpgradeVersionService versionService;

    @PostMapping("/loadUpgradeVersionList")
    @RequiresPermissions("versionIssue")
    public RestResponse loadUpgradeVersionList(@RequestBody Map request) {
        return versionService.findUpgradeVersionList(request, Integer.valueOf(request.get("pageNum").toString()),
                Integer.valueOf(request.get("pageSize").toString()));
    }

    @PostMapping("/upgradeVersionAdd")
    @RequiresPermissions("versionIssue:add")
    public RestResponse upgradeVersionAdd(@RequestBody Map request) {
        return versionService.addUpgradeVersion(request);
    }

    @PostMapping("/deleteUpgradeVersion")
    @RequiresPermissions("versionIssue:del")
    public RestResponse deleteUpgradeVersion(HttpServletRequest request) {
        return versionService.deleteUpgradeVersion(getParameter(request, UpgradeVersion.class));
    }

    @PostMapping("/versionPartSend")
    @RequiresPermissions("versionIssue:issue")
    public RestResponse versionPartSend(@RequestBody Map request) {
        return versionService.sendVersion2PartStation(Long.valueOf(request.get("id").toString()), (List) request.get("allUpgradeSelect"));
    }


    @PostMapping("/versionAllSend")
    @RequiresPermissions("versionIssue:issue")
    public RestResponse versionAllSend(@RequestBody Map request) {
        return versionService.sendVersion2AllStation(Long.valueOf(request.get("id").toString()));
    }
}
