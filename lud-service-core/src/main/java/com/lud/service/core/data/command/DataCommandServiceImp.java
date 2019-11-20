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
package com.lud.service.core.data.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.lud.service.core.api.data.command.DataCommand;
import com.lud.service.core.api.data.command.DataCommandService;
import com.lud.service.core.api.data.jdbc.JDBCService;
import com.lud.service.core.api.data.resolves.DataResolverManager;
import com.lud.util.comm.util.Content;

/**
 * @author sunqinqiu 
 * @date   2019-03-03 15:59
 */
@Service
public class DataCommandServiceImp implements DataCommandService {
    @Autowired
    private DataResolverManager dataResolverManager;
    @Autowired
    private JDBCService         jdbcService;
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-03 16:00
     * @param name
     * @param flag
     * @param args
     * @return
     */
    @Override
    public Map<String, Serializable> exe(String name, Map<String, Serializable> args) {
        Map<String, Serializable> result = new HashMap<>();
        DataCommand command = dataResolverManager.getDataCommand(name);
        command.getItems().forEach(item -> {
            List<Serializable> argList = new ArrayList<>();
            for (String arg : item.getArgs().split(",")) {
                argList.add(args.get(arg));
            }
            String cmd = item.getCommand();
            String action = Content.getBetween(cmd, "", " ");
            String itemName = item.getName();
            if (action.equals("insert") && item.isAutoid()) {
                args.put(itemName + ".autoid", jdbcService.insertIdentity(item.getJdbc(), item.getCommand(), argList));
            } else {
                args.put(itemName + ".result", jdbcService.update(item.getJdbc(), item.getCommand(), argList));
            }
        });
        return result;
    }
    
}
