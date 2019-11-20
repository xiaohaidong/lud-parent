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
package com.lud.iot.business.core.api.system.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-10-15 03:35
 */
public class SystemEntity implements Serializable {
    /**
     * 
     */
    private static final long  serialVersionUID = 1L;
    public static final String NODE             = "com.lud.system";
    /**
     * 
     */
    @Getter
    @Setter
    private String             server;
    /**
     * 
     */
    @Getter
    @Setter
    private List<NetWorkEntity>   network;
    /**
     * 
     */
    @Getter
    @Setter
    private OperatingEntity       operating;
    /**
     * 
     */
    @Getter
    @Setter
    private HardWearEntity        hardWear;
    
}
