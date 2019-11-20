/*
 * Copyright 2018-2019 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lud.util.comm.io;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.HTMLText;
import com.lud.util.content.util.type.EncodeType;

/**
 * 网络有关的程序
 * @author sunqinqiu 
 * @date   2019-09-13 22:04
 */
public class NetWorker {
    private static final Logger loger = LoggerFactory.getLogger(NetWorker.class);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-16 18:05
     * @param url
     * @return
     */
    public static String getHttpContent(String url) {
        return getHttpContent(url, EncodeType.ENCODE_UTF8);
    }
    
    /**
     * 获取远端数据
     * @author sunqinqiu 
     * @date   2019-09-13 22:27
     * @param from
     * @return
     */
    public static String getHttpContent(String url, String encode) {
        HttpGet httpget = new HttpGet(url);
        try (CloseableHttpClient httpclient = HttpClients.createDefault(); CloseableHttpResponse response = httpclient.execute(httpget)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_NETWORK_ERROR, e);
            return CT.EMPTY;
        }
    }
    
    /**
     * URL转为Map
     * @author sunqinqiu 
     * @date   2019-09-16 21:41
     * @param content
     * @return
     */
    public static Map<String, String> getMapByUrl(String content) {
        Map<String, String> result = new HashMap<>();
        for (String kv : content.split(CT.STRING_AND)) {
            String[] kvs = kv.split(CharacterContent.STRING_EQUAL);
            if (kvs.length == 2) {
                result.put(kvs[0], kvs[1]);
            }
        }
        return result;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 18:58
     * @param url
     * @param data
     * @param userAgent
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     * @throws Exception
     */
    public static String httpRequest(String url, String data, String userAgent, int connectTimeoutMs, int readTimeoutMs) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader(HTMLText.CONTENT_TYPE, HTMLText.CONTENT_TYPE_XML);
        httpPost.addHeader(HTMLText.USER_AGENT, userAgent);
        httpPost.setEntity(new StringEntity(data, EncodeType.ENCODE_UTF8));
        try (BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build(), null, null, null); CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build(); CloseableHttpResponse httpResponse = httpClient.execute(httpPost);) {
            return EntityUtils.toString(httpResponse.getEntity(), EncodeType.ENCODE_UTF8);
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_NETWORK_ERROR, e);
            return CT.EMPTY;
        }
    }
    
    /**
     * POST;application/json
     * @author sunqinqiu 
     * @date   2019-09-13 22:28
     * @param url
     * @param json
     * @return
     */
    public static String postJSON(String url, String json) {
        HttpPost httpPost = new HttpPost(url);
        StringEntity requestEntity = new StringEntity(json, EncodeType.ENCODE_UTF8);
        requestEntity.setContentEncoding(EncodeType.ENCODE_UTF8);
        httpPost.setHeader(HTMLText.CONTENT_TYPE, HTMLText.CONTENT_TYPE_JSON);
        httpPost.setEntity(requestEntity);
        try (CloseableHttpClient httpclient = HttpClients.createDefault(); CloseableHttpResponse response = httpclient.execute(httpPost)) {
            return EntityUtils.toString(response.getEntity(), EncodeType.ENCODE_UTF8);
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_NETWORK_ERROR, e);
            return CT.EMPTY;
        }
    }
    
    private NetWorker() {}
}
