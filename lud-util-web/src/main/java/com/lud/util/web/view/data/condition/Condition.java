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
package com.lud.util.web.view.data.condition;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-11-05 11:40
 */
public class Condition {
    
    /**
     * 参数
     */
    @Getter
    @Setter
    private String  args;
    
    /**
     * 内容
     */
    @Getter
    @Setter
    private String  content;
    
    /**
     * 是否可以使用带参数的方式传值，除非实在没法写，否则都允许
     */
    @Getter
    @Setter
    private boolean cparas;
    
    /**
     * 是否需要执行其他程序
     */
    @Getter
    @Setter
    private String  invoke;
    
    /**
     * 
     */
    @Getter
    @Setter
    private String  value;
    
    /**
     * 
     */
    @Getter
    @Setter
    private String  valueType;
}
