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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.lud.service.core.api.config.ResourceService;
import com.lud.service.core.api.data.jdbc.JDBCService;
import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.util.text.CT;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 05:52
 */
@Service
public class JDBCServiceImp implements JDBCService {
    private final Logger    loger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JDBCFactory     jdbcFactory;
    /**
     * 
     */
    @Autowired
    private ResourceService resourceService;
    
    /**
     * 执行命令
     * @author sunqinqiu 
     * @date   2018-08-12 16:18
     * @param jdbcName
     * @param sqls
     */
    @Override
    public void execute(String jdbcName, String sqls) {
        try {
            JdbcTemplate jdbcTemplate = jdbcFactory.getJdbcTemplate(jdbcName);
            if (jdbcTemplate == null) { return; }
            for (String sql : sqls.split(";")) {
                if (sql.length() > 4) {
                    jdbcTemplate.execute(sql);
                }
            }
        } catch (Exception e) {
            this.loger.error("{}{}", resourceService.getError("1002.0005"), e);
        }
    }
    
    @Override
    public String getJDBCType(String jdbcName) {
        return jdbcFactory.getJDBC(jdbcName).getType();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-04 12:52
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    @Override
    public int insertIdentity(String jdbcName, String sql, List<Serializable> args) {
        try {
            JdbcTemplate jdbcTemplate = jdbcFactory.getJdbcTemplate(jdbcName);
            if (jdbcTemplate == null) { return -1; }
            jdbcTemplate.update(sql, args == null ? null : args.toArray());
            return jdbcTemplate.queryForObject("select @@identity", int.class);
        } catch (Exception e) {
            this.loger.error("{}{}", resourceService.getError("1002.0004"), e);
            return Content.INT_EMPTY;
        }
    }
    
    /**
     * 获取INT
     * @author sunqinqiu 
     * @date   2018-08-12 16:19
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    @Override
    public int queryForInt(String jdbcName, String sql, List<Serializable> args) {
        JdbcTemplate jdbcTemplate = jdbcFactory.getJdbcTemplate(jdbcName);
        if (jdbcTemplate == null) { return 0; }
        try {
            return jdbcTemplate.queryForObject(sql, args == null ? null : args.toArray(), int.class);
        } catch (Exception e) {
            this.loger.error("{}{}", resourceService.getError("1002.0002.1"), e);
            return Content.INT_EMPTY;
        }
    }
    
    /**
     * 获取列表信息
     * @author sunqinqiu 
     * @date   2018-08-12 16:19
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    @Override
    public List<Map<String, Serializable>> queryForList(String jdbcName, String sql, List<Serializable> args) {
        try {
            JdbcTemplate jdbcTemplate = jdbcFactory.getJdbcTemplate(jdbcName);
            if (jdbcTemplate == null) { return new ArrayList<>(); }
            return Convert.toList(jdbcTemplate.queryForList(sql, args == null ? null : args.toArray()));
        } catch (Exception e) {
            this.loger.error("{}{}", resourceService.getError("1002.0002"), e);
            return ListMapUtil.getEmptyArrayList();
        }
    }
    
    /**
     * 获取MAP
     * @author sunqinqiu 
     * @date   2018-08-12 16:19
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    @Override
    public Map<String, Serializable> queryForMap(String jdbcName, String sql, List<Serializable> args) {
        List<Map<String, Serializable>> list = queryForList(jdbcName, sql, args);
        if (list == null || list.isEmpty()) { return new HashMap<>(); }
        return list.get(0);
    }
    
    /**
     * 获取值
     * @author sunqinqiu 
     * @date   2018-08-12 16:18
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    @Override
    public String queryForValue(String jdbcName, String sql, List<Serializable> args) {
        JdbcTemplate jdbcTemplate = jdbcFactory.getJdbcTemplate(jdbcName);
        if (jdbcTemplate == null) { return null; }
        try {
            return jdbcTemplate.queryForObject(sql, args == null ? null : args.toArray(), String.class);
        } catch (Exception e) {
            this.loger.error("{}{}", resourceService.getError("1002.0002.2"), e);
            return CT.EMPTY;
        }
    }
    
    /**
     * 执行SQL语句
     * @author sunqinqiu 
     * @date   2018-08-12 16:18
     * @param jdbcName
     * @param sql
     * @param args
     * @return
     */
    @Override
    public int update(String jdbcName, String sql, List<Serializable> args) {
        try {
            JdbcTemplate jdbcTemplate = jdbcFactory.getJdbcTemplate(jdbcName);
            if (jdbcTemplate == null) { return -1; }
            return jdbcTemplate.update(sql, args == null ? null : args.toArray());
        } catch (Exception e) {
            this.loger.error("{}{}", resourceService.getError("1002.0003"), e);
            return Content.INT_EMPTY;
        }
    }
}
