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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.comm.util.Transcode;
import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CT;

/**
 * 图片管理
 * @author sunqinqiu 
 * @date   2018-09-07 17:11
 */
public class PictureUtil {
    private static final Logger loger = LoggerFactory.getLogger(PictureUtil.class);
    
    /**
     * 获取Base64
     * @author sunqinqiu 
     * @date   2018-09-07 17:14
     * @param path
     * @return
     */
    public static String getBase64(String path) {
        byte[] data = null;
        try (InputStream inputStream = new FileInputStream(path)) {
            data = new byte[inputStream.available()];
            if ((inputStream.read(data)) > 0) { return Transcode.byteToBase64(data); }
            return CT.EMPTY;
        } catch (IOException e) {
            loger.error(ErrorText.UTIL_PICTURE_ENCODE, e);
            return CT.EMPTY;
        }
    }
    
    /**
     * 获取图片
     * @author sunqinqiu 
     * @date   2018-09-07 17:14
     * @param path
     * @return
     */
    public static boolean getPicByBase64(String img, String path) {   // 对字节数组字符串进行Base64解码并生成图片
        if (img == null) { return false; }
        try (OutputStream out = new FileOutputStream(path)) {
            byte[] b = Transcode.base64ToByte(img);
            out.write(b);
            return true;
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_PICTURE_DECODE, e);
            return false;
        }
    }
    
    private PictureUtil() {}
}
