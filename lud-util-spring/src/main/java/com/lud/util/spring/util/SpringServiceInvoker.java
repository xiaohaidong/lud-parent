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

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.MessageText;
import com.lud.util.content.util.type.ClassType;

/**
 * 反射调用
 * @author sunqinqiu 
 * @date   2018-12-31 14:18
 */
public class SpringServiceInvoker {
    /**
     * 序号
     */
    public static final Logger loger = LoggerFactory.getLogger(SpringServiceInvoker.class);
    
    /**
     * 执行
     * @author sunqinqiu 
     * @date   2018-12-31 14:22
     * @param process
     * @param args
     */
    public static void invoke(String process, Object... args) {
        try {
            int index = process.lastIndexOf(CharacterContent.CHAR_POINT);
            String beanName = process.substring(0, index);
            String methodName = process.substring(index + 1);
            Object bean = SpringContextUtil.getBean(beanName);
            if (bean != null) {
                Class<?>[] paraTypes = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    paraTypes[i] = args[i].getClass();
                    if (paraTypes[i].toString().endsWith(ClassType.NAME_MAP)) {
                        paraTypes[i] = ClassType.CLASS_MAP;
                    }
                    if (paraTypes[i].toString().endsWith(ClassType.NAME_LIST)) {
                        paraTypes[i] = ClassType.CLASS_LIST;
                    }
                }
                Method method = ReflectionUtils.findMethod(bean.getClass(), methodName, paraTypes);
                ReflectionUtils.invokeMethod(method, bean, args);
            } else {
                loger.error(MessageText.SPRING_BEAN_INVOKE_ERR, beanName);
            }
        } catch (Exception e) {
            loger.error(MessageText.SPRING_BEAN_INVOKE_ERR, e.toString());
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 18:01
     * @param object
     */
    public static void run(List<Map<String, Object>> object) {
        object.forEach(item -> {
            for (String exe : item.get("exe").toString().split(",")) {
                Object bean = item.get("obj");
                Method method = ReflectionUtils.findMethod(bean.getClass(), exe);
                ReflectionUtils.invokeMethod(method, bean);
            }
        });
    }
    
    /**
     * 重构方法
     */
    private SpringServiceInvoker() {}
}
