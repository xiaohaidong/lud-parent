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

import com.lud.service.core.api.data.runner.SQLRunner;
import com.lud.service.core.api.data.runner.SQLRunnerService;
import com.lud.test.util.SpringServiceTester;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 19:16
 */
public class SQLRunnerServiceUpdateTest extends SpringServiceTester {
    
    @Autowired
    private SQLRunnerService sqlRunnerService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-02 18:32
     */
    @Test
    public void testMySQLRunner() {
        SQLRunner runner = sqlRunnerService.getRunner("lud-core", "lud_runtime_version");
        runner.addFieldValue("code", "sys").autoInsertField("id");
        loger.trace("INSERT VALUE{}", sqlRunnerService.insert(runner));
    }
    
    @Test
    public void testOracleRunner() {}
    
    @Test
    public void testSQLServerRunner() {
        SQLRunner runner = sqlRunnerService.getRunner("nec-mis", "lud_runtime_version");
        try {
            runner.addFieldValue("code", "sys").autoInsertField("id");
            loger.trace("INSERT VALUE{}", sqlRunnerService.insert(runner));
        } catch (Exception e) {
            
        }
    }
    
}
