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
package com.lud.util.web.util.view;

import java.util.Map;

import org.dom4j.Node;
import org.springframework.stereotype.Service;

import com.lud.util.comm.io.XML;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.util.text.CT;
import com.lud.util.web.api.view.ViewCommandEntity;
import com.lud.util.web.api.view.ViewServiceEntity;

/**
 * @author sunqinqiu 
 * @date   2019-03-03 11:42
 */
@Service
public class WebViewCommandService {
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-03-03 17:05
     * @param xml
     * @param node
     * @param view
     */
    
    public void resolveCommadNode(XML xml, Node node, ViewServiceEntity view) {
        node.selectNodes(CT.STRING_COMMAND).forEach(cmdItem -> {
            Map<String, String> cmdMap = xml.getNodeAttributes(cmdItem, true);
            ViewCommandEntity cmd = new ViewCommandEntity();
            cmd.setName(cmdMap.remove(CT.STRING_NAME));
            cmd.setFlag(cmdMap.remove(CT.STRING_FLAG));
            cmd.setArgs(cmdMap.remove(CT.STRING_ARGS));
            cmd.setInvoke(cmdMap.remove(CT.STRING_INVOKE));
            cmd.setList(Convert.toBoolean(cmdMap.remove(CT.STRING_ISLIST)));
            cmd.setDataName(cmdMap.remove(CT.STRING_DATANAME));
            cmd.setAction(cmdMap.remove(CT.STRING_TEXT));
            cmd.setConfig(cmdMap);
            view.getCommands().add(cmd);
        });
    }
    
}
