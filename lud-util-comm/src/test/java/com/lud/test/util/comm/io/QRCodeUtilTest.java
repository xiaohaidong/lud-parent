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

import com.google.zxing.BarcodeFormat;
import com.lud.util.comm.io.QRCodeUtil;
import com.lud.util.comm.server.ServerResource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 16:47
 */
public class QRCodeUtilTest {
    private final Logger loger   = LoggerFactory.getLogger(this.getClass());
    String               content = "123456789孙钦秋";
    
    @Test
    public void testWriteToFile() {
        loger.trace("=====MAKE QRCODE=============");
        QRCodeUtil.writeToFile(content, BarcodeFormat.QR_CODE, ServerResource.getAbsolutePath("/resource-test/orgs/comm.png"), 100, 100);
        try (OutputStream stream = new FileOutputStream(new File(ServerResource.getAbsolutePath("/resource-test/orgs/coxmm.png")))) {
            QRCodeUtil.writeToStream(content, BarcodeFormat.QR_CODE, stream, 100, 100);
            QRCodeUtil.writeToStream(content, BarcodeFormat.CODE_39, stream, 100, 100);
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testWriteToStream() {
        
    }
}
