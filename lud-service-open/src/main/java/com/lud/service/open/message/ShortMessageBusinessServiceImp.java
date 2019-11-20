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
package com.lud.service.open.message;

import com.alibaba.fastjson.JSON;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.open.api.ApiResult;
import com.lud.service.open.api.message.ShortMesageFactory;
import com.lud.service.open.api.message.ShortMessage;
import com.lud.service.open.api.message.ShortMessageBusinessService;
import com.lud.service.open.api.message.ShortMessageConfig;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.redis.node.BusinessNode;
import com.lud.util.content.util.text.CT;
import com.lud.util.dubbo.util.CoreService;
import com.lud.util.spring.util.Preload;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送接口
 * @author sunqinqiu 
 * @date   2019-09-16 14:33
 */
@Service
@Preload
public class ShortMessageBusinessServiceImp extends CoreService implements ShortMessageBusinessService {
    @Reference
    private ShortMesageFactory shortMesageFactory;
    
    /**
     * 解析发送短信配置文件
     * @author sunqinqiu 
     * @date   2019-09-19 07:19
     */
    
    @Override
    public void run() {
        DataQueryExecuter exe = new DataQueryExecuter("lud-core.lud_shortmessage_interface_query", DataQueryContent.DEF, DataQueryContent.DEF);
        dataQueryService.queryForList(exe).forEach(item -> {
            ShortMessageConfig config = new ShortMessageConfig();
            config.setProject(Convert.toString(item.get(DataQueryContent.PROJECT)));
            config.setDriver(Convert.toString(item.get("driver")));
            config.setFeelv(Convert.toDouble(item.get("feelv")));
            config.setConfig(ListMapUtil.toMap(Convert.toString(item.get(CT.STRING_CONFIG))));
            redisService.hset(BusinessNode.REDIS_NODE_FOR_BUSINESS_SHORT_MESSAGE_CONFIG, config.getProject(), JSON.toJSONString(config));
        });
    }
    
    /**
     * 获取配置文件
     * @author sunqinqiu 
     * @date   2019-09-19 07:42
     * @param project
     * @return 
     */
    private ShortMessageConfig getConfig(String project) {
        String content = redisService.hget(BusinessNode.REDIS_NODE_FOR_BUSINESS_SHORT_MESSAGE_CONFIG, project);
        return JSON.parseObject(content, ShortMessageConfig.class);
    }
    
    /**
     * 发送短信
     * @author sunqinqiu 
     * @date   2019-09-16 14:43
     * @param tels
     * @param message
     * @return
     */
    @Override
    public boolean send(String project, String tels, String templement, Map<String, Serializable> data) {
        ShortMessageConfig config = getConfig(project);
        ShortMessage message = new ShortMessage();
        message.setConfig(config.getConfig());
        message.setData(data);
        message.setDriver(config.getDriver());
        message.setTels(tels);
        message.setTemplement(templement);
        ApiResult result = shortMesageFactory.send(message);
        sendLog(config, result);
        return result.isSuccess();
    }
    
    /**
     * 记录日志
     * @author sunqinqiu 
     * @date   2019-09-19 09:08
     * @return
     */
    private void sendLog(ShortMessageConfig config, ApiResult result) {
        Map<String, Serializable> resultData = result.getData();
        String content = Convert.toString(resultData.get("content"));
        Map<String, Serializable> data = getDefData(config, content, result.getMessage());
        int length = (content.length() / 60) + 1;
        String tels = Convert.toString(resultData.get("success"));
        if (tels.isEmpty()) return;
        double cost = config.getFeelv() * length * tels.split(",").length;
        data.put("issucess", 1);
        data.put("sender", "system");
        for (String tel : tels.split(",")) {
            data.put("mobitel", tel);
            dataCommandService.exe("lud-core.lud_shortmessage_info_insert", data);
        }
        data.put("cost", cost);
        dataCommandService.exe("lud-core.lud_shortmessage_interface_update", data);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-26 02:47
     * @param config
     * @param content
     * @param message
     * @return
     */
    private Map<String, Serializable> getDefData(ShortMessageConfig config, String content, String message) {
        Map<String, Serializable> data = new HashMap<>();
        data.put("project", config.getProject());
        data.put("driver", config.getDriver());
        data.put("sendtime", DateUtil.getNow());
        data.put("content", content);
        data.put("message", message);
        return data;
    }
    
}
