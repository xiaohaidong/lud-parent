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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.user.role.UserRoleMenuEntity;
import com.lud.service.business.api.user.role.UserRoleManager;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.io.XML;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.redis.node.BusinessNode;
import com.lud.util.content.util.text.CT;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;

/**
 * 用户权限
 * @author sunqinqiu 
 * @date   2019-01-06 20:48
 */
@Service
@Preload
public class UserRoleManagerImp extends CoreService implements UserRoleManager {
    /**
     * UserRoleService
     * @author sunqinqiu 
     * @date   2019-01-07 14:18
     */
    @Override
    public void run() {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_user_role_query", DataQueryContent.DEF, DataQueryContent.DEF);
        dataQueryService.queryForList(exe).forEach(item -> resolveRole(Convert.toString(item.get("project")), Convert.toString(item.get("code"))));
    }
    
    /**
     * 获取某个菜单下的权限
     * @author sunqinqiu 
     * @date   2019-01-08 19:42
     * @param project
     * @param role
     * @param application
     * @param menu
     * @return
     */
    @Override
    public UserRoleMenuEntity getApplicationMenu(String project, String role, String application, String menu) {
        String key = MessageFormat.format("{0}.{1}.{2}.{3}", project, role, application, menu);
        return JSON.parseObject(this.redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERROLE_APPLICATIONMENU, key), UserRoleMenuEntity.class);
    }
    
    /**
     * 获取某个Application下的所有权限菜单
     * @author sunqinqiu 
     * @date   2019-01-08 19:38
     * @param project
     * @param role
     * @param application
     * @return
     */
    @Override
    public List<String> getApplicationMenus(String project, String roles, String application) {
        List<String> result = new ArrayList<>();
        for (String role : roles.split(",")) {
            String key = MessageFormat.format("{0}.{1}.{2}", project, role, application);
            try {
                JSON.parseArray(this.redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERROLE_APPLICATIONMENUS, key), String.class).forEach(code -> {
                    if (!result.contains(code)) {
                        result.add(code);
                    }
                });
            } catch (Exception e) {
                continue;
            }
        }
        return result;
    }
    
    /**
     * 获取Application列表
     * @author sunqinqiu 
     * @date   2019-01-08 19:34
     * @param project
     * @param role
     * @return
     */
    @Override
    public List<String> getApplications(String project, String roles) {
        List<String> result = new ArrayList<>();
        for (String role : roles.split(",")) {
            String key = MessageFormat.format("{0}.{1}", project, role);
            JSON.parseArray(this.redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERROLE_APPLICATION, key), String.class).forEach(code -> {
                if (!result.contains(code)) {
                    result.add(code);
                }
            });
        }
        return result;
    }
    
    /**
     * 解析某个项目下的某个特定权限的信息
     * @author sunqinqiu 
     * @date   2019-01-08 19:34
     * @param project
     * @param code
     */
    @Override
    public void resolveRole(String project, String code) {
        List<String> applications = new ArrayList<>();
        String xmlPath = this.resourceService.getReourcePath("project.config");
        xmlPath = ServerResource.getAbsolutePath(MessageFormat.format(xmlPath, project + "/userrole/" + code + ".xml"));
        try (XML xml = new XML(xmlPath)) {
            if (xml.getDocument() == null) { return; }
            xml.getDocument().selectNodes("/configuration/application").forEach(node -> {
                /**
                 * 第一层循环：应用
                 */
                String application = xml.getNodeAttributes(node, false).get(CT.STRING_NAME);
                if (!applications.contains(application)) {
                    applications.add(application);
                }
                List<String> applicationMenus = new ArrayList<>();
                xml.getNodesAttributes(node.selectNodes("menu"), true).forEach(item -> {
                    /**
                     * 第二层循环：菜单
                     */
                    String menu = item.get(CT.STRING_NAME);
                    if (menu.length() == 4) {
                        String parentMenu = menu.substring(0, 2);
                        if (!applicationMenus.contains(parentMenu)) {
                            applicationMenus.add(parentMenu);
                        }
                    }
                    if (!applicationMenus.contains(menu)) {
                        applicationMenus.add(menu);
                    }
                    String menuCode = MessageFormat.format("{0}.{1}.{2}.{3}", project, code, application, menu);
                    UserRoleMenuEntity roleMenu = new UserRoleMenuEntity();
                    roleMenu.setDatas(item.get("data").split(","));
                    roleMenu.setActions(item.get("text").split(","));
                    this.redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERROLE_APPLICATIONMENU, menuCode, JSON.toJSONString(roleMenu));
                });
                String key = MessageFormat.format("{0}.{1}.{2}", project, code, application);
                this.redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERROLE_APPLICATIONMENUS, key, JSON.toJSONString(applicationMenus));
            });
            String key = MessageFormat.format("{0}.{1}", project, code);
            this.redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERROLE_APPLICATION, key, JSON.toJSONString(applications));
        } catch (Exception e) {
            loger.error("{}", this.resourceService.getError("1010.0001"));
        }
    }
}