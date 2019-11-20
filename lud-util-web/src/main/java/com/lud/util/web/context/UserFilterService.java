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
package com.lud.util.web.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lud.util.web.core.WebUserService;

/**
 * @author sunqinqiu 
 * @date   2019-10-25 20:28
 */
public class UserFilterService extends HandlerInterceptorAdapter {
    @Autowired
    private WebUserService webUserService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 20:30
     * @param httpRequest
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String methd = request.getMethod().toLowerCase();
        String token = webUserService.getAndUpdateUserToken();
        if (token.isEmpty()) {
            if (methd.equals("get")) {
                request.getRequestDispatcher("/").forward(request, response);
            }
            if (methd.equals("post")) {
                response.setStatus(600);
            }
            return false;
        }
        return true;
    }
}
