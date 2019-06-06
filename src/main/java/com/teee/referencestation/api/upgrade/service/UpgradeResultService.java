package com.teee.referencestation.api.upgrade.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teee.referencestation.api.basestation.service.BaseStationService;
import com.teee.referencestation.api.upgrade.model.UpgradeResult;
import com.teee.referencestation.api.upgrade.model.UpgradeResultVo;
import com.teee.referencestation.api.upgrade.model.UpgradeVersion;
import com.teee.referencestation.api.user.model.SysUser;
import com.teee.referencestation.api.user.service.UserService;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.ObjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@Service
public class UpgradeResultService extends BaseService {

    @Autowired
    private UserService userService;
    @Autowired
    private UpgradeVersionService versionService;
    @Autowired
    private BaseStationService stationService;

    public void saveUpgradeResultBatch(List<UpgradeResult> resultList) {
        session.insert("upgradeResult.insertUpgradeResultBatch", resultList);
    }

    public RestResponse findUpgradeVersionList(Map request, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> resultList = session.selectList("upgradeResult.getDataList", request);
        convert2Vo(resultList);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo(resultList);
        return RestResponse.success(pageInfo);
    }

    private void convert2Vo(List<Map<String, Object>> resultList) {
        for (Map<String, Object> result : resultList) {
            long terminalId = Long.valueOf(result.get("terminalId").toString());
            Map stationInfo = (Map) stationService.loadBaseStationById(terminalId).getResult();
            result.put("terminalName", ObjUtil.isEmpty(stationInfo.get("name")) ? "" : stationInfo.get("name").toString());
            long preVersionId = ObjUtil.isEmpty(stationInfo.get("preVersionId")) ? -1 : Long.valueOf(result.get("preVersionId").toString());
            UpgradeVersion preVersion = versionService.findUpgradeVersionById(preVersionId);
            result.put("preVersion", preVersion == null ? "" : preVersion.getVersionNum());
            long afterVersionId = ObjUtil.isEmpty(stationInfo.get("afterVersionId")) ? -1 : Long.valueOf(result.get("afterVersionId").toString());
            UpgradeVersion afterVersion = versionService.findUpgradeVersionById(afterVersionId);
            result.put("afterVersion", afterVersion == null ? "" : afterVersion.getVersionNum());
            long sendUserId = ObjUtil.isEmpty(stationInfo.get("sendUserId")) ? -1 : Long.valueOf(result.get("sendUserId").toString());
            SysUser sendUser = userService.findUserById(sendUserId);
            result.put("sendUserName", sendUser == null ? "" : sendUser.getRealName());
        }
    }

    public UpgradeResult findLatestResultByTerminalId(long terminalId) {
        return session.selectOne("upgradeResult.findLatestResultByTerminalId", terminalId);
    }

    public UpgradeResult findResultByTerminalIdAndVersionId(long stationId, long versionId) {
        Map request = new HashMap(4);
        request.put("stationId", stationId);
        request.put("versionId", versionId);
        return session.selectOne("upgradeResult.findResultByTerminalIdAndVersionId", request);
    }

    public int modifyResultOnly(UpgradeResult result) {
        return session.update("upgradeResult.updateResultOnly", result);
    }
}
