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
package com.lud.test.service.smartcampus.service.teacher;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.smartcampus.api.teacher.TeacherService;
import com.lud.test.util.SpringServiceTester;

/**
 * @author sunqinqiu 
 * @date   2019-09-06 18:01
 */
public class TeacherServiceTest extends SpringServiceTester {
    @Autowired
    private TeacherService teacherService;
    
    /**
     * 同步教师信息
     * @author sunqinqiu 
     * @date   2019-09-06 18:09
     */
    @Test
    public void test() {
        teacherService.run();
    }
}
