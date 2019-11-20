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
import lombok.Setter;

/**
 * 查询数据模型
 * @author sunqinqiu 
 * @date   2019-01-04 19:40
 */
public class DataQuery implements Serializable {
    /**
     * 
     */
    private static final long           serialVersionUID = 1L;
    
    /**
     *使用JDBC 
     */
    @Getter
    @Setter
    private String                      jdbc;
    
    /**
     * 名称
     */
    @Getter
    @Setter
    private String                      name;
    
    /**
     * 关键字
     */
    @Getter
    @Setter
    private String                      key;
    
    /**
     * 表名
     */
    @Getter
    @Setter
    private String                      table;
    
    /**
     * 排序
     */
    @Getter
    @Setter
    private String                      order;
    
    /**
     * 分组
     */
    @Getter
    @Setter
    private String                      group;
    
    /**
     * 查询字段
     */
    @Getter
    @Setter
    private Map<String, DataQueryField> fields;
    
    /**
     * 限制条件
     */
    @Getter
    @Setter
    private Map<String, DataQueryField> limits;
    
    /**
     * 构造函数
     */
    public DataQuery() {
        fields = new HashMap<>();
        limits = new HashMap<>();
    }
}
