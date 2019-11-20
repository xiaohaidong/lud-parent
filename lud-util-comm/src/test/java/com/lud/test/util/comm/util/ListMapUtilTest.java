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
package com.lud.test.util.comm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lud.util.comm.util.ListMapUtil;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 10:16
 */
public class ListMapUtilTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void test() {
        loger.trace("{}", JSON.toJSONString(ListMapUtil.getEmptyList()));
        loger.trace("{}", JSON.toJSONString(ListMapUtil.getEmptySerializableMap()));
        loger.trace("{}", JSON.toJSONString(ListMapUtil.getEmptyArrayList()));
        loger.trace("{}", JSON.toJSONString(ListMapUtil.getEmptyMap()));
        Map<String, Serializable> data = new HashMap<>();
        data.put("s", "s");
        data.put("i", 12);
        data.put("d", 12.00);
        data.put("dt", new Date());
        List<Map<String, Serializable>> list = new ArrayList<>();
        list.add(data);
        data.put("list", (Serializable) list);
        
        String sList = JSON.toJSONString(list);
        String sMap = JSON.toJSONString(data);
        loger.trace("{}", ListMapUtil.getEmptySerializableMap());
        loger.trace("{}", sMap);
        loger.trace("{}", sList);
        loger.trace("{}", ListMapUtil.toList(sList));
        loger.trace("{}", ListMapUtil.toMap(sMap));
        
    }
}
