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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lud.service.open.api.ApiResult;
import com.lud.service.open.api.swjtu.traffic.TrafficAPIToken;
import com.lud.util.comm.io.NetWorker;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.DEncrypt;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.comm.util.ListMapUtil;
import com.lud.util.spring.util.Preload;
import com.lud.util.spring.util.SpringRuntimeService;

import lombok.Getter;

/**
 * @author sunqinqiu 
 * @date   2019-09-06 09:23
 */
@Service
@Preload
public class TrafficUtilService {
    
    /**
     * 按照页码获取数据
     * @author sunqinqiu 
     * @date   2019-09-06 11:58
     * @return
     */
    private static final int     DATA_PER_PAGE = 200;
    
    /**
     * 配置信息
     */
    @Autowired
    private SpringRuntimeService springRuntimeService;
    
    /**
     * Token
     */
    @Getter
    private TrafficAPIToken      trafficAPIToken;
    
    /**
     * 配置文件
     */
    private Map<String, String>  config;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 06:30
     * @param json
     * @return
     */
    public ApiResult getApiResult(String json) {
        ApiResult result = new ApiResult();
        if (json.length() > 0) {
            Map<String, Serializable> content = ListMapUtil.toMap(json);
            Map<String, Serializable> data = new HashMap<>();
            data.put("data", content.get("Data"));
            result.setData(data);
            result.setMessage(Convert.toString(content.get("Message")));
            result.setSuccess(Convert.toBoolean(content.get("Status")));
        }
        return result;
    }
    
    /**
     * 检索配置文件
     * @author sunqinqiu 
     * @date   2019-09-06 09:24
     * @return
     */
    public boolean run() {
        config = springRuntimeService.getOptions();
        trafficAPIToken = new TrafficAPIToken();
        trafficAPIToken.setDataUrl(config.get("swjtu.traffic.url"));
        trafficAPIToken.setAppId(config.get("swjtu.traffic.appid"));
        trafficAPIToken.setAppKey(config.get("swjtu.traffic.appkey"));
        return true;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-06 11:06
     * @param url
     * @param data
     * @param token
     * @return
     */
    public ApiResult getData(String url, Map<String, String> data, boolean token) {
        if (data == null) {
            data = new HashMap<>();
        }
        StringBuilder requestURL = new StringBuilder(transUrl(url));
        if (!data.isEmpty()) {
            requestURL.append("?1=1");
            for (Entry<String, String> entry : data.entrySet()) {
                requestURL.append("&");
                requestURL.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        String jsonData = NetWorker.getHttpContent(requestURL.toString());
        return getApiResult(jsonData);
    }
    
    /**
     * 获取所有数据
     * @author sunqinqiu 
     * @date   2019-10-25 02:25
     * @param url
     * @param data
     * @return
     */
    public List<Map<String, Serializable>> getAllData(String url, Map<String, String> data) {
        List<Map<String, Serializable>> list = getDataListByPage(url, data, 1);
        return list == null ? ListMapUtil.getEmptyArrayList() : list;
    }
    
    /**
     * 获取数据列表
     * @author sunqinqiu 
     * @date   2019-10-24 13:33
     * @param url
     * @param data
     * @param page
     * @return
     */
    private List<Map<String, Serializable>> getDataListByPage(String url, Map<String, String> data, int page) {
        List<Map<String, Serializable>> resultList = new ArrayList<>();
        data = (data == null ? new HashMap<>() : data);
        data.put("page", page + "");
        data.put("limit", DATA_PER_PAGE + "");
        ApiResult result = this.getData(url, data, false);
        if (result == null) { return ListMapUtil.getEmptyArrayList(); }
        if (result.isSuccess()) {
            Map<String, Serializable> datas = Convert.convert(result.getData().get("data"));
            int count = Convert.toInt(datas.get("count"));
            if (count > 0) {
                resultList = Convert.convert(datas.get("data"));
            }
            int relCount = resultList.size();
            if (relCount == DATA_PER_PAGE) {
                resultList.addAll(getDataListByPage(url, data, page + 1));
            }
        }
        return resultList;
    }
    
    /**
     * 获取信息
     * @author sunqinqiu 
     * @date   2019-09-13 22:38
     * @param url
     * @param data
     * @return
     */
    public ApiResult postData(String url, Map<String, Serializable> data) {
        Map<String, Serializable> request = new HashMap<>();
        request.put("Appid", trafficAPIToken.getAppId());
        String time = DateUtil.getNow(DateUtil.FORMAT_TYPE_DATEANDTIME_FULL);
        String sign = DEncrypt.getMD5Encode(trafficAPIToken.getAppId() + time + trafficAPIToken.getAppKey());
        request.put("Sign", sign);
        request.put("Time", time);
        request.put("Data", (Serializable) data);
        return getApiResult(NetWorker.postJSON(transUrl(url), JSON.toJSONString(request)));
    }
    
    /**
     * 转换路径
     * @author sunqinqiu 
     * @date   2019-09-06 10:03
     * @param url
     * @return
     */
    public String transUrl(String url) {
        return trafficAPIToken.getDataUrl() + this.config.get("swjtu.traffic." + url);
    }
}
