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
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.application.ApplicationWedgeService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.redis.node.BusinessNode;
import com.lud.util.dubbo.util.CoreService;

/**
 * @author sunqinqiu 
 * @date   2019-10-26 05:20
 */
@Service
public class ApplicationWedgeServiceImp extends CoreService implements ApplicationWedgeService {
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-24 00:32
     * @param code
     */
    @Override
    public void resolveWedge(String code) {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_application_wedge_query", "def,app", "app").addData("app", code);
        redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_APPLICATION_WEDGE, code, JSON.toJSONString(dataQueryService.queryForList(exe)));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-24 01:22
     * @param code
     * @return
     */
    @Override
    public List<Map<String, Serializable>> getWedgeList(String code) {
        return ListMapUtil.toList(redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_APPLICATION_WEDGE, code));
    }
}
