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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lud.service.business.api.user.group.ApplicationGroup;

/**
 * @author sunqinqiu 
 * @date   2019-10-26 15:08
 */
public interface UserRoleService {
    
    /**
     * 获取该用户下的所有权限
     * @author sunqinqiu 
     * @date   2019-10-26 15:19
     * @param token
     * @return
     */
    List<ApplicationGroup> getApplicationGroup(String token);
    
    /**
     * 获取应用程序信息
     * @author sunqinqiu 
     * @date   2019-10-26 15:59
     * @param token
     * @param appCode
     * @return
     */
    Map<String, Serializable> getApplication(String token, String appCode);
    
    /**
     * 获取某个应用程序下的菜单
     * @author sunqinqiu 
     * @date   2019-10-26 16:10
     * @param token
     * @param appCode
     * @return
     */
    List<Map<String, Serializable>> getApplicationMenus(String token, String appCode);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 18:27
     * @param token
     * @return
     */
    List<Map<String, Serializable>> getWedgeList(String token, Map<String, Serializable> args);
    
    /**
     * 获取用户在某个菜单下的权限
     * @author sunqinqiu 
     * @date   2019-11-01 12:54
     * @param token
     * @param application
     * @param menu
     * @return
     */
    UserRoleMenuEntity getRoleMenu(String token, String application, String menu);
}
