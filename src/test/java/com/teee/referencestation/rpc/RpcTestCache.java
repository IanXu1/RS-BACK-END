package com.teee.referencestation.rpc;

import com.teee.referencestation.rpc.ice.teee.*;
import com.zeroc.Ice.EndpointSelectionType;

import java.util.UUID;

public class RpcTestCache {

    public static void main(String[] args) {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "ice/config.client", extraArgs)) {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("TEEERSDCAPIServant");
            TEEERSDCAPIServantPrx teeersdcapiServantPrx = TEEERSDCAPIServantPrx.checkedCast(base);
            teeersdcapiServantPrx.ice_connectionCached(false);
            teeersdcapiServantPrx.ice_endpointSelection(EndpointSelectionType.Random);
            if (teeersdcapiServantPrx == null) {
                throw new Error("Invalid proxy");
            }

            RPCHeader rpcHeader = new RPCHeader();
            rpcHeader.reqNo = UUID.randomUUID().toString();
            rpcHeader.reqTimeStamp = System.currentTimeMillis();

            CacheKey cacheKey = new CacheKey();
            cacheKey.cacheBusinessType = CacheBusinessType.E_AUTH;
            cacheKey.uniqueKey = "teee:auth:rs";
            SetCacheReq cacheReq = new SetCacheReq(rpcHeader, cacheKey, 5 * 60000, "test");
            TEEERSDCAPIServant.SetCacheResult setResult = teeersdcapiServantPrx.SetCache(cacheReq);
            System.out.println(setResult.returnValue);

            RPCHeader getRpcHeader = new RPCHeader();
            getRpcHeader.reqNo = UUID.randomUUID().toString();
            getRpcHeader.reqTimeStamp = System.currentTimeMillis();

            CacheKey getCacheKey = new CacheKey();
            getCacheKey.cacheBusinessType = CacheBusinessType.E_AUTH;
            getCacheKey.uniqueKey = "teee:auth:rs";

            GetCacheReq getCacheReq = new GetCacheReq(getRpcHeader, getCacheKey);
            TEEERSDCAPIServant.GetCacheResult getResult = teeersdcapiServantPrx.GetCache(getCacheReq);
            System.out.println(getResult.resp.value);
        }
    }
}
