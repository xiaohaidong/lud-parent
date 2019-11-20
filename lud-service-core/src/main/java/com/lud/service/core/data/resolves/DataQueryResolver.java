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
package com.lud.service.core.data.resolves;

import java.util.List;
import java.util.Map;

import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lud.service.core.api.data.builder.SQLBuilderService;
import com.lud.service.core.api.data.query.DataQuery;
import com.lud.service.core.api.data.query.DataQueryField;
import com.lud.service.core.api.data.runner.SQLRunner;
import com.lud.service.core.api.redis.RedisService;
import com.lud.util.comm.io.XML;
import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.redis.node.RedisNode;
import com.lud.util.content.util.text.CT;

/**
 * @author sunqinqiu 
 * @date   2019-01-05 10:40
 */
@Service
public class DataQueryResolver {
    
    @Autowired
    @Qualifier("SQLBuilderServiceImp")
    private SQLBuilderService sqlBuilderService;
    @Autowired
    private RedisService      redisService;
    
    /**
     * 解析字段
     * @author sunqinqiu 
     * @date   2019-01-05 00:32
     * @param jdbcName
     * @param table
     * @param filedItem
     * @return
     */
    private String getFiled(String jdbcName, String table, Map<String, String> filedItem) {
        String type = Convert.toString(filedItem.get("type"));
        String text = Content.getDefaultValue(filedItem.get("text"), "{t}.*").trim();
        if ("c".equals(type)) {
            text = resolveChildSearch(jdbcName, table, text);
        }
        return text;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-01-05 10:49
     * @param xml
     * @param jdbcName
     * @param moudle
     * @param tableName
     * @param jdbcType
     */
    public void resolve(XML xml, String jdbcName, String moudle, String tableName, String jdbcType) {
        xml.getDocument().selectNodes("/configuration/querys/item").forEach(node -> {
            DataQuery query = new DataQuery();
            resolveDataQueryConfig(xml, node, query, jdbcName, tableName);
            resolveDataQueryFields(xml, node, query, jdbcType);
            resolveDataQueryLimits(xml, node, query, jdbcType);
            redisService.hset(RedisNode.REDIS_NODE_FOR_DATA_QUERY, query.getName(), JSON.toJSONString(query));
        });
    }
    
    /**
     * 解析子查询
     * @author sunqinqiu 
     * @date   2019-01-05 00:33
     * @param jdbcName
     * @param table
     * @param text
     * @return
     */
    private String resolveChildSearch(String jdbcName, String ptable, String text) {
        StringBuilder bulder = new StringBuilder();
        for (String item : text.split(";")) {
            String table = Content.getBetween(item, CT.EMPTY, "[");
            String fileds = Content.getBetween(item, "[", "]");
            String cross = Content.getBetween(item, "]", "=");
            String key = Content.getBetween(item, "=", ":");
            String where = Content.getBetween(item, ":", CT.EMPTY);
            key = where.isEmpty() ? Content.getBetween(item, "=", CT.EMPTY) : key;
            for (String filed : fileds.split(",")) {
                bulder.append(bulder.length() > 0 ? "," : CT.EMPTY);
                SQLRunner runner = new SQLRunner(jdbcName, table);
                runner.top(1).select(filed).where(cross, "=", ptable + "." + key).where(where);
                String keyAs = key + "_" + filed.replace("(", CT.EMPTY).replace(")", CT.EMPTY).replace("*", CT.EMPTY);
                bulder.append("(" + sqlBuilderService.getQuerySQL(runner) + ") as ").append(keyAs);
            }
        }
        return bulder.toString();
    }
    
    /**
     * 解析基础数据
     * @author sunqinqiu 
     * @date   2019-01-04 21:24
     * @param xml
     * @param query
     */
    private void resolveDataQueryConfig(XML xml, Node node, DataQuery query, String jdbcName, String tableName) {
        Map<String, String> item = xml.getNodeAttributes(node, false);
        query.setTable(Content.getDefaultValue(item.get("table"), tableName));
        query.setJdbc(Content.getDefaultValue(item.get("jdbc"), jdbcName));
        query.setName(query.getJdbc() + "." + query.getTable() + "_" + Content.getDefaultValue(item.get("name"), "query"));
        query.setKey(Content.getDefaultValue(item.get("key"), "id"));
        query.setGroup(Content.getDefaultValue(item.get("group"), CT.EMPTY));
        query.setOrder(Content.getDefaultValue(item.get("order"), query.getKey() + " desc"));
    }
    
    /**
     * 解析select字段
     * @author sunqinqiu 
     * @date   2019-01-04 21:25
     * @param xml
     * @param query
     */
    private void resolveDataQueryFields(XML xml, Node node, DataQuery query, String jdbcType) {
        List<Node> filedList = node.selectNodes("fileds/filed");
        if (filedList == null) {
            query.getFields().put("def", new DataQueryField("{t}.*", ""));
        }
        xml.getNodesAttributes(filedList, true).forEach(filedItem -> {
            String forType = Convert.toString(filedItem.get("for"));
            if (forType.equals("") || forType.contains(jdbcType)) {
                DataQueryField filed = new DataQueryField(getFiled(query.getJdbc(), query.getTable(), filedItem), Convert.toString(filedItem.get("args")));
                query.getFields().put(filedItem.get("name"), filed);
            }
        });
    }
    
    /**
     * 解析Limit
     * @author sunqinqiu 
     * @date   2019-01-04 21:25
     * @param xml
     * @param query
     */
    private void resolveDataQueryLimits(XML xml, Node node, DataQuery query, String jdbcType) {
        List<Node> limitList = node.selectNodes("limits/limit");
        if (limitList == null) {
            query.getLimits().put("def", new DataQueryField(" 1=1 ", ""));
        }
        xml.getNodesAttributes(limitList, true).forEach(limitItem -> {
            String forType = Convert.toString(limitItem.get("for"));
            if (forType.equals("") || forType.contains(jdbcType)) {
                DataQueryField limit = new DataQueryField(limitItem.get("text"), limitItem.get("args"));
                query.getLimits().put(limitItem.get("name"), limit);
            }
        });
    }
}
