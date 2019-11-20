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
package com.lud.util.web.view.data;

import java.io.Serializable;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import com.lud.service.core.api.redis.RedisService;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.view.ViewDataEntity;

/**
 * @author sunqinqiu
 * @date 2019-02-24 21:55
 */
@Service("com.lud.web.util.view.data.webviewredisdataservice")
public class WebViewRedisDataService implements IWebViewDataService {
    /**
     * REDIS服务
     */
    @Reference
    protected RedisService redisService;
    
    @Override
    public Serializable getData(ViewDataEntity data, ContextRequest request) {
        String[] querys = data.getQuery().split("/");
        String result = data.getConfig().get("result");
        String content = "";
        if (querys.length == 2) {
            content = redisService.hget(querys[0], querys[1].replace("{project}", request.getProject()));
        }
        if (querys.length == 1) {
            content = redisService.get(querys[0]);
        }
        if (result.equals("map")) { return (Serializable) ListMapUtil.toMap(content); }
        if (result.equals("list")) { return (Serializable) ListMapUtil.toList(content); }
        return content;
    }
}
