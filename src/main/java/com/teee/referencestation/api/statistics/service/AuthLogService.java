package com.teee.referencestation.api.statistics.service;

import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.rpc.ice.teee.*;
import com.teee.referencestation.rpc.ice.util.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhanglei
 */
@Service
public class AuthLogService extends BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthLogService.class);

    public RestResponse findAuthLogList(Map<String, Object> queryParams, Integer pageNum, Integer pageSize) {
        //构造排序
        OrderByPair pair = new OrderByPair();
        pair.setDcColumnType(DCColumnType.E_AUTH_LOG_AUTH_TIMESTAMP);
        pair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{pair};

        RestResponse restResponse = new RestResponse();
        try {
            //将string日期字段转换为timestamp
            PageInfo pageInfo = super.queryPageInfoNoCache(queryParams, pageNum, pageSize, DCTableType.E_TABLE_AUTH_LOG,
                    orderByPairs, AuthLog.class);
            restResponse.setCode(HttpStatus.OK.value());
            restResponse.setResult(pageInfo);
        } catch (Exception e) {
            LOGGER.error("AuthLogService Exception", e);
            restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            restResponse.setResult(null);
            restResponse.setMsg("分页查询出错");
        }
        return restResponse;
    }
}
