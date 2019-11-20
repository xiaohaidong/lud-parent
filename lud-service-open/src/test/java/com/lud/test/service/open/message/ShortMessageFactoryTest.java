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
package com.lud.test.service.open.message;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.open.api.message.ShortMesageFactory;
import com.lud.service.open.api.message.ShortMessage;
import com.lud.test.util.SpringServiceTester;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.comm.util.ListMapUtil;

/**
 * @author sunqinqiu 
 * @date   2019-10-26 00:15
 */
public class ShortMessageFactoryTest extends SpringServiceTester {
    @Autowired
    private ShortMesageFactory shortMesageFactory;
    
    @Test
    public void test() {
        ShortMessage message = new ShortMessage();
        message.setConfig(ListMapUtil.toMap("{\"userid\":\"xnjdyjsy-009\",\"password\":\"sqq102720\"}"));
        message.setDriver("shangxun");
        message.setTemplement("sm.car.issues.note");
        message.setTels("18084815085");
        Map<String, Serializable> data = new HashMap<>();
        data.put("user", "孙钦秋");
        data.put("date", DateUtil.format(new Date(), DateUtil.FORMAT_TYPE_DATE_FULL));
        message.setData(data);
        info(shortMesageFactory.send(message));
    }
}
