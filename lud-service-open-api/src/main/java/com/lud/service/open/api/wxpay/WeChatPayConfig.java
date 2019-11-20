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
import lombok.Setter;

public class WeChatPayConfig implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 当前项目
     */
    @Setter
    @Getter
    private String            project;
    
    /**
     * AppID
     */
    @Setter
    @Getter
    private String            appID;
    
    /**
     * MchID
     */
    @Setter
    @Getter
    private String            mchID;
    
    /**
     * Key
     */
    @Setter
    @Getter
    private String            key;
    
    /**
     * 证书
     */
    @Setter
    @Getter
    private String            cert;
}
