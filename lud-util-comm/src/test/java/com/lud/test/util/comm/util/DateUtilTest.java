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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.comm.util.DateUtil;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 09:52
 */
public class DateUtilTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void testFormat() {
        loger.trace("{}", DateUtil.format(DateUtil.getNow(), DateUtil.FORMAT_TYPE_DATE_FULL));
    }
    
    @Test
    public void testGetCurrentTimestamp() {
        loger.trace("{}", DateUtil.getCurrentTimestamp());
    }
    
    @Test
    public void testGetCurrentTimestampMs() {
        loger.trace("{}", DateUtil.getCurrentTimestampMs());
    }
    
    @Test
    public void testGetDateByWeek() {
        loger.trace("{}", DateUtil.getDateByWeek(DateUtil.getNow(), 1, 1));
    }
    
    @Test
    public void testGetMonths() {
        loger.trace("MONTHS:{}", DateUtil.getMonths("2019-01-01", "2020-01-01"));
    }
    
    @Test
    public void testGetNow() {
        loger.trace("{}", DateUtil.getNow());
    }
    
    @Test
    public void testGetNowString() {
        loger.trace("{}", DateUtil.getNow(DateUtil.FORMAT_TYPE_DATE_FULL));
    }
    
    @Test
    public void testIsDateTime() {
        loger.trace("{}", DateUtil.isDateTime("", DateUtil.FORMAT_TYPE_DATE_FULL));
        loger.trace("{}", DateUtil.isDateTime("xxxxxx", DateUtil.FORMAT_TYPE_DATE_FULL));
        loger.trace("{}", DateUtil.isDateTime("2019-01-01", DateUtil.FORMAT_TYPE_DATE_FULL));
    }
    
    @Test
    public void testToDateTime() {
        loger.trace("{}", DateUtil.toDateTime("2019-01-01", DateUtil.FORMAT_TYPE_DATE_FULL));
        loger.trace("{}", DateUtil.toDateTime("2019-01-01", "xxx"));
        loger.trace("{}", DateUtil.toDateTime("", "xxx"));
    }
}
