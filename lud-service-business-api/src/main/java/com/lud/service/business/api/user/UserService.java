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
package com.lud.service.business.api.user;

import java.io.Serializable;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2019-01-06 20:49
 */
public interface UserService {
    /**
     * 获取用户信息
     * @author sunqinqiu 
     * @date   2019-10-26 15:14
     * @param token
     * @return
     */
    UserEntity getUserInfoByToken(String token);
    
    /**
     * 获取用户的Token
     * @author sunqinqiu 
     * @date   2019-10-26 15:14
     * @param project
     * @param userid
     * @param usergroup
     * @return
     */
    String getUserToken(String project, String userid, String usergroup);
    
    /**
     * 登出系统
     * @author sunqinqiu 
     * @date   2019-10-26 15:14
     * @param token
     */
    void logout(String token);
    
    /**
     * 设置用户的SESSION信息
     * @author sunqinqiu 
     * @date   2019-10-26 15:14
     * @param token
     * @param map
     */
    void setUserSession(String token, Map<String, Serializable> map);
    
    /**
     * 设置用户SESSION过期时间
     * @author sunqinqiu 
     * @date   2019-10-28 01:26
     * @param token
     */
    boolean updateUserSession(String token);
    
}
