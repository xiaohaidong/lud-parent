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
package com.lud.util.spring.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lud.util.comm.util.ListComparator;

/**
 * @author sunqinqiu 
 * @date   2019-10-25 16:00
 */
@Service
public class SpringPreloadService {
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 17:33
     * @return
     */
    private List<Map<String, Object>> getList(boolean isfresh) {
        List<Map<String, Serializable>> sort = new ArrayList<>();
        Map<String, Object> beans = SpringContextUtil.getBeansWithAnnotation(Preload.class);
        beans.forEach((k, v) -> {
            Preload preload = v.getClass().getAnnotation(Preload.class);
            if (isfresh && !preload.fresh()) return;
            Map<String, Serializable> map = new HashMap<>();
            map.put("fresh", preload.fresh());
            map.put("exe", preload.exe());
            map.put("seq", preload.seq());
            map.put("name", k);
            sort.add(map);
        });
        sort.sort(new ListComparator("seq"));
        List<Map<String, Object>> list = new ArrayList<>();
        sort.forEach(item -> {
            Map<String, Object> nitem = new HashMap<>();
            nitem.putAll(item);
            nitem.put("obj", beans.get(nitem.get("name")));
            list.add(nitem);
        });
        return list;
    }
    
    /**
     * 系统启动后执行
     * @author sunqinqiu 
     * @date   2019-10-25 17:15
     */
    public void runPreload() {
        SpringServiceInvoker.run(getList(false));
    }
    
    /**
     * 系统刷新资源后执行
     * @author sunqinqiu 
     * @date   2019-10-25 17:15
     */
    public void freshReource() {
        SpringServiceInvoker.run(getList(true));
    }
}
