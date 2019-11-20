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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.ListMapUtil;

/**
 * 操作.properties
 * @author sunqinqiu 
 * @date   2019-10-17 23:02
 */
public class PropertiesUtil {
    /**
     * 获取所有数据
     * @author sunqinqiu 
     * @date   2019-10-17 23:07
     * @param file
     * @return
     */
    public static Map<String, String> getAbsProperties(String file, String suffix) {
        try (FileInputStream fileInput = new FileInputStream(file); InputStreamReader reader = new InputStreamReader(fileInput, "utf-8")) {
            Properties properties = new Properties();
            properties.load(reader);
            return toMap(properties, suffix);
        } catch (IOException e) {
            return ListMapUtil.getEmptyMap();
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-24 15:34
     * @param file
     * @return
     */
    public static Map<String, String> getProperties(String file) {
        try {
            return toMap(PropertiesLoaderUtils.loadAllProperties(file), "");
        } catch (IOException e) {
            return ListMapUtil.getEmptyMap();
        }
    }
    
    /**
     * Properties转Map
     * @author sunqinqiu 
     * @date   2019-10-24 16:42
     * @param properties
     * @return
     */
    public static Map<String, String> toMap(Properties properties, String suffix) {
        if (properties == null) return ListMapUtil.getEmptyMap();
        Map<String, String> map = new HashMap<>();
        properties.forEach((key, value) -> map.put((suffix.isEmpty() ? "" : suffix + ".") + key, Convert.toString(value)));
        return map;
    }
    
    private PropertiesUtil() {}
}
