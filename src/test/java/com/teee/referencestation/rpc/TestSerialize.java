package com.teee.referencestation.rpc;

import com.mysql.cj.util.Base64Decoder;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.rpc.ice.teee.WarningInfo;
import com.teee.referencestation.rpc.ice.util.IceUtil;
import org.junit.Test;

import java.util.Base64;

public class TestSerialize extends BaseService {

    public static void main(String[] args) {
        TestSerialize testSerialize = new TestSerialize();
        testSerialize.test();
    }

    private void test() {
        String str = "wwAAAAEBCAAAAAAAAAAAAQAAAAAAAAAAZwAAAAAAAABkAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALd3JufDB8MTAyfDANjrGfagEAAAAAAAAAAAAAAAAAAAAAAABkAAAAAAAAAAAAAAAAAAAAAAAAAAALd3JufDB8MTAyfDAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        byte[] bytes = Base64.getDecoder().decode(str);

        WarningInfo warningInfo = deserialize4Ice(IceUtil.getApiServantPrx().ice_getCommunicator(), bytes, WarningInfo.class);
        System.out.println(warningInfo);
    }

}
