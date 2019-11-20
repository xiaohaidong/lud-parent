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
package com.lud.service.business.service.code;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSON;
import com.lud.service.business.api.code.CodeService;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.redis.node.BusinessNode;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;

/**
 * @author sunqinqiu 
 * @date   2019-10-03 13:54
 */
@Service
@Preload
public class CodeServiceImp extends CoreService implements CodeService {
    /**
     * 解析所有数据
     * @author sunqinqiu 
     * @date   2019-10-03 13:55
     */
    @Override
    public void run() {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_code_group_query", DataQueryContent.DEF, DataQueryContent.DEF);
        dataQueryService.queryForList(exe).forEach(this::resolve);
    }
    
    /**
     * 获取某个编码表的数据列表
     * @author sunqinqiu 
     * @date   2019-10-03 13:55
     * @param code
     * @return
     */
    @Override
    public List<Map<String, Serializable>> getContent(String code) {
        return ListMapUtil.toList(redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_CODE_CONTENT, code));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-03 14:08
     * @param item
     */
    private void resolve(Map<String, Serializable> item) {
        String code = Convert.toString(item.get("code"));
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_code_content_query", DataQueryContent.DEF, DataQueryContent.DEF).addData("groupx", code);
        List<Map<String, Serializable>> list = dataQueryService.queryForList(exe);
        if (!list.isEmpty()) {
            redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_CODE_CONTENT, code, JSON.toJSONString(list));
        }
    }
    
}
