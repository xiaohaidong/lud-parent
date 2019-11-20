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

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * Request
 * @author sunqinqiu 
 * @date   2019-01-01 19:09
 */

public class ContextRequest implements Serializable {
    
    /**
     * 
     */
    private static final long   serialVersionUID = 1L;
    
    // ************************************************
    // 以下和项目有关的信息
    // ************************************************
    /**
     * Domain信息
     */
    @Setter
    @Getter
    private String              domain;
    /**
     * 项目
     */
    @Setter
    @Getter
    private String              project;
    
    // ************************************************
    // 以下和本次请求有关的信息
    // ************************************************
    /**
     * 页面信息
     */
    @Setter
    @Getter
    private String              service;
    
    /**
     * 标志位
     */
    @Setter
    @Getter
    private String              flag;
    
    /**
     * 传输的数据
     */
    @Setter
    @Getter
    private Map<String, String> data;
    
    // ************************************************
    // 以下和用户有关的信息
    // ************************************************
    /**
     * 验证码
     */
    @Setter
    @Getter
    private String              token;
    
    /**
     * 用户信息
     */
    @Setter
    @Getter
    private String              userid;
    /**
     * USERGROUP
     */
    @Setter
    @Getter
    private String              userGroup;
    /**
     * 
     */
    @Setter
    @Getter
    private String              userGroupKey;
    
    @Setter
    @Getter
    private String              userDepartment;
    // ************************************************
    // 以下和权限有关的信息
    // ************************************************
    /**
     * 当前的权限代码
     */
    @Setter
    @Getter
    private String              menu;
    /**
     * 操作权限
     */
    @Setter
    @Getter
    private String[]            menuActions;
    /**
     * 数据权限
     */
    @Setter
    @Getter
    private String[]            menuDatas;

    @Setter
    @Getter
    private String            role;
    
}
