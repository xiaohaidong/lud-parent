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
package com.lud.service.open.api.message;

import java.io.Serializable;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2019-09-16 14:34
 */
public interface ShortMessageBusinessService {
    /**
     * 解析发送短信配置文件
     * @author sunqinqiu 
     * @date   2019-09-19 07:19
     */
    void run();
    
    /**
     * 发送短信是否成功
     * @author sunqinqiu 
     * @date   2019-10-26 01:48
     * @param project
     * @param tels
     * @param templement
     * @param content
     * @return
     */
    boolean send(String project, String tels, String templement, Map<String, Serializable> content);
    
}
