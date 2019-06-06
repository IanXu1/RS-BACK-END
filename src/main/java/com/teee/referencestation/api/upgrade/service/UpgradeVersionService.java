package com.teee.referencestation.api.upgrade.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teee.referencestation.api.basestation.service.BaseStationService;
import com.teee.referencestation.api.upgrade.model.UpgradeFile;
import com.teee.referencestation.api.upgrade.model.UpgradeResult;
import com.teee.referencestation.api.upgrade.model.UpgradeVersion;
import com.teee.referencestation.api.upgrade.model.UpgradeVersionVo;
import com.teee.referencestation.api.user.model.SysUser;
import com.teee.referencestation.api.user.service.UserService;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.rpc.ice.teee.BaseStationInfo;
import com.teee.referencestation.utils.AESUtil;
import com.teee.referencestation.utils.FileUtil;
import com.teee.referencestation.utils.JwtUtil;
import com.teee.referencestation.utils.ObjUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zhanglei
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UpgradeVersionService extends BaseService {

    @Autowired
    private UpgradeResultService resultService;
    @Autowired
    private BaseStationService stationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UpgradeFileService fileService;

    public RestResponse findUpgradeVersionList(Map request, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UpgradeVersion> resultList = session.selectList("upgradeVersion.getDataList", request);
        PageInfo<UpgradeVersionVo> pageInfo = new PageInfo<>(convert2Vo(resultList));
        return RestResponse.success(pageInfo);
    }

    private List<UpgradeVersionVo> convert2Vo(List<UpgradeVersion> resultList) {
        List<UpgradeVersionVo> voList = new ArrayList<>();
        for (UpgradeVersion version : resultList) {
            UpgradeVersionVo vo = new UpgradeVersionVo();
            vo.setId(version.getId());
            vo.setVersionNum(version.getVersionNum());
            vo.setState(version.getState());
            UpgradeFile file = fileService.findUpgradeFileById(version.getFileId());
            vo.setFileName(file == null ? "" : file.getFileName());
            SysUser sendUser = userService.findUserById(version.getSendUserId());
            vo.setSendUserName(sendUser == null ? "" : sendUser.getRealName());
            SysUser createUser = userService.findUserById(version.getCreatedBy());
            vo.setCreateUserName(createUser == null ? "" : createUser.getRealName());
            vo.setCreateTime(version.getCreatedDate());
            vo.setSendTime(version.getSendTime());
            voList.add(vo);
        }
        return voList;
    }

    public RestResponse addUpgradeVersion(Map<String, Object> objectMap) {
        long fileId = Long.valueOf(objectMap.get("fileId").toString());
        // 将升级文件更新为已选中，下次升级不能选择
        UpgradeVersion version = new UpgradeVersion();
        UpgradeFile file = fileService.findUpgradeFileById(fileId);
        String realPath;
        try {
            realPath = AESUtil.getInstance().decrypt(file.getFilePath());
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.error("上传发生异常");
        }
        byte[] fileBytes = FileUtil.getBytesByFile(realPath);
        version.setVersionNum(new String(FileUtil.subBytes(fileBytes, 20, 12)).trim());
        version.setFileId(fileId);
        version.setState(0);
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        version.setCreatedBy(Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
        int effects = session.insert("upgradeVersion.insertUpgradeVersion", version);
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(effects > 0 ? HttpStatus.OK.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());
        restResponse.setMsg(effects > 0 ? "升级版本添加成功" : "升级版本添加失败");
        return restResponse;
    }

    public RestResponse deleteUpgradeVersion(UpgradeVersion version) {
        // 逻辑删除位置为已删除
        version.setDeleted(Constant.DATA_STATUS.DELETED);
        // 获取审计信息
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        version.setLastModifiedBy(Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
        int effects = modifyUpgradeVersionOnly(version);
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(HttpStatus.OK.value());
        if (effects <= 0) {
            restResponse.setMsg("删除无效");
        } else {
            restResponse.setMsg("删除成功");
        }
        return restResponse;
    }


    public UpgradeVersion findUpgradeVersionById(long id) {
        return session.selectOne("upgradeVersion.findUpgradeVersionById", id);
    }

    public RestResponse sendVersion2PartStation(long id, List<Integer> allUpgradeSelect) {
        // 获取审计信息
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        // 获取当前下发版本信息
        UpgradeVersion version = findUpgradeVersionById(id);
        // 设置为已下发
        version.setState(1);
        // 设置下法人信息
        long sendUserId = Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID));
        version.setSendUserId(sendUserId);
        version.setSendTime(LocalDateTime.now());
        modifyUpgradeVersionOnly(version);
        UpgradeFile upgradeFile = fileService.findUpgradeFileById(version.getFileId());
        // 已下发
        upgradeFile.setState(2);
        fileService.modifyFileOnly(upgradeFile);
        // 构造升级结果

        long[] stationIds = allUpgradeSelect.stream().mapToLong(item -> item).toArray();
        List<UpgradeResult> resultList = saveResultBatchWithStationIds(stationIds, version);
        if (ObjUtil.isNotEmpty(resultList)) {
            resultService.saveUpgradeResultBatch(resultList);
        }

        // 主动通知终端升级
        for (UpgradeResult result : resultList) {
            super.forward2terminal(result.getTerminalId());
        }
        return RestResponse.success("下发成功");
    }


    public UpgradeVersion findLatestVersionByTerminalId(long terminalId) {
        return session.selectOne("upgradeVersion.findLatestVersionByTerminalId", terminalId);
    }

    private int modifyUpgradeVersionOnly(UpgradeVersion version) {
        return session.update("upgradeVersion.updateUpgradeVersion", version);
    }

    public RestResponse sendVersion2AllStation(Long id) {
        //获取审计信息
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        //获取当前下发版本信息
        UpgradeVersion version = findUpgradeVersionById(id);
        //设置为已下发
        version.setState(1);
        //设置下法人信息
        long sendUserId = Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID));
        version.setSendTime(LocalDateTime.now());
        version.setSendUserId(sendUserId);
        modifyUpgradeVersionOnly(version);
        UpgradeFile upgradeFile = fileService.findUpgradeFileById(version.getFileId());
        //已下发
        upgradeFile.setState(2);
        fileService.modifyFileOnly(upgradeFile);
        //构造升级结果
        List<BaseStationInfo> baseStationInfoList = stationService.loadAvailableStation();
        long[] stationIds = baseStationInfoList.stream().mapToLong(item -> item.getBaseStationInfoId()).toArray();
        List<UpgradeResult> resultList = saveResultBatchWithStationIds(stationIds, version);
        if (ObjUtil.isNotEmpty(resultList)) {
            resultService.saveUpgradeResultBatch(resultList);
        }
        // 主动通知终端升级
        for (UpgradeResult result : resultList) {
            super.forward2terminal(result.getTerminalId());
        }
        return RestResponse.success("下发成功");
    }

    /**
     * @desc 通过地基站id list批量保存升级结果
     * @return
     */
    public List<UpgradeResult> saveResultBatchWithStationIds(long[] ids, UpgradeVersion afterVersion) {
        List<UpgradeResult> resultList = new ArrayList<>();
        for (long baseStationId : ids) {
            UpgradeResult oldResult = resultService.findResultByTerminalIdAndVersionId(baseStationId, afterVersion.getId());
            if (ObjUtil.isNotEmpty(oldResult)) {
                oldResult.setState(0);
                oldResult.setErrorCode(-1);
                resultService.modifyResultOnly(oldResult);
                continue;
            }
            UpgradeResult result = new UpgradeResult();
            result.setTerminalId(baseStationId);
            UpgradeVersion preVersion = findLatestVersionByTerminalId(baseStationId);
            result.setPreVersionId(ObjUtil.isEmpty(preVersion) ? -1 : preVersion.getId());
            result.setAfterVersionId(afterVersion.getId());
            //未升级
            result.setState(0);
            // 第一次保存升级结果没有error code
            result.setErrorCode(-1);
            result.setCreatedBy(afterVersion.getCreatedBy());
            result.setCreatedDate(LocalDateTime.now());
            result.setSendUserId(afterVersion.getSendUserId());
            resultList.add(result);
        }
        return resultList;
    }

    public UpgradeVersion findLatestVersion() {
        return session.selectOne("upgradeVersion.findLatestVersion");
    }

    public UpgradeVersion findInitVersion() {
        return session.selectOne("upgradeVersion.findInitVersion");
    }

    public UpgradeVersion findLatestVersionByFileId(long fileId) {
        return session.selectOne("upgradeVersion.findLatestVersionByFileId", fileId);
    }
}
