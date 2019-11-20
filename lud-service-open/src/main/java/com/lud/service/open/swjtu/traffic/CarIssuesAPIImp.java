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
import java.util.TreeMap;

import org.apache.dubbo.config.annotation.Service;

import com.lud.service.open.api.ApiResult;
import com.lud.service.open.api.swjtu.traffic.CarIssuesAPI;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.content.open.swjtu.TrafficAPIContent;

/**
 * @author sunqinqiu 
 * @date   2019-09-12 09:58
 */
@Service
public class CarIssuesAPIImp extends TrafficBaseService implements CarIssuesAPI {
    
    @Override
    public ApiResult delay(String carNumber, String endDate, int fee, String payType, String from) {
        Map<String, Serializable> data = new TreeMap<>();
        data.put("plateNo", carNumber);
        data.put("delayTime", endDate);
        data.put("delayMoney", fee);
        data.put("payType", payType);
        data.put("dataFroms", from);
        return this.trafficUtilService.postData("api.busi.car.delay", data);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 02:19
     * @param keyword
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Serializable>> getCarsList(String keyword) {
        Map<String, String> data = new HashMap<>();
        if (!keyword.isEmpty()) {
            data.put("keyword", keyword);
        }
        List<Map<String, Serializable>> list = this.trafficUtilService.getAllData("api.report.cars", data);
        List<Map<String, Serializable>> result = new ArrayList<>();
        list.forEach(item -> {
            Map<String, Serializable> map = new HashMap<>();
            map.put("ownerregeid", Convert.toStringTrim(item.get(TrafficAPIContent.OWNER_REGEID)));
            map.put("ownername", Convert.toStringTrim(item.get(TrafficAPIContent.OWNER_NAME)));
            map.put("ownersex", Convert.toStringTrim(item.get(TrafficAPIContent.OWNER_SEX)));
            map.put("ownerdepartment", Convert.toStringTrim(item.get(TrafficAPIContent.OWNER_DEPARTEMNT)));
            map.put("ownermobitel", Convert.toStringTrim(item.get(TrafficAPIContent.OWNER_MOBITEL)));
            map.put("carregeid", Convert.toStringTrim(item.get(TrafficAPIContent.CAR_REGID)));
            map.put("cartype", Convert.toStringTrim(item.get(TrafficAPIContent.CAR_TYPE)));
            map.put("carnumber", Convert.toStringTrim(item.get(TrafficAPIContent.CAR_NUMUBER)));
            map.put("carncolor", Convert.toStringTrim(item.get(TrafficAPIContent.CAR_NCOLOR)));
            map.put("issureid", Convert.toStringTrim(item.get(TrafficAPIContent.ISSURE_ID)));
            map.put("issuretype", Convert.toStringTrim(item.get(TrafficAPIContent.ISSURE_TYPE)));
            map.put("issurebegindate", DateUtil.toDateTime(item.get(TrafficAPIContent.ISSURE_BEGIN_DATE), TrafficAPIContent.ISSURE_DATE_FAT));
            map.put("issureenddate", DateUtil.toDateTime(item.get(TrafficAPIContent.ISSURE_END_DATE), TrafficAPIContent.ISSURE_DATE_FAT));
            map.put("issurebalance", Convert.toDouble(item.get(TrafficAPIContent.ISSURE_BALANCE)));
            map.put("issuredeposit", Convert.toDouble(item.get(TrafficAPIContent.ISSURE_DEPOSIT)));
            map.put("issurepostion", Convert.toStringTrim(item.get(TrafficAPIContent.ISSURE_POSTION)));
            map.put("issurenote", Convert.toStringTrim(item.get(TrafficAPIContent.ISSURE_NOTE)));
            map.put("operatoruser", Convert.toStringTrim(item.get(TrafficAPIContent.OPERATOR_USER)));
            map.put("operatordate", DateUtil.toDateTime(item.get(TrafficAPIContent.OPERATOR_DATE), TrafficAPIContent.ISSURE_DATE_FAT));
            map.put("operatororg", Convert.toStringTrim(item.get(TrafficAPIContent.OPERATOR_ORG)));
            result.add(map);
        });
        return result;
    }
}
