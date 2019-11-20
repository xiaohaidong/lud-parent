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
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.HTMLText;

/**
 * 对值类型数据的转换
 * @author sunqinqiu 
 * @date   2018-05-06 17:37
 */
public final class Content {
    private static final Logger   loger            = LoggerFactory.getLogger(Content.class);
    
    public static final long      LONG_EMPTY       = -1;
    public static final int       INT_EMPTY        = -1;
    private static final String[] REGE_FIT_HTML    = { "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>", "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>", "<[^>]+>" };
    private static final String   REGE_FIT_PICTURE = "<img.+?src=\"(.+?)\".+?/?>";
    private static final String   JAVASCRIPT       = "javascript";
    private static final String   TEXTS            = "\\.";
    private static final String[] UNSAFE_STRINGS   = { "'", " ", "<", ">", ";", "&", "%", "iframe", "script", "--" };
    
    /**
     * 查找某个字符串某两个字符串之间的字符
     * @author sunqinqiu 
     * @date   2018-05-15 00:15
     * @param content
     * @param begin
     * @param end
     * @return
     */
    public static String getBetween(String content, String beginChar, String endChar) {
        int begin = 0;
        int end = content.length();
        if (!beginChar.isEmpty()) {
            begin = content.indexOf(beginChar);
            if (begin < 0) { return CT.EMPTY; }
            begin = begin + 1;
        }
        if (!endChar.isEmpty()) {
            end = content.indexOf(endChar);
            if (end < 0) { return CT.EMPTY; }
        }
        if (begin > end) {
            int tmp = begin;
            begin = end;
            end = tmp;
        }
        return content.substring(begin, end);
    }
    
    /**
     * 获取默认的List
     * @author sunqinqiu 
     * @date   2018-08-10 09:31
     * @param list
     * @return
     */
    public static List<Serializable> getDefalutList(List<Serializable> list) {
        return list == null ? new ArrayList<>() : list;
    }
    
    /**
     * 获取Default
     * @author sunqinqiu 
     * @date   2018-08-10 09:29
     * @param map
     * @return
     */
    public static Map<String, Serializable> getDefaultMap(Map<String, Serializable> map) {
        return map == null ? new HashMap<>() : map;
    }
    
    /**
     * 获取默认值
     * @author sunqinqiu
     * @param value 传入的参数
     * @param defaultValue 如果为空返回的参数
     * @return 返回的值
     */
    public static String getDefaultValue(Object value, String defaultValue) {
        String val = Convert.toString(value);
        return val.isEmpty() ? defaultValue : val;
    }
    
    /**
     * 从HTML代码中搜索到第一张图片
     * @author sunqinqiu
     * @param content
     * @return
     */
    
    public static String getFirstPictrue(String content) {
        Matcher m = Pattern.compile(REGE_FIT_PICTURE, Pattern.CASE_INSENSITIVE).matcher(content);
        if (m.find()) { return m.group(1); }
        return CT.EMPTY;
    }
    
