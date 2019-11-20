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
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lud.service.core.api.data.query.DataPager;
import com.lud.service.core.api.data.query.DataQueryExecuter;
import com.lud.service.core.api.data.query.DataQueryService;
import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.DateUtil;
import com.lud.util.content.util.text.CT;
import com.lud.util.web.api.context.ContextRequest;
import com.lud.util.web.api.view.ViewDataEntity;
import com.lud.util.web.core.RequestService;
import com.lud.util.web.view.data.condition.Condition;
import com.lud.util.web.view.data.condition.WebDataConditionService;

/**
 * @author sunqinqiu
 * @date 2019-02-24 21:55
 */
@Service("com.lud.web.util.view.data.webviewjdbcdataservice")
public class WebViewJdbcDataService implements IWebViewDataService {
    /**
     * 数据库服务
     */
    @Reference
    private DataQueryService        dataQueryService;
    
    /**
     * 
     */
    @Autowired
    private RequestService          requestService;
    
    /**
     * 自动条件
     */
    @Autowired
    private WebDataConditionService webDataConditionService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-24 04:32
     * @param data
     * @param request
     * @return
     */
    @Override
    public Serializable getData(ViewDataEntity data, ContextRequest request) {
        DataQueryExecuter exe = getDataQueryExecuter(data, request);
        Serializable result = null;
        String resultType = data.getConfig().get(CT.STRING_RESULT);
        switch (resultType) {
            case "map":
                result = (Serializable) dataQueryService.queryForMap(exe);
                break;
            case "value":
                result = dataQueryService.queryForValue(exe);
                break;
            case "int":
                result = dataQueryService.queryForInt(exe);
                break;
            case "page":
                DataPager pager = new DataPager(Convert.toInt(request.getData().get("pager-index")), Convert.toInt(request.getData().get("pager-size")));
                result = dataQueryService.queryForPager(exe, pager);
                break;
            default:
                result = (Serializable) dataQueryService.queryForList(exe);
                break;
        }
        return result;
    }
    
    /**
     * 获取数据
     * @author sunqinqiu 
     * @date   2019-02-26 22:56
     * @param data
     * @param request
     * @return
     */
    private Serializable getValueByContent(String type, Object value) {
        Serializable content = null;
        switch (type) {
            case "date":
                content = DateUtil.toDateTime(value, DateUtil.FORMAT_TYPE_DATE_FULL);
                break;
            case "time":
                content = DateUtil.toDateTime(value, DateUtil.FORMAT_TYPE_DATEANDTIME_FULL);
                break;
            case "int":
                content = Convert.toInt(value);
                break;
            case "double":
                content = Convert.toDouble(value);
                break;
            case "boolean":
                content = Convert.toBoolean(value);
                break;
            default:
                content = Convert.toString(value);
                break;
        }
        return content;
    }
    
    /**
     * 获取DataQuery
     * @author sunqinqiu 
     * @date   2019-11-05 16:02
     * @param data
     * @param request
     * @return
     */
    private DataQueryExecuter getDataQueryExecuter(ViewDataEntity data, ContextRequest request) {
        String query = data.getQuery();
        String fields = Content.getDefaultValue(data.getConfig().get("fields"), "def");
        StringBuilder limits = new StringBuilder(Content.getDefaultValue(data.getConfig().get("limits"), "def"));
        /**
         * 加上权限系统
         */
        String roles = Convert.toString(data.getConfig().get("roles"));
        if (!roles.isEmpty()) {
            List<String> canSelectRoles = Arrays.asList(roles.split(","));
            String[] menuDatas = request.getMenuDatas();
            for (String menuData : menuDatas) {
                if (canSelectRoles.contains(menuData)) {
                    limits.append((limits.length() > 0 ? "," : "")).append(menuData);
                }
            }
        }
        /**
         * 
         */
        DataQueryExecuter exe = new DataQueryExecuter(query, fields, limits.toString());
        int top = Convert.toInt(data.getConfig().get("top"));
        getArgs(exe, data, request);
        getWhere(exe, data, request);
        getOrder(exe, data, request);
        exe.top(top);
        return exe;
    }
    
    /**
     * 加上条件子句
     * @author sunqinqiu 
     * @date   2019-11-05 16:07
     * @param exe
     * @param data
     * @param request
     */
    private void getWhere(DataQueryExecuter exe, ViewDataEntity data, ContextRequest request) {
        
        StringBuilder whereBuilder = new StringBuilder();
        StringBuilder argsBuilder = new StringBuilder();
        /**
         * 配置信息中的where子句
         */
        String where = Convert.toString(data.getConfig().get("where"));
        if (!where.isEmpty()) {
            where = where.replace("{keys}", request.getData().get("keys").replace(",", "','"));
            whereBuilder.append(where);
        }
        /**
         * 自动条件
         */
        getAutowhere(exe, data, request, whereBuilder, argsBuilder);
        if (whereBuilder.length() > 0) {
            exe.addWhere(whereBuilder.toString(), argsBuilder.toString());
        }
    }
    
