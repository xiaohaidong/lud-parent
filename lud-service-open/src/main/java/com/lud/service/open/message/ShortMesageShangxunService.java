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

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.service.core.api.config.ResourceService;
import com.lud.service.open.api.ApiResult;
import com.lud.service.open.api.message.ShortMesageService;
import com.lud.service.open.api.message.ShortMessage;
import com.lud.util.comm.io.NetWorker;
import com.lud.util.comm.util.Convert;
import com.lud.util.spring.util.SpringRuntimeService;

/**
 * 商讯接口发送接口
 * @author sunqinqiu 
 * @date   2019-10-25 23:31
 */
@Service("com.lud.service.open.message.shangxun.shortmessage")
public class ShortMesageShangxunService implements ShortMesageService {
    @Autowired
    private SpringRuntimeService springRuntimeService;
    /**
     * 
     */
    @Reference
    private ResourceService      resourceService;
    
    /**
     * 获取短信息
     * @author sunqinqiu 
     * @date   2019-10-25 23:36
     * @param templement
     * @param data
     * @return
     */
    private String getMessage(String templement, Map<String, Serializable> data) {
        String content = resourceService.getMessage(templement);
        if (data.isEmpty()) return content;
        for (Entry<String, Serializable> item : data.entrySet()) {
            content = content.replace("{" + item.getKey() + "}", Convert.toString(item.getValue()));
        }
        return content;
    }
    
    /**
     * 发送短消息
     * @author sunqinqiu 
     * @date   2019-10-25 23:32
     * @param message
     * @return
     */
    @Override
    public ApiResult send(ShortMessage message) {
        ApiResult apiResult = new ApiResult();
        try {
            String content = getMessage(message.getTemplement(), message.getData());
            String url = springRuntimeService.getContext("sm.shangxun.send");
            Map<String, Serializable> config = message.getConfig();
            String userid = Convert.toString(config.get("userid"));
            String password = Convert.toString(config.get("password"));
            StringBuilder builder = new StringBuilder(url);
            builder.append("?txt=ccdx&name=").append(userid).append("&pwd=").append(password);
            builder.append("&msg=").append(URLEncoder.encode(content, "GB2312")).append("&dst=").append(message.getTels());
            String result = NetWorker.getHttpContent(builder.toString(), "GB2312");
            Map<String, String> map = NetWorker.getMapByUrl(result);
            String errid = map.get("errid");
            apiResult.setSuccess(errid.equals("0"));
            apiResult.setMessage(errid);
            Map<String, Serializable> data = new HashMap<>();
            data.put("success", map.get("success"));
            data.put("faile", map.get("faile"));
            data.put("content", content);
            apiResult.setData(data);
        } catch (Exception e) {
            apiResult.setSuccess(false);
            apiResult.setMessage(e.getMessage());
            apiResult.setTime(new Date());
        }
        return apiResult;
    }
}
