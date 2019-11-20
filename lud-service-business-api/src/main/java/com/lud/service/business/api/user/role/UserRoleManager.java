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
package com.lud.service.business.api.user.role;

import java.util.List;

/**
 * @author sunqinqiu 
 * @date   2019-01-06 20:48
 */
public interface UserRoleManager {
    /**
     * 获取用户的权限菜单
     * @author sunqinqiu 
     * @date   2019-10-26 15:17
     * @param project
     * @param role
     * @param application
     * @param menu
     * @return
     */
    UserRoleMenuEntity getApplicationMenu(String project, String role, String application, String menu);
    
    /**
     * 获取应用程序下的菜单
     * @author sunqinqiu 
     * @date   2019-10-26 15:18
     * @param project
     * @param role
     * @param application
     * @return
     */
    List<String> getApplicationMenus(String project, String roles, String application);
    
    /**
     * 获取可用的应用程序
     * @author sunqinqiu 
     * @date   2019-10-26 15:18
     * @param project
     * @param role
     * @return
     */
    List<String> getApplications(String project, String roles);
    
    /**
     * 启动前预先加载
     * @author sunqinqiu 
     * @date   2019-01-06 23:56
     */
    void run();
    
    /**
     * 解析权限系统
     * @author sunqinqiu 
     * @date   2019-10-26 15:18
     * @param project
     * @param code
     */
    void resolveRole(String project, String code);
    
}
