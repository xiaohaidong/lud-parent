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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.core.api.redis.RedisService;
import com.lud.service.core.redis.RedisServerManager;
import com.lud.test.util.SpringServiceTester;

/**
 * @author sunqinqiu 
 * @date   2018-12-31 11:31
 */
public class RedisServiceTest extends SpringServiceTester {
    @Autowired
    private RedisService       redisService;
    @Autowired
    private RedisServerManager redisServerManager;
    
    @Test
    public void test() {
        String key = "com.lud.redis.test.string";
        redisService.set(key, "string");
        redisService.expire(key, 30);
        loger.trace("{}", redisService.get(key));
        loger.trace("{}", redisService.exists(key));
        loger.trace("{}", redisService.ttl(key));
        loger.trace("{}", redisService.del(key));
        
        key = "com.lud.redis.test.bit";
        redisService.setbit(key, 1, true);
        loger.trace("{}", redisService.getbit(key, 1));
        loger.trace("{}", redisService.del(key));
        key = "com.lud.redis.test.range";
        redisService.setrange(key, 1, "range");
        loger.trace("{}", redisService.getrange(key, 1, 10));
        loger.trace("{}", redisService.type(key));
        loger.trace("{}", redisService.del(key));
        key = "com.lud.redis.test.map";
        Map<String, String> item = new HashMap<>();
        item.put("name", "sun");
        item.put("sex", "n");
        redisService.hmset(key, item);
        redisService.hset(key, "cardid", "37");
        redisService.hset(key, "sex", "39");
        redisService.hget(key, "cardid");
        redisService.hincrby(key, "years", 1);
        loger.trace("{}", redisService.hget(key, "cardid"));
        loger.trace("{}", redisService.hmget(key, "cardid", "sex", "years"));
        loger.trace("{}", redisService.hmgetAll(key));
        redisService.hdel(key, "cardid");
        loger.trace("{}", redisService.del(key));
        loger.trace("{}", redisService.getAllKeys());
        // 断开连接
        redisServerManager.close();
        key = "com.lud.redis.test.string";
        redisService.set(key, "string");
        redisService.expire(key, 30);
        // 强制断开连接
        loger.trace("{}", redisService.del("com.lud.redis.status"));
        loger.trace("{}", redisService.del("com.lud.redis.test.string"));
        redisServerManager.forceClose();
        key = "com.lud.redis.test.string";
        redisService.set(key, "string");
        redisService.expire(key, 30);
        loger.trace("{}", redisService.get(key));
        loger.trace("{}", redisService.exists(key));
        loger.trace("{}", redisService.ttl(key));
        loger.trace("{}", redisService.del(key));
        
        key = "com.lud.redis.test.bit";
        redisService.setbit(key, 1, true);
        loger.trace("{}", redisService.getbit(key, 1));
        
        key = "com.lud.redis.test.range";
        redisService.setrange(key, 1, "range");
        loger.trace("{}", redisService.getrange(key, 1, 10));
        loger.trace("{}", redisService.type(key));
        
        key = "com.lud.redis.test.map";
        redisService.hmset(key, item);
        redisService.hset(key, "cardid", "37");
        redisService.hset(key, "sex", "39");
        redisService.hget(key, "cardid");
        redisService.hincrby(key, "years", 1);
        loger.trace("{}", redisService.hget(key, "cardid"));
        loger.trace("{}", redisService.hmget(key, "cardid", "sex", "years"));
        loger.trace("{}", redisService.hmgetAll(key));
        redisService.hdel(key, "cardid");
        loger.trace("{}", redisService.getAllKeys());
        loger.trace("{}", redisServerManager.getRedisConfig());
        redisServerManager.forceOpen();
    }
}
