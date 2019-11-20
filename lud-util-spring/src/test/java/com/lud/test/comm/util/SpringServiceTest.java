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
package com.lud.test.comm.util;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.test.util.SpringServiceTester;
import com.lud.util.spring.util.SpringContextUtil;
import com.lud.util.spring.util.SpringServiceInvoker;

/**
 * @author sunqinqiu 
 * @date   2018-12-31 14:47
 */
public class SpringServiceTest extends SpringServiceTester {
    @Autowired
    private SpringService springService;
    
    @Test
    public void test() {
        springService.sayHello();
        SpringServiceInvoker.invoke("com.lud.test.comm.bean.SpringService.sayHello");
        SpringServiceInvoker.invoke("com.lud.test.comm.bean.SpringService.sayHello", "The secondmetod");
        SpringServiceInvoker.invoke("com.lud.test.comm.bean.SpringServicex.sayHello");
        loger.trace("{}", SpringContextUtil.getAliases("com.lud.test.comm.bean.SpringService").toString());
        loger.trace("containsBean:{}", SpringContextUtil.containsBean("com.lud.test.comm.bean.SpringService"));
        loger.trace("isSingleton{}", SpringContextUtil.isSingleton("com.lud.test.comm.bean.SpringService"));
        loger.trace("getType{}", SpringContextUtil.getType("com.lud.test.comm.bean.SpringService"));
        SpringServiceImp bean = SpringContextUtil.getBean("com.lud.test.comm.bean.SpringService", SpringServiceImp.class);
        bean.sayHello();
    }
}
