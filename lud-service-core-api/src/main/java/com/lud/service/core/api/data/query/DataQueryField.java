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

import lombok.Getter;
import lombok.Setter;

/**
 * 查询项目
 * @author sunqinqiu 
 * @date   2019-01-04 19:44
 */
public class DataQueryField implements Serializable {
    
    /**
     * 序号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 内容
     */
    @Getter
    @Setter
    private String            content;
    
    /**
     * 参数
     */
    @Getter
    @Setter
    private String            args;
    
    /**
     * 构造方法
     */
    public DataQueryField(String content, String args) {
        this.content = content;
        this.args = args;
    }
}
