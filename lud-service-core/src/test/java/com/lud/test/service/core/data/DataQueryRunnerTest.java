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

import com.lud.service.core.api.data.query.DataPager;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.core.api.data.query.DataQueryService;
import com.lud.test.util.SpringServiceTester;
import com.lud.util.content.core.data.DataQueryContent;

/**
 * @author sunqinqiu 
 * @date   2019-01-05 15:04
 */
public class DataQueryRunnerTest extends SpringServiceTester {
    @Autowired
    private DataQueryService dataQueryService;
    
    @Test
    public void test() {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_company_info_query", "def,others", "def");
        loger.trace("{}", dataQueryService.queryForList(exe));
        loger.trace("{}", dataQueryService.queryForMap(exe));
        loger.trace("{}", dataQueryService.queryForPager(exe, new DataPager(1, 1)));
        new DataQueryExecuter("lud-smartcampus.lud_traffic_channel_query", DataQueryContent.COUNT, "project,code");
        exe.addData("code", "1001");
        exe.addData("project", "1001");
        loger.info(this.dataQueryService.queryForInt(exe) + "");
        loger.info(this.dataQueryService.queryForValue(exe) + "");
    }
}
