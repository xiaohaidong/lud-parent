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
package com.lud.service.core.api.data.jdbc;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * JDBC连接
 * @author sunqinqiu 
 * @date   2019-01-02 05:52
 */
public interface JDBCService {
    
    /**
     * 执行代码
     * @author sunqinqiu 
     * @date   2019-09-11 13:51
     * @param jdbcName
     * @param sqls
     */
    void execute(String jdbcName, String sqls);
    
    /**
     * 获取JDBC类型
     * @author sunqinqiu 
     * @date   2019-09-11 13:51
     * @param jdbcName
     * @return
     */
    String getJDBCType(String jdbcName);
    
    /**
     * 插入数据
     * @author sunqinqiu 
     * @date   2019-09-11 13:50
     * @param jdbcName
     * @param sqls
     * @param args
     * @return
     */
    int insertIdentity(String jdbcName, String sqls, List<Serializable> args);
    
    /**
     * 获取Int信息
     * @author sunqinqiu 
     * @date   2019-09-11 13:48
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    int queryForInt(String jdbcName, String sql, List<Serializable> args);
    
    /**
     * 获取List
     * @author sunqinqiu 
     * @date   2019-09-11 13:48
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    List<Map<String, Serializable>> queryForList(String jdbcName, String sql, List<Serializable> args);
    
    /**
     * 获取MAP信息
     * @author sunqinqiu 
     * @date   2019-09-11 13:48
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    Map<String, Serializable> queryForMap(String jdbcName, String sql, List<Serializable> args);
    
    /**
     * 获取Value值
     * @author sunqinqiu 
     * @date   2019-09-11 13:49
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    String queryForValue(String jdbcName, String sql, List<Serializable> args);
    
    /**
     * 更新
     * @author sunqinqiu 
     * @date   2019-09-11 13:50
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    int update(String jdbcName, String sql, List<Serializable> args);
    
}
