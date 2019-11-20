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
package com.lud.service.core.api.config;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 基础服务器配置
 * @author sunqinqiu 
 * @date   2019-01-06 23:28
 */
public class BaseServerConfig implements Serializable {
    
    /**
     * 序号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 最大活动数
     */
    @Getter
    @Setter
    private int               maxActive;
    
    /**
     * 最大空闲数
     */
    @Getter
    @Setter
    private int               maxIdle;
    
    /**
     * 最大等待数
     */
    @Getter
    @Setter
    private int               maxWait;
    
    /**
     * 连接次数
     */
    @Getter
    @Setter
    private int               connTimes        = 0;
    
    /**
     * 连接次数
     * @author sunqinqiu 
     * @date   2019-09-17 19:46
     */
    public void reConn() {
        this.connTimes++;
    }
}
