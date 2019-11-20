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
package com.lud.util.comm.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2018-12-30 11:48
 */
public class ListComparator implements Comparator<Map<String, Serializable>> {
    /**
     * 
     */
    private String filed;
    
    /**
     * 
     */
    public ListComparator(String filed) {
        this.filed = filed;
    }
    
    /**
     * 比较列表
     * @author sunqinqiu 
     * @date   2018-12-30 11:54
     * @param compx
     * @param compy
     * @return
     */
    @Override
    public int compare(Map<String, Serializable> compx, Map<String, Serializable> compy) {
        return Convert.toInt(compx.get(filed)) - Convert.toInt(compy.get(filed));
    }
}
