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
package com.lud.web.business.core.service.website;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunqinqiu 
 * @date   2019-03-26 23:39
 */

public class WebSiteEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Getter
    @Setter
    private String            project;
    @Getter
    @Setter
    private String            code;
    @Getter
    @Setter
    private String            name;
    @Getter
    @Setter
    private String            title;
    @Getter
    @Setter
    private String            keyWords;
    @Getter
    @Setter
    private String            skin;
    @Getter
    @Setter
    private String            tel;
    @Getter
    @Setter
    private String            email;
    @Getter
    @Setter
    private String            icp;
    @Getter
    @Setter
    private String            shortName;
    @Getter
    @Setter
    private String            domain;
}
