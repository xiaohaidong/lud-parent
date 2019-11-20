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
package com.lud.service.core.data.builder;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lud.service.core.api.data.builder.SQLBuilderService;
import com.lud.service.core.api.data.query.DataPager;
import com.lud.service.core.api.data.runner.SQLRunner;
import com.lud.service.core.data.jdbc.JDBCFactory;
import com.lud.util.content.core.data.PLSQLContent;
import com.lud.util.spring.util.SpringContextUtil;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 18:01
 */
@Component
public class SQLBuilderServiceImp implements SQLBuilderService {
    @Autowired
    private JDBCFactory jdbcFactory;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 17:27
     * @param runner
     * @return
     */
    @Override
    public String getDeleteSQL(SQLRunner runner) {
        return getSQLBuilderService(runner).getDeleteSQL(runner);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 17:27
     * @param runner
     * @return
     */
    @Override
    public String getInsertSQL(SQLRunner runner) {
        return getSQLBuilderService(runner).getInsertSQL(runner);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 03:41
     * @param runner
     * @return
     */
    @Override
    public String getPagerDataCountSQL(SQLRunner runner) {
        return getSQLBuilderService(runner).getPagerDataCountSQL(runner);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 03:41
     * @param runner
     * @param pager
     * @return
     */
    @Override
    public String getPagerSQL(SQLRunner runner, DataPager pager) {
        return getSQLBuilderService(runner).getPagerSQL(runner, pager);
    }
    
    /**
     * 获取QuerySQL
     * @author sunqinqiu 
     * @date   2019-01-02 22:45
     * @param runner
     * @return
     */
    @Override
    public String getQuerySQL(SQLRunner runner) {
        return getSQLBuilderService(runner).getQuerySQL(runner);
    }
    
    /**
     * 获取一个新的SQLBuilder
     * @author sunqinqiu 
     * @date   2019-01-02 22:28
     * @return
     */
    private SQLBuilderService getSQLBuilderService(SQLRunner runner) {
        return (SQLBuilderService) SpringContextUtil.getBean(MessageFormat.format(PLSQLContent.SQL_BULIDER_NAME, jdbcFactory.getJDBC(runner.getJdbc()).getType()));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 17:27
     * @param runner
     * @return
     */
    @Override
    public String getUpdateSQL(SQLRunner runner) {
        return getSQLBuilderService(runner).getUpdateSQL(runner);
    }
}
