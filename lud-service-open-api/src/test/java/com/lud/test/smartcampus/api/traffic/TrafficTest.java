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
package com.lud.test.smartcampus.api.traffic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lud.service.open.api.ApiResult;
import com.lud.service.open.api.swjtu.traffic.TrafficAPIToken;
import com.lud.test.util.EntityClassTester;

/**
 * @author sunqinqiu 
 * @date   2019-09-16 00:26
 */
public class TrafficTest {
    
    @Test
    public void test() throws Exception {
        @SuppressWarnings("rawtypes")
        List<Class> list = new ArrayList<>();
        list.add(ApiResult.class);
        list.add(TrafficAPIToken.class);
        EntityClassTester.testEntities(list);
    }
}
