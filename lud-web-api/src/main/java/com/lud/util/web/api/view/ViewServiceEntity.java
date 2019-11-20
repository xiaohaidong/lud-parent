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
package com.lud.util.web.api.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-01-09 17:11
 */
public class ViewServiceEntity implements Serializable {
    
    /**
     * 
     */
    private static final long       serialVersionUID = 1L;
    
    /**
     * 名称
     */
    @Getter
    @Setter
    private String                  name;
    
    /**
     * 布局
     */
    @Getter
    @Setter
    private String                  theme;
    
    /**
     * ACL策略，只分内网、公网和不限制三个等级
     */
    @Getter
    @Setter
    private String                  acl;
    /**
     * 用户组
     */
    @Getter
    @Setter
    private String                  userGroup;
    /**
     * 配置文件
     */
    @Getter
    @Setter
    private Map<String, String>     config;
    
    @Getter
    @Setter
    private boolean                 cache;
    /**
     * 内容
     */
    @Getter
    @Setter
    private List<ViewContentEntity> contents         = new ArrayList<>();
    
    /**
     * 
     */
    @Getter
    @Setter
    private List<ViewCompEntity>    comps            = new ArrayList<>();
    /**
     * 
     */
    @Getter
    @Setter
    private List<ViewDataEntity>    datas            = new ArrayList<>();
    /**
     * 
     */
    @Getter
    @Setter
    private List<ViewCommandEntity> commands         = new ArrayList<>();
}
