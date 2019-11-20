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
package com.lud.test.service.core.redis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lud.service.core.redis.RedisServerManager;
import com.lud.test.util.SpringServiceTester;
import com.lud.util.comm.runtime.RuntimeCounter;

import redis.clients.jedis.ShardedJedis;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 23:44
 */
public class RedisServerManagerTest extends SpringServiceTester {
    @Autowired
    private RedisServerManager redisServerManager;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-30 23:47
     */
    @Test
    public void test() {
        loger.trace("========Test Redis===========");
        ShardedJedis clinet = redisServerManager.getRedisClient();
        RuntimeCounter counter = new RuntimeCounter();
        clinet.set("com.lud.redis.status", JSON.toJSONString(redisServerManager.getRedisConfig()));
        counter.count("JISHI");
    }
    
}
