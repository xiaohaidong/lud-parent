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
package com.lud.util.web.view.entity.render;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.lud.util.comm.util.Content;
import com.lud.util.content.redis.node.WebViewNode;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.web.api.view.IViewCompService;
import com.lud.util.web.api.view.ViewCompEntity;
import com.lud.util.web.api.view.ViewServiceEntity;
import com.lud.util.web.util.view.ViewFactory;

/**
 * @author sunqinqiu 
 * @date   2019-11-03 16:38
 */
public abstract class AbstractViewCompService extends CoreService implements IViewCompService {
    @Autowired
    protected ViewFactory viewFactory;
    
    /**
     * 获取COMP数据
     * @author sunqinqiu 
     * @date   2019-10-03 20:40
     * @param name
     * @param flag
     * @return
     */
    @Override
    public String getCompContent(String name, String flag) {
        String nameFlag = name + "-" + flag;
        if (redisService.hexists(WebViewNode.REDIS_NODE_FOR_WEBVIEW_COMP, nameFlag)) {
            return redisService.hget(WebViewNode.REDIS_NODE_FOR_WEBVIEW_COMP, nameFlag);
        } else {
            return redisService.hget(WebViewNode.REDIS_NODE_FOR_WEBVIEW_COMP, name);
        }
    }
    
    /**
    * 虚拟化的方法
    * @author sunqinqiu 
    * @date   2019-11-03 16:45
    * @param view
    * @param comp
    */
    public void getRenderContent(ViewServiceEntity view, ViewCompEntity comp) {
        String tempFile = comp.getTheme();
        if (!tempFile.endsWith(".html")) {
            tempFile = MessageFormat.format("/view-service/comp/{0}.html", tempFile);
        }
        ModelMap map = new ModelMap();
        map.put(CT.STRING_COMPID, (view.getName() + "-" + comp.getName().replace(".", "_")).replace("-", "_"));
        map.put(CT.STRING_CONFIG, comp.getConfig());
        map.put(CT.STRING_DETAIL, getDatail(view, comp));
        redisService.hset(WebViewNode.REDIS_NODE_FOR_WEBVIEW_COMP, view.getName() + CharacterContent.PATH_SPLITER_M + comp.getName(), Content.replaceBlank(viewFactory.getFileView(tempFile, map)));
    }
    
    /**
     * 处理Detail节点
     * @author sunqinqiu 
     * @date   2019-11-03 17:00
     * @param compName
     * @param detail
     * @param flag
     * @return
     */
    public List<Map<String, String>> getDatail(ViewServiceEntity view, ViewCompEntity comp) {
        return comp.getDetail();
    }
}
