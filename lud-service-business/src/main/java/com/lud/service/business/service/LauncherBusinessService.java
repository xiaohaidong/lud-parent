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
package com.lud.service.business.service;

import com.lud.util.spring.util.SpringLauncher;

import java.io.IOException;

/**
 * 核心服务启动库
 * @author sunqinqiu 
 * @date   2018-12-29 03:25
 */
public class LauncherBusinessService {

    /**
     * 启动器
     * @author sunqinqiu 
     * @date   2018-12-29 03:34
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
        SpringLauncher.run("lud-service-business", true);
    }
}