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
package com.lud.util.content.util.type;

/**
 * 基础数据类型
 * @author sunqinqiu 
 * @date   2019-09-17 20:15
 */
public class ClassType {
    
    /**
     * Map
     */
    public static final String   NAME_MAP   = "Map";
    
    /**
     * List
     */
    public static final String   NAME_LIST  = "List";
    
    /**
     * List class
     */
    public static final Class<?> CLASS_LIST = java.util.List.class;
    
    /**
     * Map class
     */
    public static final Class<?> CLASS_MAP  = java.util.Map.class;
    
    /**
     * 基础数据类型构造函数
     */
    private ClassType() {}
}
