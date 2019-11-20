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
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.project.ProjectConfigService;
import com.lud.util.comm.io.FileUtil;
import com.lud.util.comm.io.XMLUtil;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.redis.node.BusinessNode;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;

/**
 * @author sunqinqiu 
 * @date   2019-10-26 13:53
 */
@Service
@Preload
public class ProjectConfigServiceImp extends CoreService implements ProjectConfigService {
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-06 23:59
     */
    private String configModelPath = "";
    
    /**
     * 获取模板地址
     * @author sunqinqiu 
     * @date   2019-10-26 13:59
     */
    public void run() {
        configModelPath = ServerResource.getAbsolutePath(this.resourceService.getReourcePath("project.model"));
    }
    
    /**
     * 解析项目配置信息
     * @author sunqinqiu 
     * @date   2019-10-26 14:02
     * @param project
     */
    @Override
    public void resolve(String project) {
        checkConfig(project);
        this.redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_PROJECT_CONFIG, project, JSON.toJSONString(resolveConfigByXML(project)));
    }
    
    /**
     * 获取项目配置信息
     * @author sunqinqiu 
     * @date   2019-10-26 14:17
     * @param project
     * @param key
     * @return
     */
    @Override
    public String getConfig(String project, String key) {
        return Convert.toString(getAllConfig(project).get(key));
    }
    
    /**
     * 获取某些配置
     * @author sunqinqiu 
     * @date   2019-10-26 14:10
     * @param project
     * @param keys
     * @return
     */
    @Override
    public Map<String, Serializable> getConfigMap(String project, String... keys) {
        Map<String, Serializable> result = new HashMap<>();
        getAllConfig(project).forEach((key, value) -> {
            for (String nkey : keys) {
                if (key.equals(nkey)) result.put(key, value);
            }
        });
        return result;
    }
    
    /**
     * 获取所有配置信息
     * @author sunqinqiu 
     * @date   2019-10-26 14:13
     * @param project
     * @return
     */
    @Override
    public Map<String, Serializable> getAllConfig(String project) {
        return ListMapUtil.toMap(this.redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_PROJECT_CONFIG, project));
    }
    
    /**
     * 检查配置文件是否存在
     * @author sunqinqiu 
     * @date   2019-10-26 14:02
     * @param project
     */
    private void checkConfig(String project) {
        final String target = MessageFormat.format(ServerResource.getAbsolutePath(this.resourceService.getReourcePath("project.config")), project);
        FileUtil.listDir(configModelPath).forEach(name -> {
            String from = MessageFormat.format("{0}/{1}", configModelPath, name);
            String to = MessageFormat.format("{0}/{1}", target, name);
            if (!FileUtil.exists(to)) {
                FileUtil.copyDir(from, to);
            }
        });
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 14:05
     * @param project
     * @return
     */
    private Map<String, Serializable> resolveConfigByXML(String project) {
        Map<String, Serializable> config = new HashMap<>();
        String configPath = MessageFormat.format(ServerResource.getAbsolutePath(this.resourceService.getReourcePath("project.config")), project);
        FileUtil.list(configPath + "/config", false).forEach(item -> {
            String group = item.get("code");
            String path = item.get("path");
            XMLUtil.resolveConfig(path, true).forEach((k, v) -> config.put(MessageFormat.format("{0}.{1}", group, k), v));
        });
        return config;
    }
    
}
