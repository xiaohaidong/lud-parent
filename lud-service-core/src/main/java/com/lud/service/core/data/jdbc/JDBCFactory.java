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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;
import com.lud.service.core.api.config.ResourceService;
import com.lud.service.core.api.config.SystemConfigService;
import com.lud.util.comm.io.FileUtil;
import com.lud.util.comm.server.ServerResource;
import com.lud.util.content.util.text.CT;
import com.lud.util.spring.util.Preload;

import lombok.Getter;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 00:00
 */
@Service
@Preload(seq = 10)
public class JDBCFactory {
    /**
     * 日志
     */
    private final Logger        loger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 系统配置文件
     */
    @Autowired
    private SystemConfigService systemConfigService;
    
    /**
     * 资源文件服务
     */
    @Autowired
    private ResourceService     resourceService;
    /**
     * 
     */
    @Getter
    private Map<String, JDBC>   jdbcs = new HashMap<>();
    
    /**
     * 解析所有数据源
     * @author sunqinqiu 
     * @date   2019-01-02 00:15
     */
    public void run() {
        try {
            for (Entry<String, JDBC> jdbc : jdbcs.entrySet()) {
                jdbc.getValue().getPool().close();
            }
            this.jdbcs.clear();
            String path = resourceService.getReourcePath("jdbc.dir");
            String curjdbc = systemConfigService.getConfig("resource.jdbc");
            path += "/jdbc-" + curjdbc;
            FileUtil.list(ServerResource.getAbsolutePath(path), false).forEach(this::resolve);
        } catch (Exception e) {
            this.loger.error("{}{}", resourceService.getError("1002.0001"), e);
        }
        
    }
    
    /**
     * 解析单个文件
     * @author sunqinqiu 
     * @date   2019-01-02 03:54
     * @param map
     */
    private void resolve(Map<String, String> map) {
        String code = map.get(CT.STRING_CODE);
        String xmlpath = map.get(CT.STRING_PATH);
        if (this.jdbcs.containsKey(code)) { return; }
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(MessageFormat.format("file:{0}", xmlpath));
        try {
            DruidDataSource pool = context.getBean(DruidDataSource.class);
            JDBC model = new JDBC();
            model.setPool(pool);
            model.setJdbct(context.getBean(JdbcTemplate.class));
            model.setTrans(context.getBean(DataSourceTransactionManager.class));
            model.setType(pool.getDbType());
            this.jdbcs.put(code, model);
        } catch (Exception e) {
            context.close();
            this.loger.error("{}", resourceService.getError("1002.0001"));
        }
    }
    
    /**
     * 是否包含该JDBC
     * @author sunqinqiu 
     * @date   2019-01-02 00:14
     * @param code
     * @return
     */
    public boolean contains(String code) {
        return jdbcs.containsKey(code);
    }
    
    /**
     * 获取JDBC
     * @author sunqinqiu 
     * @date   2019-01-02 00:13
     * @param code
     * @return
     */
    public JDBC getJDBC(String code) {
        if (contains(code)) { return getJdbcs().get(code); }
        return null;
    }
    
    /**
     * 获取JdbcTemplate
     * @author sunqinqiu 
     * @date   2019-01-03 02:21
     * @param code
     * @return
     */
    public JdbcTemplate getJdbcTemplate(String code) {
        JDBC jdbc = getJDBC(code);
        return jdbc == null ? null : jdbc.getJdbct();
    }
    
}
