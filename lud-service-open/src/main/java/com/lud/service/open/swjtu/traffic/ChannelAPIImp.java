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
package com.lud.service.open.swjtu.traffic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import com.lud.service.open.api.swjtu.traffic.ChannelAPI;

/**
 * 车道信息
 * @author sunqinqiu 
 * @date   2019-09-06 09:39
 */
@Service
public class ChannelAPIImp extends TrafficBaseService implements ChannelAPI {
    /**
     * 同步车道信息
     * @author sunqinqiu 
     * @date   2019-09-06 09:41
     * @return
     */
    @Override
    public List<Map<String, Serializable>> getChannelList() {
        return this.trafficUtilService.getAllData("api.report.channel", null);
    }
    
}
