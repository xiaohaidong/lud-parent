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
package com.lud.test.service.core.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.core.api.data.command.DataCommandService;
import com.lud.test.util.SpringServiceTester;

/**
 * @author sunqinqiu 
 * @date   2019-03-03 16:49
 */
public class DataCommandServiceTest extends SpringServiceTester {
    @Autowired
    private DataCommandService dataCommandService;
    
    @Test
    public void test() {
        Map<String, Serializable> args = new HashMap<>();
        List<String> keys = new ArrayList<>();
        keys.add("18");
        keys.add("19");
        args.put("keys", (Serializable) keys);
        args.put("project", "100202");
        dataCommandService.exe("lud-core.lud_department_group_delete", args);
    }
}
