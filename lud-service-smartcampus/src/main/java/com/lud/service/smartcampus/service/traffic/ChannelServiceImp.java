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
package com.lud.service.smartcampus.service.traffic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.open.api.swjtu.traffic.ChannelAPI;
import com.lud.service.smartcampus.api.traffic.ChannelService;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.util.text.CT;
import com.lud.util.dubbo.util.CoreService;

/**
 * @author sunqinqiu 
 * @date   2019-10-25 03:40
 */
@Service
public class ChannelServiceImp extends CoreService implements ChannelService {
    @Reference
    private ChannelAPI api;
    
    @Override
    public void asyc() {
        api.getChannelList().forEach(this::asycChannel);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 03:47
     * @param item
     */
    private void asycChannel(Map<String, Serializable> item) {
        String project = this.springRuntimeService.getContext("traffic.project");
        Serializable channel = item.get("chnnUID");
        String name = Convert.toString(item.get("chnnName"));
        String park = Convert.toString(item.get("parkUID"));
        DataQueryExecuter exe = new DataQueryExecuter("lud-smartcampus.lud_traffic_channel_query", DataQueryContent.COUNT, "project,code");
        exe.addData(CT.STRING_CODE, channel);
        exe.addData(CT.STRING_PROJECT, project);
        if (this.dataQueryService.queryForInt(exe) == 0) {
            Map<String, Serializable> args = new HashMap<>();
            args.put(CT.STRING_PROJECT, project);
            args.put(CT.STRING_CODE, item.get("chnnUID"));
            args.put(CT.STRING_NAME, name);
            args.put("num", item.get("chnnNo"));
            args.put("park", item.get("parkUID"));
            args.put("direction", name.contains("入") ? "0" : "1");
            dataCommandService.exe("lud-smartcampus.lud_traffic_channel_insert", args);
        }
        asycPark(project, park, Convert.toString(item.get("ParkName")));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 03:47
     * @param project
     * @param code
     * @param name
     */
    private void asycPark(String project, String code, String name) {
        DataQueryExecuter exe = new DataQueryExecuter("lud-smartcampus.lud_traffic_park_query", DataQueryContent.COUNT, "project,code");
        exe.addData(CT.STRING_CODE, code);
        exe.addData(CT.STRING_PROJECT, project);
        if (this.dataQueryService.queryForInt(exe) == 0) {
            Map<String, Serializable> args = new HashMap<>();
            args.put(CT.STRING_PROJECT, project);
            args.put(CT.STRING_CODE, code);
            args.put(CT.STRING_NAME, name);
            dataCommandService.exe("lud-smartcampus.lud_traffic_park_insert", args);
        }
    }
}
