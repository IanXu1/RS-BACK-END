package com.teee.referencestation.rpc;

import com.teee.referencestation.rpc.ice.teee.BaseStationInfo;
import com.teee.referencestation.rpc.ice.teee.Position;
import com.teee.referencestation.rpc.ice.util.PageInfo;

import java.util.*;

public class TestPageInfo {

    public static void main(String[] args) {
        List<BaseStationInfo> lists = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            BaseStationInfo baseStationInfo = new BaseStationInfo();
            baseStationInfo.setPassword("123");
            baseStationInfo.setUsername("123");
            baseStationInfo.setPosition(new Position(0, 0, 0));
            lists.add(baseStationInfo);
        }

        int pageNum = 3;
        int pageSize = 20;
        int total = 55;
        PageInfo<BaseStationInfo> pageInfo = new PageInfo<>(pageNum, pageSize, total);
        pageInfo.initPageInfo(lists);

        System.out.println(pageInfo);
    }



}
