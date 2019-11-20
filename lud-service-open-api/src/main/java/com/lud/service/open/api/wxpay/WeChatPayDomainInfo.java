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
 * @date   2019-09-14 15:03
 */
public class WeChatPayDomainInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 当前的域名
     */
    @Getter
    private String            domain;
    /**
     * 是否主要域名
     */
    @Getter
    private boolean           primaryDomain;
    
    /**
     * 初始化
     */
    public WeChatPayDomainInfo(String domain, boolean primaryDomain) {
        this.domain = domain;
        this.primaryDomain = primaryDomain;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 14:33
     * @return
     */
    @Override
    public String toString() {
        return "DomainInfo{" + "domain='" + domain + '\'' + ", primaryDomain=" + primaryDomain + '}';
    }
}
