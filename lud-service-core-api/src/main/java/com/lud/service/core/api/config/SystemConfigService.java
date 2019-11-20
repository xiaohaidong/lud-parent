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
package com.lud.service.core.api.config;

/**
 * 系统配置服务
 * @author sunqinqiu 
 * @date   2019-01-01 01:36
 */
public interface SystemConfigService {
    /**
     * 获取配置项
     * @author sunqinqiu 
     * @date   2019-09-11 13:47
     * @param key
     * @return
     */
    String getConfig(String key);
    
    /**
     * 解析系统配置文件
     * @author sunqinqiu 
     * @date   2019-09-11 13:47
     */
    void run();
}
