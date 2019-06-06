package com.teee.referencestation.common.cache;

import com.teee.referencestation.utils.Md5EncryptionUtil;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.rpc.ice.teee.DCPagedQueryResp;
import com.teee.referencestation.rpc.ice.teee.DCTableType;
import com.teee.referencestation.rpc.ice.teee.PagedQueyResult;
import com.teee.referencestation.rpc.ice.teee.TEEERSDCAPIServant;
import com.teee.referencestation.utils.JsonUtil;
import com.teee.referencestation.utils.JwtUtil;
import com.teee.referencestation.utils.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhanglei
 */
@Aspect
@Component
@Slf4j
@SuppressWarnings("ALL")
public class IceCacheAspect {

    @Pointcut("@annotation(com.teee.referencestation.common.cache.EnableIceCacheService)")
    public void dealCacheServiceCut() {
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Around(value = "dealCacheServiceCut()")
    public Object dealCacheService(ProceedingJoinPoint point) throws Throwable {
        Method method = getMethod(point);
        // 获取注解对象
        EnableIceCacheService cacheServiceAnnotation = method.getAnnotation(EnableIceCacheService.class);
        //所有参数
        Object[] args = point.getArgs();
        String cacheKey = parseKey(method, args);
        if (ObjUtil.isEmpty(cacheKey)) {
            return point.proceed();
        }
        log.info("{} enable cache service,cacheKey:{}", point.getSignature(), cacheKey);
        EnableIceCacheService.CacheOperation cacheOperation = cacheServiceAnnotation.cacheOperation();
        if (cacheOperation == EnableIceCacheService.CacheOperation.QUERY) {
            return processQuery(point, cacheKey, method, args);
        }
        if (cacheOperation == EnableIceCacheService.CacheOperation.ADD || cacheOperation == EnableIceCacheService.CacheOperation.DELETE
                || cacheOperation == EnableIceCacheService.CacheOperation.MODIFY) {
            return processUpdateAndDelete(point, cacheKey);
        }
        return point.proceed();
    }

    /**
     * 查询处理,
     * 将查询的id list管理起来，根据数量划分分区，没1W个id为一个分区
     */
    private Object processQuery(ProceedingJoinPoint point, String cacheKey, Method method, Object[] args) throws Throwable {
        // 获取分页参数
        Integer pageNum = getParameterValue("#pageNum", method, args, Integer.class);
        Integer pageSize = getParameterValue("#pageSize", method, args, Integer.class);

        //取出缓存
        IdListCache redisCache = (IdListCache) redisTemplate.opsForValue().get(cacheKey);
        if (redisCache != null) {
            //page reasonable
            int pages = (redisCache.total / pageSize) + 1;
            if (pageNum < 1) {
                pageNum = 1;
            }
            if (pageNum > pages) {
                pageNum = pages;
            }
        }
        //起始点在哪个分区
        int partitionIndex = (pageNum - 1) * pageSize / Constant.ICE_CACHE.PAGINATION;
        //终点在哪个分区
        int partitionEnd = pageNum * pageSize / Constant.ICE_CACHE.PAGINATION;

        //判断缓存是否存在
        if (redisCache == null || redisCache.total == 0 || redisCache.idLists == null) {

            return loadPageQueryFromDB(point, cacheKey, pageNum, pageSize, method);
        } else {
            //当前缓存中没有分页数据，再次从DB中获取
            if (!redisCache.idLists.containsKey(partitionIndex)) {

                return loadPageQueryFromDB(point, cacheKey, pageNum, pageSize, method);
            }
            //取出分区缓存数据
            long[] idList = redisCache.getIdLists().get(partitionIndex);
            if (partitionIndex != partitionEnd) {
                //当前缓存中没有分页数据，再次从DB中获取
                if (!redisCache.idLists.containsKey(partitionEnd)) {

                    return loadPageQueryFromDB(point, cacheKey, pageNum, pageSize, method);
                }
                long[] idListEnd = redisCache.getIdLists().get(partitionEnd);
                //数组扩容
                idList = Arrays.copyOf(idList, idList.length + idListEnd.length);
                System.arraycopy(idListEnd, 0, idList, idList.length, idListEnd.length);
            }

            //构造返回的idList
            int to = (pageNum - 1) * pageSize - Constant.ICE_CACHE.PAGINATION * partitionIndex;
            int from = (pageNum + 1) * pageSize - Constant.ICE_CACHE.PAGINATION * partitionIndex;
            if (2 * pageSize > idList.length) {
                from = idList.length;
            }
            long[] queryIdLists = Arrays.copyOfRange(idList, to, from);

            PagedQueyResult result = new PagedQueyResult();
            result.idList = queryIdLists;
            result.total = redisCache.getTotal();
            result.idListSize = queryIdLists.length;
            log.info("{} enable cache service, has cacheKey:{} , return", point.getSignature(), cacheKey);
            TEEERSDCAPIServant.DCPagedQueryResult queryResult = new TEEERSDCAPIServant.DCPagedQueryResult();
            DCPagedQueryResp pagedQueyResult = new DCPagedQueryResp();
            pagedQueyResult.pagedQueyResult = result;
            queryResult.resp = pagedQueyResult;
            return queryResult;
        }
    }

    /**
     * 从DB中获取ice分页的id list，并计算id list所在分区，将其放入分区中缓存起来
     *
     * @param point
     * @param cacheKey
     * @param pageNum
     * @param pageSize
     * @return
     */
    public TEEERSDCAPIServant.DCPagedQueryResult loadPageQueryFromDB(ProceedingJoinPoint point, String cacheKey, Integer pageNum, Integer pageSize, Method method) throws Throwable {

        TEEERSDCAPIServant.DCPagedQueryResult result = (TEEERSDCAPIServant.DCPagedQueryResult) point.proceed();

        if (result == null) {
            return null;
        }

        PagedQueyResult pagedQueyResult = result.resp.pagedQueyResult;
        int total = pagedQueyResult.total;
        long[] idLists = pagedQueyResult.idList;

        //page reasonable
        int pages = (total / pageSize) + 1;
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageNum > pages) {
            pageNum = pages;
        }

        // 初始化缓存
        IdListCache cache = new IdListCache();
        cache.setTotal(total);
        Map<Integer, long[]> idListMap = new HashMap<>(8);
        //将id list分区，每个分区10000条
        int partitions = total / Constant.ICE_CACHE.PAGINATION;
        //起始点在哪个分区
        int partitionIndex = (pageNum - 1) * pageSize / Constant.ICE_CACHE.PAGINATION;
        //终点在哪个分区
        int partitionEnd = pageNum * pageSize / Constant.ICE_CACHE.PAGINATION;
        //将查询的数据放在idListMap
        if (partitionIndex == partitionEnd) {
            idListMap.put(partitionIndex, Arrays.copyOfRange(idLists, 0, idLists.length));
        } else {
            idListMap.put(partitionIndex, Arrays.copyOfRange(idLists, 0, Constant.ICE_CACHE.PAGINATION));
            idListMap.put(partitionEnd, Arrays.copyOfRange(idLists, Constant.ICE_CACHE.PAGINATION, idLists.length));
        }
        cache.setIdLists(idListMap);

        //将分区的数据放入缓存
        redisTemplate.opsForValue().set(cacheKey, cache);
        // 获取注解对象
        EnableIceCacheService cacheServiceAnnotation = method.getAnnotation(EnableIceCacheService.class);
        //设置缓存失效时间
        redisTemplate.expire(cacheKey, cacheServiceAnnotation.expire(), TimeUnit.MINUTES);

        //构造返回的idList
        int to = (pageNum - 1) * pageSize - Constant.ICE_CACHE.PAGINATION * partitionIndex;
        int from = (pageNum + 1) * pageSize - Constant.ICE_CACHE.PAGINATION * partitionIndex;
        if (2 * pageSize > pagedQueyResult.idListSize) {
            from = pagedQueyResult.idListSize;
        }
        long[] queryIdLists = Arrays.copyOfRange(idLists, to, from);
        pagedQueyResult.idList = queryIdLists;

        log.info("after {} proceed,save result to cache,redisKey:{},save content:{}", point.getSignature(), cacheKey, result);
        return result;
    }

