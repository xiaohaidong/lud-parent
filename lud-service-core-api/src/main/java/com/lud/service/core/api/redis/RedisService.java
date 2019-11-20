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
package com.lud.service.core.api.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sunqinqiu 
 * @date   2018-12-29 03:57
 */
public interface RedisService {
    /**
     * 删除
     * @author sunqinqiu 
     * @date   2019-09-11 14:04
     * @param key
     * @return
     */
    long del(String key);
    
    /**
     * 是否存在
     * @author sunqinqiu 
     * @date   2019-09-11 14:04
     * @param key
     * @return
     */
    boolean exists(String key);
    
    /**
     * 有效期限
     * @author sunqinqiu 
     * @date   2019-09-11 14:04
     * @param key
     * @param minute
     * @return
     */
    long expire(String key, int minute);
    
    /**
     * 获取值
     * @author sunqinqiu 
     * @date   2019-09-11 14:05
     * @param key
     * @return
     */
    String get(String key);
    
    /**
     * 获取所有的KEY
     * @author sunqinqiu 
     * @date   2019-09-11 14:04
     * @return
     */
    Set<String> getAllKeys();
    
    /**
     * 获取BIT信息
     * @author sunqinqiu 
     * @date   2019-09-11 14:05
     * @param key
     * @param offset
     * @return
     */
    boolean getbit(String key, long offset);
    
    /**
     * 获取
     * @author sunqinqiu 
     * @date   2019-09-11 14:06
     * @param key
     * @param startOffset
     * @param endOffset
     * @return
     */
    String getrange(String key, long startOffset, long endOffset);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 14:26
     * @param key
     * @param field
     * @return
     */
    Long hdel(String key, String field);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 14:26
     * @param key
     * @param field
     * @return
     */
    String hget(String key, String field);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 14:26
     * @param key
     * @param field
     * @param step
     * @return
     */
    Long hincrby(String key, String field, int step);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-28 01:36
     * @param key
     * @param field
     * @return
     */
    boolean hexists(String key, String field);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 14:26
     * @param key
     * @param field
     * @return
     */
    List<String> hmget(String key, String... field);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 14:26
     * @param key
     * @return
     */
    Map<String, String> hmgetAll(String key);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 14:26
     * @param key
     * @param hash
     * @return
     */
    String hmset(String key, Map<String, String> hash);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 14:26
     * @param key
     * @param field
     * @param value
     * @return
     */
    Long hset(String key, String field, String value);
    
    /**
     * 设置
     * @author sunqinqiu 
     * @date   2019-09-11 14:04
     * @param key
     * @param value
     * @return
     */
    String set(String key, String value);
    
    /**
     * 设置Bit信息
     * @author sunqinqiu 
     * @date   2019-09-11 14:05
     * @param key
     * @param offset
     * @param value
     * @return
     */
    boolean setbit(String key, long offset, boolean value);
    
    /**
     * 设置数值
     * @author sunqinqiu 
     * @date   2019-09-11 14:05
     * @param key
     * @param offset
     * @param value
     * @return
     */
    long setrange(String key, long offset, String value);
    
    /**
     * 设置TTL
     * @author sunqinqiu 
     * @date   2019-09-11 14:04
     * @param key
     * @return
     */
    long ttl(String key);
    
    /**
     * 类型
     * @author sunqinqiu 
     * @date   2019-09-11 14:03
     * @param key
     * @return
     */
    String type(String key);
    
}
