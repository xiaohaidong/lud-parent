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

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.CT;

/**
 * 随机数
 * @author sunqinqiu 
 * @date   2018-05-06 20:37
 */
public final class RandomUtil {
    private static final char[] CHARS_ALL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    
    private static final Random RANDOM    = new SecureRandom();
    
    /**
     * @author sunqinqiu
     * @version 2017年12月23日-下午8:42:02
     * @description 生成随机数种子
     * @return
     */
    public static String generatePBESalt() {
        return getRandom(8);
    }
    
    /**
     * @author sunqinqiu
     * @version 2017年12月23日-下午11:00:54
     * @description 获取一个不同长度的字符串
     * @param length
     * @return
     */
    public static String getRandom(int length) {
        byte[] salt = new byte[length];
        RANDOM.nextBytes(salt);
        return Transcode.byteToBase64(salt);
    }
    
    /**
     * 获取一个随机字符串
     * @author sunqinqiu
     * @version 2017年12月23日-下午11:08:28
     * @description description
     * @param length
     * @return
     */
    public static String getRandomChar(int length) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            buffer.append(CHARS_ALL[RANDOM.nextInt(62)]);
        }
        return buffer.toString();
    }
    
    /**
     * 获取UUID
     * @author sunqinqiu
     * @version 2017年12月26日-下午8:37:11
     * @description 获取UID
     * @return
     */
    public static String getUID() {
        return UUID.randomUUID().toString().replaceAll(CharacterContent.PATH_SPLITER_M, CT.EMPTY).substring(0, 32).toUpperCase();
    }
    
    private RandomUtil() {}
}
