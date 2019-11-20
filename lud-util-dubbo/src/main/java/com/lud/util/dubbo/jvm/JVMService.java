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
package com.lud.util.dubbo.jvm;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import com.lud.service.core.api.redis.RedisService;

/**
 * @author sunqinqiu 
 * @date   2019-10-20 16:28
 */
@Service
public class JVMService extends JVMAbstractService {
    /**
     * 
     */
    @Reference
    private RedisService redis;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 21:11
     * @return
     */
    @Override
    public RedisService getRedisService() {
        return redis;
    }
}
