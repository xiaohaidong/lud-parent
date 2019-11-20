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
package com.lud.service.core.api.data.runner;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lud.service.core.api.data.query.DataPager;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 05:48
 */
public interface SQLRunnerService {
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 17:15
     * @param runner
     * @return
     */
    int delete(SQLRunner runner);
    
    /**
     * 获取新Runner
     * @author sunqinqiu 
     * @date   2019-01-02 17:58
     * @param jdbc
     * @param table
     * @return
     */
    SQLRunner getRunner(String jdbc, String table);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 17:15
     * @param runner
     * @return
     */
    int insert(SQLRunner runner);
    
    /**
     * 获取INT
     * @author sunqinqiu 
     * @date   2019-01-02 18:19
     * @param runner
     * @return
     */
    int queryForInt(SQLRunner runner);
    
    /**
     * 获取LIST
     * @author sunqinqiu 
     * @date   2019-01-02 18:19
     * @param runner
     * @return
     */
    List<Map<String, Serializable>> queryForList(SQLRunner runner);
    
    /**
     * 获取MAP
     * @author sunqinqiu 
     * @date   2019-01-02 18:19
     * @param runner
     * @return
     */
    Map<String, Serializable> queryForMap(SQLRunner runner);
    
    /**
     * 获取DataPager
     * @author sunqinqiu 
     * @date   2019-01-02 18:19
     * @param runner
     * @return
     */
    DataPager queryForPager(SQLRunner runner);
    
    /**
     * 获取VALUE
     * @author sunqinqiu 
     * @date   2019-01-02 18:19
     * @param runner
     * @return
     */
    String queryForValue(SQLRunner runner);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 17:15
     * @param runner
     * @return
     */
    int update(SQLRunner runner);
}
