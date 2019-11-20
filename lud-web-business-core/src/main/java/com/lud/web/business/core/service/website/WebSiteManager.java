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
package com.lud.web.business.core.service.website;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;
import com.lud.util.web.context.HttpContext;

/**
 * @author sunqinqiu 
 * @date   2019-03-26 23:50
 */
@Service
@Preload(seq = 10)
public class WebSiteManager extends CoreService {
    private static final String WEBSITE_INFO = "com.lud.web.site.info";
    /**
     * 存储网站对应的domain
     * 一个DOMAIN只能对应一个网站
     */
    private Map<String, String> domains      = new HashMap<>();
    
    /**
     * 解析网站信息
     * @author sunqinqiu 
     * @date   2019-03-26 23:57
     */
    
    public void run() {
        resolveWebChannel();
    }
    
    /**
     * 获取WebisteINFO
     * @author sunqinqiu 
     * @date   2019-03-27 02:00
     * @return
     */
    public WebSiteEntity getWebSite() {
        String domain = HttpContext.getDomain();
        String code = domains.get(domain);
        String siteJSON = this.redisService.hget(WebSiteManager.WEBSITE_INFO, code);
        return JSON.parseObject(siteJSON, WebSiteEntity.class);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-26 23:57
     */
    private void resolveWebChannel() {
        domains.clear();
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_website_info_query", DataQueryContent.DEF, DataQueryContent.DEF);
        this.dataQueryService.queryForList(exe).forEach(item -> {
            WebSiteEntity site = new WebSiteEntity();
            site.setCode(Convert.toString(item.get("code")));
            site.setProject(Convert.toString(item.get("project")));
            site.setDomain(Convert.toString(item.get("domain")));
            site.setEmail(Convert.toString(item.get("email")));
            site.setIcp(Convert.toString(item.get("icp")));
            site.setKeyWords(Convert.toString(item.get("keywords")));
            site.setName(Convert.toString(item.get("name")));
            site.setShortName(Convert.toString(item.get("short")));
            site.setSkin(Convert.toString(item.get("skin")));
            site.setTel(Convert.toString(item.get("tel")));
            site.setTitle(Convert.toString(item.get("title")));
            String code = site.getProject() + "." + site.getCode();
            this.redisService.hset(WebSiteManager.WEBSITE_INFO, code, JSON.toJSONString(site));
            domains.put(site.getDomain(), code);
        });
    }
    
}
