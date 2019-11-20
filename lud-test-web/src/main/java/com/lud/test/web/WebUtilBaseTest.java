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
package com.lud.test.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lud.test.util.SpringServiceTester;
import com.lud.util.content.util.text.CT;
import com.lud.util.web.api.context.ContextRequest;

import junit.framework.TestCase;

/**
 * @author sunqinqiu 
 * @date   2019-01-07 02:01
 */
@WebAppConfiguration
public class WebUtilBaseTest extends SpringServiceTester {
    
    /**
     * 
     */
    protected MockMvc                mockMvc;
    
    /**
     * 
     */
    @Autowired
    protected WebApplicationContext  wac;
    
    /**
     * 
     */
    @Autowired
    protected MockHttpSession        session;
    
    /**
     * 
     */
    @Autowired
    protected MockHttpServletRequest request;
    
    /**
     * 
     */
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-15 21:24
     */
    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-18 02:23
     */
    @Test
    public void testvoid() {
        loger.info("Begin test");
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-20 21:20
     * @param url
     */
    protected void testUrl(String url) {
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)).andExpect(MockMvcResultMatchers.status().is(200)).andDo(MockMvcResultHandlers.print()).andReturn();
            int status = mvcResult.getResponse().getStatus();
            TestCase.assertEquals(status, 200);
        } catch (Exception e) {
            loger.error(e.toString());
        }
    }
    
    public static HttpServletRequest request() {
        try {
            if (RequestContextHolder.currentRequestAttributes() == null) { return null; }
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static String getDomain() {
        HttpServletRequest request = request();
        if (request == null) { return CT.EMPTY; }
        return request.getServerName();
    }
    
    protected ContextRequest getRequest() {
        ContextRequest req = new ContextRequest();
        req.setDomain(getDomain());
        req.setProject("100202");
        req.setUserGroup("manager");
        req.setMenu("2001");
        req.setUserid("sysmanager");
        req.setDomain("bwc.swjtu.osxor.com");
        req.setToken("9D891E731F75DEAE56884D79E9816736B7488080");
        Map<String, String> data = new HashMap<>();
        data.put("code", "SmctucForTeacher");
        data.put("lud-role", "SmctucForTeacher:0102");
        req.setData(data);
        return req;
    }
}
