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

import com.lud.service.core.api.data.runner.SQLField;
import com.lud.service.core.api.data.runner.SQLRunner;
import com.lud.util.content.core.data.PLSQLContent;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 21:09
 */
public class SQLBuilderUtil {
    /**
     * This is the builder
     */
    private StringBuilder builder;
    private SQLRunner     runner;
    
    /**
     * 初始化SQLBuilder 
     */
    public SQLBuilderUtil(SQLRunner runner) {
        builder = new StringBuilder();
        this.runner = runner;
    }
    
    /**
     * 选择
     * @author sunqinqiu 
     * @date   2019-01-02 21:37
     * @return
     */
    public SQLBuilderUtil append(Object content) {
        builder.append(content);
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 21:12
     * @return
     */
    public SQLBuilderUtil delete() {
        builder.append(PLSQLContent.K_DELETE);
        return this;
    }
    
    /**
     * 字段
     * @author sunqinqiu 
     * @date   2019-01-02 21:37
     * @return
     */
    public SQLBuilderUtil field() {
        builder.append(runner.getFields());
        return this;
    }
    
    /**
     * 查询
     * @author sunqinqiu 
     * @date   2019-01-02 21:45
     * @return
     */
    public SQLBuilderUtil from() {
        builder.append(PLSQLContent.K_FROM);
        builder.append(runner.getTable());
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-02 22:04
     * @return
     */
    public SQLBuilderUtil group() {
        if (!runner.getGroups().isEmpty()) {
            builder.append(PLSQLContent.K_GROUP).append(runner.getGroups());
        }
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-04 11:34
     * @return
     */
    public SQLBuilderUtil inertFileds() {
        StringBuilder fs = new StringBuilder();
        StringBuilder vs = new StringBuilder();
        for (SQLField field : runner.getFieldList()) {
            fs.append(",").append(field.getName());
            vs.append(",").append(field.getValue());
        }
        builder.append(MessageFormat.format(PLSQLContent.K_INSERT_VALUE, fs.substring(1), vs.substring(1)));
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 21:14
     * @return
     */
    public SQLBuilderUtil insert() {
        builder.append(PLSQLContent.K_INSERT).append(runner.getTable());
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 03:57
     * @return
     */
    public SQLBuilderUtil limit() {
        builder.append(PLSQLContent.K_LIMIT);
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-02 22:04
     * @return
     */
    public SQLBuilderUtil order() {
        if (!runner.getGroups().isEmpty()) {
            builder.append(PLSQLContent.K_ORDER).append(runner.getGroups());
        } else {
            if (!runner.getOrders().isEmpty()) {
                builder.append(PLSQLContent.K_ORDER).append(runner.getOrders());
            }
        }
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 03:11
     * @return
     */
    public SQLBuilderUtil select() {
        builder.append(PLSQLContent.K_SELECT);
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-02 21:40
     * @return
     */
    public SQLBuilderUtil top() {
        int topnum = runner.getTop();
        if (topnum > 0) {
            builder.append(PLSQLContent.K_TOP).append(topnum + "  ");
        }
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 03:57
     * @return
     */
    public SQLBuilderUtil topLimit() {
        int topnum = runner.getTop();
        if (topnum > 0) {
            this.limit().append(topnum);
        }
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 16:33
     * @return
     */
    public SQLBuilderUtil topRownum() {
        String where = runner.getWheres().toString();
        int topnum = runner.getTop();
        if (topnum > 0) {
            if (where.isEmpty()) {
                builder.append(PLSQLContent.K_WHERE);
            } else {
                builder.append(PLSQLContent.K_AND);
            }
            builder.append(" ROWNUM<= ").append(topnum);
        }
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-02 21:33
     * @return
     */
    @Override
    public String toString() {
        return builder.toString().replace("{t}", runner.getTable());
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 21:14
     * @return
     */
    public SQLBuilderUtil update() {
        builder.append(PLSQLContent.K_UPDATE).append(runner.getTable());
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-04 10:40
     * @return
     */
    public SQLBuilderUtil updateSet() {
        builder.append(PLSQLContent.K_SET);
        for (SQLField field : runner.getFieldList()) {
            builder.append(MessageFormat.format("{0}={1}", field.getName(), field.getValue()));
        }
        return this;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-02 21:52
     * @return
     */
    public SQLBuilderUtil where() {
        String where = runner.getWheres().toString();
        if (!where.isEmpty()) {
            builder.append(PLSQLContent.K_WHERE);
            builder.append(where);
        }
        return this;
    }
}
