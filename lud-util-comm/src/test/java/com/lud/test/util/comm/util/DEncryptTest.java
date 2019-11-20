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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.DEncrypt;
import com.lud.util.comm.util.RandomUtil;

/**
 * 
 * @author sunqinqiu 
 * @date   2019-09-14 02:40
 */
public class DEncryptTest {
    private final Logger loger   = LoggerFactory.getLogger(this.getClass());
    private String       content = "ludis";
    private String       key     = "lud";
    
    @Test
    public void testAES() {
        loger.trace("===========AES================================");
        key = DEncrypt.generateAESKey(128, key);
        String para = RandomUtil.getRandomChar(16);
        String econtent = DEncrypt.getAESEncrypt(content, key, para);
        loger.trace("AESE:{}", econtent);
        loger.trace("AESD:{}", DEncrypt.getAESDecrypt(econtent, key, para));
    }
    
    @Test
    public void testDES() {
        loger.trace("===========DES================================");
        String deskey = DEncrypt.generateDESKey(key);
        loger.trace("DESKEY：{}", deskey);
        String econtent = DEncrypt.getDESEncrypt(content, deskey);
        loger.trace("DESEC：{}", econtent);
        loger.trace("DESC：{}", DEncrypt.getDESDecrypt(econtent, deskey));
    }
    
    @Test
    public void testGetMD5() throws Exception {
        loger.trace("===========MD5================================");
        loger.trace("MD5:{}", DEncrypt.getMD5Encode(content));
        loger.trace("SHA:{}", DEncrypt.getSHAEncode(content));
        loger.trace("SHA512:{}", DEncrypt.getSHA512(content));
        loger.trace("HMACSHA256:{}", DEncrypt.getMacSha256(content, key));
        Map<String, String> data = new HashMap<>();
        data.put("ax", "1");
        data.put("xx", "2");
        data.put("dx", "3");
        data.put("cx", "4");
        data.put("dx", "3");
        loger.trace("generateSignatureMD5:{}", DEncrypt.generateSignature(data, key, DEncrypt.ALGORITHM_MD5));
        loger.trace("generateSignatureSHA256:{}", DEncrypt.generateSignature(data, key, DEncrypt.HMAC_SHA256));
        data.put("cx", "4");
        DEncrypt.generateSignature(data, key);
        data.put("sign", DEncrypt.generateSignature(data, key, DEncrypt.HMAC_SHA256));
        loger.trace("generateSignatureMD5:{}", DEncrypt.generateSignature(data, key, DEncrypt.ALGORITHM_MD5));
        loger.trace("generateSignatureSHA256:{}", DEncrypt.isSignatureValid(data, DEncrypt.generateSignature(data, key, DEncrypt.HMAC_SHA256)));
    }
    
    @Test
    public void testGetMd5ByFile() {
        String fileName = ServerResource.getAbsolutePath("/resource-test/comm/test.xml");
        loger.trace("FileName:{}", fileName);
        loger.trace("FileMD5:{}", DEncrypt.getMd5ByFile(new File(fileName)));
    }
    
    @Test
    public void testHMAC() {
        loger.trace("===========HMAC================================");
        String mackey = DEncrypt.generateHMACKey();
        String econtent = DEncrypt.getHMACEncode(content, mackey);
        loger.trace("HMAC：", econtent);
    }
    
    @Test
    public void testPBE() {
        loger.trace("===========PBE================================");
        String sal = RandomUtil.generatePBESalt();
        String econtent = DEncrypt.getPBEEncrypt(content, key, sal);
        loger.trace("PBEE:{}", econtent);
        econtent = DEncrypt.getPBEDecrypt(econtent, key, sal);
        loger.trace("PBED:{}", econtent);
    }
    
    @Test
    public void testRSASign() {
        loger.trace("===========RSA================================");
        Map<String, Object> keyMap = DEncrypt.initRSAKey();
        loger.trace(JSON.toJSONString(keyMap));
        String ekey = DEncrypt.getRSAPrivateKey(keyMap);
        loger.trace("PRIVATE:{}", ekey);
        String econtent = DEncrypt.getRSASign(content, "x");
        econtent = DEncrypt.getRSASign(content, ekey);
        loger.trace("数字签名：{}", econtent);
        loger.trace("验证签名：{}", DEncrypt.getRSAVerify(content, DEncrypt.getRSAPublicKey(keyMap), econtent));
        loger.trace("验证签名：{}", DEncrypt.getRSAVerify(content, "s", econtent));
    }
}
