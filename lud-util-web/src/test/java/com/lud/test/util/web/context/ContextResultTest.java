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

import com.alibaba.fastjson.JSON;
import com.lud.test.web.WebUtilBaseTest;
import com.lud.util.web.context.HttpContext;

/**
 * @author sunqinqiu 
 * @date   2019-09-15 21:14
 */
public class ContextResultTest extends WebUtilBaseTest {
    
    @Test
    public void test() {
        loger.info(HttpContext.getRequest().toString());
        loger.info(HttpContext.getContextPath());
        loger.info(HttpContext.getDomain());
        loger.info(JSON.toJSONString(HttpContext.getParameters()));
        HttpContext.getResponse().addHeader("x", "y");
    }
}
