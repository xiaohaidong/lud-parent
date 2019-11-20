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
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.open.api.ApiResult;
import com.lud.service.open.api.swjtu.traffic.CarIssuesAPI;
import com.lud.service.smartcampus.api.traffic.CarIssuesService;
import com.lud.util.content.core.data.DataQueryContent;
import com.lud.util.content.open.swjtu.TrafficAPIContent;
import com.lud.util.dubbo.util.CoreService;

/**
 * @author sunqinqiu 
 * @date   2019-10-25 03:00
 */
@Service
public class CarIssuesServiceImp extends CoreService implements CarIssuesService {
    @Reference
    private CarIssuesAPI api;
    
    /**
     * 更新数据
     * @author sunqinqiu 
     * @date   2019-10-25 03:05
     * @param keyword
     */
    @Override
    public void asycCarsIssues(String keyword) {
        api.getCarsList(keyword).forEach(this::asycCarIssues);
    }
    
    /**
     * 更新一辆车的数据
     * @author sunqinqiu 
     * @date   2019-10-25 03:05
     * @param item
     */
    private void asycCarIssues(Map<String, Serializable> item) {
        item.put("project", this.springRuntimeService.getContext("traffic.project"));
        asycCarIssueItem(item);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 03:56
     * @param data
     */
    private void asycCarIssueItem(Map<String, Serializable> data) {
        DataQueryExecuter exe = new DataQueryExecuter("lud-smartcampus.lud_traffic_carissue_query", DataQueryContent.COUNT, TrafficAPIContent.MSTR_ISSUREID);
        exe.addData(TrafficAPIContent.MSTR_ISSUREID, data.get(TrafficAPIContent.MSTR_ISSUREID));
        dataCommandService.exe("lud-smartcampus.lud_traffic_carissue_" + (this.dataQueryService.queryForInt(exe) == 0 ? "insert" : "asyc"), data);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 04:03
     * @param carNumber
     * @param endDate
     * @param fee
     * @param payType
     * @param from
     */
    @Override
    public ApiResult delay(String carNumber, String endDate, int fee, String payType, String from) {
        ApiResult result = api.delay(carNumber, endDate, fee, payType, from);
        if (result.isSuccess()) {
            asycCarsIssues(carNumber);
        }
        return result;
    }
}
