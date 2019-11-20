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
package com.lud.test.service.core.mongo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lud.service.core.api.config.ResourceService;
import com.lud.service.core.api.mongo.MongoService;
import com.lud.service.core.mongo.MongoServerManager;
import com.lud.test.util.SpringServiceTester;

/**
 * @author sunqinqiu 
 * @date   2018-12-31 23:43
 */
public class MongoServiceTest extends SpringServiceTester {
    @Autowired
    private MongoServerManager mongoServerManager;
    @Autowired
    private MongoService       mongoService;
    @Autowired
    private ResourceService    resourceService;
    
    @Test
    public void test() {
        loger.trace("{}", resourceService.getError("1000.0001"));
        loger.trace("{}", resourceService.getMessage("1000.0001"));
        mongoService.getDataBaseList();
        List<Map<String, Serializable>> where = new ArrayList<>();
        where.add(mongoService.getNewWhere("level", "=", "I"));
        where.add(mongoService.getNewWhere("dtm", "<=", new Date()));
        where.add(mongoService.getNewWhere("dtm", "<", new Date()));
        where.add(mongoService.getNewWhere("dtm", ">=", com.lud.util.comm.util.DateUtil.toDateTime("2018-10-10", "yyyy-MM-dd")));
        where.add(mongoService.getNewWhere("dtm", ">", com.lud.util.comm.util.DateUtil.toDateTime("2018-10-10", "yyyy-MM-dd")));
        loger.trace(JSON.toJSONString(mongoService.select("loger-system", "2019-09-26", "level,clsname,dtm,content", where, "dtm", -1, 0, 10)));
        List<Map<String, Serializable>> list = mongoService.getCollections("loger-system");
        loger.info(JSON.toJSONString(list));
        mongoService.insertList("loger-system", "TESTDAT", list);
        list = mongoService.getDBInfo("loger-system");
        list.forEach(item -> mongoService.insertOne("loger-system", "TESTDAT", item));
        testException();
    }
    
    public void testException() {
        List<Map<String, Serializable>> where = new ArrayList<>();
        mongoServerManager.forceClose();
        loger.trace("{}", resourceService.getError("1000.0001"));
        loger.trace("{}", resourceService.getMessage("1000.0001"));
        mongoService.getDataBaseList();
        where = new ArrayList<>();
        where.add(mongoService.getNewWhere("level", "=", "I"));
        where.add(mongoService.getNewWhere("dtm", "<=", new Date()));
        where.add(mongoService.getNewWhere("dtm", "<", new Date()));
        where.add(mongoService.getNewWhere("dtm", ">=", com.lud.util.comm.util.DateUtil.toDateTime("2018-10-10", "yyyy-MM-dd")));
        where.add(mongoService.getNewWhere("dtm", ">", com.lud.util.comm.util.DateUtil.toDateTime("2018-10-10", "yyyy-MM-dd")));
        loger.trace(JSON.toJSONString(mongoService.select("loger-system", "2018-11-27", "level,clsname,dtm,content", where, "dtm", -1, 0, 1)));
        List<Map<String, Serializable>> list = mongoService.getCollections("loger-system");
        mongoService.getCollections("loger-system");
        mongoService.insertList("loger-system", "TESTDAT", list);
        mongoService.getDBInfo("loger-system");
        list.forEach(item -> mongoService.insertOne("loger-system", "TESTDAT", item));
        mongoServerManager.forceOpen();
    }
}
