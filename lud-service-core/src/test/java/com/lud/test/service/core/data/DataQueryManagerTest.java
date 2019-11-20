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
package com.lud.test.service.core.data;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lud.service.core.api.data.query.DataQuery;
import com.lud.service.core.api.data.resolves.DataResolverManager;
import com.lud.test.util.SpringServiceTester;
import com.lud.util.comm.runtime.RuntimeCounter;

/**
 * @author sunqinqiu 
 * @date   2019-01-04 19:49
 */
public class DataQueryManagerTest extends SpringServiceTester {
    @Autowired
    private DataResolverManager dataResolverManager;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-04 20:12
     */
    @Test
    public void test() {
        dataResolverManager.run();
        RuntimeCounter counter = new RuntimeCounter();
        for (int i = 0; i < 10; i++) {
            DataQuery query = dataResolverManager.getDataQuery("lud-core.lud_runtime_company_query" + (i > 0 ? i : ""));
            JSON.toJSONString(query);
        }
        counter.count("JS:");
    }
}
