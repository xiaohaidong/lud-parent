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
package com.lud.service.core.data.runner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.lud.service.core.api.data.builder.SQLBuilderService;
import com.lud.service.core.api.data.jdbc.JDBCService;
import com.lud.service.core.api.data.query.DataPager;
import com.lud.service.core.api.data.runner.SQLRunner;
import com.lud.service.core.api.data.runner.SQLRunnerService;
import com.lud.service.core.data.jdbc.JDBCFactory;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.util.text.CT;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 05:48
 */
@Service
public class SQLRunnerServiceImp implements SQLRunnerService {
    public final Logger       loger = LoggerFactory.getLogger(this.getClass());
    /**
     * 
     */
    @Autowired
    @Qualifier("SQLBuilderServiceImp")
    private SQLBuilderService sqlBuilderService;
    /**
     * JDBC工厂
     */
    @Autowired
    private JDBCFactory       jdbcFactory;
    /**
     * JDBC服务
     */
    @Autowired
    private JDBCService       jdbcService;
    
    /**
     * 删除
     * @author sunqinqiu 
     * @date   2019-01-03 17:25
     * @param runner
     * @return
     */
    @Override
    public int delete(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return -1; }
        return jdbcService.update(runner.getJdbc(), sqlBuilderService.getDeleteSQL(runner), runner.getArgs());
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-02 19:45
     * @param runner
     * @return
     */
    private String getQuerySQL(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return null; }
        return sqlBuilderService.getQuerySQL(runner);
    }
    
    /**
     * 获取新的SQLRunner
     * @author sunqinqiu 
     * @date   2019-01-02 05:51
     * @param jdbc
     * @param table
     * @return
     */
    @Override
    public SQLRunner getRunner(String jdbc, String table) {
        if (!jdbcFactory.contains(jdbc)) { return null; }
        return new SQLRunner(jdbc, table);
    }
    
    /**
     * 插入
     * @author sunqinqiu 
     * @date   2019-01-03 17:26
     * @param runner
     * @return
     */
    
    @Override
    public int insert(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return -1; }
        String jdbcType = this.jdbcService.getJDBCType(runner.getJdbc());
        if (runner.getAutoFileld().isEmpty()) { return this.jdbcService.update(runner.getJdbc(), sqlBuilderService.getInsertSQL(runner), runner.getArgs()); }
        int rusult = 0;
        if ("oracle".equals(jdbcType)) {
            rusult = insertSequence(runner);
        } else {
            rusult = insertIdentity(runner);
        }
        return rusult;
    }
    
    /**
     * 具有@@identity的数据库的自增字段的方法,比如MYSQL和SQLSERVER
     * @author sunqinqiu 
     * @date   2019-01-04 13:02
     * @param runner
     * @return
     */
    public int insertIdentity(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return -1; }
        return jdbcService.insertIdentity(runner.getJdbc(), sqlBuilderService.getInsertSQL(runner), runner.getArgs());
    }
    
    /**
     * 具有Sequence的数据库，比如ORACLE的自增字段的方法
     * @author sunqinqiu 
     * @date   2019-01-04 13:01
     * @param runner
     * @return
     */
    public int insertSequence(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return -1; }
        int seq = this.jdbcService.queryForInt(runner.getJdbc(), "select SEQ_" + runner.getTable() + ".nextval", null);
        runner.addFieldValue(runner.getAutoFileld(), seq);
        return this.jdbcService.update(runner.getJdbc(), sqlBuilderService.getInsertSQL(runner), runner.getArgs());
    }
    
    /**
     * 查询Int数据
     * @author sunqinqiu 
     * @date   2019-01-02 18:28
     * @param runner
     * @return
     */
    @Override
    public int queryForInt(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return -1; }
        return jdbcService.queryForInt(runner.getJdbc(), getQuerySQL(runner), runner.getArgs());
    }
    
    /**
     * 查询列表
     * @author sunqinqiu 
     * @date   2019-01-02 18:28
     * @param runner
     * @return
     */
    @Override
    public List<Map<String, Serializable>> queryForList(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return ListMapUtil.getEmptyArrayList(); }
        return jdbcService.queryForList(runner.getJdbc(), getQuerySQL(runner), runner.getArgs());
    }
    
    /**
     * 查询单条数据
     * @author sunqinqiu 
     * @date   2019-01-02 18:28
     * @param runner
     * @return
     */
    @Override
    public Map<String, Serializable> queryForMap(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return ListMapUtil.getEmptySerializableMap(); }
        runner.top(1);
        List<Map<String, Serializable>> list = queryForList(runner);
        if (list == null || list.isEmpty()) { return new HashMap<>(); }
        return list.get(0);
    }
    
    /**
     * 查询分页程序
     * @author sunqinqiu 
     * @date   2019-01-02 18:28
     * @param runner
     * @return
     */
    @Override
    public DataPager queryForPager(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return null; }
        runner.getDataPager().setDataCount(jdbcService.queryForInt(runner.getJdbc(), sqlBuilderService.getPagerDataCountSQL(runner), runner.getArgs()));
        runner.getDataPager().reckonPagerNum();
        runner.getDataPager().setDataList(jdbcService.queryForList(runner.getJdbc(), sqlBuilderService.getPagerSQL(runner, runner.getDataPager()), runner.getArgs()));
        return runner.getDataPager();
    }
    
    /**
     * 查询值
     * @author sunqinqiu 
     * @date   2019-01-02 18:28
     * @param runner
     * @return
     */
    @Override
    public String queryForValue(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return CT.EMPTY; }
        return jdbcService.queryForValue(runner.getJdbc(), getQuerySQL(runner), runner.getArgs());
    }
    
    /**
     * 更新
     * @author sunqinqiu 
     * @date   2019-01-03 17:26
     * @param runner
     * @return
     */
    @Override
    public int update(SQLRunner runner) {
        if (!jdbcFactory.contains(runner.getJdbc())) { return -1; }
        return jdbcService.update(runner.getJdbc(), sqlBuilderService.getUpdateSQL(runner), runner.getArgs());
    }
}
