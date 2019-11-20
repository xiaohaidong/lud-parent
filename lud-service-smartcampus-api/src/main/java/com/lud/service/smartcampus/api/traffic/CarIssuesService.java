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
package com.lud.service.smartcampus.api.traffic;

import com.lud.service.open.api.ApiResult;

/**
 * @author sunqinqiu 
 * @date   2019-10-25 02:59
 */
public interface CarIssuesService {
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 03:00
     * @param keyword
     */
    void asycCarsIssues(String keyword);
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 04:05
     * @param carNumber
     * @param endDate
     * @param fee
     * @param payType
     * @param from
     * @return
     */
    ApiResult delay(String carNumber, String endDate, int fee, String payType, String from);
}
