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
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-09-15 18:40
 */
public class WeChatPayResult implements Serializable {
    
    /**
     * 
     */
    private static final long   serialVersionUID = 1L;
    @Getter
    @Setter
    private boolean             valid;
    @Getter
    @Setter
    private boolean             result;
    @Getter
    @Setter
    private String              message;
    @Getter
    @Setter
    private Map<String, String> content;
    
    public WeChatPayResult() {
        valid = false;
        result = false;
    }
}
