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
 * 资源配置
 * @author sunqinqiu 
 * @date   2018-12-31 15:43
 */
public interface ResourceService {
    /**
     * 获取错误信息
     * @author sunqinqiu 
     * @date   2019-09-11 13:46
     * @param key
     * @return
     */
    String getError(String key);
    
    /**
     * 获取消息信息
     * @author sunqinqiu 
     * @date   2019-09-11 13:46
     * @param key
     * @return
     */
    String getMessage(String key);
    
    /**
     * 获取资源文件路径
     * @author sunqinqiu 
     * @date   2019-09-11 13:46
     * @param key
     * @return
     */
    String getReourcePath(String key);
    
    /**
     * 解析资源文件
     * @author sunqinqiu 
     * @date   2019-09-11 13:45
     */
    void run();
    
}
