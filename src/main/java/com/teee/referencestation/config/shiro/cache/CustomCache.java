package com.teee.referencestation.config.shiro.cache;

import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.utils.JwtUtil;
import com.teee.referencestation.utils.PropertiesUtil;
import com.teee.referencestation.utils.RedisUtil;
import com.teee.referencestation.utils.SerializeUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.*;

/**
 * 重写Shiro的Cache保存读取
 * @author zhanglei
 */
public class CustomCache<K,V> implements Cache<K,V> {

    private static final SerializeUtils serializeUtils = new SerializeUtils();

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/4 18:33
     */
    private String getKey(Object key) {
        return Constant.PREFIX_SHIRO_CACHE + JwtUtil.getClaim(key.toString(), Constant.ACCOUNT);
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if(!RedisUtil.exists(this.getKey(key))){
            return null;
        }
        return RedisUtil.getObject(this.getKey(key));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 读取配置文件，获取Redis的Shiro缓存过期时间
        PropertiesUtil.readProperties("config.properties");
        String shiroCacheExpireTime = PropertiesUtil.getProperty("shiroCacheExpireTime");
        // 设置Redis的Shiro缓存
        return RedisUtil.setObject(this.getKey(key), value, Long.valueOf(shiroCacheExpireTime));
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if(!RedisUtil.exists(this.getKey(key))){
            return null;
        }
        RedisUtil.delKey(this.getKey(key));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        RedisUtil.flushDB();
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        Long size = RedisUtil.dbSize();
        return size.intValue();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {

        Set<byte[]> keys = RedisUtil.keys(new String("*").getBytes());
        Set<Object> set = new HashSet<>();
        for (byte[] bs : keys) {
            set.add(serializeUtils.deserialize(bs));
        }
        return set;
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<>();
        for (Object key : keys) {
            values.add(RedisUtil.getObject(this.getKey(key)));
        }
        return values;
    }
}
