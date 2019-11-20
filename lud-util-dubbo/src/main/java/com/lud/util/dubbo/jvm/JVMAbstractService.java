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
package com.lud.util.dubbo.jvm;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lud.service.core.api.redis.RedisService;
import com.lud.util.comm.runtime.JVMInfo;
import com.lud.util.content.redis.node.RuntimeNode;
import com.lud.util.spring.jvm.IJVMService;
import com.lud.util.spring.util.SpringRuntimeService;

/**
 * @author sunqinqiu 
 * @date   2019-10-20 16:40
 */

public abstract class JVMAbstractService implements IJVMService {
    
    @Autowired
    private SpringRuntimeService springRuntimeService;
    
    /**
     * 获取Redis
     * @author sunqinqiu 
     * @date   2019-10-25 21:10
     * @return
     */
    public abstract RedisService getRedisService();
    
    /**
     * 监测JVMinfo
     * @author sunqinqiu 
     * @date   2019-10-20 16:45
     * @param name
     * @param jvm
     */
    @Override
    public void monitorJVMInfo() {
        String name = springRuntimeService.getRunat() + "/" + springRuntimeService.getName();
        RedisService redis = getRedisService();
        if (redis != null) {
            redis.hset(RuntimeNode.IOT_JVM_NODE, name, JSON.toJSONString(JVMInfo.getRuntimeInfo()));
        }
    }
}
