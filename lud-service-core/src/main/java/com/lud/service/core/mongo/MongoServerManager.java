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
package com.lud.service.core.mongo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.service.core.api.config.ResourceService;
import com.lud.service.core.api.server.ServerConfig;
import com.lud.util.comm.io.XMLUtil;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.redis.node.RedisNode;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;

/**
 * @author sunqinqiu 
 * @date   2018-12-31 22:40
 */
@Service
public class MongoServerManager {
    /**
     * 
     */
    @Autowired
    private ResourceService resourceService;
    /**
     * 
     */
    private ServerConfig    mongoConfig;
    /**
     * 
     */
    private MongoClient     mongoClient;
    /**
     * 
     */
    private boolean         isForceClose = false;
    
    public void close() {
        mongoClient.close();
        mongoClient = null;
    }
    
    /**
     * 强制关闭
     * @author sunqinqiu 
     * @date   2018-12-31 19:49
     */
    public void forceClose() {
        mongoClient.close();
        mongoClient = null;
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
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 22:59
     * @return
     */
    public MongoClient getMongoClient() {
        if (mongoClient == null && !isForceClose) {
            ServerConfig config = getMongoConfig();
            Builder builder = new Builder();
            builder.connectionsPerHost(config.getMaxActive());
            builder.connectTimeout(config.getMaxWait());
            builder.maxConnectionIdleTime(config.getMaxIdle());
            mongoClient = new MongoClient(new ServerAddress(config.getIp(), config.getPort()), builder.build());
        }
        return mongoClient;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 22:59
     * @return
     */
    private ServerConfig getMongoConfig() {
        if (mongoConfig == null) {
            mongoConfig = new ServerConfig();
            Map<String, String> config = XMLUtil.resolveConfig(resourceService.getReourcePath(RedisNode.REDIS_FILED_FOR_RESOURCE_SERVER_MONGODB));
            mongoConfig.setIp(config.get("ip"));
            mongoConfig.setPort(Convert.toInt(config.get("port")));
            mongoConfig.setUser(config.get("user"));
            mongoConfig.setPwd(config.get("pwd"));
            mongoConfig.setMaxActive(Convert.toInt(config.get("maxActive")));
            mongoConfig.setMaxIdle(Convert.toInt(config.get("maxIdle")));
            mongoConfig.setMaxWait(Convert.toInt(config.get("maxWait")));
        }
        mongoConfig.reConn();
        return mongoConfig;
    }
}
