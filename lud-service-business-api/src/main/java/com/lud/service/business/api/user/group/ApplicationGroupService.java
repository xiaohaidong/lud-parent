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

import java.util.List;

/**
 * @author sunqinqiu 
 * @date   2019-10-26 13:09
 */
public interface ApplicationGroupService {
    /**
     * 获取某个用户组下的所有应用
     * @author sunqinqiu 
     * @date   2019-01-08 19:52
     * @param project
     * @param userGroup
     * @return
     */
    List<ApplicationGroup> getProjectApplicationGroup(String project, String userGroup);
    
    /**
     * 解析某个用户组下的所有的应用程序组
     * @author sunqinqiu 
     * @date   2019-10-26 13:22
     * @param project
     * @param userGroup
     * @return 
     */
    void resolve(String project, String userGroup);
    
}
