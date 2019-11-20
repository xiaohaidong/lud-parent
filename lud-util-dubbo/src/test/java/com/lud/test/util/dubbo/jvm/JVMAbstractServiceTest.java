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
package com.lud.test.util.dubbo.jvm;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.test.util.SpringServiceTester;
import com.lud.util.dubbo.jvm.JVMService;
import org.springframework.stereotype.Service;

/**
 * @author sunqinqiu 
 * @date   2019-10-20 15:15
 */
@Service
public class JVMAbstractServiceTest extends SpringServiceTester {
    @Autowired
    JVMService jvmService;
    
    @Test
    public void test() {
        try {
            jvmService.monitorJVMInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
