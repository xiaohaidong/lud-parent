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

import com.lud.service.core.api.config.SystemConfigService;
import com.lud.util.comm.io.XMLUtil;
import com.lud.util.content.redis.node.RedisNode;
import com.lud.util.spring.util.Preload;

/**
 * @author sunqinqiu 
 * @date   2019-01-01 01:36
 */
@Service
@Preload(seq = 0, fresh = false)
public class ConfigServiceImp implements SystemConfigService {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    Map<String, String>  config;
    
    /**
     * 获取配置信息
     * @author sunqinqiu 
     * @date   2019-10-25 22:25
     * @param key
     * @return
     */
    @Override
    public String getConfig(String key) {
        return config.get(key);
    }
    
    /**
     * 运行命令
     * @author sunqinqiu 
     * @date   2019-10-25 22:25
     */
    @Override
    public void run() {
        loger.trace("SystemConfig is loadding....");
        config = XMLUtil.resolveRootConfigByGroup(RedisNode.RESOURCE_SYSTEM_CONFIG_PATH);
    }
}
