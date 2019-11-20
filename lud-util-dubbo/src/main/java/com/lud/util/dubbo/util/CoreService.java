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
package com.lud.util.dubbo.util;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.core.api.config.ResourceService;
import com.lud.service.core.api.data.command.DataCommandService;
import com.lud.service.core.api.data.query.DataQueryService;
import com.lud.service.core.api.redis.RedisService;
import com.lud.util.spring.util.SpringRuntimeService;

/**
 * @author sunqinqiu 
 * @date   2019-01-06 14:56
 */
public abstract class CoreService {
    /**
     * 日志系统
     */
    protected final Logger         loger = LoggerFactory.getLogger(this.getClass());
    /**
     * REDIS服务
     */
    @Reference
    protected RedisService         redisService;
    /**
     * 消息和资源调用
     */
    @Reference
    protected ResourceService      resourceService;
    /**
     * 数据库服务
     */
    @Reference
    protected DataQueryService     dataQueryService;
    /**
     * 命令服务
     */
    @Reference
    protected DataCommandService   dataCommandService;
    
    /**
     * 资源服务
     */
    @Autowired
    protected SpringRuntimeService springRuntimeService;
}