    /**
     * 自动条件
     * @author sunqinqiu 
     * @date   2019-11-07 11:36
     * @param exe
     * @param data
     * @param request
     * @param whereBuilder
     * @param argsBuilder
     */
    private void getAutowhere(DataQueryExecuter exe, ViewDataEntity data, ContextRequest request, StringBuilder whereBuilder, StringBuilder argsBuilder) {
        String query = data.getQuery();
        String jdbc = query.split("\\.")[0];
        boolean isAutoWhere = Convert.toBoolean(data.getConfig().get("autowhere"));
        if (isAutoWhere) {
            request.getData().forEach((key, value) -> {
                getCondition(exe, key, value, jdbc, whereBuilder, argsBuilder);
            });
        }
    }
    
    /**
     * 每个参数的处理
     * @author sunqinqiu 
     * @date   2019-11-07 11:39
     * @param exe
     * @param key
     * @param value
     * @param jdbc
     * @param whereBuilder
     * @param argsBuilder
     */
    private void getCondition(DataQueryExecuter exe, String key, String value, String jdbc, StringBuilder whereBuilder, StringBuilder argsBuilder) {
        String kvalue = Content.getSafeString(value);
        if (key.startsWith("w-") && !kvalue.isEmpty()) {
            String[] keys = key.split("-");
            if (keys.length >= 2) {
                Object[] args = new String[keys.length - 2];
                for (int i = 2; i < keys.length; i++) {
                    args[i - 2] = keys[i];
                }
                Condition condition = webDataConditionService.getCondition(jdbc, keys[1]);
                if (condition.isCparas()) {
                    whereBuilder.append(whereBuilder.length() > 0 ? " and " : "");
                    whereBuilder.append(MessageFormat.format(condition.getContent(), args));
                    argsBuilder.append(argsBuilder.length() > 0 ? "," : "");
                    argsBuilder.append(MessageFormat.format(condition.getArgs(), args));
                    for (Object arg : args) {
                        exe.addData(arg.toString(), getValueByContent(condition.getValueType(), MessageFormat.format(condition.getValue(), kvalue)));
                    }
                } else {
                    String content = condition.getContent();
                    if (content.contains("{v}")) {
                        content = content.replace("{v}", kvalue);
                    }
                    if (content.contains("{vs}")) {
                        content = content.replace("{vs}", kvalue.replace(",", "','"));
                    }
                    whereBuilder.append(MessageFormat.format(content, args));
                }
            }
        }
    }
    
    /**
     * 获取参数
     * @author sunqinqiu 
     * @date   2019-02-26 22:56
     * @param exe
     * @param data
     * @param request
     */
    private void getArgs(DataQueryExecuter exe, ViewDataEntity data, ContextRequest request) {
        /**
         * 添加系统级的一些参数
         */
        Map<String, Serializable> reqData = requestService.getDataByRequest(request);
        if (reqData != null) {
            exe.addAllData(reqData);
        }
        /**
         * 添加指定参数
         */
        String args = Convert.toString(data.getConfig().get("args"));
        if (args.length() > 0) {
            for (String arg : args.split(",")) {
                boolean keyIsValye = true;
                String[] argsc = new String[2];
                argsc[0] = arg;
                if (arg.contains(":")) {
                    argsc = arg.split(":");
                    argsc[1] = request.getData().get(argsc[1]);
                    keyIsValye = false;
                }
                if (arg.contains("=")) {
                    argsc = arg.split("=");
                    keyIsValye = false;
                }
                if (keyIsValye) {
                    argsc[1] = request.getData().get(argsc[0]);
                }
                exe.addData(argsc[0], argsc[1]);
            }
        }
    }
    
    /**
     * 获取排序
     * @author sunqinqiu 
     * @date   2019-02-26 23:09
     * @param exe
     * @param data
     * @param request
     */
    private void getOrder(DataQueryExecuter exe, ViewDataEntity data, ContextRequest request) {
        String order = Convert.toString(data.getConfig().get("order"));
        if (order.length() > 0) {
            exe.order(order);
        }
        String orderRequest = Convert.toString(request.getData().get("pager-order"));
        if (!orderRequest.isEmpty()) {
            exe.order(orderRequest);
        }
    }
}