    /**
     * 删除和新增处理,新增或删除后刷新缓存
     */
    private Object processUpdateAndDelete(ProceedingJoinPoint point, String cacheKey) throws Throwable {
        try {
            return point.proceed();
        } finally {
            //TODO 考虑是删除所有缓存还是当前这个人的缓存
            Set<String> keys = redisTemplate.keys(cacheKey);
            redisTemplate.delete(keys);
        }
    }

    private Method getMethod(JoinPoint joinPoint) throws Exception {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        return method;
    }

    /**
     * 获取redis的key
     */
    private String parseKey(Method method, Object[] args) {
        String key = "IdList";
        //获取表名
        DCTableType type = getParameterValue("#type", method, args, DCTableType.class);
        key += ":" + (type == null ? "*" : type.value());
        //获取请求参数
        Map<String, Object> map = getParameterValue("#queryParams", method, args, Map.class);
        if (map == null) {
            key += ":*";
        } else {
            //移除分页参数，只有查询条件做缓存Key
            map.remove("pageNum");
            map.remove("pageSize");

            //Map转List
            List<Map.Entry<String, Object>> list = new ArrayList<>(map.entrySet());
            //移除值为空的无效查询参数,然后排序
            List<Map.Entry<String, Object>> sortedList = list.stream().filter(m -> ObjUtil.isNotEmpty(m.getValue()))
                    .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey())).collect(Collectors.toList());
            key += ":" + Md5EncryptionUtil.md5WithNoSalt(JsonUtil.toJSONString(sortedList));
        }

        try {
            String token = (String) SecurityUtils.getSubject().getPrincipal();
            key += ":" + JwtUtil.getClaim(token, Constant.USER_ID);
        } catch (Exception e) {
            log.error("cannot get current user id, use the * replace");
            key += ":*";
        }

        return key;
    }


    /**
     * 通过el表达式获取参数值
     */
    private <T> T getParameterValue(String fieldKey, Method method, Object[] args, Class<T> cls) {
        String key = method.getName();

        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }

        return (T) parser.parseExpression(fieldKey).getValue(context, cls);
    }

}
