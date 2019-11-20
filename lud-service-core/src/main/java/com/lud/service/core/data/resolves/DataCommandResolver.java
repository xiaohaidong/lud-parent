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
import com.lud.service.core.api.data.command.DataCommand;
import com.lud.service.core.api.data.command.DataCommandItem;
import com.lud.service.core.api.redis.RedisService;
import com.lud.service.core.data.jdbc.JDBCFactory;
import com.lud.util.comm.io.XML;
import com.lud.util.comm.util.Content;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.redis.node.RedisNode;

/**
 * @author sunqinqiu 
 * @date   2019-03-03 12:16
 */
@Service
public class DataCommandResolver {
    @Autowired
    @Qualifier("SQLBuilderServiceImp")
    private SQLBuilderService sqlBuilderService;
    @Autowired
    private RedisService      redisService;
    /**
     * JDBC
     */
    @Autowired
    private JDBCFactory       jdbcFactory;
    
    /**
     * 解析数据
     * @author sunqinqiu 
     * @date   2019-03-03 12:30
     * @param xml
     * @param jdbcName
     * @param moudle
     * @param tableName
     * @param jdbcType
     */
    public void resolve(XML xml, String jdbcName, String moudle, String tableName, String jdbcType) {
        xml.getDocument().selectNodes("/configuration/commands/item").forEach(node -> {
            DataCommand command = new DataCommand();
            resolveDataCommandConfig(xml, node, command, jdbcName, tableName);
            redisService.hset(RedisNode.REDIS_NODE_FOR_DATA_COMMAND, command.getName(), JSON.toJSONString(command));
        });
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-03 12:48
     * @param xml
     * @param node
     * @param command
     * @param jdbcName
     * @param tableName
     */
    private void resolveDataCommandConfig(XML xml, Node node, DataCommand command, String jdbcName, String tableName) {
        Map<String, String> item = xml.getNodeAttributes(node, false);
        command.setName(jdbcName + "." + tableName + "_" + Content.getDefaultValue(item.get("name"), "cmd"));
        List<Node> cmdList = node.selectNodes("cmd");
        if (cmdList != null) {
            xml.getNodesAttributes(cmdList, true).forEach(cmdItem -> {
                String forType = Convert.toString(cmdItem.get("for"));
                String iJdbcName = Content.getDefaultValue(cmdItem.get("jdbc"), jdbcName);
                String jdbcType = this.jdbcFactory.getJDBC(iJdbcName).getType();
                if (forType.equals("") || forType.contains(jdbcType)) {
                    DataCommandItem dataCommandItem = new DataCommandItem();
                    dataCommandItem.setName(Convert.toString(cmdItem.get("name")));
                    dataCommandItem.setAutoid(Convert.toBoolean(cmdItem.get("autoid")));
                    dataCommandItem.setArgs(Convert.toString(cmdItem.get("args")));
                    dataCommandItem.setJdbc(iJdbcName);
                    dataCommandItem.setCommand(cmdItem.get("text").replace("{t}", tableName));
                    command.getItems().add(dataCommandItem);
                }
            });
        }
    }
}
