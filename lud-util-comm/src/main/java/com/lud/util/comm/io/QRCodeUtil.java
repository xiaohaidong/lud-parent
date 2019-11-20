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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.lud.util.comm.util.Transcode;
import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.IOContent;
import com.lud.util.content.util.type.EncodeType;

/**
 * 各种图形码
 * @author sunqinqiu 
 * @date   2018-05-09 23:51
 */
public final class QRCodeUtil {
    private static final int    BLACK = 0xFF000000;
    private static final int    WHITE = 0xFFFFFFFF;
    private static final Logger loger = LoggerFactory.getLogger(QRCodeUtil.class);
    
    /**
     * 获取条形码的数据
     * @author sunqinqiu 
     * @date   2018-05-10 00:10
     * @param code
     * @param format
     * @param width
     * @param height
     * @return
     */
    private static BitMatrix getBitMatrix(String code, BarcodeFormat format, int width, int height) {
        EnumMap<EncodeHintType, String> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, EncodeType.ENCODE_UTF8);
        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(Transcode.trans(code, EncodeType.ENCODE_UTF8, EncodeType.ENCODE_ISO_8859_1), format, width, height, hints);
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_QRCODE, e);
        }
        return matrix;
    }
    
    /**
     * 得到图片
     * @author sunqinqiu 
     * @date   2018-05-10 00:09
     * @param matrix
     * @return
     */
    private static BufferedImage getQRCodeImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
    
    /**
     * 写入文件
     * @author sunqinqiu 
     * @date   2018-05-10 00:10
     * @param matrix
     * @param format
     * @param file
     */
    private static void writeToFile(BitMatrix matrix, String format, File file) {
        try {
            BufferedImage image = getQRCodeImage(matrix);
            if (!ImageIO.write(image, format, file)) {
                loger.error(ErrorText.QRCODE_FORMATERROR, format, file);
            }
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_QRCODE, e);
        }
    }
    
    /**
     * 写入文件
     * @author sunqinqiu 
     * @date   2018-05-10 00:10
     * @param code
     * @param format
     * @param path
     * @param width
     * @param height
     */
    public static void writeToFile(String code, BarcodeFormat format, String path, int width, int height) {
        File file = new File(path);
        FileUtil.checkDir(file);
        BitMatrix bitMatrix = getBitMatrix(code, format, width, height);
        writeToFile(bitMatrix, IOContent.SUFFIX_PNG, file);
    }
    
    /**
     * 写入到流
     * @author sunqinqiu 
     * @date   2018-05-10 00:19
     * @param matrix
     * @param format
     * @param stream
     * @throws IOException
     */
    private static void writeToStream(BitMatrix matrix, String format, OutputStream stream) {
        try {
            BufferedImage image = getQRCodeImage(matrix);
            if (!ImageIO.write(image, format, stream)) {
                loger.error(ErrorText.QRCODE_FORMATERROR, format);
            }
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_QRCODE, e);
        }
    }
    
    /**
     * 写入到流
     * @author sunqinqiu 
     * @date   2018-05-10 00:22
     * @param code
     * @param format
     * @param stream
     * @param width
     * @param height
     */
    public static void writeToStream(String code, BarcodeFormat format, OutputStream stream, int width, int height) {
        BitMatrix bitMatrix = getBitMatrix(code, format, width, height);
        writeToStream(bitMatrix, IOContent.SUFFIX_PNG, stream);
    }
    
    private QRCodeUtil() {}
}
