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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lud.iot.api.mqtt.MqttMessage;
import com.lud.iot.util.mqtt.MqttService;
import com.lud.util.spring.util.Preload;

/**
 * @author sunqinqiu 
 * @date   2019-10-20 12:37
 */
@Service("com.lud.iot.business.core.client.system.systemmonitor")
@Preload(seq = 20)
public class SystemMonitorClientService {
    public final Logger      loger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MqttService      mqttService;
    @Autowired
    private SystemManagement systemManagement;
    
    /**
     * 发送信息
     * @author sunqinqiu 
     * @date   2019-10-20 12:42
     */
    public void run() {
        try {
            MqttMessage message = mqttService.getNewMessage("server/public", "com.lud.iot.business.core.server.system.systemmonitor.regeSystemInfo");
            systemManagement.resolStaticConfig();
            message.getData().put("sys", JSON.toJSONString(systemManagement.getSystem()));
            Thread.sleep(1000);
            for (int i = 0; i < 3; i++) {
                mqttService.send(message);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            loger.error("{}", e);
        }
    }
}
