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
package com.lud.service.open.wxpay;

import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lud.service.open.api.ApiResult;
import com.lud.service.open.api.wxpay.WeChatPayConfig;
import com.lud.service.open.api.wxpay.WeChatPayConfigSystem;
import com.lud.service.open.api.wxpay.WeChatPayRequestService;
import com.lud.service.open.api.wxpay.WeChatPayRunConfig;
import com.lud.service.open.api.wxpay.WeChatPayService;
import com.lud.util.comm.io.W3CXML;
import com.lud.util.comm.util.DEncrypt;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.comm.util.RandomUtil;
import com.lud.util.content.open.tencent.WeChatPay;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;

/**
 * @author sunqinqiu 
 * @date   2019-09-14 14:38
 */
@Service
@Preload
public class WeChatPayServiceImp extends CoreService implements WeChatPayService {
    
    @Autowired
    private WeChatPayRequestService requestService;
    
    /**
     * 初始化数据
     * @author sunqinqiu 
     * @date   2019-09-14 14:47
     */
    public void run() {
        initWeChatPayFinalContent();
    }
    
    /**
     * 填充数据
     * @author sunqinqiu 
     * @date   2019-09-15 02:06
     * @param reqData
     * @return
     * @throws Exception
     */
    private Map<String, String> fillRequestData(final WeChatPayRunConfig runconfig, Map<String, String> data) {
        WeChatPayConfig config = runconfig.getConfig();
        data.put("appid", config.getAppID());
        data.put("mch_id", config.getMchID());
        data.put("nonce_str", RandomUtil.getRandomChar(32));
        data.put("sign_type", runconfig.getSignType().equals(DEncrypt.ALGORITHM_MD5) ? "MD5" : "HMAC-SHA256");
        data.put("attach", runconfig.getConfig().getProject());
        data.put("sign", DEncrypt.generateSignature(data, config.getKey(), runconfig.getSignType()));
        return data;
    }
    
    /**
     * 初始化FinalContent
     * @author sunqinqiu 
     * @date   2019-09-14 23:39
     */
    private void initWeChatPayFinalContent() {
        String userAgen = WeChatPay.WXPAYSDK_VERSION + " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") + ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();
        UserAgentContent.setUserAgent(userAgen);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-15 18:27
     * @param result
     * @return
     */
    private boolean isResponseSignatureValid(final WeChatPayRunConfig runconfig, Map<String, String> reqData) {
        try {
            return DEncrypt.isSignatureValid(reqData, runconfig.getConfig().getKey(), runconfig.getSignType());
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 操作XML
     * @author sunqinqiu 
     * @date   2019-09-15 18:15
     * @return
     */
    private ApiResult processResponseXml(final WeChatPayRunConfig runconfig, String xml) {
        Map<String, String> payResult = W3CXML.xmlToMap(xml);
        ApiResult result = new ApiResult();
        if (payResult.containsKey(WeChatPay.RETURN_CODE)) {
            String returnCode = payResult.get(WeChatPay.RETURN_CODE);
            result.setSuccess(returnCode.equals(WeChatPay.SUCCESS));
            result.setMessage(payResult.get(WeChatPay.RETURN_MSG));
            result.setValid(this.isResponseSignatureValid(runconfig, payResult));
            result.setData(ListMapUtil.toMap(JSON.toJSONString(payResult)));
        }
        return result;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-15 02:23
     * @param config
     * @param runconfig
     * @param url
     * @param reqData
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     * @throws Exception
     */
    private String requestWithoutCert(final WeChatPayRunConfig runconfig, String url, Map<String, String> data, int connectTimeoutMs, int readTimeoutMs) {
        WeChatPayConfig config = runconfig.getConfig();
        String msgUUID = data.get("nonce_str");
        String reqBody = W3CXML.mapToXML(data);
        return requestService.requestWithoutCert(config, url, msgUUID, reqBody, connectTimeoutMs, readTimeoutMs, runconfig.isAutoReport());
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-15 03:00
     * @param reqData
     * @return
     * @throws Exception
     */
    @Override
    public ApiResult unifiedOrder(WeChatPayRunConfig runconfig, Map<String, String> reqData) {
        return unifiedOrder(runconfig, reqData, WeChatPayConfigSystem.getHttpConnectTimeoutMs(), WeChatPayConfigSystem.getHttpReadTimeoutMs());
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-15 02:36
     * @param config
     * @param runconfig
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @return
     * @throws Exception
     */
    @Override
    public ApiResult unifiedOrder(WeChatPayRunConfig runconfig, Map<String, String> data, int connectTimeoutMs, int readTimeoutMs) {
        String url = runconfig.isUseSandbox() ? WeChatPay.SANDBOX_UNIFIEDORDER_URL_SUFFIX : WeChatPay.UNIFIEDORDER_URL_SUFFIX;
        String response = requestWithoutCert(runconfig, url, fillRequestData(runconfig, data), connectTimeoutMs, readTimeoutMs);
        return processResponseXml(runconfig, response);
    }
}
