package com.teee.referencestation.api.terminalaccount.service;

import com.teee.referencestation.api.statistics.model.OperationLog;
import com.teee.referencestation.api.statistics.service.OperationLogService;
import com.teee.referencestation.api.sysmanage.service.DictionaryService;
import com.teee.referencestation.api.terminalaccount.model.TerminalIdListVo;
import com.teee.referencestation.api.user.model.SysUser;
import com.teee.referencestation.api.user.service.UserService;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.cache.EnableIceCacheService;
import com.teee.referencestation.common.constant.Dictionary;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.rpc.ice.teee.*;
import com.teee.referencestation.rpc.ice.util.IceUtil;
import com.teee.referencestation.rpc.ice.util.PageInfo;
import com.teee.referencestation.utils.ObjUtil;
import com.teee.referencestation.utils.RedisUtil;
import com.zeroc.Ice.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@Service
@Slf4j
public class TerminalAccountService extends BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalAccountService.class);

    @Autowired
    private UserService userService;
    @Autowired
    private OperationLogService operationService;
    @Autowired
    private DictionaryService dicService;

    public RestResponse findAccountList(Map<String, Object> queryParams, Integer pageNum, Integer pageSize) {
        //构造排序
        OrderByPair pair = new OrderByPair();
        pair.setDcColumnType(DCColumnType.E_AUDITINGINFO_CREATED_TIMESTAMP);
        pair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{pair};

        RestResponse restResponse = new RestResponse();
        try {
            queryParams.put("eq_isDeleted", false);
            PageInfo pageInfo = super.queryPageInfo4Ice(queryParams, pageNum, pageSize, DCTableType.E_TABLE_TERMINAL_ACCOUNT,
                    orderByPairs, TerminalAccount.class);
            restResponse.setCode(HttpStatus.OK.value());
            handlePageInfo(pageInfo);
            restResponse.setResult(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            restResponse.setResult(null);
            restResponse.setMsg("分页查询出错");
        }
        return restResponse;
    }

    private void handlePageInfo(PageInfo pageInfo) {
        List<TerminalAccount> list = pageInfo.getList();
        pageInfo.setList(convert2Vo(list));
    }

    private List<Map<String, Object>> convert2Vo(List<TerminalAccount> list) {
        List<Map<String, Object>> infoList = new ArrayList<>();
        for (TerminalAccount terminalAccount : list) {
            Map<String, Object> map = new HashMap<>(8);
            map.put("id", terminalAccount.getTerminalAccountId());
            map.put("username", terminalAccount.getUsername());
            map.put("password", terminalAccount.getPassword());
            int accountType = terminalAccount.getAccountType();
            map.put("accountType", accountType);
            map.put("accountTypeName", dicService.loadItemNameByDicCodeAndItemCode(Dictionary.AccountType.DIC_CODE, accountType));
            SysUser user = userService.findUserById(terminalAccount.getAuditingInfo().getCreatedBy());
            map.put("createdBy", user == null ? "" : user.getRealName());
            map.put("createdDate", terminalAccount.getAuditingInfo().getCreatedTimeStamp());
            map.put("isExpiryCtlOn", terminalAccount.getExpiryCtlInfo().isExpiryCtlOn);
            map.put("expireStartTime", terminalAccount.getExpiryCtlInfo().expiryCtlStartTimeStamp);
            map.put("expireEndTime", terminalAccount.getExpiryCtlInfo().expiryCtlEndTimeStamp);
            infoList.add(map);
        }
        return infoList;
    }

    @EnableIceCacheService(cacheOperation = EnableIceCacheService.CacheOperation.ADD)
    public RestResponse addAccount(TerminalAccount terminalAccount, String expireStartTime, String expireEndTime) {
        terminalAccount.setAuditingInfo(IceUtil.generateAuditingInfo(null, false));
        terminalAccount.setExpiryCtlInfo(IceUtil.generateExpiryCtlInfo(expireStartTime, expireEndTime));
        RestResponse restResponse = new RestResponse();
        try {
            long resultCode = super.addInfo4Ice(terminalAccount, DCTableType.E_TABLE_TERMINAL_ACCOUNT, false);
            restResponse.setCode(HttpStatus.OK.value());
            restResponse.setMsg("添加成功");

            OperationLog log = new OperationLog();
            log.setLogLevel(Dictionary.LogLevel.MID);
            log.setOperationName("新增接入账户");
            log.setContent("新增接入账户[" + terminalAccount.getUsername() + "]信息");
            operationService.addOperationLog(log);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            restResponse.setMsg("添加失败");
        }
        return restResponse;
    }

    public boolean findAccountByUsername(String username) {
        Map<String, Object> queryParams = new HashMap<>(8);
        queryParams.put("eq_accountUsername", username);
        queryParams.put("eq_isDeleted", false);
        return super.isDataExists(queryParams, DCTableType.E_TABLE_TERMINAL_ACCOUNT, TerminalAccount.class);
    }

    public RestResponse loadTerminalAccountById(long id) {
        Map<String, Object> queryParams = new HashMap<>(8);
        queryParams.put("eq_terminalAccountId", id);
        RestResponse restResponse = new RestResponse();
        try {
            PageInfo pageInfo = super.queryPageInfo4Ice(queryParams, 1, 1, DCTableType.E_TABLE_TERMINAL_ACCOUNT,
                    null, TerminalAccount.class);
            restResponse.setCode(HttpStatus.OK.value());
            restResponse.setResult(ObjUtil.isEmpty(pageInfo) ? new TerminalAccount() : convert2Vo(pageInfo.getList()).get(0));
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return restResponse;
    }

    @EnableIceCacheService(cacheOperation = EnableIceCacheService.CacheOperation.MODIFY)
    public RestResponse modifyAccount(TerminalAccount account, String expireStartTime, String expireEndTime) {
        //获取ice接口代理
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        //构造ice接口request
        long[] id = new long[]{account.getTerminalAccountId()};
        DCGetReq getReq = new DCGetReq(RedisUtil.generateHeader(), DCTableType.E_TABLE_TERMINAL_ACCOUNT, false, id, null);
        //根据ID查询地基站信息
        TEEERSDCAPIServant.DCGetResult getResult = servantPrx.DCGet(getReq);
        byte[][] results = getResult.resp.body;
        if (ObjUtil.isEmpty(results)) {
            return RestResponse.error("无效数据");
        }
        //反序列化地基站信息
        InputStream inputStream = new InputStream(servantPrx.ice_getCommunicator(), results[0]);
        inputStream.startEncapsulation();
        TerminalAccount oldAccount = TerminalAccount.ice_read(inputStream);
        inputStream.endEncapsulation();


        BeanUtils.copyProperties(account, oldAccount, new String[]{"range", "auditingInfo"});
        oldAccount.setAuditingInfo(IceUtil.generateAuditingInfo(oldAccount.getAuditingInfo(),false));
        oldAccount.setExpiryCtlInfo(IceUtil.generateExpiryCtlInfo(expireStartTime, expireEndTime));

        RestResponse restResponse = new RestResponse();
        try {
            long resultCode = super.addInfo4Ice(oldAccount, DCTableType.E_TABLE_TERMINAL_ACCOUNT, true);
            restResponse.setCode(HttpStatus.OK.value());
            restResponse.setMsg("更新信息成功");

            OperationLog log = new OperationLog();
            log.setLogLevel(Dictionary.LogLevel.MID);
            log.setOperationName("修改接入账户");
            log.setContent("修改接入账户[" + oldAccount.getUsername() + "]信息");
            operationService.addOperationLog(log);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            restResponse.setMsg("更新信息失败");
        }

        return restResponse;
    }

    @EnableIceCacheService(cacheOperation = EnableIceCacheService.CacheOperation.DELETE)
    public RestResponse deleteTerminalAccount(Long id) {
        int retCode = super.delete4ice(id, DCTableType.E_TABLE_TERMINAL_ACCOUNT);

        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.MID);
        log.setOperationName("删除接入账户");
        log.setContent("删除接入账户[" + id + "]信息");
        operationService.addOperationLog(log);

        return retCode == 500 ? RestResponse.error("删除失败") : RestResponse.success("删除成功");
    }

    @EnableIceCacheService(cacheOperation = EnableIceCacheService.CacheOperation.DELETE)
    public RestResponse deleteTerminalAccountBatch(TerminalIdListVo listVo) {
        List<Long> ids = listVo.getIds();
        for (long id : ids) {
            int retCode = super.delete4ice(id, DCTableType.E_TABLE_TERMINAL_ACCOUNT);
            if (retCode == 500) {
                return RestResponse.error("批量删除失败");
            }

            OperationLog log = new OperationLog();
            log.setLogLevel(Dictionary.LogLevel.HIGH);
            log.setOperationName("删除接入账户");
            log.setContent("删除接入账户[" + id + "]信息");
            operationService.addOperationLog(log);
        }
        return RestResponse.success("批量删除成功");
    }

    public RestResponse<TerminalAccount> loadTerminalAccountByUsername(String username) {
        OrderByPair pair = new OrderByPair();
        pair.setDcColumnType(DCColumnType.E_AUDITINGINFO_CREATED_TIMESTAMP);
        pair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{pair};

        Map<String, Object> queryParams = new HashMap<>(8);
        queryParams.put("eq_accountUsername", username);
        RestResponse restResponse = new RestResponse();
        PageInfo pageInfo = super.queryPageInfo4Ice(queryParams, 1, 1, DCTableType.E_TABLE_TERMINAL_ACCOUNT,
                orderByPairs, TerminalAccount.class);
        restResponse.setCode(HttpStatus.OK.value());
        restResponse.setResult(ObjUtil.isEmpty(pageInfo.getList()) ? new TerminalAccount() : pageInfo.getList().get(0));
        return restResponse;
    }
}
