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

/**
 * @author sunqinqiu 
 * @date   2019-09-14 21:30
 */
public interface WeChatPayReportService {
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 22:08
     * @param config
     * @param uuid
     * @param elapsedTimeMillis
     * @param firstDomain
     * @param primaryDomain
     * @param firstConnectTimeoutMillis
     * @param firstReadTimeoutMillis
     * @param firstHasDnsError
     * @param firstHasConnectTimeout
     * @param firstHasReadTimeout
     */
    boolean report(WeChatPayConfig config, String uuid, long elapsedTimeMillis, String firstDomain, boolean primaryDomain, int firstConnectTimeoutMillis, int firstReadTimeoutMillis, boolean firstHasDnsError, boolean firstHasConnectTimeout, boolean firstHasReadTimeout);
    
}
