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

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-10-16 02:14
 */
public class NetWorkEntity implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Getter
    @Setter
    private String            ip;
    @Getter
    @Setter
    private String            name;
    @Getter
    @Setter
    private int               mtu;
    @Getter
    @Setter
    private String            title;
    @Getter
    @Setter
    private String            mac;
    @Getter
    @Setter
    private long              recv;
    @Getter
    @Setter
    private long              recvps;
    @Getter
    @Setter
    private long              recverr;
    @Getter
    @Setter
    private long              sent;
    @Getter
    @Setter
    private long              sentps;
    @Getter
    @Setter
    private long              senterr;
    @Getter
    @Setter
    private long              speed;
    
}
