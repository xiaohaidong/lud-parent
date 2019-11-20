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
package com.lud.service.open.api.message;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-10-25 05:50
 */
public class ShortMessage implements Serializable {
    /**
     * 
     */
    private static final long         serialVersionUID = 1L;
    
    /**
     * 配置信息
     */
    @Getter
    @Setter
    private Map<String, Serializable> config;
    
    /**
     * 数据
     */
    @Getter
    @Setter
    private Map<String, Serializable> data;
    /**
     * 发送接口
     */
    @Getter
    @Setter
    private String                    driver;
    /**
     * 发送号码
     */
    @Getter
    @Setter
    private String                    tels;
    
    /**
     * 模板
     */
    @Getter
    @Setter
    private String                    templement;
}
