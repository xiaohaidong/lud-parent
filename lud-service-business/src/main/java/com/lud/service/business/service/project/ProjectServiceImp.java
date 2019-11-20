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
package com.lud.service.business.service.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.project.ProjectConfigService;
import com.lud.service.business.api.project.ProjectService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.redis.node.BusinessNode;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;

/**
 * 
 * @author sunqinqiu 
 * @date   2019-01-05 17:43
 */
@Service
@Preload(seq = 2)
public class ProjectServiceImp extends CoreService implements ProjectService {
    @Autowired
    private ProjectConfigService projectConfigService;
    
    /**
     * 解析所有信息
     * @author sunqinqiu 
     * @date   2019-01-06 16:16
     */
    
    @Override
    public void run() {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_project_info_query", DataQueryContent.CACHE, DataQueryContent.DEF);
        this.dataQueryService.queryForList(exe).forEach(this::resolveProject);
    }
    
    /**
     * 解析基本信息
     * @author sunqinqiu 
     * @param item 
     * @date   2019-01-06 16:24
     */
    private void resolveProject(Map<String, Serializable> item) {
        String code = Convert.toString(item.get(DataQueryContent.PROJECT));
        this.redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_PROJECT, code, JSON.toJSONString(item));
        projectConfigService.resolve(code);
        
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 17:31
     * @return
     */
    @Override
    public List<Map<String, Serializable>> getProjectList() {
        List<Map<String, Serializable>> list = new ArrayList<>();
        this.redisService.hmgetAll(BusinessNode.REDIS_NODE_FOR_BUSINESS_PROJECT).forEach((key, value) -> list.add(ListMapUtil.toMap(value)));
        return list;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 15:31
     * @param project
     * @return
     */
    @Override
    public Map<String, Serializable> getProjectInfo(String project) {
        return ListMapUtil.toMap(this.redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_PROJECT, project));
    }
    
}
