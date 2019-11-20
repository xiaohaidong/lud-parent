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
package com.lud.test.business.service.code;

import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.business.api.code.CodeService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.core.api.data.query.DataQueryService;
import com.lud.service.core.api.redis.RedisService;
import com.lud.test.util.SpringServiceTester;
import com.lud.util.comm.runtime.RuntimeCounter;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.redis.node.BusinessNode;

/**
 * @author sunqinqiu 
 * @date   2019-10-03 14:15
 */
public class CodeServiceTest extends SpringServiceTester {
    @Reference
    protected DataQueryService dataQueryService;
    @Reference
    protected RedisService     redisService;
    @Autowired
    private CodeService        codeService;
    private String[]           codes = { "nes.region.china", "nes.person.servicemen", "iac.dev.digit" };
    private int                times = 2;
    
    @Test
    public void test() {
        codeService.run();
        testDataQuery();
        testDataFQuery();
        testCodeService();
        testCodeNService();
    }
    
    private void testCodeNService() {
        for (String code : codes) {
            RuntimeCounter counter = new RuntimeCounter();
            for (int i = 0; i < times; i++) {
                redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_CODE_CONTENT, code);
            }
            counter.count("TEST:CodeService:CODE(" + code + "):");
        }
    }
    
    private void testCodeService() {
        for (String code : codes) {
            RuntimeCounter counter = new RuntimeCounter();
            for (int i = 0; i < times; i++) {
                codeService.getContent(code);
            }
            counter.count("TEST:CodeService:CODE(" + code + "):");
        }
    }
    
    private void testDataFQuery() {
        for (String code : codes) {
            DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_code_content_query", DataQueryContent.DEF, DataQueryContent.DEF).addData("groups", code);
            RuntimeCounter counter = new RuntimeCounter();
            for (int i = 0; i < times; i++) {
                dataQueryService.queryForList(exe);
            }
            counter.count("TEST:DATAQUERYF:CODE(" + code + "):");
        }
    }
    
    private void testDataQuery() {
        for (String code : codes) {
            RuntimeCounter counter = new RuntimeCounter();
            for (int i = 0; i < times; i++) {
                DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_code_content_query", DataQueryContent.DEF, DataQueryContent.DEF).addData("groups", code);
                dataQueryService.queryForList(exe);
            }
            counter.count("TEST:DATAQUERY:CODE(" + code + "):");
        }
    }
}
