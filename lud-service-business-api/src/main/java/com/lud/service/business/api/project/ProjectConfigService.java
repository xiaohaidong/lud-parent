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
package com.lud.service.business.api.project;

import java.io.Serializable;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2019-10-26 13:57
 */
public interface ProjectConfigService {
    /**
     * 获取所有配置信息
     * @author sunqinqiu 
     * @date   2019-10-26 14:13
     * @param project
     * @return
     */
    Map<String, Serializable> getAllConfig(String project);
    
    /**
     * 获取某些配置
     * @author sunqinqiu 
     * @date   2019-10-26 14:10
     * @param project
     * @param keys
     * @return
     */
    Map<String, Serializable> getConfigMap(String project, String... keys);
    
    /**
     * 获取项目配置信息
     * @author sunqinqiu 
     * @date   2019-10-26 14:17
     * @param project
     * @param key
     * @return
     */
    String getConfig(String project, String key);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-27 15:05
     * @param project
     */
    void resolve(String project);
    
}
