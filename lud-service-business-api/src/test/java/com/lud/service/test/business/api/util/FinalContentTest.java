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
package com.lud.service.test.business.api.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import com.lud.service.business.api.user.UserEntity;
import com.lud.service.business.api.user.group.ApplicationGroup;
import com.lud.service.business.api.user.group.UserGroupEntity;
import com.lud.service.business.api.user.role.UserRoleEntity;
import com.lud.service.business.api.user.role.UserRoleMenuEntity;
import com.lud.test.util.EntityClassTester;

/**
 * @author sunqinqiu 
 * @date   2019-09-15 23:48
 */
public class FinalContentTest {
    
    @SuppressWarnings("rawtypes")
    @Test
    public void test() {
        List<Class> list = new ArrayList<>();
        list.add(ApplicationGroup.class);
        list.add(UserEntity.class);
        list.add(UserGroupEntity.class);
        list.add(UserRoleEntity.class);
        list.add(UserRoleMenuEntity.class);
        EntityClassTester.testEntities(list);
    }
}
