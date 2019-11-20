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
package com.lud.util.content.redis.node;

/**
 * 和路径有关的配置
 * @author sunqinqiu 
 * @date   2018-12-30 23:28
 */
public class RedisNode {
    /**
     * Redis Server Config Path
     */
    public static final String RESOURCE_SYSTEM_CONFIG_PATH             = "/resource-system/config-runtime/system.xml";
    public static final String RESOURCE_REDIS_CONFIG_POOL_URL          = "/configuration/pool/item";
    public static final String RESOURCE_REDIS_CONFIG_POOL_SERVER       = "/configuration/pools/item";
    
    /**
     * Redis Resource path
     */
    public static final String REDIS_NODE_FOR_RESOURCE_PATH            = "com.lud.resource.path";
    
    /**
     * Redis Message
     */
    public static final String REDIS_NODE_FOR_RESOURCE_MESSAGE         = "com.lud.resource.message";
    public static final String REDIS_NODE_FOR_RESOURCE_ERROR           = "com.lud.resource.error";
    
    /**
     * 消息,XML中的路径
     */
    public static final String REDIS_FILED_FOR_MESSAGE_MESSAGE         = "message.message";
    public static final String REDIS_FILED_FOR_MESSAGE_ERROR           = "message.error";
    public static final String REDIS_FILED_FOR_RESOURCE_SERVER_MONGODB = "server.mongodb";
    public static final String REDIS_FILED_FOR_RESOURCE_SERVER_AMQP    = "server.amqp";
    public static final String REDIS_FILED_FOR_RESOURCE_SERVER_MTQQ    = "server.mqtt";
    
    /**
     * 数据库
     */
    public static final String REDIS_NODE_FOR_DATA_QUERY               = "com.lud.resource.data.query";
    public static final String REDIS_NODE_FOR_DATA_COMMAND             = "com.lud.resource.data.command";
    
    private RedisNode() {}
    
}
