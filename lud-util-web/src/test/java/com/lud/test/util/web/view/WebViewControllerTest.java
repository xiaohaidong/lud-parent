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
package com.lud.test.util.web.view;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.lud.test.web.WebUtilBaseTest;

/**
 * @author sunqinqiu 
 * @date   2019-10-03 12:42
 */
public class WebViewControllerTest extends WebUtilBaseTest {
    @Autowired
    private WebViewControllerService webViewController;
    
    @Test
    public void test() {
        try {
            loger.info(webViewController.read(new ModelMap(), "web-login/main"));
            loger.info(webViewController.render(new ModelMap(), "test", "test", "main"));
            loger.info(webViewController.exe(new ModelMap(), "service-lud-user-info-login", "login"));
            loger.info(webViewController.service(new ModelMap(), "mudl-website-login-default-content", "i"));
        } catch (Exception e) {
            loger.error("{]", e);
        }
    }
}
