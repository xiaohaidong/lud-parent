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
import java.util.List;

import org.junit.Test;

import com.lud.service.core.api.config.BaseServerConfig;
import com.lud.service.core.api.data.command.DataCommand;
import com.lud.service.core.api.data.command.DataCommandItem;
import com.lud.service.core.api.data.query.DataPager;
import com.lud.service.core.api.data.query.DataQuery;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.core.api.data.query.DataQueryField;
import com.lud.service.core.api.data.runner.SQLField;
import com.lud.service.core.api.data.runner.SQLRunner;
import com.lud.service.core.api.redis.RedisConfig;
import com.lud.service.core.api.server.ServerConfig;
import com.lud.test.util.EntityClassTester;
import com.lud.util.content.redis.node.RedisNode;

/**
 * @author sunqinqiu 
 * @date   2019-09-16 04:54
 */
public class FinalContentTest {
    
    @Test
    public void test() throws Exception {
        @SuppressWarnings("rawtypes")
        List<Class> list = new ArrayList<>();
        list.add(BaseServerConfig.class);
        list.add(DataCommand.class);
        list.add(DataCommandItem.class);
        list.add(DataPager.class);
        list.add(DataQuery.class);
        list.add(DataQueryExecuter.class);
        list.add(DataQueryField.class);
        list.add(SQLField.class);
        list.add(DataQuery.class);
        list.add(RedisConfig.class);
        list.add(ServerConfig.class);
        list.add(SQLRunner.class);
        list.add(RedisNode.class);
        EntityClassTester.testEntities(list);
    }
}
