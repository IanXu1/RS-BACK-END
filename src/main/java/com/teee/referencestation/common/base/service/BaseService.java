package com.teee.referencestation.common.base.service;

import com.teee.referencestation.common.cache.EnableIceCacheService;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.rpc.ice.teee.*;
import com.teee.referencestation.rpc.ice.util.IceUtil;
import com.teee.referencestation.rpc.ice.util.PageInfo;
import com.teee.referencestation.utils.ObjUtil;
import com.teee.referencestation.utils.RedisUtil;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.InputStream;
import com.zeroc.Ice.OutputStream;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service基类
 * <p>
 * 1、统一实现分页方法；
 * 2、暴露SqlSessionTemplate对象，提供给子类执行SQL调用。
 *
 * @author LDB
 * @date 2018/1/08 10:06
 */
@Slf4j
@NoArgsConstructor
public class BaseService {

    /**
     * 注入一个SqlSessionTemplate，用于执行SQL
     */
    @Autowired
    protected SqlSessionTemplate session;


    public <T> PageInfo<T> queryPageInfo4Ice(Map<String, Object> queryParams, Integer pageNum, Integer pageSize, DCTableType type,
                                             OrderByPair[] pairs, Class<T> cls) {

        //根据条件查询idList
        TEEERSDCAPIServant.DCPagedQueryResult result = ((BaseService) AopContext.currentProxy()).getIdLists(queryParams,
                pageNum, pageSize, type, pairs);

        if (result == null) {
            //构造mybatis分页数据PageInfo
            PageInfo<T> pageInfo = new PageInfo<>(1, pageSize, 0);
            pageInfo.initPageInfo(Collections.emptyList());
            return pageInfo;
        }

        //构造id list请求
        DCGetReq dcGetReq = new DCGetReq();
        dcGetReq.dcTableType = type;
        dcGetReq.ids = result.resp.pagedQueyResult.idList;
        dcGetReq.rpcHeader = RedisUtil.generateHeader();
        int total = result.resp.pagedQueyResult.total;

        //page reasonable
        int pages = (total / pageSize) + 1;
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageNum > pages) {
            pageNum = pages;
        }

        //获取ice代理对象
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        //根据id list查询实际数据
        TEEERSDCAPIServant.DCGetResult getResult = servantPrx.DCGet(dcGetReq);
        //获取序列化后的数据
        byte[][] getBody = getResult.resp.body;
        //反序列化数据源
        List<T> entityList = new ArrayList<>();
        for (byte[] bytes : getBody) {
            entityList.add(deserialize4Ice(servantPrx.ice_getCommunicator(), bytes, cls));
        }

        // 遍历查询出的结果集，过滤掉已经删除的数据
        entityList = filterDeleteData(entityList, pageSize);

