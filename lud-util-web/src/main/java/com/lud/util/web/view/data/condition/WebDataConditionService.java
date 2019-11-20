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
package com.lud.util.web.view.data.condition;

import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import com.lud.service.core.api.data.jdbc.JDBCService;
import com.lud.util.comm.io.FileUtil;
import com.lud.util.comm.io.XML;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.util.text.CT;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;

/**
 * @author sunqinqiu 
 * @date   2019-11-05 11:37
 */
@Preload
@Service
public class WebDataConditionService extends CoreService {
    /**
     * 
     */
    private Map<String, Condition> conditions = new HashMap<>();
    /**
     * 
     */
    private Map<String, String>    jdbcTypes  = new HashMap<>();
    
    @Reference
    private JDBCService            jdbc;
    
    /**
     * 解析数据
     * @author sunqinqiu 
     * @date   2019-11-05 11:39
     */
    public void run() {
        resolve();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-11-05 12:57
     */
    public void resolve() {
        conditions.clear();
        jdbcTypes.clear();
        /**
         * 获取JDBC的TYPE类型
         */
        String path = ServerResource.getAbsolutePath(this.resourceService.getReourcePath("conditions.data"));
        FileUtil.list(path, false).forEach(item -> {
            String code = item.get(CT.STRING_CODE);
            String file = item.get(CT.STRING_PATH);
            try (XML xml = new XML(file)) {
                xml.getNodesAttributes("/configuration/item", true).forEach(data -> {
                    Condition condition = new Condition();
                    condition.setArgs(Content.getDefaultValue(data.get("args"), "{0}"));
                    condition.setContent(Convert.toString(data.get("text")));
                    condition.setInvoke(Convert.toString(data.get("invoke")));
                    condition.setCparas(Convert.toBoolean(Content.getDefaultValue(data.get("cparas"), "1")));
                    condition.setValue(Content.getDefaultValue(data.get("value"), "{0}"));
                    condition.setValueType(Content.getDefaultValue(data.get("value"), "String"));
                    conditions.put(code + "." + data.get("key"), condition);
                });
            } catch (Exception e) {
                loger.error(e.toString());
            }
        });
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-11-05 12:56
     * @param jdbc
     * @param name
     * @return
     */
    public Condition getCondition(String jdbc, String name) {
        return conditions.get(getJDBCType(jdbc) + "." + name);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-11-05 12:05
     * @param jdbcName
     * @return
     */
    private String getJDBCType(String jdbcName) {
        if (jdbcTypes.containsKey(jdbcName)) return jdbcTypes.get(jdbcName);
        String jdbcType = jdbc.getJDBCType(jdbcName);
        jdbcTypes.put(jdbcName, jdbcType);
        return jdbcType.toLowerCase();
    }
}
