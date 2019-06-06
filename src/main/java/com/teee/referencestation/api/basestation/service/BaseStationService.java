package com.teee.referencestation.api.basestation.service;

import com.teee.referencestation.api.basestation.model.MapPosition;
import com.teee.referencestation.api.basestation.model.StationOptionVo;
import com.teee.referencestation.api.statistics.model.OperationLog;
import com.teee.referencestation.api.statistics.service.OperationLogService;
import com.teee.referencestation.api.upgrade.model.UpgradeVersion;
import com.teee.referencestation.api.upgrade.service.UpgradeVersionService;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.cache.EnableIceCacheService;
import com.teee.referencestation.common.constant.Dictionary;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.rpc.ice.teee.*;
import com.teee.referencestation.rpc.ice.util.IceUtil;
import com.teee.referencestation.rpc.ice.util.PageInfo;
import com.teee.referencestation.utils.JsonUtil;
import com.teee.referencestation.utils.ObjUtil;
import com.teee.referencestation.utils.RedisUtil;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.InputStream;
import io.micrometer.core.instrument.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;

/**
 * @author zhanglei
 * @date 2019-1-21 17:56:24
 */
@Service
public class BaseStationService extends BaseService {

    @Autowired
    private OperationLogService operationService;
    @Autowired
    private UpgradeVersionService versionService;

    private static final Logger logger = LoggerFactory.getLogger(BaseStationService.class);

    @Autowired
    private Counter baseStationCounter;

    /**
     * 由于ice接入端要求，第一次查询idList，之后根据id查询数据
     * 1.根据当前用户id和查询条件md5为key缓存当前查询条件下的idList
     * 2.给缓存设置过期时间，如果缓存不存在就从新调用ice接口获取idList
     * 3.如果查询条件变化，从新调用ice接口获取idLost
     *
     * @param queryParams
     * @param pageNum
     * @param pageSize
     * @return
     */
    public RestResponse findStationList(Map<String, Object> queryParams, Integer pageNum, Integer pageSize) {
        baseStationCounter.increment();
        //构造排序
        OrderByPair pair = new OrderByPair();
        pair.setDcColumnType(DCColumnType.E_AUDITINGINFO_CREATED_TIMESTAMP);
        pair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{pair};
        // 过滤被删除的数据
        queryParams.put("eq_isDeleted", false);

        PageInfo pageInfo = super.queryPageInfo4Ice(queryParams, pageNum, pageSize, DCTableType.E_TABLE_BASE_STATION_INFO,
                orderByPairs, BaseStationInfo.class);
        handlePageInfo(pageInfo);
        return RestResponse.success(pageInfo);
    }

    private void handlePageInfo(PageInfo pageInfo) {
        List<BaseStationInfo> list = pageInfo.getList();
        pageInfo.setList(convert2Vo(list));
    }

    private List<Map<String, Object>> convert2Vo(List<BaseStationInfo> list) {
        List<Map<String, Object>> infoList = new ArrayList<>();
        for (BaseStationInfo baseStationInfo : list) {
            Map<String, Object> map = new HashMap<>(8);
            map.put("id", baseStationInfo.getBaseStationInfoId());
            map.put("baseStationInfoId", baseStationInfo.getBaseStationInfoId());
            map.put("name", baseStationInfo.getName());
            map.put("username", baseStationInfo.getUsername());
            map.put("password", baseStationInfo.getPassword());
            map.put("namePlate", baseStationInfo.getNamePlate());
            map.put("alt", baseStationInfo.getPosition() == null ? "" : formatDouble(baseStationInfo.getPosition().getAltitude()));
            map.put("lng", baseStationInfo.getPosition() == null ? "" : formatDouble(baseStationInfo.getPosition().getLongitude()));
            map.put("lat", baseStationInfo.getPosition() == null ? "" : formatDouble(baseStationInfo.getPosition().getLatitude()));
            Position[] positions = baseStationInfo.getRange();
            String range = "";
            for (Position position : positions) {
                range += " " + formatDouble(position.getLongitude()) + "," + formatDouble(position.getLatitude());
            }
            map.put("range", range.trim());
            map.put("isExpiryCtlOn", baseStationInfo.getExpiryCtlInfo().isExpiryCtlOn);
            map.put("expireStartTime", baseStationInfo.getExpiryCtlInfo().expiryCtlStartTimeStamp);
            map.put("expireEndTime", baseStationInfo.getExpiryCtlInfo().expiryCtlEndTimeStamp);
            infoList.add(map);
        }
        return infoList;
    }

