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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.error.ErrorText;

/**
 * 日期操作类
 * @author sunqinqiu 
 * @date   2018-05-06 20:13
 */
public final class DateUtil {
    private static final Logger loger                         = LoggerFactory.getLogger(DateUtil.class);
    
    // 格式化类型
    public static final String  FORMAT_TYPE_DATEANDTIME_FULL  = "yyyy-MM-dd HH:mm:ss";
    public static final String  FORMAT_TYPE_DATEANDTIME_SHORT = "yyyyMMddHHmmss";
    public static final String  FORMAT_TYPE_DATE_FULL         = "yyyy-MM-dd";
    public static final String  FORMAT_TYPE_DATE_SHORT        = "yyyyMMdd";
    public static final String  FORMAT_TYPE_DATE_HH           = "yyyyMMddHH";
    public static final String  FORMAT_TYPE_TIME_FULL         = "HH:mm:ss";
    public static final String  FORMAT_TYPE_TIME_SHORT        = "HHmmss";
    public static final String  FORMAT_TYPE_YEAR_M            = "yyyyMM";
    
    /**
     * 格式化时间格式
     * @author sunqinqiu
     * @param date 需要转换格式的日期
     * @param format 格式化类型
     * @return
     */
    public static String format(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    
    /**
     * 获取当前时间戳/秒
     * @author sunqinqiu
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
    
    /**
     * 获取当前时间戳/毫秒
     * @author sunqinqiu
     * @return
     */
    public static long getCurrentTimestampMs() {
        return System.currentTimeMillis();
    }
    
    /**
     * 计算从第哪天开始，第几周的周几是几号
     * @author sunqinqiu
     * @param begin 开始日期
     * @param week 第几周
     * @param weekday 周几
     * @return
     */
    public static Date getDateByWeek(Date begin, int week, int weekday) {
        Calendar calen = Calendar.getInstance();
        calen.setTime(begin);
        calen.add(Calendar.DAY_OF_YEAR, (week - 1) * 7 + weekday - 1);
        return calen.getTime();
    }
    
    /**
     * 获取两个日期之间的月份
     * @author sunqinqiu 
     * @date   2018-12-10 21:11
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<String> getMonths(String beginDate, String endDate) {
        List<String> result = new ArrayList<>();
        
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        
        min.setTime(toDateTime(beginDate, FORMAT_TYPE_DATE_FULL));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        
        max.setTime(toDateTime(endDate, FORMAT_TYPE_DATE_FULL));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        
        Calendar curr = min;
        while (curr.before(max)) {
            result.add(format(curr.getTime(), FORMAT_TYPE_YEAR_M));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }
    
    /**
     * 获取当前时间
     * @author sunqinqiu
     * @return 返回当前时间
     */
    public static Date getNow() {
        return new Date();
    }
    
    /**
     * 获取当前时间的格式化日期
     * @author sunqinqiu
     * @param format 格式化类型
     * @return
     */
    public static String getNow(String format) {
        return format(getNow(), format);
    }
    
    /**
     * 验证数据是否为日期类型的数据
     * @author sunqinqiu
     * @param value
     * @param format
     * @return
     */
    public static boolean isDateTime(Object value, String format) {
        String date = Convert.toString(value);
        if (date.isEmpty()) { return false; }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            formatter.parse(date);
            return true;
        } catch (Exception ex) {
            loger.error(ErrorText.UTIL_DATE_ERR_TODATE);
            return false;
        }
    }
    
    /**
     * 转换为日期类型
     * @author sunqinqiu
     * @param value 需要转换的数据
     * @param format 格式化类型
     * @return
     * @throws ParseException 
     */
    public static Date toDateTime(Object value, String format) {
        try {
            return new SimpleDateFormat(format).parse(Convert.toString(value));
        } catch (Exception ex) {
            loger.error(ErrorText.UTIL_DATE_ERR_TODATE);
            return new Date();
        }
    }
    
    private DateUtil() {}
}
