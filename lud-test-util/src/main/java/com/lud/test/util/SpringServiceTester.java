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
package com.lud.test.util;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

/**
 * 测试基类
 * @author sunqinqiu 
 * @date   2018-12-31 14:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class SpringServiceTester {
    /**
     * 日志文件
     */
    public final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 00:42
     * @param obj
     */
    public void info(Object obj) {
        loger.info("{}", JSON.toJSONString(obj == null ? obj : "null"));
    }
}
