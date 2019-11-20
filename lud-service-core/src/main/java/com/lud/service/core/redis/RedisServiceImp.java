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
package com.lud.service.core.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.core.api.redis.RedisService;
import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.util.text.CT;

import redis.clients.jedis.ShardedJedis;

/**
 * @author sunqinqiu 
 * @date   2018-12-29 03:57
 */
@Service
public class RedisServiceImp implements RedisService {
    
    public final Logger        loger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private RedisServerManager redisServerManager;
    
    /**
     * 删除某个键
     * @author sunqinqiu 
     * @date   2018-08-21 16:13
     * @param key
     */
    @Override
    public long del(String key) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.del(key);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return -1;
        }
    }
    
    /**
     * 是否存在这个值
     * @author sunqinqiu 
     * @date   2018-05-12 19:01
     * @param key
     * @return
     */
    @Override
    public boolean exists(String key) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.exists(key);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return false;
        }
    }
    
    /**
     * 设置多少秒后过期
     * @author sunqinqiu 
     * @date   2018-05-12 19:03
     * @param key
     * @param seconds
     * @return
     */
    @Override
    public long expire(String key, int minute) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.expire(key, minute * 60);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return -1;
        }
    }
    
    /**
    * 获取单个值
    * @author sunqinqiu 
    * @date   2018-05-12 18:49
    * @param key
    * @return
    */
    @Override
    public String get(String key) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.get(key);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return CT.EMPTY;
        }
    }
    
    /**
     * 获取所有KEYS
     * @author sunqinqiu 
     * @date   2018-12-31 11:28
     * @return
     */
    @Override
    public Set<String> getAllKeys() {
        Set<String> keys = new HashSet<>();
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            client.getAllShards().forEach(item -> keys.addAll(item.keys("*")));
        } catch (Exception ex) {
            loger.error(ex.toString());
        }
        return keys;
    }
    
    /**
     * 获取BIT单个值
     * @author sunqinqiu 
     * @date   2018-05-12 18:49
     * @param key
     * @return
     */
    @Override
    public boolean getbit(String key, long offset) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.getbit(key, offset);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return false;
        }
    }
    
    /**
     * 获取参数值
     * @author sunqinqiu 
     * @date   2018-05-12 19:31
     * @param key
     * @param startOffset
     * @param endOffset
     * @return
     */
    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.getrange(key, startOffset, endOffset);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return CT.EMPTY;
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 12:45
     * @param key
     * @param field
     * @return
     */
    @Override
    public Long hdel(String key, String field) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.hdel(key, field);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return Content.LONG_EMPTY;
        }
    }
    
    @Override
    public boolean hexists(String key, String field) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.hexists(key, field);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return false;
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 12:00
     * @param key
     * @param field
     * @return
     */
    @Override
    public String hget(String key, String field) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.hget(key, field);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return CT.EMPTY;
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 12:38
     * @param key
     * @param field
     * @param step
     * @return
     */
    @Override
    public Long hincrby(String key, String field, int step) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.hincrBy(key, field, step);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return Content.LONG_EMPTY;
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 12:10
     * @param key
     * @param field
     * @return
     */
    @Override
    public List<String> hmget(String key, String... field) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.hmget(key, field);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return ListMapUtil.getEmptyList();
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 12:45
     * @param key
     * @return
     */
    @Override
    public Map<String, String> hmgetAll(String key) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.hgetAll(key);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return ListMapUtil.getEmptyMap();
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 12:00
     * @param key
     * @param field
     * @return
     */
    @Override
    public String hmset(String key, Map<String, String> hash) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.hmset(key, hash);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return hmsset(key, hash);
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 19:55
     * @param key
     * @param hash
     * @return
     */
    private String hmsset(String key, Map<String, String> hash) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            for (Entry<String, String> field : hash.entrySet()) {
                client.hset(key, field.getKey(), hash.get(field.getValue()));
            }
            return hash.size() + "";
        } catch (Exception ex) {
            loger.error(ex.toString());
            return CT.EMPTY;
        }
    }
    
    /**
     * hest
     * @author sunqinqiu 
     * @date   2018-12-31 11:58
     * @param key
     * @param field
     * @param value
     * @return
     */
    @Override
    public Long hset(String key, String field, String value) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.hset(key, field, value);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return Content.LONG_EMPTY;
        }
    }
    
    /**
     * 设置单个值
     * @author sunqinqiu 
     * @date   2018-05-12 16:50
     * @param key
     * @param value
     * @return
     */
    @Override
    public String set(String key, String value) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.set(key, value);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return CT.EMPTY;
        }
    }
    
    /**
     * 设置BIT单个值
     * @author sunqinqiu 
     * @date   2018-05-12 16:50
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean setbit(String key, long offset, boolean value) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.setbit(key, offset, value);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return false;
        }
    }
    
    /**
     * 设置单数据
     * @author sunqinqiu 
     * @date   2018-05-12 19:29
     * @param key
     * @param offset
     * @param value
     * @return
     */
    @Override
    public long setrange(String key, long offset, String value) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.setrange(key, offset, value);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return 0;
        }
    }
    
    /**
     * 查看还有多长时间过期
     * @author sunqinqiu 
     * @date   2018-05-12 19:09
     * @param key
     * @return
     */
    @Override
    public long ttl(String key) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.ttl(key) / 60;
        } catch (Exception ex) {
            loger.error(ex.toString());
            return 0;
        }
    }
    
    /**
     * 获取类型
     * @author sunqinqiu 
     * @date   2018-05-12 18:58
     * @param key
     * @return
     */
    @Override
    public String type(String key) {
        try (ShardedJedis client = redisServerManager.getRedisClient()) {
            return client.type(key);
        } catch (Exception ex) {
            loger.error(ex.toString());
            return null;
        }
    }
    
}
