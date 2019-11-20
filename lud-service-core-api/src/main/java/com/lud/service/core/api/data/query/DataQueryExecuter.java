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
package com.lud.service.core.api.data.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * 查询执行器
 * @author sunqinqiu 
 * @date   2019-01-05 11:06
 */
public class DataQueryExecuter implements Serializable {
    
    /**
     * 序号
     */
    private static final long         serialVersionUID = 1L;
    
    /**
     * 分页
     */
    @Getter
    private DataPager                 pager;
    
    /**
     * 名称
     */
    @Getter
    private String                    name;
    
    /**
     * 查询前几条数据
     */
    @Getter
    private int                       top              = 0;
    
    /**
     * 字段
     */
    @Getter
    private String[]                  fields;
    
    /**
     * 条件
     */
    @Getter
    private String[]                  limits;
    
    /**
     * 分组
     */
    @Getter
    private String                    group            = "";
    
    /**
     * 条件
     */
    @Getter
    private String                    order            = "";
    
    /**
     * 自定义条件
     */
    @Getter
    private String                    where            = "";
    
    /**
     * 参数
     */
    @Getter
    private String                    args             = "";
    
    /**
     * 添加参数
     */
    @Getter
    private Map<String, Serializable> data             = new HashMap<>();
    
    /**
     * 构造函数
     */
    public DataQueryExecuter() {}
    
    /**
     * 初始化参数
     */
    public DataQueryExecuter(String name, String fields, String limits) {
        this.name = name;
        this.fields = fields.split(",");
        this.limits = limits.split(",");
    }
    
    /**
     * 增加所有数据
     * @author sunqinqiu 
     * @date   2019-09-24 04:34
     * @param name
     * @param value
     * @return
     */
    public DataQueryExecuter addAllData(Map<String, Serializable> data) {
        this.data.putAll(data);
        return this;
    }
    
    /**
     *  添加数据
     * @author sunqinqiu 
     * @date   2019-01-05 12:05
     * @param name
     * @param arg
     * @return
     */
    public DataQueryExecuter addData(String name, Serializable value) {
        this.data.put(name, value);
        return this;
    }
    
    /**
     * 增加条件
     * @author sunqinqiu 
     * @date   2019-01-05 12:02
     * @param where
     * @return
     */
    public DataQueryExecuter addWhere(String where, String args) {
        this.where = where;
        this.args = args;
        return this;
    }
    
    /**
     * 自定义分组
     * @author sunqinqiu 
     * @date   2019-01-05 12:04
     * @param group
     * @return
     */
    public DataQueryExecuter group(String group) {
        this.group = group;
        return this;
    }
    
    /**
     * 排序
     * @author sunqinqiu 
     * @date   2019-01-05 12:03
     * @param order
     * @return
     */
    public DataQueryExecuter order(String order) {
        this.order = order;
        return this;
    }
    
    /**
     * 设置分页
     * @author sunqinqiu 
     * @date   2019-01-05 12:07
     * @return
     */
    public DataQueryExecuter pager(DataPager pager) {
        this.pager = pager;
        return this;
    }
    
    /**
     * 设置
     * @author sunqinqiu 
     * @date   2019-01-05 11:59
     * @param top
     * @return
     */
    public DataQueryExecuter top(int top) {
        this.top = top;
        return this;
    }
}
