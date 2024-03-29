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
package com.lud.service.core.data.builder.types;

import org.springframework.stereotype.Component;

import com.lud.service.core.api.data.runner.SQLRunner;
import com.lud.service.core.data.builder.SQLBuilderServiceBase;

/**
 * @author sunqinqiu 
 * @date   2019-01-02 20:50
 */
@Component("com.lud.core.service.data.builder.types.for.oracle")
public class SQLBuilderServiceForOracle extends SQLBuilderServiceBase {
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-03 20:45
     * @param runner
     * @return
     */
    @Override
    public String getQuerySQL(SQLRunner runner) {
        return this.getSQLBuilder(runner).select().field().from().where().topRownum().group().order().toString();
    }
    
}
