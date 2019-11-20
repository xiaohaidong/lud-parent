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
package com.lud.test.service.open.swjtu.teacher;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.open.api.swjtu.teacher.TeacherAPI;
import com.lud.test.util.SpringServiceTester;

/**
 * @author sunqinqiu 
 * @date   2019-10-25 00:39
 */
public class TeacherAPIServiceTest extends SpringServiceTester {
    @Autowired
    TeacherAPI service;
    
    /**
     * 测试接口
     * @author sunqinqiu 
     * @date   2019-10-25 01:01
     */
    @Test
    public void test() {
        this.info(service.getTeacherList());
    }
}