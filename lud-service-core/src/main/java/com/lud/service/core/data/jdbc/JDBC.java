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
package com.lud.service.core.data.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-01-01 23:54
 */

public class JDBC {
    /**
     * 连接池
     */
    @Getter
    @Setter
    private DruidDataSource              pool;
    
    /**
     * JdbcTemplate
     */
    @Getter
    @Setter
    private JdbcTemplate                 jdbct;
    
    /**
     * DataSourceTransactionManager
     */
    @Getter
    @Setter
    private DataSourceTransactionManager trans;
    
    /**
     * 类型
     */
    @Getter
    @Setter
    private String                       type;
}
