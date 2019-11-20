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
package com.lud.service.business.api.code;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 编码表统一管理
 * @author sunqinqiu 
 * @date   2019-10-03 13:49
 */
public interface CodeService {
    /**
     * 获取数据
     * @author sunqinqiu 
     * @date   2019-10-03 13:54
     * @param code
     * @return
     */
    List<Map<String, Serializable>> getContent(String code);
    
    /**
     * 解析数据
     * @author sunqinqiu 
     * @date   2019-10-03 13:53
     */
    void run();
}
