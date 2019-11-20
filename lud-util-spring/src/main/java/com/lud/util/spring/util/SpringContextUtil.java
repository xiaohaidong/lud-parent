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
package com.lud.util.spring.util;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.context.ApplicationContext;

/**
 * Spring操作Bean
 * @author sunqinqiu 
 * @date   2018-12-31 14:19
 */
public class SpringContextUtil {
    /**
     * 应用程序注册器
     */
    private static ApplicationContext applicationContext;
    
    /**
     * 是否包含该BEAN
     * @author sunqinqiu 
     * @date   2018-05-12 11:09
     * @param name
     * @return
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }
    
    /**
     * 获取实例列表
     * @author sunqinqiu 
     * @date   2018-05-12 11:10
     * @param name
     * @return
     */
    public static String[] getAliases(String name) {
        return applicationContext.getAliases(name);
    }
    
    /**
     * 获取BEAN
     * @author sunqinqiu 
     * @date   2018-05-12 11:09
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        if (containsBean(name)) { return applicationContext.getBean(name); }
        return null;
    }
    
    /**
     * 获取有注解的BEANS
     * @author sunqinqiu 
     * @date   2019-10-25 16:21
     * @return
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> type) {
        return applicationContext.getBeansWithAnnotation(type);
    }
    
    /**
     * 获取BEAN
     * @author sunqinqiu 
     * @date   2019-10-24 20:47
     * @param <T>
     * @param type
     * @return
     */
    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }
    
    /**
     * 获取BEAN
     * @author sunqinqiu 
     * @date   2018-05-12 11:09
     * @param name
     * @param requiredType
     * @return
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }
    
    /**
     * 获取TYPE
     * @author sunqinqiu 
     * @date   2018-05-12 11:10
     * @param name
     * @return
     */
    public static Class<?> getType(String name) {
        return applicationContext.getType(name);
    }
    
    /**
     * 是否为单例模式
     * @author sunqinqiu 
     * @date   2018-05-12 11:10
     * @param name
     * @return
     */
    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }
    
    /**
     * 设置ApplicationContext
     * @author sunqinqiu 
     * @date   2018-05-12 11:08
     * @param appContent
     */
    public static void setApplicationContext(ApplicationContext appContent) {
        applicationContext = appContent;
    }
    
    /**
     * 构造函数
     */
    private SpringContextUtil() {}
}
