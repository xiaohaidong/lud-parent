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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.comm.util.Convert;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 13:47
 */
public class ConvertTest {
    private final Logger loger       = LoggerFactory.getLogger(this.getClass());
    private String       content     = "<SCRIT>testGetText</SCRPT>";
    private String       contentNull = null;
    
    @Test
    public void testConvert() {
        List<Map<String, Object>> list = null;
        loger.trace("{}", Convert.toList(list));
        list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("s", "String");
        map.put("i", 13);
        map.put("b", true);
        map.put("d", new Date());
        list.add(map);
        loger.trace("{}", Convert.toList(list));
    }
    
    @Test
    public void testToBoolean() {
        loger.trace("{}", Convert.toBoolean("true"));
        loger.trace("{}", Convert.toBoolean(1));
        loger.trace("{}", Convert.toBoolean("on"));
        loger.trace("{}", Convert.toBoolean("yes"));
        loger.trace("{}", Convert.toBoolean("success"));
        loger.trace("{}", Convert.toBoolean(contentNull));
    }
    
    @Test
    public void testToDouble() {
        loger.trace("{}", Convert.toDouble("1x"));
        loger.trace("{}", Convert.toDouble(1));
        loger.trace("{}", Convert.toDouble("12.00"));
        loger.trace("{}", Convert.toDouble(contentNull));
    }
    
    @Test
    public void testToInt() {
        loger.trace("{}", Convert.toInt("1"));
        loger.trace("{}", Convert.toInt(1));
        loger.trace("{}", Convert.toInt("12.00"));
        loger.trace("{}", Convert.toInt(contentNull));
    }
    
    @Test
    public void testToList() {
        Convert.toStringTrim(" ");
        Convert.toStringTrim(null);
    }
    
    @Test
    public void testToStringObject() {
        loger.trace("{}", Convert.toString(content));
        loger.trace("{}", Convert.toString(contentNull));
    }
}
