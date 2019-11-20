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
import java.util.List;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2019-01-05 17:43
 */
public interface ProjectService {
    
    /**
     * 解析项目信息
     * @author sunqinqiu 
     * @date   2019-02-19 20:08
     */
    void run();
    
    /**
     * 获取项目基础信息
     * @author sunqinqiu 
     * @date   2019-10-26 15:30
     * @param project
     * @return
     */
    Map<String, Serializable> getProjectInfo(String project);
    
    /**
     * 获取项目列表
     * @author sunqinqiu 
     * @date   2019-10-26 17:31
     * @return
     */
    List<Map<String, Serializable>> getProjectList();
}
