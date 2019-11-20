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
package com.lud.service.business.service.application;

import java.io.Serializable;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.application.ApplicationMuneService;
import com.lud.service.business.api.application.ApplicationService;
import com.lud.service.business.api.application.ApplicationWedgeService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.redis.node.BusinessNode;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;

/**
 * @author sunqinqiu 
 * @date   2019-01-06 04:16
 */
@Service
@Preload
public class ApplicationServiceImp extends CoreService implements ApplicationService {
    @Autowired
    private ApplicationMuneService  applicationMuneService;
    @Autowired
    private ApplicationWedgeService applicationWedgeService;
    
    /**
    * 解析应用程序信息
    * @author sunqinqiu 
    * @date   2019-01-06 04:21
    */
    @Override
    public void run() {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_application_info_query", DataQueryContent.CACHE, DataQueryContent.CACHE);
        dataQueryService.queryForList(exe).forEach(item -> {
            String code = Convert.toString(item.get(DataQueryContent.CODE));
            redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_APPLICATION, code, JSON.toJSONString(item));
            applicationMuneService.resolveMenu(code);
            applicationWedgeService.resolveWedge(code);
        });
    }
    
    /**
     * 获取应用程序信息
     * @author sunqinqiu 
     * @date   2019-10-26 05:27
     * @param code
     * @return
     */
    @Override
    public Map<String, Serializable> getApplication(String code) {
        return ListMapUtil.toMap(redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_APPLICATION, code));
    }
    
}
