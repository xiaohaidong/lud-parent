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
import com.lud.util.comm.io.ZipUtil;
import com.lud.util.comm.server.ServerResource;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 15:13
 */
public class ZipUtilTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void testCompression() throws InterruptedException {
        loger.trace("====COM==============");
        String inputFile = ServerResource.getAbsolutePath("/resource-test/comm/");
        String inputFileOut = ServerResource.getAbsolutePath("/resource-test/test/");
        FileUtil.copyDir(inputFile, inputFileOut);
        String outFile = ServerResource.getAbsolutePath("/resource-test/orgs/comm.zip");
        ZipUtil.compression(inputFile, outFile);
        inputFile = ServerResource.getAbsolutePath("/resource-test/empty/");
        outFile = ServerResource.getAbsolutePath("/resource-test/orgs/empty.zip");
        ZipUtil.compression(inputFile, outFile);
        inputFile = ServerResource.getAbsolutePath("/resource-test/empty/x.xml");
        outFile = ServerResource.getAbsolutePath("/resource-test/orgs/empty.zip");
        ZipUtil.compression(inputFile, outFile);
        inputFile = ServerResource.getAbsolutePath("/resource-test/comm/comm");
        outFile = ServerResource.getAbsolutePath("/resource-test/orgs/comm.zip");
        ZipUtil.decompression(outFile, "UTF-8", inputFileOut);
        inputFile = ServerResource.getAbsolutePath("/resource-test/comm/comm/x.xml");
        outFile = ServerResource.getAbsolutePath("/resource-test/orgs/comm.zip");
        ZipUtil.decompression(outFile, "UTF-8", inputFileOut);
        inputFile = ServerResource.getAbsolutePath("/resource-test/comm/comm/x.xml");
        outFile = ServerResource.getAbsolutePath("/resource-test/orgs/comm.zip");
        ZipUtil.decompression(outFile, "UTF-8", inputFileOut);
        FileUtil.delete(new File(outFile));
    }
    
}
