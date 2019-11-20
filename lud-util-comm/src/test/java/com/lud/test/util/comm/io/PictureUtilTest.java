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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.comm.io.PictureUtil;
import com.lud.util.comm.server.ServerResource;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 16:23
 */
public class PictureUtilTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void test() {
        String path = ServerResource.getAbsolutePath("/resource-test/comm/untitled.png");
        String base64 = PictureUtil.getBase64(path);
        base64 = PictureUtil.getBase64(ServerResource.getAbsolutePath("/resource-test/comm/y.png"));
        base64 = PictureUtil.getBase64(ServerResource.getAbsolutePath("/resource-test/comm/x.png"));
        loger.trace(base64);
        loger.trace("{}", PictureUtil.getPicByBase64(base64, ServerResource.getAbsolutePath("/resource-test/comm/x.png")));
        base64 = PictureUtil.getBase64(ServerResource.getAbsolutePath("/resource-test/comm/untitledx.png"));
        base64 = PictureUtil.getBase64(ServerResource.getAbsolutePath("/resource-test/comm/empty.png"));
        loger.trace("{}", PictureUtil.getPicByBase64(null, ServerResource.getAbsolutePath("/resource-test/comm/x.png")));
        loger.trace("{}", PictureUtil.getPicByBase64("kj12", null));
        loger.trace("{}", PictureUtil.getPicByBase64("kj12", ServerResource.getAbsolutePath("/resource-test/comm/x.png")));
    }
}
