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
package com.lud.web.server.controller.runtime;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lud.util.web.controller.BaseWebController;

/**
 * Root Controller
 * @author sunqinqiu 
 * @date   2019-01-01 15:11
 */
@Controller
public class RootController extends BaseWebController {
    
    /**
     * ROOT重定向
     * @author sunqinqiu 
     * @date   2019-02-23 14:42
     * @param response
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/")
    public void root(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        try {
            String rootUrl = this.domainService.getRootByDomain(this.getRequest().getDomain());
            request.getRequestDispatcher(rootUrl).forward(request, response);
        } catch (Exception ex) {
            request.getRequestDispatcher("/runtime/error/page-404").forward(request, response);
        }
    }
}
