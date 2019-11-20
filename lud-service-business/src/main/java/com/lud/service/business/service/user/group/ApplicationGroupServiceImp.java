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
package com.lud.service.business.service.user.group;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.user.group.ApplicationGroup;
import com.lud.service.business.api.user.group.ApplicationGroupService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.redis.node.BusinessNode;
import com.lud.util.dubbo.util.CoreService;

/**
 * @author sunqinqiu 
 * @date   2019-10-26 13:08
 */
@Service
public class ApplicationGroupServiceImp extends CoreService implements ApplicationGroupService {
    /**
     * 解析某个用户组
     * @author sunqinqiu 
     * @date   2019-10-26 13:20
     * @param project
     * @param userGroup
     * @return 
     */
    @Override
    public void resolve(String project, String userGroup) {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_user_group_application_group_query", DataQueryContent.CACHE, DataQueryContent.PROJECT);
        exe.addData(DataQueryContent.PROJECT, project).addData("usergroup", userGroup);
        List<ApplicationGroup> applicationGroupList = new ArrayList<>();
        this.dataQueryService.queryForList(exe).forEach(item -> {
            ApplicationGroup applicationGroup = new ApplicationGroup();
            applicationGroup.setCode(Convert.toString(item.get("code")));
            applicationGroup.setName(Convert.toString(item.get("name")));
            applicationGroup.setIcon(Convert.toString(item.get("icon")));
            applicationGroup.setDescription(Convert.toString(item.get("description")));
            applicationGroup.setApplications(relApplicationGroupApplications(project, applicationGroup.getCode()));
            applicationGroupList.add(applicationGroup);
        });
        String key = MessageFormat.format("{0}.{1}", project, userGroup);
        this.redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERGROUP_APPLICATION_GROUP, key, JSON.toJSONString(applicationGroupList));
    }
    
    /**
     * 获取某个应用组下面的所有应用程序
     * @author sunqinqiu 
     * @date   2019-10-26 13:34
     * @param project
     * @param userGroup
     * @return
     */
    private List<Map<String, Serializable>> relApplicationGroupApplications(String project, String applicationgroup) {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_project_application_query", "project,app", DataQueryContent.PROJECT);
        exe.addData(DataQueryContent.PROJECT, project).addData("applicationgroup", applicationgroup);
        return this.dataQueryService.queryForList(exe);
    }
    
    /**
     * 获取某个用户组下的所有应用
     * @author sunqinqiu 
     * @date   2019-01-08 19:51
     * @param project
     * @param userGroup
     * @return
     */
    @Override
    public List<ApplicationGroup> getProjectApplicationGroup(String project, String userGroup) {
        String key = MessageFormat.format("{0}.{1}", project, userGroup);
        return JSON.parseArray(this.redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERGROUP_APPLICATION_GROUP, key), ApplicationGroup.class);
    }
}
