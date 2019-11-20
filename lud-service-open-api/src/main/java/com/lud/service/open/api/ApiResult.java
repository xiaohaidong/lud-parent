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
package com.lud.service.open.api;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-09-06 11:17
 */

public class ApiResult implements Serializable {
    
    /**
     * 
     */
    private static final long         serialVersionUID = 1L;
    
    /**
     * 状态
     */
    @Getter
    @Setter
    private boolean                   success          = false;
    
    /**
     * 
     */
    @Getter
    @Setter
    private boolean                   valid            = false;
    
    /**
     * 消息
     */
    @Getter
    @Setter
    private String                    message          = "NoMessage";
    
    /**
     * 操作时间
     */
    @Getter
    @Setter
    private Date                      time             = new Date();
    
    /**
     * 获取数据
     */
    @Getter
    @Setter
    private Map<String, Serializable> data;
}
