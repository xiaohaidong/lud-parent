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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.redis.node.WebViewNode;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;
import com.lud.util.web.api.view.IViewCompService;
import com.lud.util.web.api.view.ViewCompEntity;
import com.lud.util.web.api.view.ViewServiceEntity;
import com.lud.util.web.view.webcontrol.WebControlService;

/**
 * @author sunqinqiu 
 * @date   2019-10-03 19:52
 */
@Service("com.lud.web.util.view.entity.render.viewcompservice.edit")
public class ViewCompServiceForEdit extends AbstractViewCompService implements IViewCompService {
    @Autowired
    private WebControlService webControlService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 21:25
     * @param view
     * @param comp
     */
    @Override
    public void getRenderContent(ViewServiceEntity view, ViewCompEntity comp) {
        for (char flag : comp.getFlag().toCharArray()) {
            redisService.hset(WebViewNode.REDIS_NODE_FOR_WEBVIEW_COMP, view.getName() + CharacterContent.PATH_SPLITER_M + comp.getName() + "-" + flag, Content.replaceBlank(getContent(view, comp, flag + "")));
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 21:25
     * @param view
     * @param comp
     * @param flag
     * @return
     */
    private String getContent(ViewServiceEntity view, ViewCompEntity comp, String flag) {
        String tempFile = comp.getTheme();
        if (!tempFile.endsWith(".html")) {
            tempFile = MessageFormat.format("/view-service/comp/{0}.html", tempFile);
        }
        ModelMap map = new ModelMap();
        map.put(CT.STRING_COMPID, (view.getName() + "-" + comp.getName().replace(".", "_")).replace("-", "_"));
        map.put(CT.STRING_CONFIG, comp.getConfig());
        map.put(CT.STRING_DETAIL, getEditorDetail(comp.getName(), comp.getDetail(), flag));
        map.put(CT.STRING_FLAG, flag);
        return viewFactory.getFileView(tempFile, map);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 21:35
     * @param detail
     * @return
     */
    private List<Map<String, String>> getEditorDetail(String compName, List<Map<String, String>> detail, String flag) {
        List<Map<String, String>> list = new ArrayList<>();
        detail.forEach(item -> {
            String itemFlag = Content.getDefaultValue(item.get(CT.STRING_FLAG), "aevp");
            if (itemFlag.contains(flag)) {
                Map<String, String> dt = new HashMap<>();
                dt.putAll(item);
                String filed = dt.get("filed");
                dt.put("name", Content.getDefaultValue(dt.get("name"), compName + "#" + filed));
                dt.put("value", Content.getDefaultValue(dt.get("value"), "${" + compName + "." + filed + "}"));
                list.add(getWebContol(dt, flag));
            }
        });
        return list;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 22:35
     * @param item
     * @return
     */
    private Map<String, String> getWebContol(Map<String, String> item, String flag) {
        if (flag.equals("a")) {
            item.put(CT.STRING_VALUE, Content.getDefaultValue(item.get("def-value"),item.get("data")==null?"":"\"\""));
        }
        String text = Convert.toString(item.get(CT.STRING_TEXT));
        if (text.isEmpty()) {
            text = "{0}";
        }
        String content = "ae".contains(flag) ? webControlService.getWebControlContent(item) : webControlService.getWebControlViewContent(item);
        item.put(CT.STRING_TEXT, MessageFormat.format(text, content));
        return item;
    }
}
