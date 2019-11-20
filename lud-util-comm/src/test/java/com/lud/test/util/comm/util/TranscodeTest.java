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
package com.lud.test.util.comm.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.comm.util.Transcode;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 12:52
 */
public class TranscodeTest {
    private final Logger loger   = LoggerFactory.getLogger(this.getClass());
    String               content = "&#20013;&#25991;&#27468;\u4e2d\u6587\u6b4c中文Chinese*＆%￥㊚㊛囍㊒\r\n\u4E2D\u5348\u4F60\u81EA\u8D1F";
    
    @Test
    public void testTrans() {
        
        loger.trace("{}", Transcode.trans("中文Client", "GBK", "UTF-8"));
        loger.trace("{}", Transcode.trans(content, "GBK", "UTF-8"));
        loger.trace("{}", Transcode.trans(content, "GBK", "UTF-8x"));
        loger.trace("{}", Transcode.getByte(content));
        loger.trace("{}", Transcode.getByte(content, "UTF8X"));
        byte[] dt = Transcode.getByte(content, "UTF-8");
        loger.trace("{}", Transcode.encode(dt));
        loger.trace("{}", Transcode.decode(Transcode.encode(dt)));
        loger.trace("{}", Transcode.getString(dt));
        loger.trace("{}", Transcode.getString(dt, "UTF"));
        loger.trace("{}", Transcode.stringToUnicode(content));
        loger.trace("{}", Transcode.unicodeToString(content));
        String base64 = Transcode.stringToBase64(content);
        loger.trace("{}", base64);
        loger.trace("{}", Transcode.base64Tostring(base64));
        loger.trace("{}", Transcode.hexToString(Transcode.stringToHex(content)));
        base64 = Transcode.stringToBin(content);
        loger.trace("{}", base64);
        loger.trace("{}", Transcode.binToString(base64));
    }
    
}