    private static String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置保留多少位小数
        nf.setMaximumFractionDigits(20);
        // 取消科学计数法
        nf.setGroupingUsed(false);
        //返回结果
        return nf.format(d);
    }

    @EnableIceCacheService(cacheOperation = EnableIceCacheService.CacheOperation.ADD)
    public RestResponse addStation(Map<String, Object> param) {
        BaseStationInfo baseStationInfo = handleParamMap(param);
        baseStationInfo.setAuditingInfo(IceUtil.generateAuditingInfo(null, false));
        try {
            long resultCode = super.addInfo4Ice(baseStationInfo, DCTableType.E_TABLE_BASE_STATION_INFO, false);

            //记录操作日志
            OperationLog log = new OperationLog();
            log.setLogLevel(Dictionary.LogLevel.MID);
            log.setOperationName("新增地基站");
            log.setContent("新增地基站[" + baseStationInfo.getName() + "]信息");
            operationService.addOperationLog(log);

            return RestResponse.success("添加成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return RestResponse.success("添加失败");
        }
    }

    public static BaseStationInfo handleParamMap(Map<String, Object> param) {
        BaseStationInfo baseStationInfo = new BaseStationInfo();
        baseStationInfo.setBaseStationInfoId(param.get("id") == null ? 0 : Integer.valueOf(param.get("id").toString()));
        baseStationInfo.setName(param.get("name").toString());
        baseStationInfo.setUsername(param.get("username").toString());
        baseStationInfo.setPassword(param.get("password").toString());
        baseStationInfo.setNamePlate(param.get("namePlate").toString());
        Position position = new Position();
        position.setAltitude(Double.valueOf(ObjUtil.isEmpty(param.get("alt")) ? "0" : param.get("alt").toString()));
        position.setLongitude(Double.valueOf(param.get("lng").toString()));
        position.setLatitude(Double.valueOf(param.get("lat").toString()));
        baseStationInfo.setPosition(position);

        String range = param.get("range").toString();
        String[] arr = range.split(" ");
        List<Position> positions = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (ObjUtil.isEmpty(arr[i]) || !arr[i].contains(",")) {
                continue;
            }
            String[] lngLat = arr[i].split(",");
            Position pos = new Position();
            pos.setLongitude(Double.valueOf(lngLat[0]));
            pos.setLatitude(Double.valueOf(lngLat[1]));
            positions.add(pos);
        }
        baseStationInfo.setRange(positions.toArray(new Position[positions.size()]));

        //设置有效期
        baseStationInfo.setExpiryCtlInfo(IceUtil.generateExpiryCtlInfo(
                ObjUtil.isNotEmpty(param.get("expireStartTime")) ? param.get("expireStartTime").toString() : "",
                ObjUtil.isNotEmpty(param.get("expireEndTime")) ? param.get("expireEndTime").toString() : ""
        ));

        return baseStationInfo;
    }

    /**
     * 根据前台传入的信息更新地基站
     *
     * @param params
     * @return
     */
    @EnableIceCacheService(cacheOperation = EnableIceCacheService.CacheOperation.MODIFY)
    public RestResponse modifyStation(Map<String, Object> params) {
        BaseStationInfo baseStationInfo = handleParamMap(params);
        //获取ice接口代理
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        //构造ice接口request
        long[] id = new long[]{baseStationInfo.getBaseStationInfoId()};
        DCGetReq getReq = new DCGetReq(RedisUtil.generateHeader(), DCTableType.E_TABLE_BASE_STATION_INFO, false, id, null);
        //根据ID查询地基站信息
        TEEERSDCAPIServant.DCGetResult getResult = servantPrx.DCGet(getReq);
        byte[][] results = getResult.resp.body;
        if (ObjUtil.isEmpty(results)) {
            return RestResponse.error("无效数据");
        }
        //反序列化地基站信息
        InputStream inputStream = new InputStream(servantPrx.ice_getCommunicator(), results[0]);
        inputStream.startEncapsulation();
        BaseStationInfo oldBaseStation = BaseStationInfo.ice_read(inputStream);
        inputStream.endEncapsulation();


        BeanUtils.copyProperties(baseStationInfo, oldBaseStation, new String[]{"range", "auditingInfo"});
        oldBaseStation.setRange(baseStationInfo.getRange());
        oldBaseStation.setAuditingInfo(IceUtil.generateAuditingInfo(oldBaseStation.getAuditingInfo(), false));
        oldBaseStation.setExpiryCtlInfo(baseStationInfo.getExpiryCtlInfo());

        try {
            long resultCode = super.addInfo4Ice(oldBaseStation, DCTableType.E_TABLE_BASE_STATION_INFO, true);

            //记录操作日志
            OperationLog log = new OperationLog();
            log.setLogLevel(Dictionary.LogLevel.MID);
            log.setOperationName("修改地基站");
            log.setContent("修改地基站[" + baseStationInfo.getName() + "]信息");
            operationService.addOperationLog(log);

            return RestResponse.success("更新信息成功");
        } catch (Exception e) {
            logger.error("BaseStationService", e);
            ;
            return RestResponse.error("更新信息失败");
        }
    }

    /**
     * 通过地基站名字查询地基站信息
     *
     * @param name
     * @return
     */
    public boolean findBaseStationByName(String name) {
        Map<String, Object> queryParams = new HashMap<>(8);
        queryParams.put("eq_stationName", name);
        queryParams.put("eq_isDeleted", false);
        return isDataExists(queryParams, DCTableType.E_TABLE_BASE_STATION_INFO, BaseStationInfo.class);
    }

    public boolean findBaseStationByUsername(String username) {
        Map<String, Object> queryParams = new HashMap<>(8);
        queryParams.put("eq_stationUsername", username);
        queryParams.put("eq_isDeleted", false);
        return isDataExists(queryParams, DCTableType.E_TABLE_BASE_STATION_INFO, BaseStationInfo.class);
    }

    public RestResponse loadBaseStationById(long id) {
        Map<String, Object> queryParams = new HashMap<>(8);
        queryParams.put("eq_stationId", id);
        RestResponse restResponse = new RestResponse();
        PageInfo pageInfo = super.queryPageInfo4Ice(queryParams, 1, 1, DCTableType.E_TABLE_BASE_STATION_INFO,
                null, BaseStationInfo.class);
        restResponse.setCode(HttpStatus.OK.value());
        restResponse.setResult(ObjUtil.isEmpty(pageInfo.getList()) ? JsonUtil.vo2map(new BaseStationInfo()) : convert2Vo(pageInfo.getList()).get(0));
        return restResponse;
    }

    public BaseStationInfo findById(long id) {
        Map<String, Object> queryParams = new HashMap<>(8);
        queryParams.put("eq_stationId", id);
        PageInfo pageInfo = super.queryPageInfo4Ice(queryParams, 1, 1, DCTableType.E_TABLE_BASE_STATION_INFO,
                null, BaseStationInfo.class);
        return ObjUtil.isEmpty(pageInfo) ? new BaseStationInfo() : (BaseStationInfo) pageInfo.getList().get(0);
    }

    @EnableIceCacheService(cacheOperation = EnableIceCacheService.CacheOperation.DELETE)
    public RestResponse deleteBaseStation(Long id) {
        int retCode = super.delete4ice(id, DCTableType.E_TABLE_BASE_STATION_INFO);

        //记录操作日志
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.HIGH);
        log.setOperationName("删除地基站");
        log.setContent("删除地基站[" + id + "]信息");
        operationService.addOperationLog(log);

        return retCode == 500 ? RestResponse.error("删除失败") : RestResponse.success("删除成功");

    }

    public List<BaseStationInfo> loadAvailableStation() {
        //构造查询条件
        Map<String, Object> queryParams = new HashMap<>(4);
        queryParams.put("eq_isDeleted", false);

        //构造排序
        OrderByPair orderByPair = new OrderByPair();
        orderByPair.setDcColumnType(DCColumnType.E_AUDITINGINFO_CREATED_TIMESTAMP);
        orderByPair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{orderByPair};

        try {
            return super.loadAllAvailable4Ice(queryParams, DCTableType.E_TABLE_BASE_STATION_INFO,
                    orderByPairs, BaseStationInfo.class);
        } catch (Exception e) {
            logger.error("BaseStationService", e);
            return Collections.emptyList();
        }
    }

    public List<StationOptionVo> loadAllStationOption() {
        List<StationOptionVo> optionVoList = new ArrayList<>();
        List<BaseStationInfo> stationInfoList = loadAvailableStation();
        for (BaseStationInfo baseStationInfo : stationInfoList) {
            StationOptionVo vo = new StationOptionVo();
            vo.setId(baseStationInfo.getBaseStationInfoId());
            vo.setName(baseStationInfo.getName());
            vo.setDisabled(false);
            optionVoList.add(vo);
        }
        return optionVoList;
    }

    /**
     * @return
     * @desc 获取所有地基站和接入端的位置信息
     */
    public RestResponse loadAllPositions() {
        //获取ice接口代理
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        //构造地基站请求
        DCGetAccountStatusReq bsReq = new DCGetAccountStatusReq();
        bsReq.mode = GetAccountStatusMode.E_GETACCOUNTSTATUSMODE_ALL_BS;
        bsReq.ids = new long[0];
        DCGetAccountStatusResp bsResp = queryPosition4Ice(servantPrx, super.serialize4Ice(servantPrx.ice_getCommunicator(), bsReq));

        List<BaseStationInfo> baseStationInfos = loadDataByIds(servantPrx, DCTableType.E_TABLE_BASE_STATION_INFO, bsResp, BaseStationInfo.class);

        List<MapPosition> positionList = new ArrayList<>();
        if (ObjUtil.isNotEmpty(bsResp) && ObjUtil.isNotEmpty(baseStationInfos)) {
            for (DCAccoutStatus dcAccoutStatus : bsResp.dcAccoutStatusSeq) {
                if (!dcAccoutStatus.dcPostionStatus.isValid) {
                    continue;
                }
                MapPosition position = new MapPosition();
                position.setId(dcAccoutStatus.idInfo.uuid);
                UpgradeVersion currentVersion = versionService.findLatestVersionByTerminalId(dcAccoutStatus.idInfo.uuid);
                if (ObjUtil.isNotEmpty(currentVersion)) {
                    position.setCurrentVersion(currentVersion.getVersionNum());
                } else {
                    UpgradeVersion initVersion = versionService.findInitVersion();
                    position.setCurrentVersion(initVersion.getVersionNum());
                }
                position.setType(IDType.E_IDTYPE_BS.value());
                position.setValid(dcAccoutStatus.dcPostionStatus.isValid);
                position.setLng(dcAccoutStatus.dcPostionStatus.latestPosition.longitude);
                position.setLat(dcAccoutStatus.dcPostionStatus.latestPosition.latitude);
                Optional<BaseStationInfo> opBs = baseStationInfos.stream().filter(s -> s.getBaseStationInfoId() == dcAccoutStatus.idInfo.uuid).findFirst();
                position.setName(opBs.get().getName());
                position.setUsername(opBs.get().getUsername());
                position.setLatestReportTime(dcAccoutStatus.dcPostionStatus.latestReportPostionTime);
                position.setScpStatus(dcAccoutStatus.loginSatusLscp);
                position.setNtripStatus(dcAccoutStatus.loginSatusNtrip);
                position.setFaultStatus(dcAccoutStatus.getDcFatalFaultStatus());
                positionList.add(position);
            }
        }
        return RestResponse.success(positionList);
    }

    public <T> List<T> loadDataByIds(TEEERSDCAPIServantPrx servantPrx, DCTableType type, DCGetAccountStatusResp resp, Class<T> cls) {
        try {
            List<DCAccoutStatus> list = new ArrayList<>(Arrays.asList(resp.dcAccoutStatusSeq));
            long[] ids = list.stream().mapToLong(s -> s.idInfo.getUuid()).toArray();
            return super.loadAllDataByIds(servantPrx, ids, type, cls);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public DCGetAccountStatusResp queryPosition4Ice(TEEERSDCAPIServantPrx servantPrx, byte[] body) {
        DCExtdPack specialPack = new DCExtdPack();
        specialPack.setRpcHeader(RedisUtil.generateHeader());
        specialPack.setDcExtdCmd(DCExtdCmd.E_CS_GET_ACCOUNT_STATUS);
        specialPack.setBody(body);

        TEEERSDCAPIServant.HandlerDCExtdPackResult result = servantPrx.HandlerDCExtdPack(specialPack);

        return super.deserialize4Ice(servantPrx.ice_getCommunicator(), result.resp.body, DCGetAccountStatusResp.class);
    }


    public RestResponse getPositionByName(Map request) {
        //获取ice接口代理
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        try {
            DCGetAccountStatusResp statusResp = queryPosition4Ice(servantPrx, convertAccountReq(request, servantPrx.ice_getCommunicator()));
            return ObjUtil.isNotEmpty(statusResp) ? RestResponse.success(statusResp.dcAccoutStatusSeq) :
                    RestResponse.error("没有对应的结果");
        } catch (Exception e) {
            logger.error("BaseStationService", e);
            return RestResponse.error("获取终端状态失败");
        }
    }

    private byte[] convertAccountReq(Map queryParams, Communicator communicator) {
        //构造排序
        OrderByPair orderByPair = new OrderByPair();
        orderByPair.setDcColumnType(DCColumnType.E_AUDITINGINFO_CREATED_TIMESTAMP);
        orderByPair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{orderByPair};
        //构造请求
        DCGetAccountStatusReq req = new DCGetAccountStatusReq();
        req.mode = GetAccountStatusMode.E_GETACCOUNTSTATUSMODE_CUSTOM;
        try {
            //构造查询条件
            queryParams.put("eq_isDeleted", false);
            String stationName = queryParams.get("like_stationName").toString().trim();
            queryParams.put("like_stationName", stationName);
            //查询数据
            List<BaseStationInfo> baseStationInfoList = super.loadAllAvailable4Ice(queryParams, DCTableType.E_TABLE_BASE_STATION_INFO,
                    orderByPairs, BaseStationInfo.class);
            if (ObjUtil.isNotEmpty(baseStationInfoList)) {
                req.ids = new long[]{baseStationInfoList.get(0).getBaseStationInfoId()};
            }
            return super.serialize4Ice(communicator, req);
        } catch (Exception e) {
            req.ids = new long[]{0};
            return super.serialize4Ice(communicator, req);
        }
    }

    @EnableIceCacheService(cacheOperation = EnableIceCacheService.CacheOperation.DELETE)
    public RestResponse deleteBaseStationBatch(Map<String, Object> param) {
        List<Integer> ids = (List) param.get("ids");
        for (Integer id : ids) {
            int retCode = super.delete4ice(id, DCTableType.E_TABLE_BASE_STATION_INFO);
            if (retCode == 500) {
                return RestResponse.error("批量删除失败");
            }
            //记录操作日志
            OperationLog log = new OperationLog();
            log.setLogLevel(Dictionary.LogLevel.HIGH);
            log.setOperationName("删除地基站");
            log.setContent("删除地基站[" + id + "]信息");
            operationService.addOperationLog(log);
        }
       return RestResponse.success("批量删除成功");
    }

    public RestResponse<BaseStationInfo> loadBaseStationByUsername(String username) {
        //构造排序
        OrderByPair pair = new OrderByPair();
        pair.setDcColumnType(DCColumnType.E_AUDITINGINFO_CREATED_TIMESTAMP);
        pair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{pair};

        Map<String, Object> queryParams = new HashMap<>(8);
        queryParams.put("eq_stationUsername", username);
        RestResponse restResponse = new RestResponse();
        PageInfo pageInfo = super.queryPageInfo4Ice(queryParams, 1, 1, DCTableType.E_TABLE_BASE_STATION_INFO,
                orderByPairs, BaseStationInfo.class);
        restResponse.setCode(HttpStatus.OK.value());
        restResponse.setResult(ObjUtil.isEmpty(pageInfo.getList()) ? new BaseStationInfo() : pageInfo.getList().get(0));
        return restResponse;
    }
}
