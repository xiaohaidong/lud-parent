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
package com.lud.util.spring.task;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.lud.util.spring.util.SpringContextUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-10-20 14:20
 */
@Component
public class TaskService {
    
    /**
     * 
     */
    private long    times = 0L;
    
    /**
     * 
     */
    @Getter
    @Setter
    private boolean run   = false;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-20 15:22
     */
    private void runTasks() {
        Map<String, Object> beans = SpringContextUtil.getBeansWithAnnotation(Task.class);
        beans.forEach((k, v) -> {
            Task task = v.getClass().getAnnotation(Task.class);
            if (this.times % task.time() == 0) {
                for (String exe : task.exe().split(",")) {
                    Method method = ReflectionUtils.findMethod(v.getClass(), exe);
                    ReflectionUtils.invokeMethod(method, v);
                }
            }
        });
    }
    
    /**
     * 定时执行
     * @author sunqinqiu 
     * @date   2019-10-20 14:40
     */
    @Scheduled(fixedRate = 60000)
    public void runTask() {
        if (!run) return;
        runTasks();
        this.times++;
        if (this.times == 24 * 60 * 365) this.times = 0;
    }
}
