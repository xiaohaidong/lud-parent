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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.core.api.data.jdbc.JDBCService;
import com.lud.service.core.data.jdbc.JDBC;
import com.lud.service.core.data.jdbc.JDBCFactory;
import com.lud.test.util.SpringServiceTester;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 00:04
 */
public class DataServiceJSBCTest extends SpringServiceTester {
    @Autowired
    private JDBCFactory jdbcFactory;
    @Autowired
    private JDBCService jdbcService;
    
    /**
     * 测试JDBC
     * @author sunqinqiu 
     * @date   2019-01-02 04:01
     */
    @Test
    public void testJDBC() {
        List<Serializable> para = new ArrayList<>();
        para.add(1);
        loger.info("{}", jdbcService.queryForInt("lud-core", "select count(*) from lud_application_info", null));
        loger.info("{}", jdbcService.queryForInt("lud-core", "select count(*) from lud_application_info where id=?", para));
        loger.info("{}", jdbcService.queryForValue("lud-core", "select count(*) from lud_application_info", null));
        loger.info("{}", jdbcService.queryForValue("lud-core", "select count(*) from lud_application_info where id=?", para));
        loger.info("{}", jdbcService.queryForMap("lud-core", "select * from lud_application_info", null));
        loger.info("{}", jdbcService.queryForMap("lud-core", "select * from lud_application_info where id=?", para));
        loger.info("{}", jdbcService.queryForMap("lud-core", "select * from lud_application_info where 1=2 and id=?", para));
        jdbcService.update("lud-core", "update lud_runtime_version set updator=updator", null);
        jdbcService.update("lud-core", "update lud_runtime_version set updator='1' where id=?", para);
        jdbcService.execute("lud-core", "update lud_runtime_version set updator=updator;x");
        JDBC jdbc = jdbcFactory.getJDBC("lud-core");
        jdbc.getPool();
        jdbc.getTrans();
        jdbc.getType();
        loger.info("{}", jdbcService.queryForMap("lud-core", "select * from lud_runtime_version where  id=?  order by id limit 1", para));
    }
    
    @Test
    public void testJDBCN() {
        loger.info("TEST JDBCN");
        List<Serializable> para = new ArrayList<>();
        para.add(1);
        loger.info("{}", jdbcService.queryForInt("lud-corex", "select count(*) from lud_application_info", null));
        loger.info("{}", jdbcService.queryForValue("lud-corex", "select count(*) from lud_application_info", null));
        loger.info("{}", jdbcService.queryForMap("lud-corex", "select * from lud_application_info", null));
        jdbcService.update("lud-corex", "update lud_runtime_version set updator=updator", null);
        jdbcService.execute("lud-corex", "update lud_runtime_version set updator=updator");
        loger.info("{}", jdbcService.queryForMap("lud-corex", "select * from lud_runtime_version where  id=?  order by id limit 1", para));
    }
}
