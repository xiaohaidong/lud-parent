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
 * @date   2019-09-14 22:37
 */
public interface WeChatPayRequestService {
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 22:40
     * @param config
     * @param suffix
     * @param uuid
     * @param data
     * @param autoReport
     * @return
     * @throws Exception
     */
    String requestWithCert(WeChatPayConfig config, String suffix, String uuid, String data, boolean autoReport);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 22:40
     * @param config
     * @param suffix
     * @param uuid
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @param autoReport
     * @return
     * @throws Exception
     */
    String requestWithCert(WeChatPayConfig config, String suffix, String uuid, String data, int connectTimeoutMs, int readTimeoutMs, boolean autoReport);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 22:40
     * @param config
     * @param suffix
     * @param uuid
     * @param data
     * @param autoReport
     * @return
     * @throws Exception
     */
    String requestWithoutCert(WeChatPayConfig config, String suffix, String uuid, String data, boolean autoReport);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 22:40
     * @param config
     * @param suffix
     * @param uuid
     * @param data
     * @param connectTimeoutMs
     * @param readTimeoutMs
     * @param autoReport
     * @return
     * @throws Exception
     */
    String requestWithoutCert(WeChatPayConfig config, String suffix, String uuid, String data, int connectTimeoutMs, int readTimeoutMs, boolean autoReport);
    
}
