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
package com.lud.util.web.freemarker;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.lud.util.content.util.text.CT;

import freemarker.cache.TemplateLoader;

/**
 * @author sunqinqiu 
 * @date   2018-08-11 13:51
 */
public class FreeMarkerLoader implements TemplateLoader {
    private String template;
    
    /**
     * 初始化
     */
    public FreeMarkerLoader(String template) {
        this.template = template;
        if (template == null) {
            this.template = CT.EMPTY;
        }
    }
    
    /**
     * 关闭模板
     * @author sunqinqiu 
     * @date   2018-08-11 17:34
     * @param templateSource
     * @throws IOException
     */
    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        ((StringReader) templateSource).close();
    }
    
    /**
     * 查找模板
     * @author sunqinqiu 
     * @date   2018-08-11 17:34
     * @param name
     * @return
     * @throws IOException
     */
    @Override
    public Object findTemplateSource(String name) throws IOException {
        return new StringReader(template);
    }
    
    /**
     * 获取最后的修改时间
     * @author sunqinqiu 
     * @date   2018-08-11 17:34
     * @param templateSource
     * @return
     */
    @Override
    public long getLastModified(Object templateSource) {
        return 0;
    }
    
    /**
     * 获取Reader
     * @author sunqinqiu 
     * @date   2018-08-11 17:34
     * @param templateSource
     * @param encoding
     * @return
     * @throws IOException
     */
    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return (Reader) templateSource;
    }
}
