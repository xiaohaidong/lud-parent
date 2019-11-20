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

import com.lud.service.core.api.data.builder.SQLBuilderService;
import com.lud.service.core.api.data.query.DataPager;
import com.lud.service.core.api.data.runner.SQLRunner;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 22:30
 */
public class SQLBuilderServiceBase implements SQLBuilderService {
    /**
     * 删除ＳＱＬ语句
     * @author sunqinqiu 
     * @date   2019-01-04 10:08
     * @param runner
     * @return
     */
    @Override
    public String getDeleteSQL(SQLRunner runner) {
        return this.getSQLBuilder(runner).delete().from().where().toString();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-04 11:12
     * @param runner
     * @return
     */
    @Override
    public String getInsertSQL(SQLRunner runner) {
        return this.getSQLBuilder(runner).insert().inertFileds().toString();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-04 11:12
     * @param runner
     * @return
     */
    @Override
    public String getPagerDataCountSQL(SQLRunner runner) {
        return this.getSQLBuilder(runner).select().append(" count(*) as num").from().where().toString();
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-04 11:12
     * @param runner
     * @param pager
     * @return
     */
    @Override
    public String getPagerSQL(SQLRunner runner, DataPager pager) {
        String sql = this.getSQLBuilder(runner).select().append("row_number() OVER(").order().append(" ) AS rownumber,").field().from().where().group().toString();
        String sqlWap = "select t.* from ({0})  t where t.rownumber>? and t.rownumber <= ?";
        runner.addArg(pager.getBeginNumber());
        runner.addArg(pager.getPageIndex() * pager.getPageSize());
        return MessageFormat.format(sqlWap, sql);
    }
    
    /**
     * 该方法无实际意义，在具体的类中实现
     * @author sunqinqiu 
     * @date   2019-01-04 11:12
     * @param runner
     * @return
     */
    @Override
    public String getQuerySQL(SQLRunner runner) {
        return null;
    }
    
    /**
     * getSQLBuilder
     * @author sunqinqiu 
     * @date   2019-01-02 22:53
     * @param runner
     * @return
     */
    protected SQLBuilderUtil getSQLBuilder(SQLRunner runner) {
        return new SQLBuilderUtil(runner);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-04 10:43
     * @param runner
     * @return
     */
    @Override
    public String getUpdateSQL(SQLRunner runner) {
        return this.getSQLBuilder(runner).update().updateSet().where().toString();
    }
}
