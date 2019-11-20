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
package com.lud.util.content.core.data;

import java.io.Serializable;

/**
 * 数据库静态资源配置
 * @author sunqinqiu 
 * @date   2019-01-02 04:10
 */
public class PLSQLContent implements Serializable {
    /**
     * 
     */
    private static final long  serialVersionUID    = 1L;
    
    /**
     * SQL Builder Name
     */
    public static final String SQL_BULIDER_NAME    = "com.lud.core.service.data.builder.types.for.{0}";
    
    /**
     * select
     */
    public static final String K_SELECT            = "select ";
    
    /**
     * top
     */
    public static final String K_TOP               = "top ";
    
    /**
     * from
     */
    public static final String K_FROM              = " from ";
    
    /**
     * where
     */
    public static final String K_WHERE             = " where ";
    
    /**
     * and
     */
    public static final String K_AND               = " and ";
    
    /**
     * or
     */
    public static final String K_OR                = " or ";
    
    /**
     * 分组
     */
    public static final String K_GROUP             = " group by ";
    
    /**
     * 排序
     */
    public static final String K_ORDER             = " order by ";
    
    /**
     * MYSQL 等limit查询
     */
    public static final String K_LIMIT             = " limit ";
    
    /**
     * 删除
     */
    public static final String K_DELETE            = "delete";
    
    /**
     * 插入
     */
    public static final String K_INSERT            = "insert into ";
    
    /**
     * 插入value
     */
    public static final String K_INSERT_VALUE      = "({0}) values({1})";
    
    /**
     * 更新
     */
    public static final String K_UPDATE            = "update ";
    
    /**
     * 更新设置
     */
    public static final String K_SET               = "set ";
    
    /**
     * 等号
     */
    public static final String K_EQ                = "=";
    
    /**
     * 默认的
     */
    public static final String V_DEF               = "def";
    
    /**
     * table替代符
     */
    public static final String V_TABLE             = "{t}";
    
    /**
     * table替代符
     */
    public static final String V_TABEL_A           = "{t}.*";
    
    /**
     * 逗号
     */
    public static final String C_D                 = ",";
    
    /**
     * 空
     */
    public static final String C_EMPTY             = "";
    
    /**
     * 空格
     */
    public static final String C_SPACE             = " ";
    
    /**
     * 每页最大数据
     */
    public static final int    PAGER_MAX_NUM       = 1000;
    
    /**
     * 默认每页数据
     */
    public static final int    PAGER_DEF_PAGEINDEX = 15;
    
    /**
     * 当前后页面数
     */
    public static final int    PAGER_BT_PAGE       = 5;
    
    private PLSQLContent() {}
}
