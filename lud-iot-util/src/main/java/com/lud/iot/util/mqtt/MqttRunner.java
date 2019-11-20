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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.util.spring.util.Preload;

/**
 * @author sunqinqiu 
 * @date   2019-10-15 03:43
 */
@Service
@Preload(seq = 10, fresh = false)
public class MqttRunner {
    public final Logger loger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MqttService mqttService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 19:36
     */
    public void run() {
        try {
            ExecutorService threadPool = Executors.newCachedThreadPool();
            threadPool.execute(() -> mqttService.run());
        } catch (Exception e) {
            loger.error(e.toString());
        }
    }
}
