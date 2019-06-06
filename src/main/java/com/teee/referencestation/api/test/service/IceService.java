package com.teee.referencestation.api.test.service;

import com.teee.referencestation.api.basestation.service.BaseStationService;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.rpc.ice.teee.*;
import com.teee.referencestation.rpc.ice.util.IceUtil;
import com.teee.referencestation.utils.ObjUtil;
import com.teee.referencestation.utils.RedisUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhanglei
 */
@Service
public class IceService extends BaseService {

    /**
     * 地球半径
     */
    private static final double PI_d = 6378.137;
    /**
     * @desc 十边形经纬网
     * @type {[*]}
     */
    private static final double[][] decagon = {
            {0, 0.0094449},
            {0.0055512, 0.00764195},
            {0.0089828, 0.0029183},
            {0.0089828, -0.0029183},
            {0.0055512, -0.00764195},
            {0, -0.0094449},
            {-0.0055512, -0.00764195},
            {-0.0089828, -0.0029183},
            {-0.0089828, 0.0029183},
            {-0.0055512, 0.00764195}

    };


    public RestResponse addBaseStation(Map request) {
        if (ObjUtil.isEmpty(request.get("lng")) || ObjUtil.isEmpty(request.get("lat")) || ObjUtil.isEmpty(request.get("username")) ||
                ObjUtil.isEmpty(request.get("password")) || ObjUtil.isEmpty(request.get("radius"))) {
            return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "参数不完整");
        }
        Double lng = Double.valueOf(request.get("lng").toString());
        Double lat = Double.valueOf(request.get("lat").toString());
        Double radius = Double.valueOf(request.get("radius").toString());
        request.put("name", UUID.randomUUID().toString().replaceAll("-", ""));
        request.put("namePlate", UUID.randomUUID().toString().replaceAll("-", ""));
        request.put("range", calculateRange(radius, lng, lat));
        BaseStationInfo baseStationInfo = BaseStationService.handleParamMap(request);
        baseStationInfo.setAuditingInfo(IceUtil.generateAuditingInfo(null, false));
        long resultCode = super.addInfo4Ice(baseStationInfo, DCTableType.E_TABLE_BASE_STATION_INFO, false);
        return RestResponse.success();
    }

    public RestResponse addTerminal(Map request) {
        if (ObjUtil.isEmpty(request.get("username")) || ObjUtil.isEmpty(request.get("password"))) {
            return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "参数不完整");
        }
        String username = request.get("username").toString();
        String password = request.get("password").toString();
        TerminalAccount terminalAccount = new TerminalAccount();
        terminalAccount.setUsername(username);
        terminalAccount.setPassword(password);
        terminalAccount.setAuditingInfo(IceUtil.generateAuditingInfo(null, false));
        long resultCode = super.addInfo4Ice(terminalAccount, DCTableType.E_TABLE_TERMINAL_ACCOUNT, false);
        return RestResponse.success();
    }

    public RestResponse flushDB() {
        //构造查询条件
        Map<String, Object> queryParams = new HashMap<>(4);
        //构造排序
        OrderByPair orderByPair = new OrderByPair();
        orderByPair.setDcColumnType(DCColumnType.E_AUDITINGINFO_CREATED_TIMESTAMP);
        orderByPair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{orderByPair};

        long[] baseStationIds = super.loadAllIds4Ice(queryParams, DCTableType.E_TABLE_BASE_STATION_INFO, orderByPairs);
        long[] terminalIds = super.loadAllIds4Ice(queryParams, DCTableType.E_TABLE_TERMINAL_ACCOUNT, orderByPairs);

        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        if (baseStationIds.length != 0) {
            for (long baseStationId : baseStationIds) {
                DCDeleteReq stationReq = new DCDeleteReq();
                stationReq.dcTableType = DCTableType.E_TABLE_BASE_STATION_INFO;
                stationReq.rpcHeader = RedisUtil.generateHeader();
                stationReq.deledIds = new long[]{baseStationId};
                TEEERSDCAPIServant.DCDeleteResult stationResult = servantPrx.DCDelete(stationReq);
                if (stationResult.resp.rpcHeader.retCode != 0) {
                    return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "清空地基站表失败");
                }
            }
        }

        if (terminalIds.length != 0) {
            for (long terminalId : terminalIds) {
                DCDeleteReq terminalReq = new DCDeleteReq();
                terminalReq.dcTableType = DCTableType.E_TABLE_TERMINAL_ACCOUNT;
                terminalReq.rpcHeader = RedisUtil.generateHeader();
                terminalReq.deledIds = new long[]{terminalId};
                TEEERSDCAPIServant.DCDeleteResult terminalResult = servantPrx.DCDelete(terminalReq);
                if (terminalResult.resp.rpcHeader.retCode != 0) {
                    return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "清空接入账户失败");
                }
            }
        }

        return RestResponse.success("清空成功");
    }


    private String calculateRange(double radius, double lng, double lat) {
        String range = "";
        for (int i = 0; i < decagon.length; i++) {
            double newLng = lng + decagon[i][0] * radius / Math.cos((lat / 180) * PI_d);
            double newLat = lat + decagon[i][1] * radius;
            range += String.format("%.7f", newLng) + ',' + String.format("%.7f", newLat) + " ";
        }
        return range;
    }
}
