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

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2019-09-19 07:39
 */
public class ShortMessageConfig implements Serializable {
    
    /**
     * 
     */
    private static final long         serialVersionUID = 1L;
    /**
     * 项目
     */
    @Getter
    @Setter
    private String                    project;
    /**
     * 驱动
     */
    @Getter
    @Setter
    private String                    driver;
    /**
     * 配置
     */
    @Getter
    @Setter
    private Map<String, Serializable> config;
    /**
     * 费率
     */
    @Getter
    @Setter
    private double                    feelv;
}
