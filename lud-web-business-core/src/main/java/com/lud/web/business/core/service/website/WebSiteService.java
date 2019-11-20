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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sunqinqiu 
 * @date   2019-03-26 23:50
 */
@Service
public class WebSiteService {
    
    @Autowired
    private WebSiteManager webSiteManager;
    
    /**
     * 获取网站信息
     * @author sunqinqiu 
     * @date   2019-03-27 02:03
     * @return
     */
    public WebSiteEntity getWebSite() {
        return webSiteManager.getWebSite();
    }
}
