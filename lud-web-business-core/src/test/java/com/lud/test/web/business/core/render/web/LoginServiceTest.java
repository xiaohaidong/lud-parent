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
package com.lud.test.web.business.core.render.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.test.web.WebUtilBaseTest;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.context.ContextResult;
import com.lud.web.business.core.render.web.LoginService;

/**
 * @author sunqinqiu 
 * @date   2019-09-16 04:23
 */
public class LoginServiceTest extends WebUtilBaseTest {
    @Autowired
    private LoginService loginService;
    
    @Test
    public void test() {
        Map<String, Serializable> map = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        map.put("userpwd", "5731C320E65D0317DF2595EAE901AFA5A5827507");
        ContextRequest request = new ContextRequest();
        data.put("userpwd", "sqq102720");
        request.setData(data);
        request.setProject("100201");
        request.setUserGroup("manager");
        request.setMenu("1001");
        request.setDomain("localhost");
        loginService.userLogin(map, request, new ContextResult());
    }
}
