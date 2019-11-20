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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.user.group.ApplicationGroupService;
import com.lud.service.business.api.user.group.UserGroupEntity;
import com.lud.service.business.api.user.group.UserGroupService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.redis.node.BusinessNode;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;

/**
 * 用户组
 * @author sunqinqiu 
 * @date   2019-01-06 20:45
 */
@Service
@Preload
public class UserGroupServiceImp extends CoreService implements UserGroupService {
    
    @Autowired
    private ApplicationGroupService userGroupApplicationGroupService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 12:53
     */
    @Override
    public void run() {
        Map<String, List<UserGroupEntity>> groups = new HashMap<>();
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_user_group_query", DataQueryContent.DEF, DataQueryContent.DEF);
        this.dataQueryService.queryForList(exe).forEach(item -> {
            String project = Convert.toString(item.get("project"));
            String userGroupCode = Convert.toString(item.get("usergroup"));
            UserGroupEntity userGroup = new UserGroupEntity();
            userGroup.setCode(userGroupCode);
            userGroup.setName(Convert.toString(item.get("groupname")));
            userGroup.setTheme(Convert.toString(item.get("theme")));
            userGroup.setSort(Convert.toInt(item.get("sort")));
            this.redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERGROUP, MessageFormat.format("{0}.{1}", project, userGroup.getCode()), JSON.toJSONString(userGroup));
            List<UserGroupEntity> list = groups.get(project);
            if (list == null) {
                list = new ArrayList<>();
                groups.put(project, list);
            }
            list.add(userGroup);
            userGroupApplicationGroupService.resolve(project, userGroupCode);
        });
        groups.forEach((k, v) -> this.redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERGROUPS, k, JSON.toJSONString(v)));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 16:42
     * @param project
     * @param userGroupCode
     * @return
     */
    @Override
    public UserGroupEntity getUserGroupEntity(String project, String userGroupCode) {
        return JSON.parseObject(this.redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_USERGROUP, MessageFormat.format("{0}.{1}", project, userGroupCode)), UserGroupEntity.class);
    }
    
}
