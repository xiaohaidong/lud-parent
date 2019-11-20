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
package com.lud.service.business.service.company;

import java.io.Serializable;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.company.CompanyService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.redis.node.BusinessNode;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;

/**
 * 
 * @author sunqinqiu 
 * @date   2019-01-05 17:42
 */
@Service
@Preload
public class CompanyServiceImp extends CoreService implements CompanyService {
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-06 02:54
     */
    @Override
    public void run() {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_company_info_query", DataQueryContent.CACHE, DataQueryContent.DEF);
        dataQueryService.queryForList(exe).forEach(item -> redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_COMPANY, Convert.toString(item.get(DataQueryContent.CODE)), JSON.toJSONString(item)));
    }
    
    /**
     * 获取公司信息
     * @author sunqinqiu 
     * @date   2019-01-06 03:41
     * @param code
     * @return
     */
    @Override
    public Map<String, Serializable> getCompany(String code) {
        return ListMapUtil.toMap(redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_COMPANY, code));
    }
}
