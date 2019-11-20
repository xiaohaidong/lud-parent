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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.service.business.api.project.ProjectConfigService;
import com.lud.service.business.api.project.ProjectService;
import com.lud.util.content.util.text.CT;
import com.lud.util.web.api.context.ContextRequest;

/**
 * @author sunqinqiu 
 * @date   2019-09-24 04:37
 */
@Service
public class RequestService {
    private static final String    SES         = "ses.";
    private static final String    USR_USERID  = "usr.userid";
    private static final String    USR_USERKEY = "usr.userkey";
    private static final String    USR_GROUP   = "usr.group";
    private static final String    USER_DEPART = "usr.department";
    /**
     * 基础信息
     */
    @Reference
    protected ProjectService       projectService;
    /**
     * 配置信息
     */
    @Reference
    protected ProjectConfigService projectConfigService;
    /**
     * 
     */
    @Autowired
    protected WebUserService       webUserService;
    
    /**
     * 获取基础数据
     * @author sunqinqiu 
     * @date   2019-09-24 04:41
     * @param request
     * @return
     */
    public Map<String, Serializable> getDataByRequest(ContextRequest request) {
        Map<String, Serializable> result = new HashMap<>();
        String project = request.getProject();
        result.put(CT.STRING_PROJECT, project);
        Map<String, Serializable> projectConfig = this.projectConfigService.getAllConfig(project);
        if (projectConfig != null) {
            projectConfig.forEach((k, v) -> {
                if (k.startsWith(RequestService.SES)) {
                    result.put(k, v);
                }
            });
        }
        result.put(RequestService.USR_USERID, request.getUserid());
        result.put(RequestService.USR_USERKEY, request.getUserGroupKey());
        result.put(RequestService.USR_GROUP, request.getUserGroup());
        result.put(RequestService.USER_DEPART, request.getUserDepartment());
        return result;
    }
}
