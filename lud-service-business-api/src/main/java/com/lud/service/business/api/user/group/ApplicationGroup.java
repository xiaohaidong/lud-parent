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
package com.lud.service.business.api.user.group;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-01-06 17:12
 */
public class ApplicationGroup implements Serializable {
    /**
     * 
     */
    private static final long               serialVersionUID = 1L;
    /**
     * 编码
     */
    @Getter
    @Setter
    private String                          code;
    /**
     * 名称
     */
    @Getter
    @Setter
    private String                          name;
    /**
     * 图标
     */
    @Getter
    @Setter
    private String                          icon;
    /**
     * 描述
     */
    @Getter
    @Setter
    private String                          description;
    /**
     * 应用列表
     */
    @Getter
    @Setter
    private List<Map<String, Serializable>> applications;
}
