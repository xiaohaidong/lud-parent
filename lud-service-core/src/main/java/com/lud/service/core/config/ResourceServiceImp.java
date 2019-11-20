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
package com.lud.service.core.config;

import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.core.api.config.ResourceService;
import com.lud.service.core.api.config.SystemConfigService;
import com.lud.service.core.api.redis.RedisService;
import com.lud.util.comm.io.XMLUtil;
import com.lud.util.content.redis.node.RedisNode;
import com.lud.util.spring.util.Preload;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 03:30
 */
@Service
@Preload(seq = 1)
public class ResourceServiceImp implements ResourceService {
    public final Logger         loger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisService        redisService;
    @Autowired
    private SystemConfigService systemConfigService;
    
    /**
     * 解析所有资源
     * @author sunqinqiu 
     * @date   2018-12-31 15:45
     */
    @Override
    public void run() {
        resolveResourcePaths();
        resolveMessage();
        resolveError();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 20:11
     * @param key
     * @return
     */
    @Override
    public String getError(String key) {
        return "ERROR[" + key + "]" + this.redisService.hget(RedisNode.REDIS_NODE_FOR_RESOURCE_ERROR, key);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 20:11
     * @param key
     * @return
     */
    @Override
    public String getMessage(String key) {
        return this.redisService.hget(RedisNode.REDIS_NODE_FOR_RESOURCE_MESSAGE, key);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-31 20:02
     * @param key
     * @return
     */
    @Override
    public String getReourcePath(String key) {
        return this.redisService.hget(RedisNode.REDIS_NODE_FOR_RESOURCE_PATH, key);
    }
    
    /**
     * 解析Message
     * @author sunqinqiu 
     * @date   2018-12-31 15:53
     */
    private void resolveError() {
        Map<String, String> map = XMLUtil.resolveRootConfigByGroup(getReourcePath(RedisNode.REDIS_FILED_FOR_MESSAGE_ERROR));
        redisService.hmset(RedisNode.REDIS_NODE_FOR_RESOURCE_ERROR, map);
    }
    
    /**
     * 解析Message
     * @author sunqinqiu 
     * @date   2018-12-31 15:53
     */
    private void resolveMessage() {
        Map<String, String> map = XMLUtil.resolveRootConfigByGroup(getReourcePath(RedisNode.REDIS_FILED_FOR_MESSAGE_MESSAGE));
        redisService.hmset(RedisNode.REDIS_NODE_FOR_RESOURCE_MESSAGE, map);
    }
    
    /**
     * 解析所有资源的路径
     * @author sunqinqiu 
     * @date   2018-12-31 15:52
     */
    private void resolveResourcePaths() {
        Map<String, String> map = XMLUtil.resolveRootConfigByGroup(systemConfigService.getConfig("resource.resourcepath"));
        redisService.hmset(RedisNode.REDIS_NODE_FOR_RESOURCE_PATH, map);
    }
    
}
