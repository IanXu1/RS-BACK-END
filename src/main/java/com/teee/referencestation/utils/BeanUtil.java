package com.teee.referencestation.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;

/**
 * JavaBean工具类，注意是JavaBean，跟SpringBean无关
 *
 * @author LDB
 * @date 2018/11/09 18:06
 */
public class BeanUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 解决Jackson无法序列化L哦擦亮DateTime的问题
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    public BeanUtil() {
    }

    public static <T> T toBean(Map<String, Object> parameter, Class<T> cls) {
        try {
            return mapper.readValue(JsonUtil.toJSONString(parameter), cls);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

}
