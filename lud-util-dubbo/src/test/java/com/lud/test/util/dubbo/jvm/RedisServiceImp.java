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
package com.lud.test.util.dubbo.jvm;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.lud.service.core.api.redis.RedisService;

/**
 * @author sunqinqiu 
 * @date   2019-10-27 00:33
 */
@Service
public class RedisServiceImp implements RedisService {
    RedisServiceImp() {
        System.out.println("HERE is THE TEST RedisServiceImp!");
    }
    
    @Override
    public long del(String key) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public boolean exists(String key) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public long expire(String key, int minute) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public String get(String key) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Set<String> getAllKeys() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean getbit(String key, long offset) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Long hdel(String key, String field) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String hget(String key, String field) {
        System.out.println("HERE?");
        return null;
    }
    
    @Override
    public Long hincrby(String key, String field, int step) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<String> hmget(String key, String... field) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Map<String, String> hmgetAll(String key) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String hmset(String key, Map<String, String> hash) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Long hset(String key, String field, String value) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String set(String key, String value) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean setbit(String key, long offset, boolean value) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public long setrange(String key, long offset, String value) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public long ttl(String key) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public String type(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hexists(String key, String field) {
        // TODO Auto-generated method stub
        return false;
    }
}
