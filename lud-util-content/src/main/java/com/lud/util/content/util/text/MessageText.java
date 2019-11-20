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
package com.lud.util.content.util.text;

/**
 * 与Spring有关的静态内容
 * @author sunqinqiu 
 * @date   2019-09-17 20:02
 */
public class MessageText {
    /**
     * Spring context 默认路径
     */
    public static final String SPRING_CONTEXT_PATH    = "classpath:spring-context.xml";
    
    /**
     * Spring启动消息
     */
    public static final String SPRING_START_INFO      = "the service {} is stating......";
    
    /**
     * Spring已启动消息
     */
    public static final String SPRING_STARTED_INFO    = "the service {} is stated......";
    
    /**
     * Spring启动错误消息
     */
    public static final String SPRING_STARTED_ERROR   = "the service {} is stated error !";
    
    /**
     * 找不到BEAN
     */
    public static final String SPRING_BEAN_INVOKE_ERR = "no this bean:{}";
    
    /**
     * 构造方法
     */
    private MessageText() {}
    
}
