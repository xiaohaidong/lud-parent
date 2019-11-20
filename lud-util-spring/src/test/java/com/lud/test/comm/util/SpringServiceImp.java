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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author sunqinqiu 
 * @date   2018-12-31 14:45
 */
@Service("com.lud.test.comm.bean.SpringService")
public class SpringServiceImp implements SpringService {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public void sayHello() {
        loger.trace("Spring say Hello!");
    }
    
    @Override
    public void sayHello(String name) {
        loger.trace("{} say Hello!", name);
    }
}
