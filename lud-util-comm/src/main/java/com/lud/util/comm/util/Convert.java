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
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CT;

/**
 * 类型转换
 * @author sunqinqiu 
 * @date   2018-05-06 17:35
 */
public final class Convert {
    private static final Logger loger = LoggerFactory.getLogger(Convert.class);
    
    /**
     * 泛型数据类型转换
     * @author sunqinqiu
     * @param value 需要转换的数据
     * @return 需要返回的类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(Object value) {
        return null == value ? null : (T) value;
    }
    
    /**
     * 转换为BOOLEAN类型
     * @author sunqinqiu
     * @param value
     * @return
     */
    public static boolean toBoolean(Object value) {
        String val = toString(value).toLowerCase();
        return (CT.STRING_SUCCESS.equals(val) || CT.STRING_TRUE.equals(val) || CT.STRING_NUM_1.equals(val) || CT.STRING_ON.equals(val) || CT.STRING_YES.equals(val));
    }
    
    /**
     * 转换为Double类型数据
     * @author sunqinqiu
     * @param value
     * @return
     */
    public static double toDouble(Object value) {
        String val = toString(value);
        if (val.isEmpty()) { return 0; }
        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException ex) {
            loger.error(ErrorText.UTIL_CONVERT_ERR_DOUBLE, ex);
            return 0.0;
        }
    }
    
    /**
     * 转换为数字类型
     * @author sunqinqiu
     * @param value
     * @return
     */
    public static int toInt(Object value) {
        String val = toString(value);
        if (val.isEmpty()) { return 0; }
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException ex) {
            loger.error(ErrorText.UTIL_CONVERT_ERR_TOINT, ex);
            return 0;
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-28 01:47
     * @param value
     * @return
     */
    public static long toLong(Object value) {
        String val = toString(value);
        if (val.isEmpty()) { return 0L; }
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException ex) {
            loger.error(ErrorText.UTIL_CONVERT_ERR_TOINT, ex);
            return 0L;
        }
    }
    
    /**
     * Objcet 转可序列化
     * @author sunqinqiu 
     * @date   2018-08-12 21:16
     * @param list
     * @return
     */
    public static List<Map<String, Serializable>> toList(List<Map<String, Object>> list) {
        return Convert.convert(list);
    }
    
    /**
     * 任意类型数据转换为STRING类型
     * @author sunqinqiu
     * @param value 需要转换的数据
     * @return 返回STRING
     */
    public static String toString(Object value) {
        return null == value ? CT.EMPTY : value.toString();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-12 15:42
     * @param value
     * @return
     */
    public static String toStringTrim(Object value) {
        return null == value ? CT.EMPTY : value.toString().trim();
    }
    
    private Convert() {}
}
