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
package com.lud.util.comm.card;

import java.io.Serializable;

import com.lud.util.content.util.text.CT;

import lombok.Getter;
import lombok.Setter;

/**
 * 身份证模型
 * 
 * @author sunqinqiu
 * @date 2018-05-06 22:54
 */

public class CardEntity implements Serializable {
    
    /**
     * 版本号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * ID号
     */
    @Getter
    private String            id               = CT.EMPTY;
    
    /**
     * 姓名
     */
    @Getter
    @Setter
    private String            name             = CT.EMPTY;
    
    /**
     * 出生日期
     */
    @Getter
    @Setter
    private String            birth;
    
    /**
     * 性别
     */
    @Getter
    @Setter
    private String            sex              = CT.EMPTY;
    
    /**
     * 是否是有效的身份证
     */
    @Getter
    @Setter
    private boolean           isIdentity       = CT.BOOL_FALSE;
    
    /**
     * 构造方法
     */
    public CardEntity(String id) {
        this.id = id;
    }
}
