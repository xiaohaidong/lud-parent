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
import com.lud.util.comm.util.RandomUtil;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 18:24
 */
public class FileUtilTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void test() {
        loger.trace("Test the file util!");
        String path = ServerResource.getAbsolutePath("/resource-test/comm/" + RandomUtil.getUID());
        String fileName = path + "/x.txt";
        System.out.println(fileName);
        loger.info(FileUtil.exists(fileName) + "");
        FileUtil.checkDir(fileName);
        loger.trace("{}", FileUtil.read(fileName));
        FileUtil.append(fileName, "c");
        FileUtil.creat(fileName, "c");
        FileUtil.append(fileName, "c");
        FileUtil.copy(fileName, path + "/copy.txt");
        FileUtil.checkDir(fileName);
        loger.trace("{}", FileUtil.read(fileName));
        loger.trace("{}", FileUtil.getName(fileName));
        loger.trace("{}", FileUtil.getLastName(fileName));
        FileUtil.list(path, false);
        FileUtil.listDir(path);
        FileUtil.delete(new File(path));
        FileUtil.delete(new File(ServerResource.getAbsolutePath("/resource-test/comm/x.xml")));
        FileUtil.getParentDirByNames(path, "xxxxx", "yyyyyyyyy");
        path = ServerResource.getAbsolutePath("/resource-test/comm/");
        FileUtil.list(path, true);
        FileUtil.listDir(path);
    }
}
