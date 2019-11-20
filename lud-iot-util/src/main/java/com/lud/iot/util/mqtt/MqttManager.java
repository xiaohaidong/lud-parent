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
package com.lud.iot.util.mqtt;

import java.util.Map;

import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.RandomUtil;
import com.lud.util.spring.util.Preload;
import com.lud.util.spring.util.SpringRuntimeService;

/**
 * @author sunqinqiu 
 * @date   2019-10-16 13:41
 */
@Service
@Preload
public class MqttManager {
    public final Logger          loger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 
     */
    private Map<String, String>  mqttConfig;
    /**
     * 
     */
    @Autowired
    private SpringRuntimeService springRuntimeService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-17 23:33
     */
    public void run() {
        this.mqttConfig = springRuntimeService.getOptions();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-17 00:11
     * @param config
     * @return
     */
    public MQTT getMQTT() {
        try {
            MQTT mqtt = new MQTT();
            mqtt.setHost(this.mqttConfig.get("mqtt.ip"));
            mqtt.setCleanSession(true);
            mqtt.setUserName(this.mqttConfig.get("mqtt.user"));
            mqtt.setPassword(this.mqttConfig.get("mqtt.pwd"));
            mqtt.setReconnectAttemptsMax(Convert.toInt(this.mqttConfig.get("mqtt.reconnmax")));
            mqtt.setReconnectDelay(Convert.toInt(this.mqttConfig.get("mqtt.reconndelay")));
            mqtt.setKeepAlive((short) Convert.toInt(this.mqttConfig.get("mqtt.alive")));
            mqtt.setSendBufferSize(Convert.toInt(this.mqttConfig.get("mqtt.buffersize")) * 1024 * 1024);
            mqtt.setClientId(RandomUtil.getUID());
            return mqtt;
        } catch (Exception e) {
            loger.error("{}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 增加密码
     * @author sunqinqiu 
     * @date   2019-10-18 00:35
     * @return
     */
    public Map<String, String> getMqttConfig() {
        return mqttConfig;
    }
    
    /**
     * 获取监听topics
     * @author sunqinqiu 
     * @date   2019-10-17 23:42
     * @return
     */
    public Topic[] getReceiverTopic() {
        String receiversContent = mqttConfig.get("mqtt.clientid") + "," + mqttConfig.get("mqtt.receivers");
        String[] receivers = receiversContent.split(",");
        Topic[] topics = new Topic[receivers.length];
        for (int i = 0; i < receivers.length; i++) {
            topics[i] = new Topic("mqtt/" + receivers[i], QoS.EXACTLY_ONCE);
        }
        return topics;
    }
    
}