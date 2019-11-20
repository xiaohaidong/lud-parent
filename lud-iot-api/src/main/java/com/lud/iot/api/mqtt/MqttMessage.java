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
package com.lud.iot.api.mqtt;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-10-17 04:32
 */
public class MqttMessage implements Serializable {
    
    /**
     * 
     */
    private static final long   serialVersionUID = 1L;
    /**
     * 
     */
    @Getter
    @Setter
    private String              name;
    /**
     * 
     */
    @Getter
    @Setter
    private String              runat;
    
    /**
     * 
     */
    @Getter
    @Setter
    private String              clientid;
    
    /**
     * 
     */
    @Getter
    @Setter
    private String              version;
    
    /**
     * 
     */
    @Getter
    @Setter
    private String              token;
    
    /**
     * 
     */
    @Getter
    @Setter
    private String              topic;
    
    /**
     * 
     */
    @Getter
    @Setter
    private Date                sendDate;
    
    /**
     * 
     */
    @Getter
    @Setter
    private String              action;
    
    /**
     * 
     */
    @Getter
    @Setter
    private Map<String, String> data;
    
}
