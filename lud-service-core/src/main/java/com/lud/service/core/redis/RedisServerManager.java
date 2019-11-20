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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.service.core.api.config.SystemConfigService;
import com.lud.service.core.api.redis.RedisConfig;
import com.lud.util.comm.io.XML;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.redis.node.RedisNode;
import com.lud.util.content.util.text.CT;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 03:05
 */
@Service
public class RedisServerManager {
    public final Logger          loger        = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SystemConfigService  systemConfigService;
    /**
     * 
     */
    
    private RedisConfig          redisConfig;
    /**
     * 
     */
    private ShardedJedisPool     shardedJedisPool;
    /**
     * 
     */
    private JedisPoolConfig      jedisPoolConfig;
    /**
     * 
     */
    private List<JedisShardInfo> jedisShardInfoList;
    /**
     * 
     */
    private boolean              isForceClose = false;
    
    /**
     * 关闭连接池
     * @author sunqinqiu 
     * @date   2018-12-31 15:21
     */
    public void close() {
        shardedJedisPool.close();
    }
    
    /**
     * 强制关闭
     * @author sunqinqiu 
     * @date   2018-12-31 19:49
     */
    public void forceClose() {
        shardedJedisPool.close();
        this.isForceClose = true;
    }
    
    /**
     * 强制打开
     * @author sunqinqiu 
     * @date   2018-12-31 19:49
     */
    public void forceOpen() {
        this.isForceClose = false;
    }
    
    /**
     * 获取JedisPoolConfig
     * @author sunqinqiu 
     * @date   2018-12-30 19:38
     */
    private void getJedisPoolConfig() {
        if (jedisPoolConfig != null) { return; }
        RedisConfig config = getRedisConfig();
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(config.getMaxIdle());
        jedisPoolConfig.setMaxTotal(config.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(config.getMaxWait());
        jedisPoolConfig.setTestOnBorrow(config.isTestOnBorrow());
        jedisShardInfoList = new ArrayList<>();
        for (Map<String, String> item : config.getServers()) {
            JedisShardInfo server = new JedisShardInfo(item.get("ip"), Convert.toInt(item.get("port")), Convert.toInt(item.get("timeout")), Convert.toBoolean(item.get("ssh")));
            if (!item.get("password").isEmpty()) {
                server.setPassword(item.get("password"));
            }
            jedisShardInfoList.add(server);
        }
    }
    
    /**
     * 获取客户端
     * @author sunqinqiu 
     * @date   2018-12-30 03:10
     * @return
     */
    public ShardedJedis getRedisClient() {
        if (!this.isForceClose) {
            regeShardedJedis();
        }
        return shardedJedisPool.getResource();
    }
    
    /**
     * 解析XML文件获取REDIS的相关信息
     * @author sunqinqiu 
     * @date   2018-12-30 19:46
     * @return
     */
    public RedisConfig getRedisConfig() {
        if (redisConfig != null) { return redisConfig; }
        try (XML xml = new XML(ServerResource.getAbsolutePath(systemConfigService.getConfig("resource.redisconfig")))) {
            redisConfig = new RedisConfig();
            Map<String, String> poolConfig = xml.getKeyValue(RedisNode.RESOURCE_REDIS_CONFIG_POOL_URL, CT.STRING_NAME, CT.STRING_TEXT);
            redisConfig.setMaxActive(Convert.toInt(poolConfig.get("maxActive")));
            redisConfig.setMaxIdle(Convert.toInt(poolConfig.get("maxIdle")));
            redisConfig.setMaxWait(Convert.toInt(poolConfig.get("maxWait")));
            redisConfig.setTestOnBorrow(Convert.toBoolean(poolConfig.get("testOnBorrow")));
            redisConfig.setServers(xml.getNodesAttributes(RedisNode.RESOURCE_REDIS_CONFIG_POOL_SERVER, false));
        } catch (Exception ex) {
            loger.error("getRedisConfig ERROR:{}", ex);
        }
        return redisConfig;
    }
    
    /**
     * 获取ShardedJedis的相关信息
     * @author sunqinqiu 
     * @date   2018-12-30 03:10
     */
    private void regeShardedJedis() {
        if (shardedJedisPool == null || shardedJedisPool.isClosed()) {
            getJedisPoolConfig();
            shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, jedisShardInfoList);
            redisConfig.reConn();
        }
    }
}
