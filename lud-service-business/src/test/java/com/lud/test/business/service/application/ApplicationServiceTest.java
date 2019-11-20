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
package com.lud.test.business.service.application;

import com.lud.service.business.api.user.group.ApplicationGroup;
import com.lud.service.business.api.user.role.UserRoleService;
import com.lud.test.util.SpringServiceTester;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author sunqinqiu 
 * @date   2019-01-06 04:32
 */
public class ApplicationServiceTest extends SpringServiceTester {
    @Autowired
    private UserRoleService userRoleService;
    
    @Test
    public void test() {
//        loger.trace("{}", applicationService.getProjectApplicationGroup("100101","manager").size());
        userRoleService.getApplicationGroup("2867D3D0A3ACEC203C6C3F90F556FC0B44CF9033");
    }
}
