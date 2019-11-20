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
package com.lud.util.content.open.tencent;

import java.io.Serializable;

/**
 * 常量
 */
public class WeChatPay implements Serializable {
    private WeChatPay() {}
    
    /**
     * 
     */
    private static final long  serialVersionUID                    = 1L;
    public static final String DOMAIN_API                          = "api.mch.weixin.qq.com";
    public static final String DOMAIN_API2                         = "api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK                        = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS                        = "apius.mch.weixin.qq.com";
    
    public static final String FAIL                                = "FAIL";
    public static final String SUCCESS                             = "SUCCESS";
    public static final String HMACSHA256                          = "HMAC-SHA256";
    public static final String MD5                                 = "MD5";
    
    public static final String FIELD_SIGN                          = "sign";
    public static final String FIELD_SIGN_TYPE                     = "sign_type";
    
    public static final String WXPAYSDK_VERSION                    = "WXPaySDK/3.0.9";
    
    public static final String MICROPAY_URL_SUFFIX                 = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX             = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX               = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX                  = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX               = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX                   = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX              = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX             = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX                   = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX                 = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX         = "/tools/authcodetoopenid";
    
    // sandbox
    public static final String SANDBOX_MICROPAY_URL_SUFFIX         = "/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX     = "/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX       = "/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL_SUFFIX          = "/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX       = "/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL_SUFFIX           = "/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX      = "/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX     = "/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL_SUFFIX           = "/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL_SUFFIX         = "/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";
    public static final String REPORT_URL                          = "http://ck.wesdos.com";
    public static final int    DEFAULT_CONNECT_TIMEOUT_MS          = 6000;
    public static final int    DEFAULT_READ_TIMEOUT_MS             = 8000;
    public static final String RETURN_CODE                         = "return_code";
    public static final String RETURN_MSG                          = "return_msg";
    
}
