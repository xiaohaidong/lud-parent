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
package com.lud.util.comm.runtime;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.JVMText;

/**
 * 硬件相关信息
 * @author sunqinqiu 
 * @date   2018-05-06 23:18
 */
public final class Hardware {
    private static final Logger loger = LoggerFactory.getLogger(Hardware.class);
    
    /**
     * 获取计算机名称
     * @author sunqinqiu 
     * @date   2018-09-08 17:54
     * @return
     */
    public static String getBindMac() {
        return getMacList().get(0).get(JVMText.MAC);
    }
    
    /**
     * 获取网卡地址
     * @author sunqinqiu
     * @param bytes
     * @return
     */
    private static String getMacAddressByByte(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bytes != null) {
            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(i == 0 ? CT.EMPTY : CharacterContent.PATH_SPLITER_M);
                String str = Integer.toHexString(bytes[i] & 0xff);
                stringBuilder.append(str.length() == 1 ? CT.STRING_NUM_0 : CT.EMPTY).append(str);
            }
        }
        return stringBuilder.toString().toUpperCase();
    }
    
    /**
     * 获取单个网卡信息
     * @author sunqinqiu
     * @param networkInterface
     * @return
     */
    
    private static Map<String, String> getMacInfo(NetworkInterface networkInterface) {
        try {
            if (networkInterface != null) {
                String mac = getMacAddressByByte(networkInterface.getHardwareAddress());
                if (!mac.isEmpty()) {
                    Map<String, String> item = new HashMap<>();
                    item.put(CT.STRING_NAME, networkInterface.getName());
                    item.put(CT.STRING_INDEX, networkInterface.getIndex() + CT.EMPTY);
                    item.put(JVMText.MAC, mac);
                    item.put(JVMText.DISPLAY_NAME, networkInterface.getDisplayName());
                    item.put(JVMText.IS_VIRTUAL, networkInterface.isVirtual() + CT.EMPTY);
                    item.put(JVMText.MTU, networkInterface.getMTU() + CT.EMPTY);
                    item.put(JVMText.IS_PTP, networkInterface.isPointToPoint() + CT.EMPTY);
                    return item;
                }
            }
        } catch (Exception e) {
            loger.error(e.toString());
        }
        return null;
    }
    
    /**
     * 获取网卡列表
     * @author sunqinqiu
     * @return
     */
    public static List<Map<String, String>> getMacList() {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                Map<String, String> item = getMacInfo(enumeration.nextElement());
                if (item != null) {
                    list.add(item);
                }
            }
        } catch (Exception e) {
            loger.error(e.toString());
        }
        return list;
    }
    
    /**
     * 获取当前系统
     * @author sunqinqiu 
     * @date   2018-09-13 12:16
     * @return
     */
    public static String getOS() {
        Properties props = System.getProperties();
        return props.getProperty(JVMText.OS_NAME);
    }
    
    /**
     * 构造函数
     */
    private Hardware() {}
}
