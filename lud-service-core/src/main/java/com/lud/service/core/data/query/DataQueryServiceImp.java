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
package com.lud.service.core.data.query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.core.api.data.query.DataPager;
import com.lud.service.core.api.data.query.DataQuery;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.core.api.data.query.DataQueryField;
import com.lud.service.core.api.data.query.DataQueryService;
import com.lud.service.core.api.data.resolves.DataResolverManager;
import com.lud.service.core.api.data.runner.SQLRunner;
import com.lud.service.core.api.data.runner.SQLRunnerService;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.util.text.CT;

/**
 * @author sunqinqiu 
 * @date   2019-01-05 09:59
 */
@Service
public class DataQueryServiceImp implements DataQueryService {
    
    @Autowired
    private DataResolverManager dataResolverManager;
    @Autowired
    private SQLRunnerService    sqlRunnerService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-05 12:15
     * @param exe
     * @return 
     */
    private SQLRunner getRuannerByExecuter(DataQueryExecuter exe) {
        DataQuery query = dataResolverManager.getDataQuery(exe.getName());
        SQLRunner runner = new SQLRunner(query.getJdbc(), query.getTable());
        // 第一步，判断是否分页
        if (exe.getPager() != null) {
            runner.pager(exe.getPager());
        }
        // 第二步,定义TOP数据
        if (exe.getTop() > 0) {
            runner.top(exe.getTop());
        }
        // 判断是否含有自定义GROUP
        setRuannerFiled(query, runner, exe);
        // 第四步，限制条件
        setRuannerLimit(query, runner, exe);
        return runner;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-05 12:31
     * @param exe
     * @return
     */
    @Override
    public int queryForInt(DataQueryExecuter exe) {
        return sqlRunnerService.queryForInt(getRuannerByExecuter(exe));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-05 12:31
     * @param exe
     * @return
     */
    @Override
    public List<Map<String, Serializable>> queryForList(DataQueryExecuter exe) {
        return sqlRunnerService.queryForList(getRuannerByExecuter(exe));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-05 12:31
     * @param exe
     * @return
     */
    @Override
    public Map<String, Serializable> queryForMap(DataQueryExecuter exe) {
        return sqlRunnerService.queryForMap(getRuannerByExecuter(exe));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-05 12:31
     * @param exe
     * @param pager
     * @return
     */
    @Override
    public DataPager queryForPager(DataQueryExecuter exe, DataPager pager) {
        SQLRunner runner = getRuannerByExecuter(exe);
        runner.pager(pager);
        return sqlRunnerService.queryForPager(runner);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-05 12:31
     * @param exe
     * @return
     */
    @Override
    public String queryForValue(DataQueryExecuter exe) {
        return sqlRunnerService.queryForValue(getRuannerByExecuter(exe));
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-05 17:57
     * @param query
     * @param runner
     * @param exe
     */
    private void setRuannerFiled(DataQuery query, SQLRunner runner, DataQueryExecuter exe) {
        String exeGroup = exe.getGroup();
        if (!exeGroup.isEmpty()) {
            setRuannerFiledWhenHaveGroup(runner, exeGroup);
        } else {
            setRuannerFiledWhenHaveNonGroup(query, runner, exe);
        }
    }
    
    /**
     * 如果有GROUP的情况下
     * @author sunqinqiu 
     * @date   2019-01-05 18:07
     * @param query
     * @param runner
     * @param exe
     * @param exeGroup
     */
    private void setRuannerFiledWhenHaveGroup(SQLRunner runner, String exeGroup) {
        String[] exeGroups = exeGroup.split(":");
        runner.select(exeGroups[0] + "," + exeGroups[1] + (exeGroup.contains(" as ") ? "" : " as counter"));
        runner.group(exeGroups[0]);
        runner.order(exeGroups[0]);
    }
    
    /**
     * 如果没有GROUP
     * @author sunqinqiu 
     * @date   2019-01-05 18:07
     * @param query
     * @param runner
     * @param exe
     */
    private void setRuannerFiledWhenHaveNonGroup(DataQuery query, SQLRunner runner, DataQueryExecuter exe) {
        // group="city,dta:count(*) as countnum,max(per) as xc"
        Map<String, DataQueryField> filedList = query.getFields();
        for (String exeFiled : exe.getFields()) {
            DataQueryField queryFiled = filedList.get(exeFiled);
            runner.select(queryFiled.getContent());
            String queryArgs = queryFiled.getArgs();
            if (!queryArgs.isEmpty()) {
                for (String arg : queryArgs.split(",")) {
                    runner.addArg(exe.getData().get(arg));
                }
            }
        }
        String order = exe.getOrder();
        if (query.getOrder().length() > 0) {
            order += order.length() > 0 ? "," : CT.EMPTY;
            order += query.getOrder();
        }
        runner.order(order);
    }
    
    /**
     * 限制条件
     * @author sunqinqiu 
     * @date   2019-01-05 17:56
     * @param query
     * @param runner
     * @param exe
     */
    private void setRuannerLimit(DataQuery query, SQLRunner runner, DataQueryExecuter exe) {
        Map<String, DataQueryField> limitList = query.getLimits();
        for (String exeLimit : exe.getLimits()) {
            DataQueryField queryLimit = limitList.get(exeLimit);
            runner.where(queryLimit.getContent());
            String queryArgs = Convert.toString(queryLimit.getArgs());
            if (!queryArgs.isEmpty()) {
                for (String arg : queryArgs.split(",")) {
                    runner.addArg(exe.getData().get(arg));
                }
            }
        }
        // 第五步，其他条件
        runner.where(exe.getWhere());
        if (exe.getArgs().length() > 0) {
            for (String arg : exe.getArgs().split(",")) {
                runner.addArg(exe.getData().get(arg));
            }
        }
    }
    
}
