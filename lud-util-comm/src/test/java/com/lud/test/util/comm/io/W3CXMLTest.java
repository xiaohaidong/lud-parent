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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lud.util.comm.io.W3CXML;
import com.lud.util.comm.util.DEncrypt;

/**
 * @author sunqinqiu 
 * @date   2019-09-14 03:10
 */
public class W3CXMLTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-14 03:14
     */
    @Test
    public void test() {
        Map<String, String> data = new HashMap<>();
        data.put("ax", "1");
        data.put("xx", "2");
        data.put("dx", "3");
        data.put("cx", "4");
        data.put("dx", "3");
        data.put(DEncrypt.FIELD_SIGN, W3CXML.generateSignedXml(data, "xxsd"));
        loger.info(W3CXML.isSignatureValid(W3CXML.mapToXML(data), "xxsd") + "");
        String xml = W3CXML.mapToXML(data);
        loger.info(xml);
        loger.info(JSON.toJSONString(W3CXML.xmlToMap(xml)));
    }
}
