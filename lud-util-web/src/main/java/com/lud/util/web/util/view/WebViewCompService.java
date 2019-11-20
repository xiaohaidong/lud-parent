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

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.lud.util.comm.io.XML;
import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.web.view.ViewContent;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.SpringContextUtil;
import com.lud.util.web.api.view.IViewCompService;
import com.lud.util.web.api.view.ViewCompEntity;
import com.lud.util.web.api.view.ViewServiceEntity;

/**
 * @author sunqinqiu 
 * @date   2019-01-09 19:21
 */
@Service
public class WebViewCompService extends CoreService {
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 20:33
     * @param item
     * @param flag
     * @return
     */
    public String getCompsContent(String serviceName, ViewCompEntity comp, String flag) {
        IViewCompService service = getService(comp);
        if (service == null) { return "no this comp"; }
        return service.getCompContent(serviceName + CharacterContent.CHS_ZH + comp.getName(), flag);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 15:19
     * @param comp
     * @return
     */
    private IViewCompService getService(ViewCompEntity comp) {
        String render = Content.getDefaultValue(comp.getRender(), CT.STRING_NORMAL);
        String extend = Convert.toString(comp.getExtend());
        if (!extend.isEmpty()) { return null; }
        render = MessageFormat.format(ViewContent.SPRING_VIEWCOMSERVICE_NAME, render);
        return (IViewCompService) SpringContextUtil.getBean(render);
    }
    
    /**
     * 获取WebViewContent的信息
     * @author sunqinqiu 
     * @date   2019-01-10 07:07
     */
    public void renderContent(ViewServiceEntity view, ViewCompEntity comp) {
        IViewCompService service = getService(comp);
        if (service == null) { return; }
        service.getRenderContent(view, comp);
    }
    
    /**
     * 解析COMP
     * @author sunqinqiu 
     * @date   2019-03-03 11:42
     * @param xml
     * @param node
     * @param view
     */
    public void resolveCompNode(XML xml, Node node, ViewServiceEntity view) {
        node.selectNodes(CT.STRING_COMP).forEach(compNode -> {
            Map<String, String> compMap = xml.getNodeAttributes(compNode, false);
            ViewCompEntity comp = new ViewCompEntity();
            comp.setName(compMap.remove(CT.STRING_NAME));
            comp.setTheme(compMap.remove(CT.STRING_THEME));
            comp.setExtend(compMap.remove(CT.STRING_EXTENDS));
            comp.setRender(Content.getDefaultValue(compMap.remove(CT.STRING_RENDER), CT.STRING_NORMAL));
            comp.setFlag(Content.getDefaultValue(compMap.remove(CT.STRING_FLAG), CT.STRING_I));
            comp.setConfig(compMap);
            List<Node> configNodeList = compNode.selectNodes(CT.STRING_CONFIG);
            if (configNodeList != null) {
                xml.getNodesAttributes(configNodeList, true).forEach(item -> comp.getConfig().put(item.get(CT.STRING_NAME), item.get(CT.STRING_TEXT)));
            }
            List<Node> detailNodeList = compNode.selectNodes(CT.STRING_DETAIL);
            if (detailNodeList != null) {
                comp.setDetail(xml.getNodesAttributes(detailNodeList, true));
            }
            view.getComps().add(comp);
            renderContent(view, comp);
        });
    }
    
}
