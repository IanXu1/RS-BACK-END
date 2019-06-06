package com.teee.referencestation.utils;

import com.teee.referencestation.rpc.ice.teee.RPCHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanglei
 */
@Component
public class RedisUtil {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate){
        RedisUtil.redisTemplate = redisTemplate;
    }

    public static boolean exists(Object key) {
        return redisTemplate.hasKey(key);
    }

    public static Object getObject(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static Object setObject(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return value;
    }

    public static Object setObjectNoExpire(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        return value;
    }

    public static void delKey(String key) {
        redisTemplate.delete(key);
    }

    public static void flushDB() {
        redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "OK";
        });
    }

    public static Long dbSize() {
        return (Long) redisTemplate.execute((RedisCallback<Long>) connection -> connection.dbSize());
    }

    public static Set<byte[]> keys(byte[] bytes) {
        return (Set<byte[]>) redisTemplate.execute((RedisCallback<Set<byte[]>>) connection -> connection.keys(bytes));
    }

    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public static RPCHeader generateHeader() {
        RPCHeader rpcHeader = new RPCHeader();
        rpcHeader.reqNo = String.valueOf(redisTemplate.opsForValue().increment("teee::bs::rpcheader:reqNo"));
        rpcHeader.reqTimeStamp = System.currentTimeMillis();
        return rpcHeader;
    }
}
