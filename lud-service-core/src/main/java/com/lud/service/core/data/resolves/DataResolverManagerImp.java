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
package com.lud.service.core.data.resolves;

import java.text.MessageFormat;

import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSON;
import com.lud.service.core.api.config.ResourceService;
import com.lud.service.core.api.data.builder.SQLBuilderService;
import com.lud.service.core.api.data.command.DataCommand;
import com.lud.service.core.api.data.query.DataQuery;
import com.lud.service.core.api.data.resolves.DataResolverManager;
import com.lud.service.core.api.redis.RedisService;
import com.lud.service.core.data.jdbc.JDBCFactory;
import com.lud.util.comm.io.FileUtil;
import com.lud.util.comm.io.XML;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.redis.node.RedisNode;
import com.lud.util.content.util.text.CT;
import com.lud.util.spring.util.Preload;

/**
 * @author sunqinqiu 
 * @date   2019-01-04 14:07
 */
@Service
@Preload(seq = 20)
public class DataResolverManagerImp implements DataResolverManager {
    private final Logger        loger = LoggerFactory.getLogger(this.getClass());
    private String              path  = "";
    /**
     * RESOURCE
     */
    @Autowired
    private ResourceService     resourceService;
    /**
     * JDBC
     */
    @Autowired
    private JDBCFactory         jdbcFactory;
    /**
     * SQLBuilder
     */
    @Autowired
    @Qualifier("SQLBuilderServiceImp")
    private SQLBuilderService   sqlBuilderService;
    /**
     * Redis
     */
    @Autowired
    private RedisService        redisService;
    /**
     * DataQuery
     */
    @Autowired
    private DataQueryResolver   dataQueryResolver;
    
    @Autowired
    private DataCommandResolver dataCommandResolver;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-03 16:05
     * @param name
     * @return
     */
    @Override
    public DataCommand getDataCommand(String name) {
        String queryString = Convert.toString(this.redisService.hget(RedisNode.REDIS_NODE_FOR_DATA_COMMAND, name));
        if (queryString.equals("")) { return null; }
        return JSON.parseObject(queryString, DataCommand.class);
    }
    
    /**
     * 获取DataQuery
     * @author sunqinqiu 
     * @date   2019-01-05 10:02
     * @param code
     * @return
     */
    @Override
    public DataQuery getDataQuery(String name) {
        String queryString = Convert.toString(this.redisService.hget(RedisNode.REDIS_NODE_FOR_DATA_QUERY, name));
        if (queryString.equals("")) { return null; }
        return JSON.parseObject(queryString, DataQuery.class);
    }
    
    /**
     * 根据JDBC解析数据源
     * @author sunqinqiu 
     * @date   2019-01-04 14:11
     * @param jdbcName
     */
    @Override
    public void resolveByJDBC(String jdbcName) {
        if (!jdbcFactory.contains(jdbcName)) { return; }
        String jdbcPath = MessageFormat.format("{0}/{1}", path, jdbcName);
        String jdbcType = this.jdbcFactory.getJDBC(jdbcName).getType();
        FileUtil.listDir(jdbcPath).forEach(moudle -> this.resolveByMoudle(jdbcName, moudle, jdbcType));
    }
    
    /**
     * 根据JDBC 和 模块解析数据源
     * @author sunqinqiu 
     * @date   2019-01-04 14:12
     * @param jdbcName
     * @param moudle
     */
    private void resolveByMoudle(String jdbcName, String moudle, String jdbcType) {
        String moudlePath = MessageFormat.format("{0}/{1}/{2}", path, jdbcName, moudle);
        FileUtil.list(moudlePath, false).forEach(item -> resolveByTable(jdbcName, moudle, item.get(CT.STRING_CODE), jdbcType));
    }
    
    /**
     * 根据单表解析数据
     * @author sunqinqiu 
     * @date   2019-01-04 21:07
     * @param jdbcName
     * @param moudle
     * @param tableName
     */
    
    private void resolveByTable(String jdbcName, String moudle, String tableName, String jdbcType) {
        String xmlPath = MessageFormat.format("{0}/{1}/{2}/{3}.xml", path, jdbcName, moudle, tableName);
        try (XML xml = new XML(xmlPath)) {
            dataQueryResolver.resolve(xml, jdbcName, moudle, tableName, jdbcType);
            dataCommandResolver.resolve(xml, jdbcName, moudle, tableName, jdbcType);
        } catch (Exception e) {
            this.loger.error("{}{}", resourceService.getError("1003.0001"), e);
        }
    }
    
    /**
     * 解析所有数据
     * @author sunqinqiu 
     * @date   2019-01-04 14:09
     */
    @Override
    public void run() {
        if (this.path.isEmpty()) {
            this.path = ServerResource.getAbsolutePath(resourceService.getReourcePath("database.data"));
        }
        FileUtil.listDir(path).forEach(this::resolveByJDBC);
    }
    
}
