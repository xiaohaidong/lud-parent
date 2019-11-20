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
import com.lud.service.core.api.data.query.DataPager;
import com.lud.service.core.api.data.runner.SQLRunner;
import com.lud.service.core.api.data.runner.SQLRunnerService;
import com.lud.test.util.SpringServiceTester;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 19:16
 */
public class SQLRunnerServiceTest extends SpringServiceTester {
    
    @Autowired
    private SQLRunnerService sqlRunnerService;
    DataPager                pager = new DataPager(1, 2);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-02 18:32
     */
    @Test
    public void testMySQLRunner() {
        SQLRunner runner = sqlRunnerService.getRunner("lud-core", "lud_database_version").select("id");
        loger.trace("{}", sqlRunnerService.queryForMap(runner));
        runner.pager(pager);
        loger.trace("{}", JSON.toJSONString(sqlRunnerService.queryForPager(runner)));
        runner = sqlRunnerService.getRunner("lud-core", "lud_database_version").select("id");
        runner.where(" id=?").addArg("2").order("id").top(1);
        loger.trace("{}", sqlRunnerService.queryForList(runner));
        loger.trace("{}", sqlRunnerService.queryForInt(runner));
        loger.trace("{}", sqlRunnerService.queryForValue(runner));
        runner.pager(new DataPager(1,1));
        loger.trace("{}", sqlRunnerService.queryForPager(runner));
        sqlRunnerService.delete(runner);
    }
    
}
