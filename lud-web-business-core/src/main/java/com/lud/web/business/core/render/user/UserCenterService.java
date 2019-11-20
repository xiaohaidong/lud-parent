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
package com.lud.web.business.core.render.user;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.lud.service.business.api.user.group.UserGroupService;
import com.lud.service.business.api.user.role.UserRoleService;
import com.lud.util.comm.util.Content;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.context.ContextResult;
import com.lud.util.web.api.view.ViewDataEntity;
import com.lud.util.web.core.RequestService;
import com.lud.util.web.core.WebUserService;
import com.lud.util.web.util.view.BWebViewRender;
import com.lud.util.web.util.view.IWebViewRender;

/**
 * @author sunqinqiu 
 * @date   2019-01-11 02:34
 */
@Service("com.lud.web.business.core.render.user.center.main")
public class UserCenterService extends BWebViewRender implements IWebViewRender {
    /**
     * 用户信息
     */
    @Reference
    protected UserRoleService  userRoleService;
    
    /**
     * 用户组信息
     */
    @Reference
    protected UserGroupService userGroupService;
    
    /**
     * 和上下文有关的服务
     */
    @Autowired
    private RequestService     requestService;
    
    /**
     * 
     */
    @Autowired
    private WebUserService     webUserService;
    
    /**
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-26 23:35
     * @param request
     * @return
     */
    @Override
    public ModelMap getModelMap(ContextRequest request) {
        ModelMap map = super.getModelMap(request);
        map.put("usergroup", this.userGroupService.getUserGroupEntity(request.getProject(), request.getUserGroup()));
        return map;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-26 23:35
     * @param data
     * @param result
     * @param request
     */
    public void getApplicationList(ViewDataEntity data, Map<String, Serializable> result, ContextRequest request) {
        result.put("list", (Serializable) userRoleService.getApplicationGroup(request.getToken()));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-26 23:35
     * @param data
     * @param result
     * @param request
     */
    public void getApplication(ViewDataEntity data, Map<String, Serializable> result, ContextRequest request) {
        String code = request.getData().get("code");
        result.put("application", (Serializable) userRoleService.getApplication(request.getToken(), code));
        result.put("menulist", (Serializable) userRoleService.getApplicationMenus(request.getToken(), code));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-23 18:57
     * @param data
     * @param result
     * @param request
     */
    @SuppressWarnings("unchecked")
    public void getMenu(ViewDataEntity data, Map<String, Serializable> result, ContextRequest request) {
        String[] menu = request.getMenu().split(":");
        String pcode = Content.substring(menu[1], 0, 2);
        request.getData().put("code", menu[0]);
        getApplication(data, result, request);
        List<Map<String, Serializable>> menusResult = (List<Map<String, Serializable>>) result.get("menulist");
        menusResult.forEach(item -> {
            if (item.get("code").equals(menu[1])) {
                result.put("menu", (Serializable) item);
            }
            if (pcode.equals(item.get("code"))) {
                result.put("pmenu", (Serializable) item);
            }
        });
    }
    
    /**
     * 获取用户信息
     * @author sunqinqiu 
     * @date   2019-09-21 14:26
     * @param data
     * @param result
     * @param request
     */
    public void getUserInfo(ViewDataEntity data, Map<String, Serializable> result, ContextRequest request) {
        result.put("user", this.webUserService.getUserInfoByToken());
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-24 01:26
     * @param data
     * @param result
     * @param request
     */
    public void getWedge(ViewDataEntity data, Map<String, Serializable> result, ContextRequest request) {
        List<Map<String, Serializable>> wedgeResult = userRoleService.getWedgeList(request.getToken(), requestService.getDataByRequest(request));
        result.put("wedges", (Serializable) wedgeResult);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-31 03:17
     * @param map
     * @param request
     * @param result
     */
    public void userLogout(Map<String, Serializable> map, ContextRequest request, ContextResult result) {
        webUserService.logout(request.getToken());
        result.sets(true, "", "登出成功！");
    }
}
