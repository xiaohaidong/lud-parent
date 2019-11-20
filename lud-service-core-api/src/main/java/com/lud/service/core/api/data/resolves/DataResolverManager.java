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
package com.lud.service.core.api.data.resolves;

import com.lud.service.core.api.data.command.DataCommand;
import com.lud.service.core.api.data.query.DataQuery;

/**
 * @author sunqinqiu 
 * @date   2019-01-04 14:09
 */
public interface DataResolverManager {
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-03 16:03
     * @param name
     * @return
     */
    DataCommand getDataCommand(String name);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-05 10:05
     * @param name
     * @return
     */
    DataQuery getDataQuery(String name);
    
    /**
     * 根据JDBC解析数据源
     * @author sunqinqiu 
     * @date   2019-01-04 14:11
     * @param jdbcName
     */
    void resolveByJDBC(String jdbcName);
    
    /**
     * 解析所有数据源
     * @author sunqinqiu 
     * @date   2019-01-04 14:10
     */
    void run();
}
