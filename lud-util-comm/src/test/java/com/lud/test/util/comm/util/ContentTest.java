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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lud.util.comm.io.NetWorker;
import com.lud.util.comm.util.Content;
import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CT;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 07:17
 */
public class ContentTest {
    private final Logger loger       = LoggerFactory.getLogger(this.getClass());
    
    private String       content     = "12122<script>testGetText</script>";
    private String       contentNull = null;
    
    @Test
    public void testGetBetween() {
        loger.trace("{}", Content.getBetween(content, "", ""));
        loger.trace("{}", Content.getBetween(content, "<", ">"));
        loger.trace("{}", Content.getBetween(content, ">", "<"));
        loger.trace("{}", Content.getBetween(content, "<", "1"));
        loger.trace("{}", Content.getBetween(content, "*", "1"));
    }
    
    @Test
    public void testGetDefalutList() {
        loger.trace("{}", JSON.toJSONString(Content.getDefalutList(null)));
        loger.trace("{}", JSON.toJSONString(Content.getDefalutList(new ArrayList<>())));
    }
    
    @Test
    public void testGetDefaultMap() {
        loger.trace("{}", JSON.toJSONString(Content.getDefaultMap(null)));
        loger.trace("{}", JSON.toJSONString(Content.getDefaultMap(new HashMap<>())));
    }
    
    @Test
    public void testGetDefaultValue() {
        loger.trace("{}", Content.getDefaultValue(contentNull, "1"));
        loger.trace("{}", Content.getDefaultValue("x", "1"));
    }
    
    @Test
    public void testGetFirstPictrue() {
        loger.trace("{}", Content.getFirstPictrue(content));
        loger.trace("{}", Content.getFirstPictrue("<img src=\"1.jpg\" />"));
    }
    
    @Test
    public void testGetSafeString() {
        String content = "<SCRIT>testGetText</SCRPT>";
        loger.trace("{}", Content.getSafeString(content));
    }
    
    @Test
    public void testGetStringArray() {
        loger.trace("{}", JSON.toJSONString(Content.getStringArray("1", "")));
        loger.trace("{}", JSON.toJSONString(Content.getStringArray("1", "2")));
    }
    
    @Test
    public void testGetStringParent() {
        loger.trace("{}", JSON.toJSONString(Content.getStringParent("1.2.3.4.5")));
    }
    
    @Test
    public void testGetText() {
        loger.trace("getText:{}", Content.getText(NetWorker.getHttpContent("http://www.xnjd.cn/site/page/index/website_nec_index/read/i?id=21517")));
        loger.trace("getText:{}", Content.getText(contentNull));
    }
    
    @Test
    public void testGetValueByJavaScript() {
        loger.trace("{}", Content.getValueByJavaScript("1+1"));
        loger.trace("{}", Content.getValueByJavaScript("1v1"));
    }
    
    @Test
    public void testIsEmpty() {
        for (Field f : ErrorText.class.getDeclaredFields()) {
            loger.trace("{}", f.getName(), f.getType());
        }
        
        loger.trace("{}", Content.isEmpty(CT.EMPTY));
        loger.trace("{}", Content.isEmpty(contentNull));
        loger.trace("{}", Content.isEmpty(content));
    }
    
    @Test
    public void testJoinString() {
        loger.trace("{}", Content.joinString(",", "1", "2"));
        loger.trace("{}", Content.joinString(",", "1"));
    }
    
    @Test
    public void testJoinStrings() {
        loger.trace("{}", Content.joinStrings(",", 1, 2));
        loger.trace("{}", Content.joinStrings(",", 1));
        
    }
    
    @Test
    public void testLeft() {
        loger.trace("{}", Content.left(content, 12));
    }
    
    @Test
    public void testSubstring() {
        loger.trace("{}", Content.substring(content, -1, -2));
        loger.trace("{}", Content.substring(contentNull, 1, 2));
        loger.trace("{}", Content.substring(content, 1, 2));
        loger.trace("{}", Content.substring(content, 1, 1112));
        loger.trace("{}", Content.substring(content, 5, 2));
    }
}
