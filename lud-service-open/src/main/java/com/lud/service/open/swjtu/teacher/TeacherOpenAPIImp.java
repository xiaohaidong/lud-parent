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
package com.lud.service.open.swjtu.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.open.api.swjtu.teacher.TeacherAPI;
import com.lud.util.comm.io.XML;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.spring.util.SpringRuntimeService;

/**
 * @author sunqinqiu 
 * @date   2019-10-25 00:31
 */
@Service
public class TeacherOpenAPIImp implements TeacherAPI {
    protected final Logger       loger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SpringRuntimeService springRuntimeService;
    
    /**
     * 读取教师信息
     * @author sunqinqiu 
     * @date   2019-10-25 00:58
     * @return
     */
    @Override
    public List<Map<String, String>> getTeacherList() {
        Map<String, String> config = springRuntimeService.getOptions();
        try (XML xml = new XML(config.get("swjtu.teacher.url.bak"), "/")) {
            List<Map<String, String>> list = xml.getChildrenKeyValues("/dataPackage/dataRow");
            if (!list.isEmpty()) {
                List<Map<String, String>> result = new ArrayList<>();
                list.forEach(item -> {
                    Map<String, String> teacher = new HashMap<>();
                    teacher.put("code", item.get("UNI_NO"));
                    teacher.put("name", item.get("NAME"));
                    teacher.put("college", item.get("DEPART"));
                    teacher.put("cardid", item.get("PERSONID"));
                    result.add(teacher);
                });
                list.clear();
                list = null;
                return result;
            }
        } catch (Exception ex) {
            loger.error(ex.toString());
        }
        return ListMapUtil.getEmptyStringArrayList();
    }
}
