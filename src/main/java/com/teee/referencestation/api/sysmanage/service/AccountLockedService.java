package com.teee.referencestation.api.sysmanage.service;

import com.teee.referencestation.api.basestation.service.BaseStationService;
import com.teee.referencestation.api.statistics.model.OperationLog;
import com.teee.referencestation.api.statistics.service.OperationLogService;
import com.teee.referencestation.api.sysmanage.model.AccountLocked;
import com.teee.referencestation.api.sysmanage.model.AccountLockedVo;
import com.teee.referencestation.api.terminalaccount.service.TerminalAccountService;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.constant.Dictionary;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.rpc.ice.teee.*;
import com.teee.referencestation.rpc.ice.util.IceUtil;
import com.teee.referencestation.rpc.ice.util.PageInfo;
import com.teee.referencestation.utils.ObjUtil;
import com.teee.referencestation.utils.RedisUtil;
import com.zeroc.Ice.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanglei
 */
@Service
@Slf4j
public class AccountLockedService extends BaseService {

    @Autowired
    private BaseStationService stationService;
    @Autowired
    private TerminalAccountService accountService;
    @Autowired
    private OperationLogService logService;

    public RestResponse findAccountLockedList(Map<String, Object> request, Integer pageNum, Integer pageSize) {

        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();

        DCExtdPack extdPack = new DCExtdPack();
        extdPack.setRpcHeader(RedisUtil.generateHeader());
        extdPack.setDcExtdCmd(DCExtdCmd.E_CS_ACCOUNTLOCKSTATUS_GETLOCKEDLIST);
        TEEERSDCAPIServant.HandlerDCExtdPackResult result = servantPrx.HandlerDCExtdPack(extdPack);

        byte[] lockedListBytes = result.resp.body;

        if (lockedListBytes == null || lockedListBytes.length == 0) {
            PageInfo<AccountLocked> pageInfo = new PageInfo<>(1, pageSize, 0);
            pageInfo.initPageInfo(Collections.emptyList());
            return RestResponse.success(pageInfo);
        }

        //反序列化地基站信息
        InputStream inputStream = new InputStream(servantPrx.ice_getCommunicator(), lockedListBytes);
        inputStream.startEncapsulation();
        DCGetLockedListResp lockedListResp = DCGetLockedListResp.ice_read(inputStream);
        inputStream.endEncapsulation();

        IdInfo[] idInfos = lockedListResp.idInfoSeq;

        int total;
        if (idInfos == null || (total = idInfos.length) == 0) {
            PageInfo<AccountLocked> pageInfo = new PageInfo<>(1, pageSize, 0);
            pageInfo.initPageInfo(Collections.emptyList());
            return RestResponse.success(pageInfo);
        }

        //page reasonable
        int pages = (total / pageSize) + 1;
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageNum > pages) {
            pageNum = pages;
        }

        int start = (pageNum -1) * pageSize;
        int end = pageNum * pageSize;

        IdInfo[] idInfoList = Arrays.copyOfRange(idInfos, start, end >= total ? total : end);

