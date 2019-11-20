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
package com.lud.util.spring.jvm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.util.spring.task.Task;

/**
 * @author sunqinqiu 
 * @date   2019-10-20 16:10
 */
@Service
@Task(time = 1)
public class JVMTaskService {
    /**
     * 
     */
    @Autowired
    private IJVMService jvmService;
    
    /**
     * 监测程序
     * @author sunqinqiu 
     * @date   2019-10-25 18:39
     */
    public void run() {
        jvmService.monitorJVMInfo();
    }
}