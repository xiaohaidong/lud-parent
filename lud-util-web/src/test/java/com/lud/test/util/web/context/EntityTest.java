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
package com.lud.test.util.web.context;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lud.test.util.EntityClassTester;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.context.ContextResult;
import com.lud.util.web.api.view.ViewCommandEntity;
import com.lud.util.web.api.view.ViewCompEntity;
import com.lud.util.web.api.view.ViewContentEntity;
import com.lud.util.web.api.view.ViewDataEntity;
import com.lud.util.web.api.view.ViewServiceEntity;

/**
 * @author sunqinqiu 
 * @date   2019-09-16 05:24
 */
public class EntityTest {
    
    @SuppressWarnings("rawtypes")
    @Test
    public void test() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Class> list = new ArrayList<>();
        list.add(ViewCommandEntity.class);
        list.add(ViewCompEntity.class);
        list.add(ViewContentEntity.class);
        list.add(ViewDataEntity.class);
        list.add(ViewServiceEntity.class);
        list.add(ContextResult.class);
        list.add(ContextRequest.class);
        EntityClassTester.testEntities(list);
    }
}
