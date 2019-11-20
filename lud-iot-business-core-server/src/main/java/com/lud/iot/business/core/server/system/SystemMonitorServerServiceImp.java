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
package com.lud.iot.business.core.server.system;

import org.springframework.stereotype.Service;

import com.lud.iot.api.mqtt.MqttMessage;
import com.lud.util.content.redis.node.RuntimeNode;
import com.lud.util.dubbo.util.CoreService;

/**
 * @author sunqinqiu 
 * @date   2019-10-20 12:51
 */
@Service("com.lud.iot.business.core.server.system.systemmonitor")
public class SystemMonitorServerServiceImp extends CoreService {
    /**
     * 总体信息
     * @author sunqinqiu 
     * @date   2019-10-20 13:14
     * @param message
     */
    public void regeSystemInfo(MqttMessage message) {
        String content = message.getData().get("sys");
        this.redisService.hset(RuntimeNode.IOT_CLIENT_NODE, message.getRunat(), content);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-24 23:26
     * @param message
     */
    public void regeJVMInfo(MqttMessage message) {
        String content = message.getData().get("jvm");
        this.redisService.hset(RuntimeNode.IOT_JVM_NODE, message.getRunat() + "/" + message.getName() + "/" + message.getClientid(), content);
    }
}
