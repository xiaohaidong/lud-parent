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
package com.lud.web.server;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * WebApplicationInitializer
 * @author sunqinqiu 
 * @date   2019-01-01 14:03
 */
public class LudWebServer implements WebApplicationInitializer {
    
    /**
     * OnStartup
     * @author sunqinqiu 
     * @date   2019-01-01 14:11
     * @param arg0
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext context) throws ServletException {
        startupSpring(context);
    }
    
    /**
     * 注册SPRING
     * @author sunqinqiu 
     * @date   2019-01-01 16:39
     * @param context
     * @throws IOException 
     */
    private void startupSpring(ServletContext context)  {
        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocations("classpath:spring-context.xml");
        ServletRegistration.Dynamic dispatcher = context.addServlet("dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(-1);
        dispatcher.addMapping("/");
    }
}
