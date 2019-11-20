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
package com.lud.test.util.comm.io;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.comm.io.FileUtil;
import com.lud.util.comm.server.ServerResource;

/**
 * @author sunqinqiu 
 * @date   2019-09-15 22:32
 */
public class FileDTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void testCompression() throws InterruptedException {
        loger.trace("====COM==============");
        String inputFile = ServerResource.getAbsolutePath("/resource-test/commd/");
        FileUtil.delete(new File(inputFile));
    }
}
