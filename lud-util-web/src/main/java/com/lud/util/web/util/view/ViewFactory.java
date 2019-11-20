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
package com.lud.util.web.util.view;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.lud.service.core.api.config.ResourceService;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.content.util.error.ErrorCode;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.type.EncodeType;
import com.lud.util.content.web.view.ViewContent;
import com.lud.util.spring.util.Preload;
import com.lud.util.web.freemarker.FreeMarkerLoader;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Getter;
import lombok.Setter;

/**
 * 页面管理器
 * @author sunqinqiu 
 * @date   2019-01-05 23:42
 */
@Component
@Preload(fresh = false)
public class ViewFactory {
    private final Logger         loger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * FreeMarker管理器
     */
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    
    /**
     * 页面路径
     */
    @Getter
    @Setter
    private String               viewPath;
    private Configuration        configc;
    private Configuration        configf;
    /**
     * 
     */
    @Reference
    private ResourceService      resourceService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 15:05
     */
    
    public void run() {
        try {
            this.viewPath = ServerResource.getAbsolutePath(resourceService.getReourcePath(ViewContent.FREEMARK_CONFIG_VIEW_ROOT));
            freeMarkerConfigurer.getConfiguration().setDirectoryForTemplateLoading(new File(viewPath));
            configf = (Configuration) freeMarkerConfigurer.getConfiguration().clone();
            configc = (Configuration) freeMarkerConfigurer.getConfiguration().clone();
        } catch (IOException e) {
            loger.error("{}{}", resourceService.getError(ErrorCode.FREEMARK_CONFIG_LOAD_ERROR), e);
        }
    }
    
    /**
     * 获取CONTENT
     * @author sunqinqiu 
     * @date   2018-06-17 15:16
     * @param content
     * @param map
     * @return
     */
    public String getContentView(String content, ModelMap map) {
        if (map == null) {
            map = new ModelMap();
        }
        try (StringWriter writer = new StringWriter()) {
            configc.setTemplateLoader(new FreeMarkerLoader(content));
            Template template = configc.getTemplate(CT.EMPTY);
            template.process(map, writer);
            String tempt = writer.toString();
            writer.flush();
            return tempt;
        } catch (Exception e) {
            return e.toString();
        }
    }
    
    /**
     * 获取文件CONTENT
     * @author sunqinqiu 
     * @date   2018-06-17 15:17
     * @param path
     * @param map
     * @return
     */
    public String getFileView(String path, ModelMap map) {
        if (map == null) {
            map = new ModelMap();
        }
        try (StringWriter out = new StringWriter()) {
            Template template = configf.getTemplate(path, EncodeType.ENCODE_UTF8);
            template.process(map, out);
            String tempt = out.toString();
            out.flush();
            return tempt;
        } catch (Exception e) {
            return e.toString();
        }
    }
}
