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

import java.util.List;
import java.util.Map;

import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lud.util.comm.io.FileUtil;
import com.lud.util.comm.io.XML;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.redis.node.WebViewNode;
import com.lud.util.content.util.error.ErrorCode;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.web.view.ViewContent;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;
import com.lud.util.web.api.view.ViewServiceEntity;

/**
 * WEB管理器
 * @author sunqinqiu 
 * @date   2019-01-07 01:59
 */
@Service
@Preload(seq = 100)
public class WebViewManager extends CoreService {
    
    /**
     * 页面管理服务
     */
    @Autowired
    private WebViewContentService webViewContentService;
    
    /**
     * 组件服务
     */
    @Autowired
    private WebViewCompService    webViewCompService;
    
    /**
     * 数据服务
     */
    @Autowired
    private WebViewDataService    webViewDataService;
    
    /**
     * 命令服务
     */
    @Autowired
    private WebViewCommandService webViewCommandService;
    
    /**
     * 获取ViewService
     * @author sunqinqiu 
     * @date   2019-01-10 13:26
     * @param name
     * @return
     */
    public ViewServiceEntity getViewServiceEntity(String name) {
        return JSON.parseObject(redisService.hget(WebViewNode.REDIS_NODE_FOR_WEBVIEW_SERVICE, name), ViewServiceEntity.class);
    }
    
    /**
     * 解析所有数据
     * @author sunqinqiu 
     * @date   2019-01-07 02:39
     */
    public void run() {
        redisService.del(WebViewNode.REDIS_NODE_FOR_WEBVIEW_SERVICE);
        redisService.del(WebViewNode.REDIS_NODE_FOR_WEBVIEW_COMP);
        redisService.del(WebViewNode.REDIS_NODE_FOR_WEBVIEW_PAGE);
        String path = ServerResource.getAbsolutePath(resourceService.getReourcePath(ViewContent.VIEW_SERVICE_PATH));
        resolveServices(path, CT.EMPTY);
    }
    
    /**
     * 解析系统
     * @author sunqinqiu 
     * @date   2019-01-09 15:36
     */
    private void resolveServices(String path, String process) {
        FileUtil.listDir(path).forEach(dir -> resolveServices(path + CharacterContent.PATH_SPLITER_S + dir, process + (process.isEmpty() ? "" : "-") + dir));
        FileUtil.list(path, false).forEach(service -> resolveService(service, process));
    }
    
    /**
     * 解析单个文件
     * @author sunqinqiu 
     * @date   2019-01-09 16:00
     * @param service
     * @param module
     */
    private void resolveService(Map<String, String> service, String module) {
        String servicePix = module + CharacterContent.PATH_SPLITER_M + service.get(CT.STRING_CODE);
        try (XML xml = new XML(service.get(CT.STRING_PATH))) {
            xml.getDocument().selectNodes(ViewContent.VIEW_SERVICE_XML_SERVICE).forEach(node -> {
                Map<String, String> config = xml.getNodeAttributes(node, false);
                ViewServiceEntity view = new ViewServiceEntity();
                view.setName(servicePix + CharacterContent.PATH_SPLITER_M + config.remove(CT.STRING_NAME));
                view.setTheme(config.remove(CT.STRING_THEME));
                view.setUserGroup(Content.getDefaultValue(config.remove(CT.STRING_USERGROUP), CT.STRING_MANAGER));
                view.setAcl(Content.getDefaultValue(config.remove(CT.SRING_ACL), "user"));
                String iscache = Content.getDefaultValue(config.remove(CT.STRING_CACHE), "1");
                view.setCache(Convert.toBoolean(iscache));
                view.setConfig(config);
                // 获取Config
                List<Node> configNodeList = node.selectNodes(CT.STRING_CONFIG);
                if (null != configNodeList) {
                    xml.getNodesAttributes(configNodeList, true).forEach(item -> view.getConfig().put(item.get(CT.STRING_NAME), item.get(CT.STRING_TEXT)));
                }
                webViewCompService.resolveCompNode(xml, node, view);
                webViewContentService.resolveContentNode(xml, node, view);
                webViewDataService.resolveDataNode(xml, node, view);
                webViewCommandService.resolveCommadNode(xml, node, view);
                this.redisService.hset(WebViewNode.REDIS_NODE_FOR_WEBVIEW_SERVICE, view.getName(), JSON.toJSONString(view));
            });
        } catch (Exception e) {
            loger.error("{}{}", this.resourceService.getError(ErrorCode.VIEWSERVICE_RES_ERROR), e);
        }
    }
}
