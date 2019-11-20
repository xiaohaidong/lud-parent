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
package com.lud.service.core.api.data.builder;

import com.lud.service.core.api.data.query.DataPager;
import com.lud.service.core.api.data.runner.SQLRunner;

/**
 * 构建SQL语句
 * @author sunqinqiu 
 * @date   2019-01-02 18:00
 */
public interface SQLBuilderService {
    
    /**
     * 删除
     * @author sunqinqiu 
     * @date   2019-01-03 17:28
     * @param runner
     * @return
     */
    String getDeleteSQL(SQLRunner runner);
    
    /**
     * 插入
     * @author sunqinqiu 
     * @date   2019-01-03 17:29
     * @param runner
     * @return
     */
    String getInsertSQL(SQLRunner runner);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 03:38
     * @param runner
     * @return
     */
    String getPagerDataCountSQL(SQLRunner runner);
    
    /**
     * 获取PAGERSQL
     * @author sunqinqiu 
     * @date   2019-01-02 18:09
     * @param runner
     * @param pager
     * @return
     */
    String getPagerSQL(SQLRunner runner, DataPager pager);
    
    /**
     * 获取QUERYSQL
     * @author sunqinqiu 
     * @date   2019-01-02 18:08
     * @param runner
     * @return
     */
    String getQuerySQL(SQLRunner runner);
    
    /**
     * 更新
     * @author sunqinqiu 
     * @date   2019-01-03 17:29
     * @param runner
     * @return
     */
    String getUpdateSQL(SQLRunner runner);
    
}
