/*
 * Copyright 2018-2019 the original author or authors. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.  
 */
package com.lud.test.web.test;

import org.junit.Test;

import com.lud.test.web.WebUtilBaseTest;

/**
 * @author sunqinqiu 
 * @date   2019-10-27 00:39
 */
public class WebTester extends WebUtilBaseTest{
    @Test
    public void test() {
        this.getRequest();
    }
}