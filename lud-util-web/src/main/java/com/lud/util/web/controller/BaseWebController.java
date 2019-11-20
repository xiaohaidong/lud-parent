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
package com.lud.util.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.business.api.project.domain.DomainService;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.context.HttpContext;
import com.lud.util.web.util.view.WebViewService;

/**
 * 基础服务
 * @author sunqinqiu 
 * @date   2019-01-07 14:45
 */
public class BaseWebController {
    
    /**
     * 项目标记
     */
    @Reference
    protected DomainService     domainService;
    
    /**
     * Web服务
     */
    @Autowired
    protected WebViewService    webViewService;
    
    /**
     * 
     */
    private Map<String, String> domainProjects = new HashMap<>();
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-28 10:59
     * @return
     */
    protected String getProject(String domain) {
        String project = domainProjects.get(domain);
        if (project != null) { return project; }
        project = domainService.getProjectCodeByDomain(domain);
        domainProjects.put(domain, project);
        return project;
    }
    
    /**
     * 获取BaseRequest信息
     * @author sunqinqiu 
     * @date   2019-01-09 00:01
     * @return
     */
    protected ContextRequest getRequest() {
        ContextRequest request = new ContextRequest();
        request.setDomain(HttpContext.getDomain());
        request.setProject(getProject(request.getDomain()));
        request.setData(HttpContext.getParameters());
        return request;
    }
}
