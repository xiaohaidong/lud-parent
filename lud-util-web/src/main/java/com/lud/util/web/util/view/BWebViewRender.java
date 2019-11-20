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
package com.lud.util.web.util.view;

import java.io.Serializable;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.lud.service.business.api.project.ProjectService;
import com.lud.service.core.api.config.ResourceService;
import com.lud.service.core.api.config.SystemConfigService;
import com.lud.util.content.util.text.CT;
import com.lud.util.web.api.context.ContextRequest;

/**
 * @author sunqinqiu 
 * @date   2019-01-11 02:35
 */
public class BWebViewRender implements IWebViewRender {
    protected final Logger        loger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 项目服务
     */
    @Reference
    protected ProjectService      projectService;
    
    /**
     * webView服务
     */
    @Autowired
    protected WebViewService      webViewService;
    
    /**
     * 
     */
    @Reference
    protected ResourceService     resourceService;
    
    /**
     * 
     */
    @Reference
    protected SystemConfigService systemConfigService;
    
    /**
     * 获取模型
     * @author sunqinqiu 
     * @date   2019-02-23 14:51
     * @param request
     * @return
     */
    @Override
    public ModelMap getModelMap(ContextRequest request) {
        ModelMap map = new ModelMap();
        String project = request.getProject();
        Map<String, Serializable> projectInfo = this.projectService.getProjectInfo(project);
        map.put(CT.STRING_PROJECT, project);
        map.put(CT.STRING_COMPANY, projectInfo.get(CT.STRING_COMPANY));
        return map;
    }
}
