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
package com.lud.service.test.smartcampus.service.traffic;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lud.service.smartcampus.api.traffic.CarIssuesService;
import com.lud.test.util.SpringServiceTester;

/**
 * @author sunqinqiu 
 * @date   2019-09-12 10:01
 */
@Ignore
public class CarIssueServiceTest extends SpringServiceTester {
    @Autowired
    private CarIssuesService service;
    
    /**
     * 同步channel信息
     * @author sunqinqiu 
     * @date   2019-09-06 18:09
     */
    @Test
    public void test() {
        loger.info(JSON.toJSONString(service.delay("川A80A80", "2020-1-1 19:00:00", 0, "微信支付", "WebSite")));
    }
}
