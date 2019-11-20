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
package com.lud.test.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.error.ErrorTest;
import com.lud.util.content.util.text.EntityTestContent;

/**
 * 实体类测试程序
 * @author sunqinqiu 
 * @date   2019-09-16 05:16
 */
public class EntityClassTester {
    private EntityClassTester() {}
    
    public static final Logger               loger      = LoggerFactory.getLogger(EntityClassTester.class);
    // 实体化数据
    private static final Map<String, Object> STATIC_MAP = new HashMap<>();
    
    /**
     * 初始化
     */
    static {
        STATIC_MAP.put("java.lang.Long", 1L);
        STATIC_MAP.put("java.lang.String", "test");
        STATIC_MAP.put("java.lang.Integer", 1);
        STATIC_MAP.put("int", 1);
        STATIC_MAP.put("long", 1);
        STATIC_MAP.put("java.util.Date", new Date());
        STATIC_MAP.put("char", '1');
        STATIC_MAP.put("java.util.Map", new HashMap<>());
        STATIC_MAP.put("boolean", true);
    }
    
    /**
     * 测试实体类列表
     * @author sunqinqiu 
     * @date   2019-10-19 09:10
     * @param entities
     */
    @SuppressWarnings("rawtypes")
    public static void testEntities(List<Class> entities) {
        entities.forEach(EntityClassTester::testEntity);
    }
    
    /**
     * 测试单个类
     * @author sunqinqiu 
     * @date   2019-10-19 10:10
     * @param entity
     */
    @SuppressWarnings("rawtypes")
    public static void testEntity(Class<?> entity) {
        try {
            Object tempInstance = new Object();
            Constructor[] constructors = entity.getConstructors();
            for (Constructor constructor : constructors) {
                final Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length == 0) {
                    tempInstance = constructor.newInstance();
                } else {
                    Object[] objects = new Object[parameterTypes.length];
                    for (int i = 0; i < parameterTypes.length; i++) {
                        objects[i] = STATIC_MAP.get(parameterTypes[i].getName());
                    }
                    tempInstance = constructor.newInstance(objects);
                }
            }
            testMethods(entity, tempInstance);
        } catch (Exception e) {
            loger.error("{}{}{}", entity.getClass(), ErrorTest.ERROR_FAIL_TEST, e);
        }
    }
    
    /**
     * 测试方法
     * @author sunqinqiu 
     * @date   2019-10-19 10:11
     * @param entity
     * @param tempInstance
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static void testMethods(Class<?> entity, Object tempInstance) throws IllegalAccessException, InvocationTargetException {
        Method[] methods = entity.getMethods();
        for (final Method method : methods) {
            if (EntityTestContent.ENTITY_METHODS.contains(method.getName())) {
                break;
            }
            final Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 0) {
                Object[] objects = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    objects[i] = STATIC_MAP.get(parameterTypes[i].getName());
                }
                method.invoke(tempInstance, objects);
            } else {
                method.invoke(tempInstance);
            }
        }
    }
}
