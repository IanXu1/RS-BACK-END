package com.teee.referencestation.api.upgrade.controller;

import com.teee.referencestation.api.upgrade.service.UpgradeService;
import com.teee.referencestation.common.http.RestResponse;
import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author zhanglei
 */
@RestController
@RequestMapping("/upgrade")
public class UpgradeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeController.class);

    @Autowired
    private UpgradeService upgradeService;

    @PostMapping("/reportVersion")
    public RestResponse reportVersion(@RequestBody Map<String, Object> request) {
        LOGGER.info("================= 上报版本 ===============");
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            LOGGER.info(entry.getKey() + " : " + entry.getValue());
        }
        return upgradeService.reportVersion(request);
    }

    @PostMapping("/reportResult")
    public RestResponse reportResult(@RequestBody Map<String, Object> request) {
        LOGGER.info("================= 上报结果 ===============");
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            LOGGER.info(entry.getKey() + " : " + entry.getValue());
        }
        return upgradeService.reportResult(request);
    }

    @PostMapping("/getAllResources")
    public RestResponse getAllResources(@RequestBody Map<String, Object> request) throws DecoderException {
        LOGGER.info("================= 获取当前版本所有升级文件 ===============");
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            LOGGER.info(entry.getKey() + " : " + entry.getValue());
        }
        return upgradeService.getAllResources(request);
    }


    @PostMapping("/getUpgradeFile")
    public RestResponse getUpgradeFile(@RequestBody Map<String, Object> request) throws Exception {
        LOGGER.info("================= 获取升级文件 ===============");
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            LOGGER.info(entry.getKey() + " : " + entry.getValue());
        }
        return upgradeService.getUpgradeFile(request);
    }

    @PostMapping("/versionAllSend")
    public RestResponse versionAllSend() {
        LOGGER.info("================= 推送升级版本信息 ===============");
        return upgradeService.sendVersion2AllStation();
    }
}
