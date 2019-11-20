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
package com.lud.util.web.view.webcontrol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.lud.util.comm.io.FileUtil;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.web.view.ViewContent;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;
import com.lud.util.web.util.view.ViewFactory;

/**
 * @author sunqinqiu 
 * @date   2019-09-28 21:30
 */
@Service
@Preload(seq = 10)
public class WebControlService extends CoreService {
    
    private static final String VIEW         = ".view";
    
    @Autowired
    private ViewFactory         wiewFactory;
    private Set<String>         controlsView = new HashSet<>();
    private Map<String, String> controls     = new HashMap<>();
    
    /**
     * 解析控件
     * @author sunqinqiu 
     * @date   2019-10-24 20:16
     */
    
    public void run() {
        controls.clear();
        String path = ServerResource.getAbsolutePath(resourceService.getReourcePath(ViewContent.FREEMARK_CONFIG_VIEW_CTR));
        FileUtil.listDir(path).forEach(dir -> FileUtil.list(path + "/" + dir, false).forEach(item -> controls.put(dir + "." + item.get("code"), FileUtil.read(item.get("path")))));
        controls.forEach((key, value) -> {
            if (controls.containsKey(key + WebControlService.VIEW)) {
                controlsView.add(key);
            }
        });
    }
    
    /**
     * 获取控件内容
     * @author sunqinqiu 
     * @date   2019-09-28 21:40
     * @param item
     * @return
     */
    public String getWebControlContent(Map<String, String> item) {
        String webctrol = Content.getDefaultValue(item.get("control"), "input.normal");
        if (!Convert.toString(item.get(CT.STRING_DATA)).isEmpty()) {
            item.put(CT.STRING_VALUE+"v", Convert.toString(item.get(CT.STRING_VALUE)).replace("${", "").replace("}", ""));
        }
        ModelMap map = new ModelMap();
        map.put("cfg", item);
        return this.wiewFactory.getContentView(controls.get(webctrol), map);
    }
    
    /**
     * 获取控件内容
     * @author sunqinqiu 
     * @date   2019-10-05 20:13
     * @param item
     * @return
     */
    public String getWebControlViewContent(Map<String, String> item) {
        String webctrol = Content.getDefaultValue(item.get("control"), "input.normal");
        if (Convert.toString(item.get(CT.STRING_DATA)).isEmpty()) {
            webctrol = controlsView.contains(webctrol) ? webctrol + WebControlService.VIEW : "show.value";
        } else {
            webctrol = controlsView.contains(webctrol) ? webctrol + WebControlService.VIEW : "show.svalue";
            item.put(CT.STRING_VALUE, Convert.toString(item.get(CT.STRING_VALUE)).replace("${", "").replace("}", ""));
        }
        ModelMap map = new ModelMap();
        map.put("cfg", item);
        return this.wiewFactory.getContentView(controls.get(webctrol), map);
    }
    
}
