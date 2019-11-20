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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lud.service.open.api.wxpay.WeChatPayDomainInfo;
import com.lud.service.open.api.wxpay.WeChatPayDomainService;
import com.lud.util.content.open.tencent.WeChatPay;

/**
 * @author sunqinqiu 
 * @date   2019-09-14 18:30
 */
@Service
public class WeChatPayDomainServiceImp implements WeChatPayDomainService {
    private final Logger loger               = LoggerFactory.getLogger(this.getClass());
    /**
     * 
     */
    WeChatPayDomainInfo  weChatPayDomainInfo = new WeChatPayDomainInfo(WeChatPay.DOMAIN_API, true);
    
    /**
     * 获取Domain信息
     * @author sunqinqiu 
     * @date   2019-09-14 18:23
     * @return
     */
    @Override
    public WeChatPayDomainInfo getWeChatPayDomainInfo() {
        return weChatPayDomainInfo;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 18:31
     * @param domain
     * @param elapsed
     * @param ex
     */
    @Override
    public void report(String domain, long elapsed, Exception ex) {
        loger.info("====This is the report=====");
    }
}
