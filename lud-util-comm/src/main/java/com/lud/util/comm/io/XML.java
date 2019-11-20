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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.type.EncodeType;

import lombok.Getter;

/**
 * XML操作类
 * @author sunqinqiu 
 * @date   2018-05-09 22:20
 */
public final class XML implements AutoCloseable {
    
    @Getter
    private String       path;
    private SAXReader    reader;
    
    @Getter
    private Document     document;
    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 从文件中构建XML文档
     */
    public XML(String xmlpath) {
        if (FileUtil.exists(xmlpath)) {
            try {
                this.path = xmlpath;
                reader = new SAXReader();
                document = reader.read(new File(this.path));
            } catch (Exception e) {
                document = null;
                loger.error(ErrorText.UTIL_XML_CREATE, e);
            }
        }
    }
    
    /**
     * 从网上获取XML文档
     */
    public XML(String url, String xmlpath) {
        try {
            this.path = xmlpath;
            reader = new SAXReader();
            document = reader.read(url);
        } catch (Exception e) {
            document = null;
            loger.error(ErrorText.UTIL_XML_CREATE, e);
        }
    }
    
    /**
     * 关闭文件
     * @author sunqinqiu 
     * @date   2018-05-09 22:47
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        this.reader = null;
    }
    
    /**
     * 判断是否存在该节点
     * @author sunqinqiu 
     * @date   2018-05-09 22:28
     * @param url
     * @return
     */
    public boolean existNode(String url) {
        if (document == null) { return false; }
        List<Node> list = document.selectNodes(url);
        return !list.isEmpty();
    }
    
    /**
     * 获取子节点的KEY-VALUE键值对
     * @author sunqinqiu 
     * @date   2018-05-09 22:35
     * @param url
     * @return
     */
    public Map<String, String> getChildrenKeyValue(String url) {
        Map<String, String> result = new HashMap<>();
        if (document == null) { return result; }
        if (!this.existNode(url)) { return result; }
        List<Element> items = ((Element) document.selectSingleNode(url)).elements();
        items.forEach(item -> result.put(item.getName(), item.getText()));
        return result;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-07 11:41
     * @param url
     * @return
     */
    public List<Map<String, String>> getChildrenKeyValues(String url) {
        List<Map<String, String>> result = new ArrayList<>();
        if (document == null) { return result; }
        if (!this.existNode(url)) { return result; }
        List<Node> list = document.selectNodes(url);
        list.forEach(node -> {
            Map<String, String> resultItem = new HashMap<>();
            ((Element) node).elements().forEach(item -> resultItem.put(item.getName(), item.getText()));
            result.add(resultItem);
        });
        return result;
    }
    
    /**
     * 获取某节点的键值对
     * @author sunqinqiu 
     * @date   2018-05-09 22:39
     * @param url
     * @param key
     * @param value
     * @return
     */
    public Map<String, String> getKeyValue(String url, String key, String value) {
        Map<String, String> config = new HashMap<>();
        getNodesAttributes(url, true).forEach(item -> config.put(item.get(key), item.get(value)));
        return config;
    }
    
    /**
     * 得到某个节点的属性
     * @author sunqinqiu 
     * @date   2018-05-09 22:29
     * @param node
     * @param getText
     * @return
     */
    public Map<String, String> getNodeAttributes(Node node, boolean getText) {
        if (node == null) { return null; }
        List<Attribute> nodeAttributes = ((Element) node).attributes();
        if (nodeAttributes == null) { return null; }
        Map<String, String> attributes = new HashMap<>();
        nodeAttributes.forEach(attribute -> attributes.put(attribute.getName(), attribute.getValue()));
        if (getText) {
            attributes.put(CT.STRING_TEXT, node.getText());
        }
        return attributes;
    }
    
    /**
     * 通过URL获取某个节点的属性
     * @author sunqinqiu 
     * @date   2018-05-09 22:31
     * @param url
     * @param getText
     * @return
     */
    public Map<String, String> getNodeAttributes(String url, boolean getText) {
        if (document == null) { return null; }
        Node node = document.selectSingleNode(url);
        if (node == null) { return null; }
        return getNodeAttributes(node, getText);
    }
    
    /**
     * 获取节点列表的属性
     * @author sunqinqiu 
     * @date   2018-05-09 22:32
     * @param elements
     * @param getText
     * @return
     */
    public List<Map<String, String>> getNodesAttributes(List<Node> nodes, boolean getText) {
        List<Map<String, String>> nodesAttributes = new ArrayList<>();
        if (nodes != null) {
            nodes.forEach(node -> nodesAttributes.add(getNodeAttributes(node, getText)));
        }
        return nodesAttributes;
    }
    
    /**
     * 通过地址获取某个节点的列表
     * @author sunqinqiu 
     * @date   2018-05-09 22:34
     * @param url
     * @param getText
     * @return
     */
    public List<Map<String, String>> getNodesAttributes(String url, boolean getText) {
        if (document == null) { return new ArrayList<>(); }
        List<Node> list = document.selectNodes(url);
        return getNodesAttributes(list, getText);
    }
    
    /**
     * 获取节点的值
     * @author sunqinqiu 
     * @date   2018-05-09 22:44
     * @param url
     * @return
     */
    public String getNodeText(String url) {
        if (document == null) { return CT.EMPTY; }
        Node node = document.selectSingleNode(url);
        if (node == null) { return CT.EMPTY; }
        return node.getText();
    }
    
    /**
     * 保存文件
     * @author sunqinqiu 
     * @date   2018-05-09 22:47
     */
    public void save() {
        save(this.path);
    }
    
    /**
     * 保存文件
     * @author sunqinqiu 
     * @date   2018-05-09 22:47
     * @param path
     */
    public void save(String path) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(EncodeType.ENCODE_UTF8);
        try {
            XMLWriter writer = new XMLWriter(new FileOutputStream(new File(path)), format);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_XML_SAVE, e);
        }
    }
}
