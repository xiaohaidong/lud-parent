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
package com.lud.test.util.web.view;

import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.project.domain.DomainService;
import com.lud.test.web.WebUtilBaseTest;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.context.HttpContext;
import com.lud.util.web.util.view.WebViewService;

/**
 * @author sunqinqiu 
 * @date   2019-09-15 22:48
 */
public class WebViewTest extends WebUtilBaseTest {
    private final Logger     loger = LoggerFactory.getLogger(this.getClass());
    /**
     * 项目标记
     */
    @Reference
    protected DomainService  domainService;
    
    /**
     * Web服务
     */
    @Autowired
    protected WebViewService webViewService;
    
    /**
     * 获取BaseRequest信息
     * @author sunqinqiu 
     * @date   2019-01-09 00:01
     * @return
     */
    protected ContextRequest getRequest() {
        ContextRequest request = new ContextRequest();
        request.setDomain(HttpContext.getDomain());
        request.setProject(domainService.getProjectCodeByDomain(request.getDomain()));
        request.setData(HttpContext.getParameters());
        return request;
    }
    
    /**
     * 测试数据
     * @author sunqinqiu 
     * @date   2019-10-03 20:49
     */
    @Test
    public void test() {
        try {
            testViewContent();
            testExe();
            testExex();
        } catch (Exception e) {
            loger.error(e.toString());
        }
    }
    
    /**
     * 测试执行正序
     * @author sunqinqiu 
     * @date   2019-11-05 17:25
     */
    public void testExe() {
        ModelMap map = new ModelMap();
        String service = "service-lud-user-info-login";
        String flag = "i";
        ContextRequest request = this.getRequest();
        map.put("projectid", request.getProject());
        request.setService(service);
        request.setFlag(flag);
        loger.info(JSON.toJSONString(webViewService.exe(service, flag, request)));
    }
    
    /**
     * 测试执行正序
     * @author sunqinqiu 
     * @date   2019-11-05 17:25
     */
    public void testExex() {
        ModelMap map = new ModelMap();
        String service = "service-xlud-user-info-login";
        String flag = "i";
        ContextRequest request = this.getRequest();
        map.put("projectid", request.getProject());
        request.setService(service);
        request.setFlag(flag);
        loger.info(JSON.toJSONString(webViewService.exe(service, flag, request)));
    }
    
    /**
     * 测试执行正序
     * @author sunqinqiu 
     * @date   2019-11-05 17:25
     */
    public void testViewContent() {
        ModelMap map = new ModelMap();
        String service = "mudl-website-login-default-news";
        String flag = "i";
        ContextRequest request = this.getRequest();
        map.put("projectid", request.getProject());
        request.setService(service);
        request.setFlag(flag);
        map.put("content", webViewService.getWebViewContent(service, flag, request));
        loger.info(map.get("content").toString());
    }
}
