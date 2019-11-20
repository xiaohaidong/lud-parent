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
package com.lud.web.business.iot.system;

import java.io.Serializable;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lud.iot.business.core.api.system.entity.SystemEntity;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.view.ViewDataEntity;

/**
 * @author sunqinqiu 
 * @date   2019-10-14 20:08
 */
@Service("com.lud.web.business.core.service.runtime.system")
public class SystemService extends CoreService {
    /**
     * 获取信息信息
     * @author sunqinqiu 
     * @date   2019-10-16 05:09
     * @param map
     * @param request
     * @param result
     */
    public void system(ViewDataEntity data, Map<String, Serializable> result, ContextRequest request) {
        String server = request.getData().get("server");
        result.put("system", JSON.parseObject(this.redisService.hget(SystemEntity.NODE, server), SystemEntity.class));
    }
}
