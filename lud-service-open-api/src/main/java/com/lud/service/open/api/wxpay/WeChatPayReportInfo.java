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
package com.lud.service.open.api.wxpay;

import java.io.Serializable;

import lombok.Getter;

/**
 * @author sunqinqiu 
 * @date   2019-09-14 19:14
 */
public class WeChatPayReportInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Getter
    private String            version          = "v1";
    @Getter
    private String            sdk              = "WXPaySDK/3.0.9";
    @Getter
    private String            uuid;
    // 交易的标识
    @Getter
    private long              timestamp;
    @Getter
    private long              elapsedTimeMillis;
    
    // 针对主域名
    @Getter
    private String            firstDomain;
    @Getter
    private boolean           primaryDomain;
    @Getter
    private int               firstConnectTimeoutMillis;
    @Getter
    private int               firstReadTimeoutMillis;
    @Getter
    private int               firstHasDnsError;
    @Getter
    private int               firstHasConnectTimeout;
    @Getter
    private int               firstHasReadTimeout;
    
    /**
     * 报告信息
     */
    public WeChatPayReportInfo(String uuid, long timestamp, long elapsedTimeMillis, String firstDomain, boolean primaryDomain, int firstConnectTimeoutMillis, int firstReadTimeoutMillis, boolean firstHasDnsError, boolean firstHasConnectTimeout, boolean firstHasReadTimeout) {
        this.uuid = uuid;
        this.timestamp = timestamp;
        this.elapsedTimeMillis = elapsedTimeMillis;
        this.firstDomain = firstDomain;
        this.primaryDomain = primaryDomain;
        this.firstConnectTimeoutMillis = firstConnectTimeoutMillis;
        this.firstReadTimeoutMillis = firstReadTimeoutMillis;
        this.firstHasDnsError = firstHasDnsError ? 1 : 0;
        this.firstHasConnectTimeout = firstHasConnectTimeout ? 1 : 0;
        this.firstHasReadTimeout = firstHasReadTimeout ? 1 : 0;
    }
}
