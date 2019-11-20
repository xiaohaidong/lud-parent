package com.lud.service.open.wxpay;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.service.open.api.wxpay.WeChatPayConfig;
import com.lud.service.open.api.wxpay.WeChatPayConfigSystem;
import com.lud.service.open.api.wxpay.WeChatPayDomainInfo;
import com.lud.service.open.api.wxpay.WeChatPayDomainService;
import com.lud.service.open.api.wxpay.WeChatPayReportService;
import com.lud.service.open.api.wxpay.WeChatPayRequestService;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.HTMLText;
import com.lud.util.content.util.type.EncodeType;

@Service
public class WeChatPayRequestServiceImp implements WeChatPayRequestService {
    protected final Logger         loger = LoggerFactory.getLogger(this.getClass());
    /**
     * 
     */
    @Autowired
    private WeChatPayDomainService weChatPayDomainService;
    /**
     * 
     */
    @Autowired
    private WeChatPayReportService weChatPayReportService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-15 01:15
     * @param useCert
     * @param config
     * @return
     * @throws Exception
     */
    public BasicHttpClientConnectionManager getBasicHttpClientConnectionManager(boolean useCert, WeChatPayConfig config) {
        BasicHttpClientConnectionManager connManager = null;
        try {
            if (useCert) {
                char[] password = config.getMchID().toCharArray();
                InputStream certStream = getCertStream(config.getCert());
                KeyStore ks = KeyStore.getInstance("PKCS12");
                ks.load(certStream, password);
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(ks, password);
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
                SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null, new DefaultHostnameVerifier());
                connManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslConnectionSocketFactory).build(), null, null, null);
            } else {
                connManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build(), null, null, null);
            }
        } catch (Exception e) {
            loger.error("Create BasicHttpClientConnectionManager error!");
        }
        return connManager;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-15 19:21
     * @param cert
     * @return
     */
    private InputStream getCertStream(String cert) {
        loger.info(cert);
        return null;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 22:17
     * @param config
     * @param suffix
     * @param uuid
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @param useCert
     * @param autoReport
     * @return
     * @throws Exception
     */
    private String request(final WeChatPayConfig config, String suffix, String uuid, String data, int connectTimeoutMs, int readTimeoutMs, boolean useCert, boolean autoReport) {
        Exception exception = null;
        long elapsedTimeMillis = 0;
        long startTimestampMs = DateUtil.getCurrentTimestampMs();
        boolean firstHasDnsErr = false;
        boolean firstHasConnectTimeout = false;
        boolean firstHasReadTimeout = false;
        WeChatPayDomainInfo domainInfo = weChatPayDomainService.getWeChatPayDomainInfo();
        if (domainInfo == null) { return CT.EMPTY; }
        try {
            String result = requestOnce(config, domainInfo.getDomain(), suffix, data, connectTimeoutMs, readTimeoutMs, useCert);
            elapsedTimeMillis = DateUtil.getCurrentTimestampMs() - startTimestampMs;
            if (autoReport) {
                weChatPayDomainService.report(domainInfo.getDomain(), elapsedTimeMillis, null);
                weChatPayReportService.report(config, uuid, elapsedTimeMillis, domainInfo.getDomain(), domainInfo.isPrimaryDomain(), connectTimeoutMs, readTimeoutMs, firstHasDnsErr, firstHasConnectTimeout, firstHasReadTimeout);
            }
            return result;
        } catch (Exception ex) {
            exception = ex;
            elapsedTimeMillis = DateUtil.getCurrentTimestampMs() - startTimestampMs;
            if (autoReport) {
                weChatPayReportService.report(config, uuid, elapsedTimeMillis, domainInfo.getDomain(), domainInfo.isPrimaryDomain(), connectTimeoutMs, readTimeoutMs, firstHasDnsErr, firstHasConnectTimeout, firstHasReadTimeout);
            }
        }
        if (autoReport) {
            weChatPayDomainService.report(domainInfo.getDomain(), elapsedTimeMillis, exception);
        }
        return CT.EMPTY;
    }
    
    /**
     * 
     * @param domain
     * @param suffix
     * @param uuid
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @param useCert 是否使用证书，针对退款、撤销等操作
     * @return
     * @throws Exception
     */
    private String requestOnce(final WeChatPayConfig config, final String domain, String suffix, String data, int connectTimeoutMs, int readTimeoutMs, boolean useCert) {
        HttpPost httpPost = new HttpPost("https://" + domain + suffix);
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build());
        StringEntity postEntity = new StringEntity(data, EncodeType.ENCODE_UTF8);
        httpPost.addHeader(HTMLText.CONTENT_TYPE, HTMLText.CONTENT_TYPE_XML);
        httpPost.addHeader(HTMLText.USER_AGENT, UserAgentContent.getUserAgent() + " " + config.getMchID());
        httpPost.setEntity(postEntity);
        try (BasicHttpClientConnectionManager connManager = getBasicHttpClientConnectionManager(useCert, config); CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build(); CloseableHttpResponse httpResponse = httpClient.execute(httpPost);) {
            return EntityUtils.toString(httpResponse.getEntity(), EncodeType.ENCODE_UTF8);
        } catch (Exception e) {
            loger.error(e.toString());
            return CT.EMPTY;
        }
    }
    
    /**
     * 可重试的，双向认证的请求
     * @param suffix
     * @param uuid
     * @param data
     * @return
     */
    @Override
    public String requestWithCert(final WeChatPayConfig config, String suffix, String uuid, String data, boolean autoReport) {
        return this.request(config, suffix, uuid, data, WeChatPayConfigSystem.getHttpConnectTimeoutMs(), WeChatPayConfigSystem.getHttpReadTimeoutMs(), true, autoReport);
    }
    
    /**
     * 可重试的，双向认证的请求
     * @param suffix
     * @param uuid
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     */
    @Override
    public String requestWithCert(final WeChatPayConfig config, String suffix, String uuid, String data, int connectTimeoutMs, int readTimeoutMs, boolean autoReport) {
        return this.request(config, suffix, uuid, data, connectTimeoutMs, readTimeoutMs, true, autoReport);
    }
    
    /**
     * 可重试的，非双向认证的请求
     * @param suffix
     * @param uuid
     * @param data
     * @return
     */
    @Override
    public String requestWithoutCert(final WeChatPayConfig config, String suffix, String uuid, String data, boolean autoReport) {
        return this.request(config, suffix, uuid, data, WeChatPayConfigSystem.getHttpConnectTimeoutMs(), WeChatPayConfigSystem.getHttpReadTimeoutMs(), false, autoReport);
    }
    
    /**
     * 可重试的，非双向认证的请求
     * @param suffix
     * @param uuid
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     */
    @Override
    public String requestWithoutCert(final WeChatPayConfig config, String suffix, String uuid, String data, int connectTimeoutMs, int readTimeoutMs, boolean autoReport) {
        return this.request(config, suffix, uuid, data, connectTimeoutMs, readTimeoutMs, false, autoReport);
    }
}
