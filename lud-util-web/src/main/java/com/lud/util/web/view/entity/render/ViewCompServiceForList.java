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

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.util.comm.util.Convert;
import com.lud.util.web.api.view.IViewCompService;
import com.lud.util.web.api.view.ViewCompEntity;
import com.lud.util.web.api.view.ViewServiceEntity;
import com.lud.util.web.view.webcontrol.WebControlService;

/**
 * @author sunqinqiu 
 * @date   2019-10-03 19:52
 */
@Service("com.lud.web.util.view.entity.render.viewcompservice.list")
public class ViewCompServiceForList extends AbstractViewCompService implements IViewCompService {
    @Autowired
    private WebControlService webControlService;
    
    @Override
    public List<Map<String, String>> getDatail(ViewServiceEntity view, ViewCompEntity comp) {
        comp.getDetail().forEach(this::getWebControl);
        return comp.getDetail();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-28 21:12
     * @param item
     */
    private void getWebControl(Map<String, String> item) {
        String control = Convert.toString(item.get("control"));
        if (!control.isEmpty()) {
            item.put("value", item.get("text"));
            String controlCtrl = webControlService.getWebControlContent(item);
            String controlText = webControlService.getWebControlViewContent(item);
            String controlCss = Convert.toString(item.get("edt")).isEmpty() ? "npd edt" : item.get("edt");
            item.put("text", "<#if roles?seq_contains(\"" + item.get("role") + "\")>" + controlCtrl + "<#else>" + controlText + "</#if>");
            String tdclass = Convert.toString(item.get("td-class"));
            tdclass += " <#if roles?seq_contains(\"" + item.get("role") + "\")>" + controlCss + "</#if>";
            item.put("td-class", tdclass);
        }
    }
}
