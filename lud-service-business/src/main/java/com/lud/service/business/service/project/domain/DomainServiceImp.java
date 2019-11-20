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
package com.lud.service.business.service.project.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;

import com.lud.service.business.api.project.domain.DomainService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.util.Convert;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;

/**
 * 
 * @author sunqinqiu 
 * @date   2019-01-05 17:44
 */
@Service
@Preload
public class DomainServiceImp extends CoreService implements DomainService {
    /**
     * Domain和Project对应表
     */
    private Map<String, String> projectDomain = new HashMap<>();
    
    /**
     * Domain和Root对应表
     */
    private Map<String, String> domainRoot    = new HashMap<>();
    
    /**
     * 解析
     * @author sunqinqiu 
     * @date   2019-01-06 04:08
     */
    @Override
    public void run() {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_project_domain_query", "cache", "def");
        dataQueryService.queryForList(exe).forEach(item -> {
            projectDomain.put(Convert.toString(item.get("domain")), Convert.toString(item.get("project")));
            domainRoot.put(Convert.toString(item.get("domain")), Convert.toString(item.get("root")));
        });
    }
    
    /**
     * 读取项目信息
     * @author sunqinqiu 
     * @date   2019-01-06 04:08
     * @param domain
     * @return
     */
    @Override
    public String getProjectCodeByDomain(String domain) {
        return projectDomain.get(domain);
    }
    
    /**
     * 读取Domain信息
     * @author sunqinqiu 
     * @date   2019-01-06 04:08
     * @param domain
     * @return
     */
    @Override
    public String getRootByDomain(String domain) {
        return domainRoot.get(domain);
    }
    
}
