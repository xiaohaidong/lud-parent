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
package com.lud.service.business.api.user.group;

/**
 * @author sunqinqiu 
 * @date   2019-01-06 20:45
 */
public interface UserGroupService {
    
    /**
     * 解析程序
     * @author sunqinqiu 
     * @date   2019-10-26 12:58
     */
    void run();
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 16:42
     * @param project
     * @param userGroupCode
     * @return
     */
    UserGroupEntity getUserGroupEntity(String project, String userGroupCode);
    
}
