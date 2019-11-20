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
package com.lud.util.spring.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lud.util.comm.io.FileUtil;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.core.runtime.RuntimeContent;
import com.lud.util.content.util.text.CT;

import lombok.Getter;

/**
 * @author sunqinqiu 
 * @date   2019-10-22 19:03
 */
@Service
public class SpringRuntimeService {
    /**
     * 应用信息
     */
    private Map<String, String> options;
    
    /**
     * 获取应用名称
     */
    @Getter
    private String              name;
    
    /**
     * 获取应用信息
     * @author sunqinqiu 
     * @date   2019-10-24 14:12
     * @return
     */
    private void getOptionsByDir(String path) {
        FileUtil.list(path, false).forEach(item -> options.putAll(PropertiesUtil.getAbsProperties(item.get(CT.STRING_PATH), item.get(CT.STRING_CODE))));
    }
    
    /**
     * 获取所有的Option信息
     * @author sunqinqiu 
     * @date   2019-10-24 15:48
     * @return
     */
    public Map<String, String> getOptions() {
        if (options == null) {
            this.options = new HashMap<>();
            this.name = PropertiesUtil.getProperties("properties/app.properties").get("name");
            this.options.put("app.name", name);
            StringBuilder path = new StringBuilder();
            path.append(ServerResource.getAbsolutePath(RuntimeContent.APP_OPTIONS_DIR));
            for (String dirName : this.name.split("/")) {
                path.append("/").append(dirName);
                getOptionsByDir(path.toString());
            }
        }
        return this.options;
    }
    
    /**
     * 获取应用上下文信息
     * @author sunqinqiu 
     * @date   2019-10-22 19:07
     */
    public String getContext(String name) {
        return Convert.toString(getOptions().get(name));
    }
    
    /**
     * 运行服务器
     * @author sunqinqiu 
     * @date   2019-10-22 19:19
     * @return
     */
    public String getRunat() {
        return getContext("app.runat");
    }
}
