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
package com.lud.iot.business.core.client.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lud.iot.api.mqtt.MqttMessage;
import com.lud.iot.util.mqtt.MqttService;
import com.lud.util.comm.runtime.JVMInfo;
import com.lud.util.spring.jvm.IJVMService;

/**
 * @author sunqinqiu 
 * @date   2019-10-23 12:11
 */
@Service
public class JVMService implements IJVMService {
    @Autowired
    private MqttService mqttService;
    
    @Override
    public void monitorJVMInfo() {
        MqttMessage message = mqttService.getNewMessage("server/public", "com.lud.iot.business.core.server.system.systemmonitor.regeJVMInfo");
        message.getData().put("jvm", JSON.toJSONString(JVMInfo.getRuntimeInfo()));
        mqttService.send(message);
    }
}