    /**
     * 获取安全字符
     * @author sunqinqiu
     * @param value
     * @return
     */
    public static String getSafeString(Object value) {
        String content = Convert.toString(value);
        for (String unsafe : UNSAFE_STRINGS) {
            content = content.replace(unsafe, CT.EMPTY);
        }
        return content;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-27 16:16
     * @param data
     * @return
     */
    public static boolean isSafeString(Map<String, String> data) {
        for (Entry<String, String> item : data.entrySet()) {
            for (String unsafe : UNSAFE_STRINGS) {
                if (item.getKey().contains(unsafe) || item.getValue().contains(unsafe)) return false;
            }
        }
        return true;
    }
    
    /**
     * 获取text和数组的组合
     * @author sunqinqiu 
     * @date   2018-05-31 19:07
     * @param text
     * @param args
     * @return
     */
    public static String[] getStringArray(String text, String args) {
        if (args.isEmpty()) { return new String[] { text }; }
        String[] spx = args.split(CharacterContent.STRING_COMMA);
        String[] rc = new String[spx.length + 1];
        rc[0] = text;
        for (int i = 0; i < spx.length; i++) {
            rc[i + 1] = spx[i];
        }
        return rc;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-12-16 12:03
     * @param text
     * @param args
     * @return
     */
    public static List<Map<String, Serializable>> getStringParent(String text) {
        String[] texts = text.split(TEXTS);
        List<Map<String, Serializable>> result = new ArrayList<>();
        StringBuilder newKey = new StringBuilder();
        int i = 0;
        for (String key : texts) {
            newKey.append(i > 0 ? CharacterContent.PATH_SPLITER_SUFFIX : CT.EMPTY).append(key);
            Map<String, Serializable> map = new HashMap<>();
            map.put(CT.STRING_CODE, newKey);
            map.put(CT.STRING_CONTENT, key);
            result.add(map);
            i++;
        }
        return result;
    }
    
    /**
     * 把HTML文本转换为普通文本
     * @author sunqinqiu
     * @param html html代码
     * @return
     */
    
    public static String getText(String html) {
        try {
            for (String reg : REGE_FIT_HTML) {
                html = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(html).replaceAll(CT.EMPTY);
            }
            return html.replaceAll(HTMLText.HTML_SPACE, CT.SPACE);
        } catch (Exception ex) {
            loger.warn(ex.toString());
            return CT.EMPTY;
        }
    }
    
    /**
     * 通过JAVASCRIPT引擎计算表达式的值
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static double getValueByJavaScript(String content) {
        try {
            ScriptEngine scriptEngine = (new ScriptEngineManager()).getEngineByName(JAVASCRIPT);
            return Convert.toDouble(scriptEngine.eval(content));
        } catch (Exception ex) {
            return 0.0;
        }
    }
    
    /**
     * 是否为空
     * @author sunqinqiu 
     * @date   2018-05-30 01:16
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }
    
    /**
     * 把数组用特殊符号链接起来
     * @author sunqinqiu
     * @param data 字符串
     * @param splite 分隔符
     * @return
     */
    public static String joinString(String splite, String... data) {
        if (data.length == 1) { return data[0]; }
        StringBuilder builder = new StringBuilder();
        for (String d : data) {
            builder.append(splite + d);
        }
        return builder.substring(splite.length());
    }
    
    /**
     * 连接字符串
     * @author sunqinqiu 
     * @date   2018-07-01 23:34
     * @param data
     * @param splite
     * @return
     */
    public static String joinStrings(String splite, long... data) {
        if (data.length == 1) { return Convert.toString(data[0]); }
        StringBuilder builder = new StringBuilder();
        for (Object d : data) {
            builder.append(splite + Convert.toString(d));
        }
        return builder.substring(splite.length());
    }
    
    /**
     * 左侧截取字符串
     * @author sunqinqiu 
     * @date   2018-05-30 01:48
     * @param content
     * @param length
     * @return
     */
    public static String left(String content, int length) {
        return substring(content, 0, length);
    }
    
    /**
          * 消除换行
     * @author sunqinqiu 
     * @date   2019-09-24 03:32
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        str = str.replaceAll("\\s{1,}", " ").replaceAll("> <", "><");
        if (str != null) {
            Pattern p = Pattern.compile("\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            dest = dest.replaceAll("::", CT.EMPTY);
        }
        return dest;
    }
    
    /**
     * 截取字符串
     * @author sunqinqiu 
     * @date   2018-05-30 01:38
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String substring(String content, int start, int end) {
        if (content == null) { return CT.EMPTY; }
        end += end < 0 ? content.length() : 0;
        start += start < 0 ? content.length() : 0;
        end = end > content.length() ? content.length() : end;
        if (start > end) { return CT.EMPTY; }
        return content.substring(start, end);
    }
    
    private Content() {}
    
}
