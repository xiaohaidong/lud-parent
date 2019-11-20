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
package com.lud.service.core.api.data.runner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lud.service.core.api.data.query.DataPager;
import com.lud.util.content.core.data.PLSQLContent;

import lombok.Getter;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 04:09
 */
public class SQLRunner implements Serializable {
    /**
     * 
     */
    private static final long  serialVersionUID = 1L;
    
    /**
     * page
     */
    @Getter
    private DataPager          dataPager;
    
    /**
     * JDBC
     */
    @Getter
    private String             jdbc;
    /**
     * 表名
     */
    @Getter
    private String             table;
    
    /**
     * 取前几条数据
     */
    @Getter
    private int                top;
    
    /**
     * 字段名，用于查询
     */
    @Getter
    private StringBuilder      fields;
    
    /**
     * 用于更新和插入
     */
    @Getter
    private List<SQLField>     fieldList;
    
    /**
     * 自增字段
     */
    @Getter
    private String             autoFileld;
    
    /**
     * 条件罗列
     */
    @Getter
    private StringBuilder      wheres;
    
    /**
     * 分组
     */
    @Getter
    private String             groups;
    
    /**
     * 条件
     */
    @Getter
    private String             orders;
    
    /**
     * 参数
     */
    @Getter
    private List<Serializable> args;
    
    /**
     * 构造一个SQLQqueryRunner
     */
    public SQLRunner(String jdbc, String table) {
        this.jdbc = jdbc;
        this.table = table;
        this.top = 0;
        this.fields = new StringBuilder();
        this.fieldList = new ArrayList<>();
        this.wheres = new StringBuilder();
        this.groups = "";
        this.orders = "";
        this.args = new ArrayList<>();
        this.dataPager = null;
        
    }
    
    /**
     * 添加参数
     * @author sunqinqiu 
     * @date   2018-06-03 14:01
     * @param arg
     * @return
     */
    public SQLRunner addArg(Serializable arg) {
        this.args.add(arg);
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 21:27
     * @param field
     * @param arg
     * @return
     */
    public SQLRunner addFieldSQL(String field, String arg) {
        this.fieldList.add(new SQLField(field, arg));
        return this;
    }
    
    /**
     * 添加需要插入或者变更的参数，仅仅在Insert或者Update的时候使用
     * @author sunqinqiu 
     * @date   2019-01-03 17:13
     * @param field
     * @param arg
     * @return
     */
    public SQLRunner addFieldValue(String field, Serializable arg) {
        this.fieldList.add(new SQLField(field, "?"));
        this.args.add(arg);
        return this;
    }
    
    /**
     * 设置自增字段
     * @author sunqinqiu 
     * @date   2019-01-05 12:16
     * @param id
     * @return
     */
    public SQLRunner autoInsertField(String id) {
        this.autoFileld = id;
        return this;
    }
    
    /**
     * 分组查询
     * @author sunqinqiu 
     * @date   2018-06-03 14:21
     * @param group
     * @return
     */
    public SQLRunner group(String group) {
        this.groups = group;
        return this;
    }
    
    /**
     * 排序
     * @author sunqinqiu 
     * @date   2018-06-03 14:21
     * @param group
     * @return
     */
    public SQLRunner order(String order) {
        this.orders = order;
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2018-09-15 10:45
     * @return
     */
    public SQLRunner pager(DataPager dataPager) {
        this.dataPager = dataPager;
        return this;
    }
    
    /**
     * 查询什么数据
     * @author sunqinqiu 
     * @date   2018-05-28 15:07
     * @param fileds
     * @return
     */
    public SQLRunner select(String fields) {
        if (this.fields.length() > 0) {
            this.fields.append(PLSQLContent.C_D);
        }
        this.fields.append(fields);
        return this;
    }
    
    /**
     * 前几条数据
     * @author sunqinqiu 
     * @date   2018-05-31 08:50
     * @param top
     * @return
     */
    public SQLRunner top(int top) {
        this.top = top;
        return this;
    }
    
    /**
     * 添加条件
     * @author sunqinqiu 
     * @date   2018-06-03 12:14
     * @return
     */
    public SQLRunner where(String where) {
        if (where.isEmpty()) { return this; }
        if (this.wheres.length() > 0) {
            this.wheres.append(PLSQLContent.K_AND);
        }
        this.wheres.append(where);
        return this;
    }
    
    /**
     * 添加条件语句
     * @author sunqinqiu 
     * @date   2018-05-28 15:07
     * @param where
     * @return
     */
    public SQLRunner where(String file, String type, String content) {
        if (wheres.length() > 0) {
            wheres.append(PLSQLContent.K_AND);
        }
        this.wheres.append(file + " " + type + " " + content);
        return this;
    }
}
