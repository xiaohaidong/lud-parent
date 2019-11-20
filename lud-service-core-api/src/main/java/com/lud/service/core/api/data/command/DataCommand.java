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
package com.lud.service.core.api.data.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 执行数据库Execute
 * @author sunqinqiu 
 * @date   2019-03-03 12:33
 */
public class DataCommand implements Serializable {
    
    /**
     * 序号
     */
    private static final long     serialVersionUID = 1L;
    
    /**
     * 名称
     */
    @Getter
    @Setter
    private String                name             = "";
    
    /**
     * 执行内容
     */
    @Getter
    @Setter
    private List<DataCommandItem> items;
    
    /**
     * 重构
     */
    public DataCommand() {
        items = new ArrayList<>();
    }
}
