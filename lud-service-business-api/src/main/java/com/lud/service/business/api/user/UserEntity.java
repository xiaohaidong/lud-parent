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
package com.lud.service.business.api.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-01-06 23:44
 */
public class UserEntity implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 用户账号
     */
    @Getter
    @Setter
    private String            userid           = "";
    /**
     * 用户临时密钥
     */
    @Getter
    @Setter
    private String            token;
    /**
     * 姓名
     */
    @Getter
    @Setter
    private String            name;
    /**
     * 用户组
     */
    @Getter
    @Setter
    private String            group;
    /**
     * 
     */
    @Getter
    @Setter
    private String            groupName;
    /**
     * 用户组KEY
     */
    @Getter
    @Setter
    private String            groupKey;
    /**
     * 所属项目
     */
    @Getter
    @Setter
    private String            project;
    /**
     * 部门
     */
    @Getter
    @Setter
    private String            department;
    /**
     * 部门名称
     */
    @Getter
    @Setter
    private String            departmentName;
    /**
     * 办公室
     */
    @Getter
    @Setter
    private String            office;
    /**
     * 权限号
     */
    @Getter
    @Setter
    private String            role;
    /**
     * 
     */
    @Getter
    @Setter
    private Date              last;
    
}
