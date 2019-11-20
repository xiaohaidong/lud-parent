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
package com.lud.test.service.core.api.data.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lud.service.core.api.data.query.DataPager;
import com.lud.service.core.api.data.query.DataQuery;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.core.api.data.query.DataQueryField;
import com.lud.service.core.api.data.runner.SQLField;
import com.lud.service.core.api.data.runner.SQLRunner;
import com.lud.service.core.api.redis.RedisConfig;
import com.lud.service.core.api.server.ServerConfig;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.core.data.PLSQLContent;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 05:42
 */
public class SQLRunnerTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-04 17:20
     */
    @Test
    public void test() {
        DataPager page = null;
        for (int i = -1; i <= 6; i++) {
            page = new DataPager(i, i * 900);
            page.setDataCount(1000);
            page.reckonPagerNum();
            page.setDataCount(0);
            page.reckonPagerNum();
            page.setDataList(new ArrayList<>());
            loger.trace(JSON.toJSONString(page));
        }
        SQLRunner runner = new SQLRunner(PLSQLContent.K_TOP, PLSQLContent.K_UPDATE).select(PLSQLContent.K_WHERE).top(1);
        runner.order(PLSQLContent.SQL_BULIDER_NAME).group(PLSQLContent.SQL_BULIDER_NAME).where(PLSQLContent.V_DEF, PLSQLContent.V_TABEL_A, PLSQLContent.V_TABLE).where(" the ").addArg("x").pager(page);
        loger.trace("{}", JSON.toJSONString(runner));
        /**
         * 
         */
        Map<String, DataQueryField> field = new HashMap<>();
        DataQueryField f = new DataQueryField(PLSQLContent.C_D, PLSQLContent.C_EMPTY);
        f.setContent(PLSQLContent.C_SPACE);
        f.setArgs(PLSQLContent.K_AND);
        DataQuery query = new DataQuery();
        query.setKey(PLSQLContent.K_DELETE);
        query.setFields(field);
        query.setGroup(PLSQLContent.K_EQ);
        query.setLimits(field);
        query.setName(PLSQLContent.K_FROM);
        query.setOrder(PLSQLContent.K_GROUP);
        query.setTable(PLSQLContent.K_INSERT);
        loger.trace("{}", JSON.toJSONString(query));
        /**
         * 
         */
        DataQueryExecuter exe = new DataQueryExecuter();
        exe.addData(PLSQLContent.K_INSERT_VALUE, PLSQLContent.K_LIMIT).addWhere(PLSQLContent.K_OR, PLSQLContent.K_ORDER).order(PLSQLContent.K_SELECT).pager(page).group(PLSQLContent.K_SET).top(1);
        loger.trace("{}", JSON.toJSONString(exe));
        
        SQLField fx = new SQLField(DataQueryContent.ID, DataQueryContent.CODE);
        loger.trace("{}", JSON.toJSONString(fx));
        ServerConfig config = new ServerConfig();
        config.setConnTimes(1);
        config.reConn();
        loger.trace("{}", JSON.toJSONString(config));
        RedisConfig cf = new RedisConfig();
        cf.reConn();
        loger.trace("{}", JSON.toJSONString(cf));
    }
    
}