        PageInfo<AccountLocked> pageInfo = new PageInfo<>(pageNum, pageSize, total);
        pageInfo.initPageInfo(convertPageInfo(idInfoList));
        return RestResponse.success(pageInfo);
    }

    private List<AccountLocked> convertPageInfo(IdInfo[] idInfoArray) {

        List<IdInfo> idInfoList = new ArrayList<>(Arrays.asList(idInfoArray));
        long[] stationLocked = idInfoList.stream().filter(idInfo -> idInfo.idType == IDType.E_IDTYPE_BS).mapToLong(idInfo -> idInfo.getUuid()).toArray();
        long[] terminalLocked = idInfoList.stream().filter(idInfo -> idInfo.idType == IDType.E_IDTYPE_TM).mapToLong(idInfo -> idInfo.getUuid()).toArray();


        //构造id list请求
        DCGetReq stationLockedGet = new DCGetReq();
        stationLockedGet.dcTableType = DCTableType.E_TABLE_BASE_STATION_INFO;
        stationLockedGet.ids = stationLocked;
        stationLockedGet.rpcHeader = RedisUtil.generateHeader();

        //构造id list请求
        DCGetReq terminalLockedGet = new DCGetReq();
        terminalLockedGet.dcTableType = DCTableType.E_TABLE_TERMINAL_ACCOUNT;
        terminalLockedGet.ids = terminalLocked;
        terminalLockedGet.rpcHeader = RedisUtil.generateHeader();

        //获取ice代理对象
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        //根据id list查询实际数据
        TEEERSDCAPIServant.DCGetResult stationResult = servantPrx.DCGet(stationLockedGet);
        TEEERSDCAPIServant.DCGetResult terminalResult = servantPrx.DCGet(terminalLockedGet);
        //获取序列化后的数据
        byte[][] stationBody = stationResult.resp.body;
        byte[][] terminalBody = terminalResult.resp.body;

        //反序列化数据源
        List<BaseStationInfo> stationList = new ArrayList<>();
        for (byte[] bytes : stationBody) {
            stationList.add(deserialize4Ice(servantPrx.ice_getCommunicator(), bytes, BaseStationInfo.class));
        }
        List<TerminalAccount> terminalList = new ArrayList<>();
        for (byte[] bytes : terminalBody) {
            terminalList.add(deserialize4Ice(servantPrx.ice_getCommunicator(), bytes, TerminalAccount.class));
        }

        List<AccountLocked> stationVoList = stationList.stream().
                map(s -> new AccountLocked(s.getBaseStationInfoId(), s.getUsername(), IDType.E_IDTYPE_BS.value(),
                        s.getAuditingInfo().getCreatedTimeStamp())).
                collect(Collectors.toList());
        List<AccountLocked> terminalVoList = terminalList.stream().
                map(s -> new AccountLocked(s.getTerminalAccountId(), s.getUsername(), IDType.E_IDTYPE_TM.value(),
                        s.getAuditingInfo().getCreatedTimeStamp())).
                collect(Collectors.toList());

        List<AccountLocked> pageVoList = new ArrayList<>();
        pageVoList.addAll(stationVoList);
        pageVoList.addAll(terminalVoList);

        //创建时间排序
        pageVoList = pageVoList.stream().sorted(Comparator.comparing(AccountLocked::getCreatedDate).reversed()).collect(Collectors.toList());
        return pageVoList;
    }

    private int unLocked(AccountLockedVo lockedVo) {
        //获取ice代理对象
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();

        //构建解锁数据
        DCLockOpReq dcLockOpReq = new DCLockOpReq();
        dcLockOpReq.isLock = false;
        dcLockOpReq.id = lockedVo.getId();
        //构造解锁请求
        DCExtdPack extdPack = new DCExtdPack();
        extdPack.setRpcHeader(RedisUtil.generateHeader());
        extdPack.setDcExtdCmd(DCExtdCmd.E_CS_ACCOUNTLOCKSTATUS_LOCKOP);
        extdPack.setBody(super.serialize4Ice(servantPrx.ice_getCommunicator(), dcLockOpReq));

        TEEERSDCAPIServant.HandlerDCExtdPackResult result = servantPrx.HandlerDCExtdPack(extdPack);
        if (result.resp.rpcHeader.retCode != HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            recordOperationLog(lockedVo.getUsername());
        }
        return result.resp.rpcHeader.retCode;
    }

    private void recordOperationLog(String username) {
        String content;
        RestResponse<BaseStationInfo> stationInfo = stationService.loadBaseStationByUsername(username);
        RestResponse<TerminalAccount> terminalInfo = accountService.loadTerminalAccountByUsername(username);

        if (ObjUtil.isNotEmpty(stationInfo.getResult().getUsername())) {
            content = "解除地基站账户[" + username + "]锁定";
        } else if (ObjUtil.isNotEmpty(terminalInfo.getResult().getUsername())) {
            content = "解除接入账户[" + username + "]锁定";
        } else {
            content = "解除终端账户[" + username + "]锁定";
        }
        //添加操作日志
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.HIGH);
        log.setContent(content);
        log.setOperationName("解锁终端账户");
        logService.addOperationLog(log);
    }

    public RestResponse unLockedAccount(AccountLockedVo lockedVo) {

        int retCode = unLocked(lockedVo);
        if (retCode == 0) {
            return RestResponse.success("解锁成功");
        }
        return RestResponse.error("解锁失败");
    }

    public RestResponse unLockedAccountBatch(List<AccountLockedVo> lockedVoList) {
        try{
            for (AccountLockedVo lockedVo : lockedVoList) {
                unLocked(lockedVo);
            }
            return RestResponse.success("批量删除成功");
        } catch (Exception e) {
            log.error("BaseStationService", e);
        }
        return RestResponse.error("批量删除失败");
    }
}
