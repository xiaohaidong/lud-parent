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
package com.lud.service.business.api.project.domain;

/**
 * @author sunqinqiu 
 * @date   2019-01-05 17:44
 */
public interface DomainService {
    
    /**
     * 根据域名获取domain信息
     * @author sunqinqiu 
     * @date   2019-06-13 12:35
     * @param domain
     * @return
     */
    String getProjectCodeByDomain(String domain);
    
    /**
     * 根据domain信息获取ROOT根目录
     * @author sunqinqiu 
     * @date   2019-06-13 12:35
     * @param domain
     * @return
     */
    String getRootByDomain(String domain);
    
    /**
     * 解析所有数据
     * @author sunqinqiu 
     * @date   2019-06-13 12:35
     */
    void run();
    
}
