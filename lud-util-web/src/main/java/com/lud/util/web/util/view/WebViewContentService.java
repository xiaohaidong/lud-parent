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
import org.springframework.stereotype.Service;

import com.lud.util.comm.io.XML;
import com.lud.util.content.util.text.CT;
import com.lud.util.web.api.view.ViewContentEntity;
import com.lud.util.web.api.view.ViewServiceEntity;

/**
 * @author sunqinqiu 
 * @date   2019-01-09 19:21
 */
@Service
public class WebViewContentService {
    /**
     * 解析Content
     * @author sunqinqiu 
     * @date   2019-01-09 18:45
     * @param xml
     * @param node
     * @param view
     */
    public void resolveContentNode(XML xml, Node node, ViewServiceEntity view) {
        node.selectNodes(CT.STRING_CONTENT).forEach(contentNode -> {
            Map<String, String> contentMap = xml.getNodeAttributes(contentNode, false);
            ViewContentEntity content = new ViewContentEntity();
            content.setName(contentMap.remove(CT.STRING_NAME));
            content.setTheme(contentMap.remove(CT.STRING_THEME));
            content.setExtend(contentMap.remove(CT.STRING_EXTENDS));
            content.setFlag(contentMap.remove(CT.STRING_FLAG));
            content.setConfig(contentMap);
            /* 读取其他参数 */
            List<Node> configNodeList = contentNode.selectNodes(CT.STRING_CONFIG);
            boolean havaConfig = (null == configNodeList) || configNodeList.isEmpty();
            if (!havaConfig) {
                xml.getNodesAttributes(configNodeList, true).forEach(item -> content.getConfig().put(item.get(CT.STRING_NAME), item.get(CT.STRING_TEXT)));
            }
            
            /* 读取组 */
            List<Node> itemNodeList = contentNode.selectNodes(CT.STRING_ITEM);
            havaConfig = (null == itemNodeList) || itemNodeList.isEmpty();
            if (!havaConfig) {
                content.setItems(xml.getNodesAttributes(itemNodeList, true));
            }
            view.getContents().add(content);
        });
    }
    
}
