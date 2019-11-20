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
package com.lud.service.business.service.user.role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.business.api.application.ApplicationMuneService;
import com.lud.service.business.api.application.ApplicationWedgeService;
import com.lud.service.business.api.user.UserEntity;
import com.lud.service.business.api.user.UserService;
import com.lud.service.business.api.user.group.ApplicationGroup;
import com.lud.service.business.api.user.group.ApplicationGroupService;
import com.lud.service.business.api.user.role.UserRoleManager;
import com.lud.service.business.api.user.role.UserRoleMenuEntity;
import com.lud.service.business.api.user.role.UserRoleService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.ListComparator;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.dubbo.util.CoreService;

/**
 * @author sunqinqiu 
 * @date   2019-10-26 15:08
 */
@Service
public class UserRoleServiceImp extends CoreService implements UserRoleService {
    /**
     * 
     */
    @Autowired
    private ApplicationGroupService   applicationGroupService;
    /**
     * 
     */
    @Autowired
    protected ApplicationMuneService  applicationMuneService;
    
    /**
     * 
     */
    @Autowired
    protected ApplicationWedgeService applicationWedgeService;
    /**
     * 
     */
    @Autowired
    private UserRoleManager           userRoleManager;
    /**
     * 
     */
    @Autowired
    private UserService               userService;
    
    /**
     * 获取应用程序信息
     * @author sunqinqiu 
     * @return 
     * @date   2019-10-26 15:47
     * @return
     */
    @Override
    public Map<String, Serializable> getApplication(String token, String appCode) {
        Map<String, Serializable> appMap = ListMapUtil.getEmptySerializableMap();
        getApplicationGroup(token).forEach(item -> item.getApplications().forEach(app -> {
            if (Convert.toString(app.get("code")).equals(appCode)) {
                appMap.putAll(app);
                return;
            }
        }));
        return appMap;
    }
    
    /**
     * 获取一个应用程序下的所有菜单
     * @author sunqinqiu 
     * @date   2019-10-26 16:01
     * @param token
     * @param appCode
     * @return
     */
    @Override
    public List<Map<String, Serializable>> getApplicationMenus(String token, String appCode) {
        List<Map<String, Serializable>> menusResult = new ArrayList<>();
        UserEntity user = userService.getUserInfoByToken(token);
        List<String> roleMenus = userRoleManager.getApplicationMenus(user.getProject(), user.getRole(), appCode);
        applicationMuneService.getMenuList(appCode).forEach(item -> {
            if (roleMenus.contains(Convert.toString(item.get("code")))) {
                menusResult.add(item);
            }
        });
        return menusResult;
    }
    
    /**
     * 获取应用组
     * @author sunqinqiu 
     * @date   2019-01-08 20:03
     * @param project
     * @param userid
     * @return 
     */
    @Override
    public List<ApplicationGroup> getApplicationGroup(String token) {
        UserEntity user = userService.getUserInfoByToken(token);
        if (user == null) { return new ArrayList<>(); }
        List<String> roleApplications = userRoleManager.getApplications(user.getProject(), user.getRole());
        List<com.lud.service.business.api.user.group.ApplicationGroup> list = applicationGroupService.getProjectApplicationGroup(user.getProject(), user.getGroup());
        Iterator<ApplicationGroup> groupItem = list.iterator();
        while (groupItem.hasNext()) {
            getApplicationGroupListItem(groupItem, roleApplications);
        }
        return list;
    }
    
    /**
     * 获取应用程序列表
     * @author sunqinqiu 
     * @date   2019-08-30 11:16
     * @param itGroup
     * @param roleApplications
     */
    private void getApplicationGroupListItem(Iterator<ApplicationGroup> groupItem, List<String> roleApplications) {
        ApplicationGroup group = groupItem.next();
        List<Map<String, Serializable>> groupApplications = group.getApplications();
        Iterator<Map<String, Serializable>> itGroupApplications = groupApplications.iterator();
        boolean groupExit = false;
        while (itGroupApplications.hasNext()) {
            Map<String, Serializable> app = itGroupApplications.next();
            boolean appExit = roleApplications.contains(app.get("code"));
            if (appExit) {
                groupExit = true;
            }
            if (!appExit) {
                itGroupApplications.remove();
            }
        }
        if (!groupExit) {
            groupItem.remove();
        }
    }
    
    /**
     * 读取所有的Wedge
     * @author sunqinqiu 
     * @date   2019-10-26 18:13
     * @param token
     * @return
     */
    @Override
    public List<Map<String, Serializable>> getWedgeList(String token, Map<String, Serializable> args) {
        UserEntity user = userService.getUserInfoByToken(token);
        List<Map<String, Serializable>> result = new ArrayList<>();
        List<String> roleApplications = userRoleManager.getApplications(user.getProject(), user.getRole());
        roleApplications.forEach(app -> {
            List<String> menus = userRoleManager.getApplicationMenus(user.getProject(), user.getRole(), app);
            applicationWedgeService.getWedgeList(app).forEach(wedge -> {
                if (menus.contains(Convert.toString(wedge.get("menu")))) {
                    result.add(wedge);
                }
            });
        });
        result.sort(new ListComparator("sort"));
        result.forEach(item -> {
            String config = Convert.toString(item.get("dataconfig"));
            if (!config.isEmpty()) {
                List<Map<String, Serializable>> dataConfigs = ListMapUtil.toList(config);
                for (Map<String, Serializable> dataConfig : dataConfigs) {
                    String query = Convert.toString(dataConfig.get("data"));
                    if (!query.isEmpty()) {
                        DataQueryExecuter exe = new DataQueryExecuter(query, Convert.toString(dataConfig.get("fileds")), Convert.toString(dataConfig.get("limits")));
                        exe.addAllData(args);
                        dataConfig.put("value", this.dataQueryService.queryForValue(exe));
                    }
                }
                item.put("dataconfig", (Serializable) dataConfigs);
            }
            
        });
        return result;
    }
    
    /**
     * 获取某个用户在某个菜单下的 
     * @author sunqinqiu 
     * @date   2019-11-01 12:44
     * @param token
     * @param roleCode
     * @return
     */
    @Override
    public UserRoleMenuEntity getRoleMenu(String token, String application, String menu) {
        UserEntity user = userService.getUserInfoByToken(token);
        for (String role : user.getRole().split(",")) {
            UserRoleMenuEntity roleEntity = this.userRoleManager.getApplicationMenu(user.getProject(), role, application, menu);
            if (roleEntity != null) return roleEntity;
        }
        return null;
    }
}
