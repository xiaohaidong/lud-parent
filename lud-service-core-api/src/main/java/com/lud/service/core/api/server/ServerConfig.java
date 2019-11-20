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
package com.lud.service.core.api.server;

import com.lud.service.core.api.config.BaseServerConfig;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2018-12-31 22:36
 */

public class ServerConfig extends BaseServerConfig {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * IP
     */
    @Getter
    @Setter
    private String            ip;
    /**
     * PORT
     */
    @Getter
    @Setter
    private int               port;
    /**
     * USER
     */
    @Getter
    @Setter
    private String            user;
    /**
     * PWD
     */
    @Getter
    @Setter
    private String            pwd;
}
