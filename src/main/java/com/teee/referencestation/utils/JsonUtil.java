package com.teee.referencestation.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Json工具类
 *
 * @author LDB
 * @date 2018/11/09 18:07
 */
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectMapper UNKNOWN_PROPERTIES_MAPPER = new ObjectMapper();

    static {
        UNKNOWN_PROPERTIES_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJSONString(Object value) {
        if (value == null) {
            return null;
        }
        StringWriter writer = new StringWriter(1000);
        try {
            MAPPER.writeValue(writer, value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    public static String toStringFormat(Object value) {
        if (value == null) {
            return null;
        }
        return formatJson(toJSONString(value));
    }

    public static Map parseObject(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return parseObject(value, Map.class);
    }

    /**
     * 将json字符串转换成bean
     *
     * @param value                   String
     * @param clazz                   Class
     * @param failOnUnknownProperties boolean
     * @param <T>                     T
     * @return T
     */
    public static <T> T parseObject(String value, Class<T> clazz, boolean failOnUnknownProperties) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            if (failOnUnknownProperties) {
                return MAPPER.readValue(value, clazz);
            } else {
                return UNKNOWN_PROPERTIES_MAPPER.readValue(value, clazz);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将Json格式字符串转换为指定类型
     *
     * @param value String
     * @param clazz Class
     * @param <T>   T
     * @return T
     */
    public static <T> T parseObject(String value, Class<T> clazz) {
        return parseObject(value, clazz, true);
    }

    /**
     * Json格式化
     *
     * @param jsonStr String
     * @return Json格式化后的String
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '"':
                    if (last != '\\') {
                        isInQuotationMarks = !isInQuotationMarks;
                    }
                    sb.append(current);
                    break;
                case '{':
                case '[':
                    sb.append(current);
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent++;
                        addIndentBlank(sb, indent);
                    }
                    break;
                case '}':
                case ']':
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent--;
                        addIndentBlank(sb, indent);
                    }
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加空格
     *
     * @param sb     StringBuilder
     * @param indent int
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    /**
     * json字符串转Map
     *
     * @param strJson String
     * @return Map
     */
    public static Map json2map(String strJson) {
        return JSON.parseObject(strJson);
    }

    public static Map vo2map(Object object) {
        String value = toJSONString(object);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return parseObject(value, Map.class);
    }

    /**
     * Map转String
     *
     * @param map Map
     * @return String
     */
    public static String map2Str(Map map) {
        if (map != null) {
            return JSON.toJSONString(map);
        }
        return null;
    }

}
