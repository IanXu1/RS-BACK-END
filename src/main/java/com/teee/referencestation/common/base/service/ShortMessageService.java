package com.teee.referencestation.common.base.service;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@Service
public class ShortMessageService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShortMessageService.class);

    private RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    @Value("${SMS_UID}")
    private String uid;
    @Value("${SMS_KRY}")
    private String key;

    /**
     * @throws
     * @Title: sendMsgUtf8
     * @param: @param content
     * @param: @param mobiles
     * @param: @return
     */
    public int sendMsgUtf8(String content, String mobiles) {
        Map maps = new HashMap(8);
        maps.put("Uid", uid);
        maps.put("Key", key);
        maps.put("smsMob", mobiles);
        maps.put("smsText", content);
        String result = sendHttpPost("http://utf8.sms.webchinese.cn", maps, "utf-8");
        return Integer.parseInt(result);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param maps    参数
     * @param type    字符编码格式
     */
    public String sendHttpPost(String httpUrl, Map<String, String> maps, String type) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, type));
        } catch (Exception e) {
            LOGGER.error("sendHttpPost, param: " + maps, e);
        }
        return sendHttpPost(httpPost, type);
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private String sendHttpPost(HttpPost httpPost, String reponseType) {
        HttpEntity entity = null;
        String responseContent = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            // 创建默认的httpClient实例.
            httpPost.setConfig(requestConfig);
            // 执行请求
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, reponseType);
        } catch (Exception e) {
            LOGGER.error("sendHttpPost, url: " + httpPost.getURI(), e);
        }
        return responseContent;
    }
}
