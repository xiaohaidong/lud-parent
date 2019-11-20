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
package com.lud.web.server.controller.web;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lud.web.business.core.service.website.WebSiteEntity;
import com.lud.web.business.core.service.website.WebSiteService;

/**
 * @author sunqinqiu 
 * @date   2019-03-23 09:58
 */
@Controller
@RequestMapping("/web/site")
public class WebSiteController {
    private static final String VIEW_BASE_PATH = "/view-render/web-site/{0}/{1}/{2}";
    @Autowired
    private WebSiteService      webSiteService;
    
    /**
     * 频道
     * @author sunqinqiu 
     * @date   2019-07-18 17:17
     * @param map
     * @param channel
     * @return
     */
    @RequestMapping("/channle/{channel}")
    public String channel(ModelMap map, @PathVariable String channel) {
        WebSiteEntity website = webSiteService.getWebSite();
        String path = MessageFormat.format(VIEW_BASE_PATH, website.getProject(), website.getCode(), website.getSkin());
        map.put("site", website);
        return path + "/channel.html";
    }
    
    /**
     * 首页
     * @author sunqinqiu 
     * @date   2019-07-18 17:17
     * @param map
     * @return
     */
    @RequestMapping(value = { "", "/" })
    public String index(ModelMap map) {
        WebSiteEntity website = webSiteService.getWebSite();
        String path = MessageFormat.format(VIEW_BASE_PATH, website.getProject(), website.getCode(), website.getSkin());
        map.put("site", website);
        return path + "/index.html";
    }
    
    /**
     * 单页面
     * @author sunqinqiu 
     * @date   2019-07-18 17:17
     * @param map
     * @param channel
     * @return
     */
    @RequestMapping("/page/{page}")
    public String page(ModelMap map, @PathVariable String page) {
        WebSiteEntity website = webSiteService.getWebSite();
        String path = MessageFormat.format(VIEW_BASE_PATH, website.getProject(), website.getCode(), website.getSkin());
        map.put("site", website);
        return path + "/index.html";
    }
}
