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
package com.lud.test.business.service.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.business.api.user.UserService;
import com.lud.test.util.SpringServiceTester;
import com.lud.util.comm.runtime.RuntimeCounter;

/**
 * @author sunqinqiu 
 * @date   2019-01-08 18:25
 */
public class UserRoleManagerTest extends SpringServiceTester {
    
    @Autowired
    private UserService     userService;
    
    
    @Test
    public void test() {
        String key = userService.getUserToken("100202", "sysmanager", "manager");
        RuntimeCounter counter = new RuntimeCounter();
        Map<String, Serializable> map = new HashMap<>();
        map.put("userid", "sysmanager");
        map.put("usergroup", "manager");
        map.put("project", "100202");
        map.put("userrole", "1001");
        userService.setUserSession(key, map);
        userService.getUserInfoByToken(key);
        userService.logout(key);
        counter.count("计算：");
    }
}
