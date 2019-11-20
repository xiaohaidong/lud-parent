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
package com.lud.util.web.api.view;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-03-03 12:04
 */
public class ViewCommandEntity implements Serializable {
    
    /**
     * 
     */
    private static final long   serialVersionUID = 1L;
    
    @Getter
    @Setter
    private String              name;
    
    @Getter
    @Setter
    private String              flag;
    
    @Getter
    @Setter
    private String              args;
    /**
     * 执行方法
     */
    @Getter
    @Setter
    private String              invoke;
    /**
     * 
     */
    @Getter
    @Setter
    private boolean             list;
    /**
     * 
     */
    @Getter
    @Setter
    private String              dataName;
    
    /**
     * 
     */
    @Getter
    @Setter
    private String              action;
    
    /**
     * 配置文件
     */
    @Getter
    @Setter
    private Map<String, String> config;
}