        //构造mybatis分页数据PageInfo
        PageInfo<T> pageInfo = new PageInfo<>(pageNum, pageSize, total);
        pageInfo.initPageInfo(entityList);
        return pageInfo;
    }


    public <T> PageInfo<T> queryPageInfoNoCache(Map<String, Object> queryParams, Integer pageNum, Integer pageSize, DCTableType type,
                                                OrderByPair[] pairs, Class<T> cls) {

        //获取ice代理对象
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        //第一次查询获取total
        PageQueryCond firstQueryCond = new PageQueryCond(true, 0,
                IceUtil.generateWhereCond(queryParams), pairs, null);
        DCPagedQueryReq firstPagedQueryReq = new DCPagedQueryReq(RedisUtil.generateHeader(), type, firstQueryCond);
        TEEERSDCAPIServant.DCPagedQueryResult queryResult = servantPrx.DCPagedQuery(firstPagedQueryReq);
        log.info("query id list response rpcHeader: {}", queryResult.resp.rpcHeader);

        int total = queryResult.resp.pagedQueyResult.total;
        if (total == 0) {
            //构造mybatis分页数据PageInfo
            PageInfo<T> pageInfo = new PageInfo<>(1, pageSize, 0);
            pageInfo.initPageInfo(Collections.emptyList());
            return pageInfo;
        }

        //page reasonable
        int pages = (total / pageSize) + 1;
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageNum > pages) {
            pageNum = pages;
        }

        //第二次查询获取真正的id list
        long[] limits = new long[]{(pageNum - 1) * pageSize, 2 * pageSize};
        //构造分页查询条件
        PageQueryCond queryCond = new PageQueryCond(false, 0,
                IceUtil.generateWhereCond(queryParams), pairs, limits);
        //构造分页查询请求信息
        DCPagedQueryReq pagedQueryReq = new DCPagedQueryReq(RedisUtil.generateHeader(), type, queryCond);
        TEEERSDCAPIServant.DCPagedQueryResult result = servantPrx.DCPagedQuery(pagedQueryReq);
        //isFirstBatch为false的时候，不返回total，需要将第一次查询的total赋值回去
        result.resp.pagedQueyResult.total = total;

        //构造id list请求
        DCGetReq dcGetReq = new DCGetReq();
        dcGetReq.dcTableType = type;
        dcGetReq.ids = result.resp.pagedQueyResult.idList;
        dcGetReq.rpcHeader = RedisUtil.generateHeader();

        //根据id list查询实际数据
        TEEERSDCAPIServant.DCGetResult getResult = servantPrx.DCGet(dcGetReq);
        //获取序列化后的数据
        byte[][] getBody = getResult.resp.body;
        //反序列化数据源
        List<T> entityList = new ArrayList<>();
        for (byte[] bytes : getBody) {
            entityList.add(deserialize4Ice(servantPrx.ice_getCommunicator(), bytes, cls));
        }

        // 遍历查询出的结果集，过滤掉已经删除的数据
        entityList = filterDeleteData(entityList, pageSize);

        //构造mybatis分页数据PageInfo
        PageInfo<T> pageInfo = new PageInfo<>(pageNum, pageSize, total);
        pageInfo.initPageInfo(entityList);
        return pageInfo;
    }

    private <T> List<T> filterDeleteData(List<T> entityList, int pageSize) {
        return entityList.stream().filter(s -> {
            Field field = null;
            AuditingInfo auditingInfo = null;
            try {
                field = s.getClass().getField("auditingInfo");
                auditingInfo = (AuditingInfo) field.get(s);
            } catch (Exception e) {
                //ignore exception
                return true;
            }
            //过滤被删除的数据
            return !auditingInfo.isIsDeleted();
        }).limit(pageSize).collect(Collectors.toList());
    }

    @EnableIceCacheService(cacheOperation = EnableIceCacheService.CacheOperation.QUERY)
    public TEEERSDCAPIServant.DCPagedQueryResult getIdLists(Map<String, Object> queryParams, Integer pageNum, Integer pageSize,
                                                            DCTableType type, OrderByPair[] pairs) {
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();

        //第一次查询获取total
        PageQueryCond firstQueryCond = new PageQueryCond(true, 0,
                IceUtil.generateWhereCond(queryParams), pairs, null);
        DCPagedQueryReq firstPagedQueryReq = new DCPagedQueryReq(RedisUtil.generateHeader(), type, firstQueryCond);
        TEEERSDCAPIServant.DCPagedQueryResult queryResult = servantPrx.DCPagedQuery(firstPagedQueryReq);
        log.info("query id list response rpcHeader: {}", queryResult.resp.rpcHeader);

        int total = queryResult.resp.pagedQueyResult.total;
        if (total == 0) {
            return null;
        }

        //page reasonable
        int pages = (total / pageSize) + 1;
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageNum > pages) {
            pageNum = pages;
        }
        //将id list分区，每个分区10000条
        int partitions = total / Constant.ICE_CACHE.PAGINATION;
        //起始点在哪个分区
        int partitionIndex = (pageNum - 1) * pageSize / Constant.ICE_CACHE.PAGINATION;
        //终点在哪个分区
        int partitionEnd = pageNum * pageSize / Constant.ICE_CACHE.PAGINATION;
        //第二次查询获取真正的id list
        long[] limits = new long[]{0, 0};
        if (partitionIndex == partitionEnd) {
            //在同一个分区的情况下
            limits = new long[]{partitionIndex * Constant.ICE_CACHE.PAGINATION, Constant.ICE_CACHE.PAGINATION};
        } else if (partitionEnd > partitionIndex) {
            //在两个分区的情况下
            limits = new long[]{partitionIndex * Constant.ICE_CACHE.PAGINATION, Constant.ICE_CACHE.PAGINATION * 2};
        }
        //构造分页查询条件
        PageQueryCond queryCond = new PageQueryCond(false, 0,
                IceUtil.generateWhereCond(queryParams), pairs, limits);
        //构造分页查询请求信息
        DCPagedQueryReq pagedQueryReq = new DCPagedQueryReq(RedisUtil.generateHeader(), type, queryCond);
        TEEERSDCAPIServant.DCPagedQueryResult result = servantPrx.DCPagedQuery(pagedQueryReq);
        //isFirstBatch为false的时候，不返回total，需要将第一次查询的total赋值回去
        result.resp.pagedQueyResult.total = total;
        return result;
    }

    public <T> long addInfo4Ice(T entity, DCTableType type, boolean isUpdate) {
        //获取ice接口代理
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        //序列化地基站信息
        byte[] bytes = serialize4Ice(servantPrx.ice_getCommunicator(), entity);
        //构造新增数据request
        DCCreateReq dcCreateReq = new DCCreateReq(RedisUtil.generateHeader(), type, isUpdate, bytes);
        TEEERSDCAPIServant.DCCreateResult dcCreateResult = servantPrx.DCCreate(dcCreateReq);

        return isUpdate ? dcCreateResult.resp.rpcHeader.retCode : dcCreateResult.resp.rpcHeader.retCode == 0 ?
                dcCreateResult.resp.addedId : dcCreateResult.resp.rpcHeader.retCode;
    }

    public <T> boolean isDataExists(Map<String, Object> queryParams, DCTableType type, Class<T> cls) {
        PageInfo pageInfo = queryPageInfoNoCache(queryParams, 1, 1, type,
                null, cls);
        if (ObjUtil.isEmpty(pageInfo) || pageInfo.getTotal() < 1) {
            return true;
        } else {
            return false;
        }
    }

    public <T> List<T> loadAllAvailable4Ice(Map<String, Object> queryParams, DCTableType type, OrderByPair[] pairs, Class<T> cls)
            throws Exception {

        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();

        return loadAllDataByIds(servantPrx, loadAllIds4Ice(queryParams, type, pairs), type, cls);
    }

    public long[] loadAllIds4Ice(Map<String, Object> queryParams, DCTableType type, OrderByPair[] pairs) {

        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();

        //第一次查询获取total
        PageQueryCond firstQueryCond = new PageQueryCond(true, 0,
                IceUtil.generateWhereCond(queryParams), pairs, null);
        DCPagedQueryReq firstPagedQueryReq = new DCPagedQueryReq(RedisUtil.generateHeader(), type, firstQueryCond);
        TEEERSDCAPIServant.DCPagedQueryResult queryResult = servantPrx.DCPagedQuery(firstPagedQueryReq);
        log.info("query id list response rpcHeader: {}", queryResult.resp.rpcHeader);

        int total = queryResult.resp.pagedQueyResult.total;
        if (total == 0) {
            return new long[]{};
        }
        //第二次查询获取真正的id list
        long[] limits = new long[]{0, total};
        //构造分页查询条件
        PageQueryCond queryCond = new PageQueryCond(false, 0,
                IceUtil.generateWhereCond(queryParams), pairs, limits);
        //构造分页查询请求信息
        DCPagedQueryReq pagedQueryReq = new DCPagedQueryReq(RedisUtil.generateHeader(), type, queryCond);
        TEEERSDCAPIServant.DCPagedQueryResult result = servantPrx.DCPagedQuery(pagedQueryReq);
        if (result == null) {
            return new long[]{};
        }
        return result.resp.pagedQueyResult.idList;
    }

    public <T> List<T> loadAllDataByIds(TEEERSDCAPIServantPrx servantPrx, long[] ids, DCTableType type, Class<T> cls) throws Exception {
        //构造id list请求
        DCGetReq dcGetReq = new DCGetReq();
        dcGetReq.dcTableType = type;
        dcGetReq.ids = ids;
        dcGetReq.rpcHeader = RedisUtil.generateHeader();
        //根据id list查询实际数据
        TEEERSDCAPIServant.DCGetResult getResult = servantPrx.DCGet(dcGetReq);
        //获取序列化后的数据
        byte[][] getBody = getResult.resp.body;
        //反序列化数据源
        List<T> entityList = new ArrayList<>();
        for (byte[] bytes : getBody) {
            entityList.add(deserialize4Ice(servantPrx.ice_getCommunicator(), bytes, cls));
        }
        return entityList;
    }

    public <T> T deserialize4Ice(Communicator communicator, byte[] bytes, Class<T> cls) {
        try {
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            InputStream inputStream = new InputStream(communicator, bytes);
            inputStream.startEncapsulation();
            Method iceRead = cls.getDeclaredMethod("ice_read", InputStream.class);
            T entity = (T) iceRead.invoke(null, inputStream);
            inputStream.endEncapsulation();
            return entity;
        } catch (Exception e) {
            log.error("deserialize ice error", e);
        }
        return null;
    }

    public <T> byte[] serialize4Ice(Communicator communicator, T entity) {
        try {
            OutputStream outputStream = new OutputStream(communicator);
            outputStream.startEncapsulation();
            Method iceWrite = entity.getClass().getDeclaredMethod("ice_write", OutputStream.class, entity.getClass());
            iceWrite.invoke(null, outputStream, entity);
            outputStream.endEncapsulation();
            return outputStream.finished();
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public int forward2terminal(long terminalId) {
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        // 构造pack
        DCExtdPack extdPack = new DCExtdPack();
        extdPack.setRpcHeader(RedisUtil.generateHeader());
        extdPack.setDcExtdCmd(DCExtdCmd.E_CS_NTF_WEB2CLI_HAS_NEW_VER);
        // 构造请求
        DcNtfWeb2CliHasNewVerReq req = new DcNtfWeb2CliHasNewVerReq();
        req.toCliId = terminalId;
        extdPack.setBody(serialize4Ice(servantPrx.ice_getCommunicator(), req));

        TEEERSDCAPIServant.HandlerDCExtdPackResult result = servantPrx.HandlerDCExtdPack(extdPack);
        return result.resp.rpcHeader.retCode;
    }

    public int delete4ice(long id, DCTableType dcTableType) {
        TEEERSDCAPIServantPrx servantPrx = IceUtil.getApiServantPrx();
        DCDeleteReq deleteReq = new DCDeleteReq();
        deleteReq.dcTableType = dcTableType;
        deleteReq.rpcHeader = RedisUtil.generateHeader();
        deleteReq.deledIds = new long[]{id};
        TEEERSDCAPIServant.DCDeleteResult result = servantPrx.DCDelete(deleteReq);
        return result.resp.rpcHeader.retCode;
    }
}
