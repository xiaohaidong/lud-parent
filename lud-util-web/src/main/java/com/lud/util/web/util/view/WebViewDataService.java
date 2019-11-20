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
import java.text.MessageFormat;
import java.util.Map;

import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.lud.util.comm.io.XML;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.web.view.ViewContent;
import com.lud.util.spring.util.SpringContextUtil;
import com.lud.util.spring.util.SpringServiceInvoker;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.view.ViewDataEntity;
import com.lud.util.web.api.view.ViewServiceEntity;
import com.lud.util.web.view.data.IWebViewDataService;

/**
 * DataService
 * @author sunqinqiu 
 * @date   2019-01-09 19:21
 */
@Service
public class WebViewDataService {
    /**
     * 获取WebViewContent的信息
     * @author sunqinqiu 
     * @param data 
     * @param request 
     * @param flag 
     * @date   2019-01-10 07:07
     */
    public Serializable getData(ViewDataEntity data, ContextRequest request) {
        String beanName = MessageFormat.format(ViewContent.SPRING_VIEW_DATA_SERVICE, data.getType());
        IWebViewDataService dataService = SpringContextUtil.getBean(beanName, IWebViewDataService.class);
        Serializable result = dataService.getData(data, request);
        if (data.getInvoke() != null) {
            SpringServiceInvoker.invoke(data.getInvoke(), data, result, request);
        }
        return result;
    }
    
    /**
     * 解析数据
     * @author sunqinqiu 
     * @date   2019-02-24 19:17
     * @param xml
     * @param node
     * @param view
     */
    public void resolveDataNode(XML xml, Node node, ViewServiceEntity view) {
        node.selectNodes(CT.STRING_DATA).forEach(dataNode -> {
            Map<String, String> dataMap = xml.getNodeAttributes(dataNode, true);
            ViewDataEntity data = new ViewDataEntity();
            data.setName(dataMap.remove(CT.STRING_NAME));
            data.setQuery(dataMap.remove(CT.STRING_QUERY));
            data.setFlag(dataMap.remove(CT.STRING_FLAG));
            data.setInvoke(dataMap.remove(CT.STRING_INVOKE));
            data.setType(dataMap.remove(CT.STRING_TYPE));
            data.setConfig(dataMap);
            view.getDatas().add(data);
        });
    }
}
