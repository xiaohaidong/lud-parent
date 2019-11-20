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
package com.lud.util.comm.io;

import com.lud.util.comm.server.ServerResource;
import com.lud.util.comm.util.Convert;
import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.XMLNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunqinqiu 
 * @date   2018-12-31 20:17
 */
public class XMLUtil {
    private static final Logger loger = LoggerFactory.getLogger(XMLUtil.class);
    
    /**
     * 解析/configuration/item
     * @author sunqinqiu 
     * @date   2018-12-31 23:02
     * @param path
     * @return
     */
    public static Map<String, String> resolveConfig(String path) {
        return resolveConfig(path, false);
    }
    
    /**
     * 解析配置文件
     * @author sunqinqiu 
     * @date   2019-01-09 00:12
     * @param path
     * @param isAbsPath
     * @return
     */
    public static Map<String, String> resolveConfig(String path, boolean isAbsPath) {
        String xmlpath = isAbsPath ? path : ServerResource.getAbsolutePath(path);
        try (XML xml = new XML(xmlpath)) {
            return xml.getKeyValue(XMLNode.CONFIG_ITEM, CT.STRING_NAME, CT.STRING_TEXT);
        } catch (Exception e) {
            loger.info(ErrorText.UTIL_XML_GET, e);
            return new HashMap<>();
        }
    }
    
    /**
     * 解析带有group(:root)/item类型的XML文件
     * @author sunqinqiu 
     * @date   2018-12-31 20:19
     * @param path
     * @return
     */
    public static Map<String, String> resolveRootConfigByGroup(String path) {
        Map<String, String> result = new HashMap<>();
        //loger.trace("path:"+path);
        String xmlpath = ServerResource.getAbsolutePath(path);
        //loger.trace("xmlfile:"+xmlpath);
        try (XML xml = new XML(xmlpath)) {
            xml.getDocument().selectNodes(XMLNode.CONFIG_GROUP).forEach(node -> {
                Map<String, String> groupInfo = xml.getNodeAttributes(node, false);
                String groupName = groupInfo.get(CT.STRING_NAME);
                String groupRoot = Convert.toString(groupInfo.get(CT.STRING_ROOT));
                xml.getNodesAttributes(node.selectNodes(CT.STRING_ITEM), true).forEach(item -> result.put(groupName + CharacterContent.PATH_SPLITER_SUFFIX + item.get(CT.STRING_NAME), groupRoot + item.get(CT.STRING_TEXT)));
            });
        } catch (Exception e) {
            loger.info(ErrorText.UTIL_XML_GET, e);
        }
        return result;
    }
    
    private XMLUtil() {}
}
