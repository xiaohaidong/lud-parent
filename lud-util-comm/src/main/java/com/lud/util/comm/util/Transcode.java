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
package com.lud.util.comm.util;

import java.lang.Character.UnicodeBlock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.type.EncodeType;

/**
 * 编码转换
 * @author sunqinqiu 
 * @date   2018-05-06 20:38
 */
public final class Transcode {
    private static final Logger   loger      = LoggerFactory.getLogger(DateUtil.class);
    protected static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    
    /**
     * base64转Byte
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static byte[] base64ToByte(String content) {
        return Base64.decodeBase64(getByte(content));
    }
    
    /**
     * Base64转字符串
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static String base64Tostring(String content) {
        return getString(base64ToByte(content));
    }
    
    /**
     * 将二进制字符串转换成int数组
     * @author sunqinqiu
     * @param content
     * @return
     */
    private static int[] binstrToIntArray(String content) {
        char[] temp = content.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }
    
    /**
     * 将二进制字符串转换为char
     * @author sunqinqiu
     * @param content
     * @return
     */
    private static char binToChar(String content) {
        int[] temp = binstrToIntArray(content);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }
    
    /**
     * 将二进制字符串转换成Unicode字符串
     * @author sunqinqiu
     * @param content 
     * @return
     */
    public static String binToString(String content) {
        String[] tempStr = stringToStrArray(content);
        char[] tempChar = new char[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            tempChar[i] = binToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }
    
    /**
     * BYTE 转Base64
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static String byteToBase64(byte[] content) {
        return Base64.encodeBase64String(content);
    }
    
    /**
     * BYTE转十六进制
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static String byteToHex(byte[] content) {
        final StringBuilder buf = new StringBuilder(content.length * 2);
        for (byte element : content) {
            buf.append(HEX_DIGITS[(element >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[element & 0x0f]);
        }
        return buf.toString();
    }
    
    /**
     * 将一个base64字符数组解码成一个字节数组
     * @author sunqinqiu
     * @param data base64字符数组
     * @return 返回解码以后的字节数组
     */
    public static byte[] decode(byte[] data) {
        return Base64.decodeBase64(data);
    }
    
    /**
     * 将一个字节数组转换成base64的字符数组
     * @author sunqinqiu
     * @param data 字节数组
     * @return base64字符数组
     */
    public static byte[] encode(byte[] data) {
        return Base64.encodeBase64(data);
    }
    
    /**
     * 指定编码为UTF-8的转字节
     * @author sunqinqiu 
     * @date   2018-05-06 21:19
     * @param content
     * @return
     */
    public static byte[] getByte(String content) {
        return getByte(content, EncodeType.ENCODE_UTF8);
    }
    
    /**
    * 指定编码为UTF-8的转字节
    * @author sunqinqiu 
    * @date   2018-05-06 22:34
    * @param content
    * @param encode
    * @return
    */
    public static byte[] getByte(String content, String encode) {
        try {
            return content.getBytes(encode);
        } catch (Exception ex) {
            loger.error(ErrorText.UTIL_TRANSCODE_ERR_GETBYTE, ex);
            return new byte[0];
        }
    }
    
    /**
     * 字节转字符串
     * @author sunqinqiu 
     * @date   2018-05-06 22:35
     * @param content
     * @return
     */
    public static String getString(byte[] content) {
        return getString(content, EncodeType.ENCODE_UTF8);
    }
    
    /**
     * 字节转字符串
     * @author sunqinqiu 
     * @date   2018-05-06 22:35
     * @param content
     * @param encode
     * @return
     */
    public static String getString(byte[] content, String encode) {
        try {
            return new String(content, encode);
        } catch (Exception ex) {
            loger.error(ErrorText.UTIL_TRANSCODE_ERR_STRING, ex);
            return CT.EMPTY;
        }
    }
    
    /**
     * 把字符串转换为十六进制BYTE
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static byte[] hexToByte(String content) {
        byte[] encrypted = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);// 取高位字节
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);// 取低位字节
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }
    
    /**
     * 十六进制转字符串
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static String hexToString(String content) {
        return getString(hexToByte(content));
    }
    
    /**
     * 字符串转换为Base64
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static String stringToBase64(String content) {
        return byteToBase64(getByte(content));
    }
    
    /**
     * 将字符串转换成二进制字符串，以空格相隔
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static String stringToBin(String content) {
        char[] chars = content.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : chars) {
            result.append(Integer.toBinaryString(c));
            result.append(CT.SPACE);
        }
        return result.toString();
    }
    
    /**
     * 字符串转16进制数据
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static String stringToHex(String content) {
        return byteToHex(getByte(content));
    }
    
    /**
     * 将初始二进制字符串转换成字符串数组，以空格相隔
     * @author sunqinqiu
     * @param content
     * @return
     */
    private static String[] stringToStrArray(String content) {
        return content.split(CT.SPACE);
    }
    
    /**
     * 将字符串转换成unicode码
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static String stringToUnicode(String content) {
        StringBuilder buffer = new StringBuilder();
        char[] chars = content.toCharArray();
        for (char curChar : chars) {
            if (UnicodeBlock.of(curChar) == UnicodeBlock.BASIC_LATIN) {
                buffer.append(curChar);
            } else if (curChar > 255) {
                buffer.append("\\u" + Integer.toHexString(curChar));
            } else {
                buffer.append("\\" + Integer.toHexString(curChar));
            }
        }
        return buffer.toString();
    }
    
    /**
     * 把字符从一种编码转换为另外一种编码
     * @author sunqinqiu
     * @param content 需要转换的数据
     * @param encodeFrom 本身的数据编码
     * @param encodeTo 需要转换为的数据编码
     * @return
     */
    public static String trans(String content, String encodeFrom, String encodeTo) {
        try {
            return new String(content.getBytes(encodeFrom), encodeTo);
        } catch (Exception ex) {
            loger.error(ErrorText.UTIL_TRANSCODE_ERR_TRANS, ex);
            return content;
        }
    }
    
    /**
     * unicode转为字符串
     * @author sunqinqiu
     * @param content
     * @return
     */
    public static String unicodeToString(String content) {
        Matcher matcher = Pattern.compile("(\\\\u(\\p{XDigit}{4}))").matcher(content);
        while (matcher.find()) {
            content = content.replace(matcher.group(1), (char) Integer.parseInt(matcher.group(2), 16) + CT.EMPTY);
        }
        return content;
    }
    
    private Transcode() {
        
    }
}
