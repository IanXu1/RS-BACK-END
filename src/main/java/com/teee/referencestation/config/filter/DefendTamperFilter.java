package com.teee.referencestation.config.filter;

import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.HttpServletUtil;
import com.teee.referencestation.utils.JsonUtil;
import com.teee.referencestation.utils.ObjUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kylinzhang
 * @desc 针对敏感URL请求参数MD5加密，防止参数篡改
 * @date 2019-3-26 09:34:58
 */
public class DefendTamperFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(DefendTamperFilter.class);
    /**
     * 摘要参数的header字段
     */
    private static final String SIGNATURE = "signature";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        RepeatableReadServletRequest servletRequest = new RepeatableReadServletRequest(request);
        try {
            boolean continueChain = this.preHandle(servletRequest, response);
            if (continueChain) {
                continueChain = this.executeChain(servletRequest, response);
            }
            if (!continueChain) {
                return;
            }
        } catch (Exception e) {
            log.error("DefendTamperFilter exception: " + request.getRequestURL());
            log.error("DefendTamperFilter exception: ", e);
        }

        filterChain.doFilter(servletRequest, response);
    }

    /**\
     * @desc 过滤不检查的url
     * @param request
     * @return
     */
    private boolean releaseUrl(HttpServletRequest request) {
        //没有签名不验证
        if (ObjUtil.isEmpty(request.getHeader(SIGNATURE))) {
            return true;
        }
        return false;
    }

    /**
     * 支持跨域
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 支持跨域
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
            return false;
        }
        return true;
    }

    protected boolean executeChain(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //判断参数摘要是否相等
        if (!releaseUrl(request)) {
            Map<String, Object> paramsMap = HttpServletUtil.getRequestParameters(request, false);
            List<String> paramsList = new ArrayList<>();
            List<String> paramList = new ArrayList<>();

            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                if (entry.getValue() instanceof List || entry.getValue() instanceof String[]) {
                    paramsList.add(entry.getKey());
                } else {
                    paramList.add(entry.getKey());
                }
            }
            //排序
            Collections.sort(paramsList);
            Collections.sort(paramList);
            //拼接参数值
            String paramStr = "";
            for (String s : paramList) {
                paramStr += paramsMap.get(s);
            }
            for (String s : paramsList) {
                if (paramsMap.get(s) instanceof List) {
                    List valList = (List) paramsMap.get(s);
                    Collections.sort(valList);
                    for (Object val : valList) {
                        if (val instanceof String) {
                            paramStr += val;
                        } else if (val instanceof Integer) {
                            paramStr += val;
                        } else {
                            LinkedHashMap<String, String> secMap = (LinkedHashMap<String, String>) val;
                            Set<String> secKeys = secMap.keySet();
                            secKeys = secKeys.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
                            for (String secKey : secKeys) {
                                paramStr += secMap.get(secKey);
                            }
                        }
                    }
                } else if (paramsMap.get(s) instanceof String[]) {
                    String[] valArray = (String[]) paramsMap.get(s);
                    Arrays.sort(valArray);
                    for (String val : valArray) {
                        paramStr += val;
                    }
                }
            }
            //hex-md5摘要
            String serverSign = DigestUtils.md5Hex(URLEncoder.encode(paramStr, "UTF-8"));
            String clientSign = request.getHeader(SIGNATURE);

            if (!clientSign.equalsIgnoreCase(serverSign)) {
                response.setHeader("Content-type", "application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpStatus.OK.value());
                ServletOutputStream out = response.getOutputStream();
                RestResponse restResponse = new RestResponse(HttpStatus.BAD_REQUEST.value(), "请求参数不完整");
                out.write(JsonUtil.toJSONString(restResponse).getBytes());
                out.flush();
                return false;
            }
        }
        return true;
    }
}
