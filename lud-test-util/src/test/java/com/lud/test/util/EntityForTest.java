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
package com.lud.test.util;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-10-19 09:41
 */

public class EntityForTest {
    
    public final Logger loger = LoggerFactory.getLogger(this.getClass());
    @Getter
    @Setter
    private String      name;
    @Getter
    @Setter
    private boolean     read;
    @Getter
    @Setter
    private String      sex;
    
    private String      content;
    
    @Test
    public void test() {
        System.out.println("x");
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return content.isEmpty() ? "1" : "2";
    }
    
    public String relTest(Map<String, Object> data) {
        return data.toString();
    }
    
    public EntityForTest() {
        this.name = "";
    }
    
    public String testError(String content) {
        try {
            return Integer.parseInt(content) + "";
        } catch (Exception e) {
            return "error";
        }
    }
}
