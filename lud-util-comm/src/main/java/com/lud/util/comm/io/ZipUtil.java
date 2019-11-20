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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CharacterContent;

/**
 * 解压缩文件
 * @author sunqinqiu 
 * @date   2018-05-09 23:40
 */
public final class ZipUtil {
    private static final Logger loger = LoggerFactory.getLogger(ZipUtil.class);
    
    /**
     * @author sunqinqiu
     * @version 2017年12月25日-下午5:25:36
     * @description 压缩
     * @param inputFilePath
     * @param outPutZip
     */
    public static void compression(String inputFilePath, String outPutZip) {
        String zipFileName = outPutZip;
        File inputFile = new File(inputFilePath);
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName)); BufferedOutputStream bo = new BufferedOutputStream(out)) {
            zip(out, inputFile, inputFile.getName(), bo);
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_ZIP_EN);
        }
    }
    
    /**
     * @author sunqinqiu
     * @version 2017年12月25日-下午5:30:39
     * @Description (解压)
     * @param zipPath zip路径
     * @param charset 编码
     * @param outPutPath 输出路径
     */
    public static void decompression(String zipPath, String charset, String outPutPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath), Charset.forName(charset)); BufferedInputStream bin = new BufferedInputStream(zin)) {
            String parent = outPutPath;
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                decompressionFile(parent, entry, bin);
            }
        } catch (IOException e) {
            loger.error(ErrorText.UTIL_ZIP_DE);
        }
    }
    
    /**
     * @author sunqinqiu
     * @version 2017年12月26日-上午12:09:39
     * @description description
     * @param fout
     * @param parent
     * @param entry
     * @param bin
     */
    private static void decompressionFile(String parent, ZipEntry entry, BufferedInputStream bin) {
        File fout = new File(parent, entry.getName());
        FileUtil.checkDir(fout);
        try (FileOutputStream out = new FileOutputStream(fout); BufferedOutputStream bout = new BufferedOutputStream(out)) {
            int b;
            while ((b = bin.read()) != -1) {
                bout.write(b);
            }
        } catch (IOException e) {
            loger.error(ErrorText.UTIL_ZIP_DE);
        }
    }
    
    /**
     * @author sunqinqiu
     * @version 2017年12月25日-下午5:30:39
     * @description 压缩文件
     * @param out
     * @param inputFile
     * @param base
     * @param bo
     */
    private static void zip(ZipOutputStream out, File inputFile, String base, BufferedOutputStream bo) {
        if (inputFile.isDirectory()) {
            zipDir(out, inputFile, base, bo);
        } else {
            zipFile(out, inputFile, base, bo);
        }
    }
    
    /**
     * 压缩文件夹
     * @author sunqinqiu 
     * @date   2019-09-11 14:10
     * @param out
     * @param inputFile
     * @param base
     * @param bo
     */
    private static void zipDir(ZipOutputStream out, File inputFile, String base, BufferedOutputStream bo) {
        File[] fl = inputFile.listFiles();
        try {
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + CharacterContent.PATH_SPLITER_S));
            }
            for (File element : fl) {
                zip(out, element, base + CharacterContent.PATH_SPLITER_S + element.getName(), bo);
            }
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_ZIP_EN);
        }
    }
    
    /**
     * 单个文件
     * @author sunqinqiu 
     * @date   2019-09-11 14:11
     * @param out
     * @param inputFile
     * @param base
     * @param bo
     */
    private static void zipFile(ZipOutputStream out, File inputFile, String base, BufferedOutputStream bo) {
        try (FileInputStream in = new FileInputStream(inputFile); BufferedInputStream bi = new BufferedInputStream(in);) {
            out.putNextEntry(new ZipEntry(base));
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b);
            }
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_ZIP_EN);
        }
    }
    
    private ZipUtil() {}
}
