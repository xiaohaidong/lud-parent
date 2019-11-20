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
package com.lud.util.comm.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.comm.util.DateUtil;

/**
 * 运行时计算时间差
 * @author sunqinqiu 
 * @date   2018-05-30 21:27
 */

public class RuntimeCounter {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    private long         begin;
    
    /**
     * 初始化
     * @author sunqinqiu 
     * @date   2018-05-30 21:31
     */
    public RuntimeCounter() {
        begin = DateUtil.getNow().getTime();
    }
    
    /**
     * 计算
     * @author sunqinqiu 
     * @date   2018-05-30 21:31
     */
    public void count(String content) {
        long end = DateUtil.getNow().getTime();
        long ms = end - begin;
        loger.info("{}耗时:{}ms", content, ms);
    }
}
