package com.jeesite.modules.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author linwei
 * @data 2018年3月13日
 * @描述：http://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/QuickStart.java
 * @示例：http://www.baeldung.com/httpclient-post-http-request
 */
public class WebHttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static CloseableHttpClient httpClient;
    public final static String ENCODE = "UTF-8";
    static {
        // httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static String get(String url) {
        return get(url, null, null);
    }
    
    public static String get(String url, Map<String, String> nameValuePair) {
        return get(url, nameValuePair, null);
    }

    public static String get(String url, Map<String, String> nameValuePair, Map<String, String> headers) {
        String data = null;
        logger.debug(">>>请求地址:{},参数:{}", url, JSON.toJSONString(nameValuePair));
        try {
            if (nameValuePair != null) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : nameValuePair.entrySet()) {
                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                
                String queryStr = URLEncodedUtils.format(params, ENCODE);
                url = url + (url.contains("?") ? "&" : "?") + queryStr;
            }
            HttpGet httpGet = new HttpGet(url);
            if (headers != null) {
                for (Map.Entry<String, String> e : headers.entrySet()) {
                    httpGet.addHeader(e.getKey(), e.getValue());
                }
            }
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity, ENCODE);
                    logger.debug(">>>返回结果:{}", data);
                    EntityUtils.consume(entity);
                } else {
                    httpGet.abort();
                }
            }
        } catch (Exception e) {
            logger.error(">>>请求异常", e);
        }
        return data;
    }

    public static byte[] getData(String url) {
        byte[] data = null;
        logger.debug(">>>请求地址:{}", url);
        try {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toByteArray(entity);
                    EntityUtils.consume(entity);
                } else {
                    httpGet.abort();
                }
            }
        } catch (Exception e) {
            logger.error(">>>请求异常", e);
        }
        return data;
    }

    public static String post(String url, Map<String, String> nameValuePair) {
        String data = null;
        logger.debug(">>>请求地址:{},参数:{}", url, JSON.toJSONString(nameValuePair));
        try {
            HttpPost httpPost = new HttpPost(url);
            if (nameValuePair != null) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : nameValuePair.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, ENCODE));
            }
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity);
                    logger.debug(">>>返回结果:{}", data);
                    EntityUtils.consume(entity);
                } else {
                    httpPost.abort();
                }
            }
        } catch (Exception e) {
            logger.error(">>>请求异常", e);
        }
        return data;
    }

    public static String postJson(String url, String json) {
        String data = null;
        logger.debug(">>>请求地址:{},参数:{}", url, json);
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json, ENCODE);
            entity.setContentEncoding(ENCODE);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity httpEntity = response.getEntity();
                    data = EntityUtils.toString(httpEntity, ENCODE);
                    logger.debug(">>>返回结果:{}", data);
                    EntityUtils.consume(httpEntity);
                } else {
                    httpPost.abort();
                }
            }
        } catch (Exception e) {
            logger.error(">>>请求异常", e);
        }
        return data;
    }
    
    /**
     * 获取Post消息体
     * 
     * @param is
     * @param contentLen
     * @return
     */
    public static String getPostBody(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return new String(message);
            } catch (IOException e) {
            }
        }
        return "";
    }

}
