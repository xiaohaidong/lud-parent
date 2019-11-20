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
package com.lud.util.spring.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lud.util.content.util.text.MessageText;

/**
 * Dubbo启动器
 * 
 * @author sunqinqiu
 * @date 2018-12-29 03:36
 */
public class SpringLauncher {
    
    /**
     * 日志
     */
    private static final Logger loger = LoggerFactory.getLogger(SpringLauncher.class);
    
    /**
     * 运行系统
     * 
     * @author sunqinqiu
     * @date 2019-09-11 14:06
     * @param service
     * @param isruning
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static void run(final String service, final boolean isruning) {
        try {
            loger.info(MessageText.SPRING_START_INFO, service);
            final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(MessageText.SPRING_CONTEXT_PATH);
            context.start();
            loger.info(MessageText.SPRING_STARTED_INFO, service);
            if (isruning) {
                System.in.read();
            }
        } catch (Exception e) {
            loger.error(e.toString());
        }
    }
    
    /**
     * 构造方法
     */
    private SpringLauncher() {}
}
