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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.util.view.WebViewController;

/**
 * @author sunqinqiu 
 * @date   2019-01-07 14:40
 */
@Controller
@RequestMapping("/web/service")
public class WebServiceController extends WebViewController {
    /**
     * 重写获取
     * @author sunqinqiu 
     * @date   2019-01-11 01:15
     * @return
     */
    @Override
    protected ContextRequest getRequest() {
        ContextRequest request = super.getRequest();
        request.setUserGroup("public");
        return request;
    }
}
