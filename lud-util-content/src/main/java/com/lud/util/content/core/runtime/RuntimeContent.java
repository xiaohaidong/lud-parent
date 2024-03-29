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
package com.lud.util.content.core.runtime;

import lombok.Getter;

/**
 * @author sunqinqiu 
 * @date   2019-10-19 17:51
 */
public class RuntimeContent {
    private RuntimeContent() {}
    
    public static final String    RESOURCE_DIR    = "lud-resource";
    public static final String    APP_OPTIONS_DIR = "/resource-apps-config";
    @Getter
    private static final String[] SPRING_RUN_DIR  = { "WRICS-BFRAME","lud-parent","lud-services", "lud-project",  "lud-runtimes" };
}
