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
package com.lud.test.util.web.context;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.lud.test.web.WebUtilBaseTest;
import com.lud.util.web.util.view.ViewFactory;

/**
 * @author sunqinqiu 
 * @date   2019-09-15 21:33
 */
public class ViewFactoryTest extends WebUtilBaseTest {
    @Autowired
    private ViewFactory viewFactory;
    
    @Test
    public void test() {
        ModelMap data = new ModelMap();
        data.put("name", "sunqinqiu");
        loger.info(viewFactory.getContentView("${name}您好！", data));
    }
}
