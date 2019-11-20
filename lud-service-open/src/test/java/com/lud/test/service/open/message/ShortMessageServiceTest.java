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

import com.lud.service.open.api.message.ShortMessageBusinessService;
import com.lud.test.util.SpringServiceTester;
import com.lud.util.comm.util.DateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2019-09-16 15:37
 */
public class ShortMessageServiceTest extends SpringServiceTester {
    @Autowired
    ShortMessageBusinessService shortMessageService;
    
    @Test
    public void test() throws UnsupportedEncodingException {
        Map<String, Serializable> data = new HashMap<>();
        data.put("user", "孙钦秋");
        data.put("date", DateUtil.format(new Date(), DateUtil.FORMAT_TYPE_DATE_FULL));
        //shortMessageService.send("100202", "18084815085,8989j0", "sm.car.issues.note", data);
    }
}
