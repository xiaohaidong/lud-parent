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
import java.util.Map;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.lud.util.comm.util.Content;
import com.lud.util.content.util.text.CT;
import com.lud.util.spring.util.SpringContextUtil;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.controller.BaseWebController;

/**
 * @author sunqinqiu 
 * @date   2019-02-23 15:28
 */
public class WebViewController extends BaseWebController {
    
    private static final String ROOT = "/view-render";
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-03 11:34
     * @param map
     * @param service
     * @param flag
     * @return
     */
    @ResponseBody
    @RequestMapping("/exe/{service}/{flag}")
    public String exe(ModelMap map, @PathVariable String service, @PathVariable String flag) {
        ContextRequest request = this.getRequest();
        map.put(CT.STRING_PROJECTID, request.getProject());
        request.setService(service);
        request.setFlag(flag);
        return JSON.toJSONString(webViewService.exe(service, flag, request));
    }
    
    /**
     * 读取静态页面
     * @author sunqinqiu 
     * @date   2019-10-03 12:41
     * @param map
     * @param page
     * @return
     */
    @RequestMapping("/read/{page}")
    public String read(ModelMap map, @PathVariable String page) {
        map.put(CT.STRING_PROJECTID, this.getRequest().getProject());
        Map<String, String> data = this.getRequest().getData();
        if (!Content.isSafeString(data)) { return "900.html"; }
        map.put("req", data);
        map.put("page", page);
        return MessageFormat.format("{0}/{1}.html", ROOT, page.replace(".", "/"));
    }
    
    /**
     * 基础服务
     * @author sunqinqiu 
     * @date   2019-01-10 20:41
     * @param service
     * @param page
     * @return
     */
    @RequestMapping("/render-{business}/{service}/{page}")
    public String render(ModelMap map, @PathVariable String business, @PathVariable String service, @PathVariable String page) {
        ContextRequest request = this.getRequest();
        map.put(CT.STRING_PROJECTID, request.getProject());
        String beanName = MessageFormat.format("com.lud.web.business.{0}.render.{1}.{2}", business, service.replace("-", "."), page.replace("-", "."));
        IWebViewRender render = SpringContextUtil.getBean(beanName, IWebViewRender.class);
        map.putAll(render.getModelMap(request));
        map.put(CT.STRING_SERVICE, service);
        map.put(CT.STRING_PAGE, page);
        return MessageFormat.format("{0}/{1}/{2}.html", ROOT, service, page);
    }
    
    /**
     * 获取Service
     * @author sunqinqiu 
     * @date   2019-01-11 01:11
     * @param map
     * @param service
     * @param action
     * @param flag
     * @return
     */
    @RequestMapping("/service/{service}/{flag}")
    public String service(ModelMap map, @PathVariable String service, @PathVariable String flag) {
        ContextRequest request = this.getRequest();
        map.put(CT.STRING_PROJECTID, request.getProject());
        request.setService(service);
        request.setFlag(flag);
        map.put(CT.STRING_CONTENT, webViewService.getWebViewContent(service, flag, request));
        return MessageFormat.format("{0}.html", CT.STRING_CONTENT);
    }
}
