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
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.context.ContextResult;

/**
 * @author sunqinqiu 
 * @date   2019-09-15 21:37
 */
public class HttpSessionUtilTest extends WebUtilBaseTest {
    
    @Test
    public void testContextRequest() {
        ContextRequest request = new ContextRequest();
        request.setData(null);
        request.setDomain("localhost");
        request.setFlag("");
        request.setProject("");
        request.setMenu("");
        request.setService("");
        request.setToken("");
        request.setUserGroup("");
        request.setUserGroupKey("");
        request.setUserid("");
        loger.info(JSON.toJSONString(request));
    }
    
    @Test
    public void testContextResult() {
        ContextResult result = new ContextResult();
        result.setAction("");
        result.setCode("");
        result.setMessage("");
        result.setResult(false);
        result.sets(false, "cd", "cddd");
        loger.info(JSON.toJSONString(result));
    }
}
