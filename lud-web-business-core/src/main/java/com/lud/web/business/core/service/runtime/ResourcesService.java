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
package com.lud.web.business.core.service.runtime;

import java.io.Serializable;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.util.dubbo.util.CoreService;
import com.lud.util.dubbo.util.PreloaderService;
import com.lud.util.spring.util.SpringPreloadService;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.context.ContextResult;

/**
 * @author sunqinqiu 
 * @date   2019-02-24 11:46
 */
@Service("com.lud.web.business.core.service.runtime.resources")
public class ResourcesService extends CoreService {
    @Autowired
    private SpringPreloadService springPreloadService;
    @Reference(group = "core", version = "0.1.0")
    private PreloaderService     corePreloaderService;
    @Reference(group = "business", version = "0.1.0")
    private PreloaderService     businessPreloaderService;
    @Reference(group = "open", version = "0.1.0")
    private PreloaderService     openPreloaderService;
    @Reference(group = "smartcampus", version = "0.1.0")
    private PreloaderService     smartcampusPreloaderService;
    @Reference(group = "iotserver", version = "0.1.0")
    private PreloaderService     iotserverPreloaderService;
    
    /**
     * 刷新资源
     * @author sunqinqiu 
     * @date   2019-10-14 20:09
     * @param map
     * @param request
     * @param result
     */
    public void fresh(Map<String, Serializable> map, ContextRequest request, ContextResult result) {
        try {
            corePreloaderService.freshReource();
            businessPreloaderService.freshReource();
            springPreloadService.freshReource();
            openPreloaderService.freshReource();
            smartcampusPreloaderService.freshReource();
            iotserverPreloaderService.freshReource();
        } catch (Exception e) {
            loger.error("{}", e.getMessage());
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-27 02:09
     * @param map
     * @param request
     * @param result
     */
    public void load(Map<String, Serializable> map, ContextRequest request, ContextResult result) {
        try {
            corePreloaderService.runPreload();
            openPreloaderService.runPreload();
            businessPreloaderService.runPreload();
            springPreloadService.runPreload();
            smartcampusPreloaderService.runPreload();
            iotserverPreloaderService.runPreload();
        } catch (Exception e) {
            loger.error("{}", e.getMessage());
        }
    }
}
