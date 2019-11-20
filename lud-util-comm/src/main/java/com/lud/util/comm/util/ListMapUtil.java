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
package com.lud.util.comm.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import com.alibaba.fastjson.JSON;

/**
 * @author sunqinqiu 
 * @date   2018-12-07 21:42
 */
public class ListMapUtil {
    /**
     * 获取一个空列表
     * @author sunqinqiu 
     * @date   2018-12-31 12:51
     * @return
     */
    public static List<Map<String, Serializable>> getEmptyArrayList() {
        return new ArrayList<>();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 00:38
     * @return
     */
    public static List<Map<String, String>> getEmptyStringArrayList() {
        return new ArrayList<>();
    }
    
    /**
     * 获取一个空列表
     * @author sunqinqiu 
     * @date   2018-12-31 12:51
     * @return
     */
    public static List<String> getEmptyList() {
        return new ArrayList<>();
    }
    
    /**
     * 获取一个空Map
     * @author sunqinqiu 
     * @date   2018-12-31 12:51
     * @return
     */
    public static Map<String, String> getEmptyMap() {
        return new CaseInsensitiveMap<>();
    }
    
    /**
     * 获取一个空Map
     * @author sunqinqiu 
     * @date   2019-01-06 01:48
     * @return
     */
    public static Map<String, Serializable> getEmptySerializableMap() {
        return new CaseInsensitiveMap<>();
    }
    
    /**
     * 将JSON转化为列表
     * @author sunqinqiu 
     * @date   2018-12-31 12:58
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Serializable>> toList(String json) {
        if (Content.isEmpty(json)) { return getEmptyArrayList(); }
        return JSON.parseObject(json, List.class);
    }
    
    /**
     * 将JSON转化为MAP
     * @author sunqinqiu 
     * @date   2018-12-31 12:58
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Serializable> toMap(String json) {
        if (Content.isEmpty(json)) { return new HashMap<>(); }
        return JSON.parseObject(json, Map.class);
    }
    
    private ListMapUtil() {}
}
