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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.comm.io.XML;
import com.lud.util.comm.io.XMLUtil;
import com.lud.util.comm.server.ServerResource;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 17:00
 */
public class XMLTest {
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    @Test
    public void test() {
        String path = ServerResource.getAbsolutePath("/resource-test/comm/test.xml");
        try (XML xml = new XML(path)) {
            loger.trace("{}", xml.getDocument());
            loger.trace("{}", xml.existNode("/configuration/group"));
            loger.trace("{}", xml.getKeyValue("/configuration/group/item", "name", "text"));
            loger.trace("{}", xml.getChildrenKeyValue("/configuration"));
            loger.trace("{}", xml.getChildrenKeyValues("/configuration/group"));
            loger.trace("{}", xml.getNodeAttributes("/configuration/group", false));
            loger.trace("{}", xml.getNodeAttributes("/configuration/group", true));
            loger.trace("{}", xml.getNodeText("/configuration/group"));
            xml.save();
        } catch (Exception ex) {
            loger.trace("{}", ex);
        }
        try (XML xml = new XML("http://news.163.com/special/00011K6L/rss_newstop.xml", ServerResource.getAbsolutePath("/resource-test/comm/163.xml"))) {
            loger.trace("{}", xml.getDocument());
            loger.trace("{}", xml.existNode("/configuration/configx"));
            loger.trace("{}", xml.getKeyValue("/configuration/configx/item", "name", "text"));
            loger.trace("{}", xml.getChildrenKeyValue("/channel"));
            loger.trace("{}", xml.getChildrenKeyValues("/configuration/confixg"));
            loger.trace("{}", xml.getNodeAttributes("/configuration/confxig", false));
            loger.trace("{}", xml.getNodeAttributes("/configuratixon/page", true));
            loger.trace("{}", xml.getNodeText("/configuratixon/page"));
            xml.save();
        } catch (Exception ex) {
            loger.trace("{}", ex);
        }
        loger.trace("{}", XMLUtil.resolveRootConfigByGroup("/resource-project/resource-config/resource/resource.xml"));
//        loger.trace("{}", XMLUtil.resolveRootConfigByGroup("/resource-test/comm/test.xmxl"));
//        loger.trace("{}", XMLUtil.resolveConfig("/resource-system/config-server/config-mongodb.xml"));
//        loger.trace("{}", XMLUtil.resolveConfig("/resource-test/comm/untitled.png"));
    }
    
}
