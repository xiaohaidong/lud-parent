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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.company.CompanyService;
import com.lud.service.business.api.project.ProjectService;
import com.lud.service.core.api.config.ResourceService;
import com.lud.util.comm.io.FileUtil;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.Convert;
import com.lud.util.spring.util.Preload;
import com.lud.util.web.util.view.ViewFactory;

/**
 * @author sunqinqiu 
 * @date   2019-03-15 22:31
 */
@Service
@Preload(seq = 20)
public class TemplateService {
    /**
     * 
     */
    @Reference
    protected ProjectService projectService;
    /**
     * 
     */
    @Reference
    protected CompanyService companyService;
    /**
     * 
     */
    @Reference
    private ResourceService  resourceService;
    /**
     * 
     */
    @Autowired
    protected ViewFactory    viewFactory;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-24 20:19
     */
    
    public void run() {
        projectService.getProjectList().forEach(item -> {
            String dir = resourceService.getReourcePath("tempcontent.project");
            ModelMap map = new ModelMap();
            map.put("project", JSON.toJSONString(item));
            map.put("company", JSON.toJSONString(companyService.getCompany(Convert.toString(item.get("company")))));
            String content = viewFactory.getFileView(dir + "/model/project.html", map);
            FileUtil.creat(ServerResource.getAbsolutePath("/resource-project/resource-web/web-view" + dir + "/" + item.get("code") + "/content.html"), content);
        });
    }
}
