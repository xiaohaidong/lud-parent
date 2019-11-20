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
package com.lud.util.web.api.context;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Result
 * @author sunqinqiu 
 * @date   2019-01-01 19:12
 */

public class ContextResult implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 执行结果
     */
    @Setter
    @Getter
    private boolean           result           = true;
    
    /**
     * 获取内容
     */
    @Setter
    @Getter
    private String            action;
    
    /**
     * 错误代码
     */
    @Setter
    @Getter
    private String            code;
    
    /**
     * 错误信息
     */
    @Setter
    @Getter
    private String            message;
    
    /**
     * 构造方法
     */
    public ContextResult() {
        this.result = true;
    }
    
    /**
     * 构造方法
     */
    public ContextResult(boolean result, String code, String message, String action) {
        sets(result, code, message);
        this.action = action;
    }
    
    /**
     * 设置数据
     * @author sunqinqiu 
     * @date   2019-03-04 01:01
     * @param result
     * @param code
     * @param message
     * @param content
     */
    public void sets(boolean result, String code, String message) {
        this.result = result;
        this.code = code;
        this.message = message;
    }
}
