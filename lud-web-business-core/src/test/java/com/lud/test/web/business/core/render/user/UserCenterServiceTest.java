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
package com.lud.test.web.business.core.render.user;

import java.io.Serializable;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.test.web.WebUtilBaseTest;
import com.lud.util.web.api.view.ViewDataEntity;
import com.lud.web.business.core.render.user.UserCenterService;

/**
 * @author sunqinqiu 
 * @date   2019-09-16 03:52
 */
public class UserCenterServiceTest extends WebUtilBaseTest {
    @Autowired
    UserCenterService userCenterService;
    
    @Test
    public void test() {
        try {
            ViewDataEntity data = new ViewDataEntity();
            userCenterService.getModelMap(getRequest());
            userCenterService.getUserInfo(data, new HashMap<String, Serializable>(), getRequest());
            userCenterService.getApplicationList(data, new HashMap<String, Serializable>(), getRequest());
            userCenterService.getApplication(data, new HashMap<String, Serializable>(), getRequest());
            userCenterService.getMenu(data, new HashMap<String, Serializable>(), getRequest());
            userCenterService.getWedge(data, new HashMap<String, Serializable>(), getRequest());
        } catch (Exception e) {
            
        }
    }
}
