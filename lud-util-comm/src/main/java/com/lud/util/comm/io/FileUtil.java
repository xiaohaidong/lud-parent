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

import com.lud.util.comm.util.DateUtil;
import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.text.CharacterContent;
import com.lud.util.content.util.text.IOContent;
import com.lud.util.content.util.type.EncodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * 对文件的操作
 * @author sunqinqiu 
 * @date   2018-05-07 00:24
 */
public final class FileUtil {
    private static final Logger loger = LoggerFactory.getLogger(FileUtil.class);
    
    /**
     * 追加文件内容
     * @author sunqinqiu
     * @param path
     * @param content
     * @return
     */
    public static boolean append(String path, String content) {
        return append(path, content, EncodeType.ENCODE_UTF8);
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-17 20:56
     * @param path
     * @param content
     * @param encode
     * @return
     */
    public static boolean append(String path, String content, String encode) {
        File file = new File(path);
        mkDir(file.getParent());
        try (FileOutputStream stream = new FileOutputStream(path, true); OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream, encode)) {
            outputStreamWriter.write(content);
            return true;
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_IO_FILE_APPEND, e);
            return false;
        }
    }
    
    /**
     * 检查目录，目录不存在则创建该目录
     * @author sunqinqiu
     * @param file
     */
    public static void checkDir(File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }
    
    /**
     * 检查目录
     * @author sunqinqiu 
     * @date   2018-05-16 18:17
     * @param filePath
     */
    public static void checkDir(String filePath) {
        checkDir(new File(filePath));
    }
    
    /**
     * 复制文件
     * @author sunqinqiu
     * @param from
     * @param to
     * @return
     * @throws IOException
     */
    public static boolean copy(String from, String to) {
        File fileFrom = new File(from);
        if (!fileFrom.exists()) { return false; }
        File toFile = new File(to);
        checkDir(toFile);
        try (InputStream inputStream = new FileInputStream(fileFrom); FileOutputStream fileOutputStream = new FileOutputStream(toFile)) {
            byte[] buffer = new byte[1444];
            int byteRead;
            while ((byteRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            return true;
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_IO_FILE_COPY, e);
        }
        return false;
    }
    
    /**
     * 复制文件夹
     * @author sunqinqiu 
     * @date   2019-01-07 01:46
     * @param from
     * @param to
     */
    public static void copyDir(String from, String to) {
        try {
            File fileForm = new File(from);
            File fileTo = new File(to);
            if (!fileTo.exists()) {
                fileTo.mkdirs();
            }
            for (File file : fileForm.listFiles()) {
                if (file.isFile()) {
                    copy(file.getPath(), to + CharacterContent.PATH_SPLITER + file.getName());
                }
                if (file.isDirectory()) {
                    copyDir(file.getPath(), to + CharacterContent.PATH_SPLITER + file.getName());
                }
            }
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_IO_FILE_COPYDIR, e);
        }
    }
    
    /**
     * 添加新文件
     * @author sunqinqiu
     * @param path
     * @param content
     * @return
     */
    public static boolean creat(String path, String content) {
        delete(new File(path));
        return append(path, content);
    }
    
    /**
     * 删除文件
     * @author sunqinqiu
     * @param file
     * @return
     */
    public static boolean delete(File file) {
        try {
            if (!file.exists()) { return false; }
            if (file.isFile()) { return java.nio.file.Files.deleteIfExists(file.toPath()); }
            for (File _file : file.listFiles()) {
                delete(_file);
            }
            return java.nio.file.Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            loger.error(e.toString());
            return false;
        }
    }
    
    /**
     * 检查文件是否存在
     * @author sunqinqiu 
     * @date   2018-05-16 18:19
     * @param path
     * @return
     */
    public static boolean exists(String path) {
        return new File(path).exists();
    }
    
    /**
     * 获取文件的后缀名
     * @author sunqinqiu 
     * @date   2019-01-01 11:36
     * @param path
     * @return
     */
    public static String getLastName(String path) {
        path = path.replace(CharacterContent.PATH_SPLITER, CharacterContent.PATH_SPLITER_S);
        int b = path.lastIndexOf(CharacterContent.CHAR_POINT);
        if (b > 0) { return path.substring(b + 1).toLowerCase().trim(); }
        return IOContent.FILE_N_TEXT;
    }
    
    /**
     * 获取文件名
     * @author autism
     * @param name
     */
    public static String getName(String path) {
        path = path.replace(CharacterContent.PATH_SPLITER, CharacterContent.PATH_SPLITER_S);
        int a = path.lastIndexOf(CharacterContent.CHAR_LINE);
        int b = path.lastIndexOf(CharacterContent.CHAR_POINT);
        if (a < b && b > 0) { return path.substring(a + 1, b).toLowerCase().trim(); }
        return CT.EMPTY;
    }
    
    /**
     * 根据名称获取父路径
     * @author sunqinqiu 
     * @date   2018-12-30 04:00
     * @return
     */
    public static String getParentDirByName(String path, String name) {
        File file = new File(path).getParentFile();
        if (file == null) { return CT.EMPTY; }
        if (file.getName().equals(name)) { return file.getAbsolutePath(); }
        return getParentDirByName(file.getAbsolutePath(), name);
    }
    
    /**
     * 根据父路径获取名称列表
     * @author sunqinqiu 
     * @date   2018-12-30 04:14
     * @param path
     * @param names
     * @return
     */
    public static String getParentDirByNames(String path, String... names) {
        for (String name : names) {
            String thePath = getParentDirByName(path, name);
            if (thePath.isEmpty()) {
                continue;
            }
            return thePath;
        }
        return CT.EMPTY;
    }
    
    /**
     * 获取文件列表
     * @author sunqinqiu
     * @param path
     * @return
     */
    public static List<Map<String, String>> list(String path, boolean readChild) {
        File parent = new File(path);
        List<Map<String, String>> list = new ArrayList<>();
        parent.listFiles((File file) -> {
            if (file.isFile()) {
                Map<String, String> item = new HashMap<>();
                item.put(CT.STRING_NAME, file.getName());
                item.put(CT.STRING_CODE, getName(file.getName()));
                item.put(CT.STRING_PATH, file.getAbsolutePath());
                item.put(IOContent.FILE_C_LAST, DateUtil.format(new Date(file.lastModified()), DateUtil.FORMAT_TYPE_DATEANDTIME_FULL));
                item.put(IOContent.FILE_C_SIZE, Double.toString(file.length() / 1024.00));
                item.put(IOContent.FILE_C_CR, Boolean.toString(file.canRead()));
                item.put(IOContent.FILE_C_CW, Boolean.toString(file.canWrite()));
                list.add(item);
            }
            if (file.isDirectory() && readChild) {
                list.addAll(list(file.getAbsolutePath(), readChild));
            }
            return true;
        });
        return list;
    }
    
    /**
     * 获取子目录
     * @author sunqinqiu 
     * @date   2018-07-23 10:09
     * @return
     */
    public static List<String> listDir(String path) {
        File parent = new File(path);
        List<String> files = new ArrayList<>();
        parent.listFiles((File file) -> {
            if (file.isDirectory()) {
                files.add(file.getName());
            }
            return true;
        });
        return files;
    }
    
    /**
     * 生成文件夹
     * @author sunqinqiu 
     * @date   2019-01-07 00:55
     * @param dirPath
     */
    public static void mkDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-09-17 20:57
     * @param path
     * @return
     */
    public static String read(String path) {
        return read(path, EncodeType.ENCODE_UTF8);
    }
    
    /**
     * 读取文件内容
     * @author sunqinqiu
     * @param path
     * @return
     */
    public static String read(String path, String encode) {
        File file = new File(path);
        if (!file.exists()) { return CT.EMPTY; }
        try (FileInputStream fileInputStream = new FileInputStream(path); InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encode); BufferedReader reader = new BufferedReader(inputStreamReader)) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line + "\n");
            }
            return content.toString();
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_IO_FILE_READ, e);
            return CT.EMPTY;
        }
    }
    
    /**
     * 构造函数
     */
    private FileUtil() {}
}
