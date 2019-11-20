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
package com.lud.test.util.comm.runtime;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lud.util.comm.runtime.JVMInfo;
import com.lud.util.comm.runtime.RuntimeCounter;
import com.lud.util.comm.util.ListComparator;
import com.lud.util.content.util.text.CT;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 11:58
 */
public class JVMInfoTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void testGetRuntimeInfo() {
        RuntimeCounter c=new RuntimeCounter();
        loger.trace("{}", JSON.toJSONString(JVMInfo.getRuntimeInfo()));
        loger.trace("{}", JSON.toJSONString(JVMInfo.getSystemInfo()));
        JVMInfo.getThreadsInfo().sort(new ListComparator(CT.STRING_ID));
        c.count("l");
    }
    
}
