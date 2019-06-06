package com.teee.referencestation.rpc;


import com.teee.referencestation.ReferenceStationApplication;
import com.teee.referencestation.api.basestation.service.BaseStationService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.rpc.ice.teee.*;
import com.teee.referencestation.rpc.ice.util.IceUtil;
import com.teee.referencestation.rpc.ice.util.PageInfo;
import com.teee.referencestation.utils.RedisUtil;
import com.zeroc.Ice.InputStream;
import com.zeroc.Ice.InvocationTimeoutException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReferenceStationApplication.class)
public class TestIceCache {

    @Autowired
    private BaseStationService baseStationService;

    @Test
    public void testAdd() {
//        AuditingInfo auditingInfo = new AuditingInfo();
//        auditingInfo.setIsDeleted(false);
//        auditingInfo.setCreatedBy(1);
//
//        Position p1 = new Position(1, 1, 1);
//        Position p2 = new Position(1, 1, 1);
//        Position p3 = new Position(1, 1, 1);
//        Position[] positions = {p1, p2, p3};
//        for (int i = 1501; i < 1601; i++) {
//            BaseStationInfo info = new BaseStationInfo();
//            info.setName("" + i);
//            info.setUsername("" + i);
//            info.setPassword("" + i);
//            info.setPosition(p1);
//            info.setRange(positions);
//            auditingInfo.setCreatedTimeStamp(System.currentTimeMillis());
//            info.setAuditingInfo(auditingInfo);
//
//            baseStationService.addStation(info);
//        }
    }

    @Test
    public void testGet() {
        //构造排序
        OrderByPair pair = new OrderByPair();
        pair.setDcColumnType(DCColumnType.E_BASE_STATION_INFO_USERNAME);
        pair.setOrderByMode(OrderByMode.E_ORDERBYMODE_ASC);
        OrderByPair[] pairs = new OrderByPair[]{pair};
        //
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("eq_isDeleted", 0);
        PageQueryCond queryCond = new PageQueryCond(true, 10000,
                IceUtil.generateWhereCond(queryMap), pairs, null);
        DCPagedQueryReq pagedQueryReq = new DCPagedQueryReq(RedisUtil.generateHeader(), DCTableType.E_TABLE_BASE_STATION_INFO, queryCond);
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        TEEERSDCAPIServant.DCPagedQueryResult queryResult = servantPrx.DCPagedQuery(pagedQueryReq);
        int total = queryResult.resp.pagedQueyResult.total;

        //构造id list请求
        DCGetReq dcGetReq = new DCGetReq();
        dcGetReq.dcTableType = DCTableType.E_TABLE_BASE_STATION_INFO;
        dcGetReq.ids = Arrays.copyOfRange(queryResult.resp.pagedQueyResult.idList, 0 , 20);
        dcGetReq.rpcHeader = RedisUtil.generateHeader();

        List<BaseStationInfo> stationInfoList = new ArrayList<>();
        TEEERSDCAPIServant.DCGetResult getResult = servantPrx.DCGet(dcGetReq);
        byte[][] getBody = getResult.resp.body;
        for (byte[] bytes : getBody) {
            InputStream inputStream = new InputStream(servantPrx.ice_getCommunicator(), bytes);
            inputStream.startEncapsulation();
            BaseStationInfo baseStationInfo = BaseStationInfo.ice_read(inputStream);
            inputStream.endEncapsulation();
            stationInfoList.add(baseStationInfo);
        }
        //构造mybatis分页数据PageInfo
        PageInfo<BaseStationInfo> pageInfo = new PageInfo<>(1, 20, Integer.valueOf(String.valueOf(total)));
        pageInfo.initPageInfo(stationInfoList);
        System.out.println(pageInfo);
    }

    @Test
    public void testPagination() throws Exception {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("eq_isDeleted", 0);
        RestResponse restResponse = baseStationService.findStationList(queryMap, 1, 20);
        System.out.println(restResponse);
    }

    @Test
    public void testGetApi() {
        StopWatch watch = new StopWatch();
        watch.start("task");
        try{
            IceUtil.getApiServantPrx();
        } catch (InvocationTimeoutException e) {
            watch.stop();
            System.out.println(watch.prettyPrint());
        }

    }
}
