package com.teee.referencestation.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Objects;

/**
 * HttpServlet工具类，封装HttpServletRequest对象的一些操作
 * 如：参数统一读取、session处理等，时间关系，暂时没写完
 *
 * @author LDB
 * @date 2018/11/09 18:27
 */
@Slf4j
public class HttpServletUtil {

    public static final String STREAM_PARAM = "stream_param";
    public static final String ORIGINAL_PARAM = "original_param";


    /**
     * 获取请求的参数,如果是POST流，读取后立即关闭
     *
     * @param request HttpServletRequest
     * @return Map
     */
    public static Map<String, Object> getRequestParameters(HttpServletRequest request) {
        return getRequestParameters(request, true);
    }

    /**
     * 获取请求的参数
     *
     * @param request     HttpServletRequest
     * @param closeStream 是否在读取完毕后关闭流
     * @return Map
     */
    public static Map<String, Object> getRequestParameters(HttpServletRequest request, boolean closeStream) {
        Object streamParamObj = request.getAttribute(STREAM_PARAM);
        if (streamParamObj != null) {
            return (Map) streamParamObj;
        } else {
            Map<String, Object> paramMap = null;
            String requestMethod = request.getMethod();
            if (StringUtils.equalsIgnoreCase(requestMethod, "GET")) {
                paramMap = getParameterFromMap(request.getParameterMap());
            } else {
                try {
                    String contentType = request.getContentType();
                    if (StringUtils.contains(contentType, "multipart/form-data")) {
                        paramMap = getParameterFromMap(request.getParameterMap());
                    } else {
                        InputStream in = request.getInputStream();
                        if (in != null) {
                            //尝试从流中读
                            paramMap = getRequestParameters(in, request);
                            if (closeStream) {
                                IOUtils.closeQuietly(in);
                            }
                        } else {
                            paramMap = getParameterFromMap(request.getParameterMap());
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getLocalizedMessage());
                }
            }
            return paramMap;
        }
    }

    /**
     * 从POST流中或GET地址中读取参数
     *
     * @param in      InputStream
     * @param request HttpServletRequest
     * @return Map
     * @throws Exception Exception
     */
    private static Map<String, Object> getRequestParameters(InputStream in, HttpServletRequest request) throws Exception {
        Map paramMap = Maps.newLinkedHashMap();
        String contentType = request.getContentType();
        //特殊情况，文件上传，不处理直接跳过
        if (StringUtils.contains(contentType, "multipart/form-data")) {
            return paramMap;
        }
        //尝试从流中读取
        byte[] bytes = IOUtils.toByteArray(in);
        String encode = "UTF-8";
        String paramStr = new String(bytes, encode);
        if (StringUtils.isNotBlank(paramStr)) {
            request.setAttribute(ORIGINAL_PARAM, paramStr);
            /*目前传参兼容两种方式,1:key=value键值对，2:直接POST JSON字符串*/
            //标识是否是key=value格式
            boolean isSingleValue = false;
            //以“{”开头并且以“}”结尾的视为直接POST JSON字符串
            if (StringUtils.startsWith(paramStr, "{") && StringUtils.endsWith(paramStr, "}")) {
                isSingleValue = true;
            }
            //以“{”开头并且以“}”结尾，编码了的JSON字符串
            if (StringUtils.startsWith(paramStr, "%7B") && StringUtils.endsWith(paramStr, "%7D")) {
                isSingleValue = true;
                paramStr = URLDecoder.decode(paramStr, encode);
            }

            //直接POST JSON字符串
            if (isSingleValue) {
                paramMap.putAll(JsonUtil.parseObject(paramStr));
            } else {//key=value形式最好将value decode一次
                String[] params = paramStr.split("&");
                for (String param : params) {
                    String[] p = param.split("=");
                    if (p.length == 1) {
                        paramMap.put(p[0], "");
                    } else {
                        paramMap.put(p[0], URLDecoder.decode(p[1], encode));
                    }
                }
            }
        }
        //从地址上再获取一次
        paramMap.putAll(getParameterFromMap(request.getParameterMap()));
        request.setAttribute(STREAM_PARAM, paramMap);
        return paramMap;
    }

    /**
     * 从GET getParameterMap()方法返回的Map中获取参数
     *
     * @param parameterMap Map
     * @return Map
     */
    public static Map<String, Object> getParameterFromMap(Map parameterMap) {
        Map<String, Object> paramMap = Maps.newHashMap();
        for (Object o : parameterMap.entrySet()) {
            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) o;
            String key = entry.getKey();
            String[] val = entry.getValue();
            if (val.length == 1) {
                paramMap.put(key, val[0]);
            } else if (val.length > 1) {
                if (key.endsWith("[]")) {
                    key = key.substring(0, key.length() - 2);
                }
                String valsStr = "";
                for (String aVal : val) {
                    if (aVal != null) {
                        if (StringUtils.isBlank(valsStr)) {
                            valsStr = aVal;
                        } else {
                            valsStr += "," + aVal;
                        }
                    }
                }
                paramMap.put(key, valsStr);
            } else {
                paramMap.put(key, null);
            }
        }
        return paramMap;
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

}
