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

import java.util.HashMap;
import java.util.Map;

import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lud.iot.api.mqtt.MqttMessage;
import com.lud.util.comm.util.DEncrypt;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.comm.util.Transcode;
import com.lud.util.spring.util.SpringServiceInvoker;

import lombok.Getter;

/**
 * MQTT接收程序
 * @author sunqinqiu 
 * @date   2019-10-16 23:39
 */
@Service
public class MqttService {
    
    public final Logger      loger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MqttManager      mqttManager;
    
    private FutureConnection connection;
    
    private boolean          isRun = true;
    
    @Getter
    private long             error = 0;
    
    /**
     * 停止执行
     * @author sunqinqiu 
     * @date   2019-10-26 22:58
     */
    public void stopRun() {
        this.isRun = false;
    }
    
    /**
     * MQTT运行程序
     * @author sunqinqiu 
     * @date   2019-10-17 23:53
     */
    public void run() {
        conn();
        connection.subscribe(mqttManager.getReceiverTopic());
        while (isRun) {
            try {
                Future<Message> futrueMessage = connection.receive();
                Message message = futrueMessage.await();
                String content = Transcode.getString(message.getPayload(), "utf-8");
                MqttMessage mqttMessage = JSON.parseObject(content, MqttMessage.class);
                String token = mqttMessage.getToken();
                String clinet = mqttMessage.getClientid();
                String version = mqttMessage.getVersion();
                if (DEncrypt.getMD5Encode(clinet + version).equals(token)) {
                    SpringServiceInvoker.invoke(mqttMessage.getAction(), mqttMessage);
                }
                message.ack();
            } catch (Exception e) {
                this.error++;
                loger.error(e.getMessage());
            }
        }
    }
    
    public MqttMessage getNewMessage(String topic, String action) {
        MqttMessage message = new MqttMessage();
        Map<String, String> config = mqttManager.getMqttConfig();
        message.setName(config.get("app.name"));
        message.setRunat(config.get("app.runat"));
        message.setClientid(config.get("mqtt.clientid"));
        message.setTopic("mqtt/" + topic);
        message.setAction(action);
        message.setToken(config.get("mqtt.token"));
        message.setVersion(config.get("mqtt.version"));
        message.setSendDate(DateUtil.getNow());
        message.setData(new HashMap<>());
        return message;
    }
    
    public void conn() {
        if (connection == null) {
            MQTT mqtt = mqttManager.getMQTT();
            connection = mqtt.futureConnection();
            connection.connect();
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-24 17:47
     * @param message
     */
    public void send(MqttMessage message) {
        try {
            conn();
            String content = JSON.toJSONString(message);
            connection.publish(message.getTopic(), content.getBytes("utf-8"), QoS.EXACTLY_ONCE, false);
        } catch (Exception e) {
            loger.error(e.toString());
        }
    }
}
