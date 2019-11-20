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

import com.lud.util.comm.util.RandomUtil;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 10:18
 */
public class RandomUtilTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void testGeneratePBESalt() {
        loger.trace("{}", RandomUtil.generatePBESalt());
    }
    
    @Test
    public void testGetRandom() {
        loger.trace("{}", RandomUtil.getRandom(10));
    }
    
    @Test
    public void testGetRandomChar() {
        loger.trace("{}", RandomUtil.getRandomChar(10));
    }
    
    @Test
    public void testGetUID() {
        loger.trace("{}", RandomUtil.getUID());
    }
}
