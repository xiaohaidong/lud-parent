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
package com.lud.util.web.core;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import com.lud.service.business.api.user.UserEntity;
import com.lud.service.business.api.user.UserService;
import com.lud.service.business.api.user.role.UserRoleMenuEntity;
import com.lud.service.business.api.user.role.UserRoleService;
import com.lud.util.comm.util.Content;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.web.view.ViewContent;
import com.lud.util.web.context.HttpContext;
import com.lud.util.web.context.HttpSessionUtil;

/**
 * @author sunqinqiu 
 * @date   2019-10-26 15:21
 */
@Service
public class WebUserService {
    /**
     * 用户服务
     */
    @Reference
    private UserService     userService;
    
    /**
     * 权限服务
     */
    @Reference
    private UserRoleService userRoleService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 15:22
     */
    public void setUserToken(String token) {
        HttpSessionUtil.setCookie(HttpContext.getDomain(), ViewContent.SESSION_CURUSERID, token, -1);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-28 01:29
     * @return
     */
    public String getAndUpdateUserToken() {
        String token = getUserToken();
        if (token.isEmpty()) return CT.EMPTY;
        if (!userService.updateUserSession(token)) return CT.EMPTY;
        return token;
    }
    
    /**
     * 设置用户登录日期
     * @author sunqinqiu 
     * @return 
     * @date   2019-10-26 15:22
     */
    public String getUserToken() {
        return Content.getDefaultValue(HttpSessionUtil.getCookie(ViewContent.SESSION_CURUSERID), CT.EMPTY);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 16:25
     */
    public void logout(String token) {
        userService.logout(token);
        HttpSessionUtil.setCookie(HttpContext.getDomain(), ViewContent.SESSION_CURUSERID, "", 0);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-28 12:19
     * @param request
     * @return
     */
    public UserEntity getUserInfoByToken() {
        String token = getUserToken();
        if (token.isEmpty()) return null;
        return userService.getUserInfoByToken(token);
    }
    
    /**
     * 获取权限信息
     * @author sunqinqiu 
     * @date   2019-11-05 16:50
     * @param menu
     * @return
     */
    public UserRoleMenuEntity getMenuRole(String token, String menu) {
        if (!menu.isEmpty()) {
            String[] menus = menu.split(":");
            if (menus.length == 2 && !menus[0].isEmpty() && !menus[1].isEmpty()) { return this.userRoleService.getRoleMenu(token, menus[0], menus[1]); }
        }
        return null;
    }
}
