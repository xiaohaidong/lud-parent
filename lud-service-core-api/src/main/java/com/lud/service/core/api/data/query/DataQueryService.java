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
package com.lud.service.core.api.data.query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 查询数据接口
 * @author sunqinqiu 
 * @date   2019-01-05 09:59
 */
public interface DataQueryService {
    
    /**
     * 获取INT
     * @author sunqinqiu 
     * @date   2019-01-02 18:19
     * @param runner
     * @return
     */
    int queryForInt(DataQueryExecuter exe);
    
    /**
     * 获取LIST
     * @author sunqinqiu 
     * @date   2019-01-02 18:19
     * @param runner
     * @return
     */
    List<Map<String, Serializable>> queryForList(DataQueryExecuter exe);
    
    /**
     * 获取MAP
     * @author sunqinqiu 
     * @date   2019-01-02 18:19
     * @param runner
     * @return
     */
    Map<String, Serializable> queryForMap(DataQueryExecuter exe);
    
    /**
     * 获取DataPager
     * @author sunqinqiu 
     * @date   2019-01-02 18:19
     * @param runner
     * @return
     */
    DataPager queryForPager(DataQueryExecuter exe, DataPager pager);
    
    /**
     * 获取VALUE
     * @author sunqinqiu 
     * @date   2019-01-02 18:19
     * @param runner
     * @return
     */
    String queryForValue(DataQueryExecuter exe);
    
}
