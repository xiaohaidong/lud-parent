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
package com.lud.test.iot.business.core.api.system;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lud.iot.business.core.api.system.entity.CpuEntity;
import com.lud.iot.business.core.api.system.entity.DiskEntity;
import com.lud.iot.business.core.api.system.entity.HardWearEntity;
import com.lud.iot.business.core.api.system.entity.NetWorkEntity;
import com.lud.iot.business.core.api.system.entity.OperatingEntity;
import com.lud.iot.business.core.api.system.entity.SystemEntity;
import com.lud.test.util.EntityClassTester;

/**
 * @author sunqinqiu 
 * @date   2019-10-18 07:28
 */
public class SystemTest {
    @Test
    public void testEntity() throws Exception {
        @SuppressWarnings("rawtypes")
        List<Class> list = new ArrayList<>();
        list.add(CpuEntity.class);
        list.add(DiskEntity.class);
        list.add(HardWearEntity.class);
        list.add(NetWorkEntity.class);
        list.add(OperatingEntity.class);
        list.add(SystemEntity.class);
        EntityClassTester.testEntities(list);
    }
}
