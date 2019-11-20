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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lud.util.comm.util.Convert;
import com.lud.util.comm.util.DEncrypt;
import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.HTMLText;
import com.lud.util.content.util.text.IOContent;
import com.lud.util.content.util.type.EncodeType;

/**
 * XML操作
 * @author sunqinqiu 
 * @date   2019-09-14 02:57
 */
public class W3CXML {
    private static final Logger loger = LoggerFactory.getLogger(W3CXML.class);
    
    /**
     * 生成MD5XML
     * @author sunqinqiu 
     * @date   2019-09-14 03:21
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String generateSignedXml(final Map<String, String> data, String key) {
        return generateSignedXml(data, key, DEncrypt.ALGORITHM_MD5);
    }
    
    /**
     * 生成带有 sign 的 XML 格式字符串
     * @param data Map类型数据
     * @param key API密钥
     * @param signType 签名类型
     * @return 含有sign字段的XML
     * @throws Exception 
     */
    public static String generateSignedXml(final Map<String, String> data, String key, String signType) {
        String sign = DEncrypt.generateSignature(data, key, signType);
        data.put(DEncrypt.FIELD_SIGN, sign);
        return mapToXML(data);
    }
    
    /**
     * 验证XML
     * @author sunqinqiu 
     * @date   2019-09-14 03:22
     * @param xmlStr
     * @param key
     * @return
     * @throws Exception
     */
    public static boolean isSignatureValid(String xml, String key) {
        Map<String, String> data = xmlToMap(xml);
        if (!data.containsKey(DEncrypt.FIELD_SIGN)) { return false; }
        String sign = data.get(DEncrypt.FIELD_SIGN);
        return DEncrypt.generateSignature(data, key).equals(sign);
    }
    
    /**
     * MAP转XML
     * @author sunqinqiu 
     * @date   2019-09-14 03:02
     * @param data
     * @return
     * @throws Exception
     */
    public static String mapToXML(Map<String, String> data) {
        try (StringWriter writer = new StringWriter();) {
            Document document = newDocument();
            Element root = document.createElement(IOContent.SUFFIX_XML);
            document.appendChild(root);
            for (Entry<String, String> entry : data.entrySet()) {
                Element filed = document.createElement(entry.getKey());
                filed.appendChild(document.createTextNode(Convert.toString(entry.getValue()).trim()));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, EncodeType.ENCODE_UTF8);
            transformer.setOutputProperty(OutputKeys.INDENT, CT.STRING_YES);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            return writer.getBuffer().toString();
        } catch (Exception ex) {
            return CT.EMPTY;
        }
    }
    
    /**
     * 来源于WXPAY
     * @author sunqinqiu 
     * @date   2019-09-14 01:13
     * @return
     * @throws ParserConfigurationException
     */
    public static Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }
    
    /**
     * 来源于WXPAY
     * @author sunqinqiu 
     * @date   2019-09-14 01:13
     * @return
     * @throws ParserConfigurationException
     */
    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature(HTMLText.DOCTYPE_DECL, true);
        documentBuilderFactory.setFeature(HTMLText.DOCTYPE_GENERAL, false);
        documentBuilderFactory.setFeature(HTMLText.DOCTYPE_PARAM, false);
        documentBuilderFactory.setFeature(HTMLText.DOCTYPE_DTD, false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);
        return documentBuilderFactory.newDocumentBuilder();
    }
    
    /**
     * XML转MAP
     * @author sunqinqiu 
     * @date   2019-09-14 02:52
     * @param strXML
     * @return
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String xml) {
        Map<String, String> data = new HashMap<>();
        try (InputStream stream = new ByteArrayInputStream(xml.getBytes(EncodeType.ENCODE_UTF8))) {
            Document doc = newDocumentBuilder().parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
        } catch (Exception ex) {
            loger.warn(ErrorText.XML_ERROR, ex.getMessage(), xml);
        }
        return data;
    }
    
    private W3CXML() {}
}
