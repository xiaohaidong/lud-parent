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

import com.alibaba.fastjson.JSON;
import com.lud.test.web.WebUtilBaseTest;
import com.lud.util.web.api.view.ViewDataEntity;
import com.lud.util.web.util.view.WebViewDataService;

/**
 * @author sunqinqiu 
 * @date   2019-10-03 15:02
 */
public class WebViewDataServiceTest extends WebUtilBaseTest {
    @Autowired
    WebViewDataService webViewDataService;
    
    @Test
    public void test() {
        ViewDataEntity data = new ViewDataEntity();
        data.setName("i");
        data.setQuery("nes.person.marriage");
        data.setType("code");
        loger.info(JSON.toJSONString(webViewDataService.getData(data, this.getRequest())));
    }
}
