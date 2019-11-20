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
package com.lud.util.content.util.error;

/**
 * 一些常用的信息
 * @author sunqinqiu 
 * @date   2018-05-26 13:59
 */
public final class ErrorText {
    public static final String UTIL_CONVERT_ERR_DOUBLE    = "非DOUBLE类型输入";
    public static final String UTIL_CONVERT_ERR_TOINT     = "非INT类型输入";
    public static final String UTIL_DATE_ERR_TODATE       = "非法日期输入";
    public static final String UTIL_DENCRYPT_ERR_AES      = "AES加密解密错误";
    public static final String UTIL_DENCRYPT_ERR_AESKEY   = "生成AESkey错误";
    public static final String UTIL_DENCRYPT_ERR_DES      = "生成DES密钥错误";
    public static final String UTIL_DENCRYPT_ERR_DESCIP   = "DES的加密解密错误";
    public static final String UTIL_DENCRYPT_ERR_DH       = "DH加密错误";
    public static final String UTIL_DENCRYPT_ERR_DHEN     = "DH解密错误";
    public static final String UTIL_DENCRYPT_ERR_DHKEY    = "初始化甲方密钥对错误";
    public static final String UTIL_DENCRYPT_ERR_DHSEC    = "生成本地密钥错误";
    public static final String UTIL_DENCRYPT_ERR_ENCODE   = "生成摘要信息错误";
    public static final String UTIL_DENCRYPT_ERR_HMAC     = "使用HMAC加密错误";
    public static final String UTIL_DENCRYPT_ERR_MACKEY   = "生成HMAC密钥错误";
    public static final String UTIL_DENCRYPT_ERR_MD5      = "MD5加密文件错误";
    public static final String UTIL_DENCRYPT_ERR_PBE      = "PBE加密解密错误";
    public static final String UTIL_DENCRYPT_ERR_PBEKEY   = "生成PBEkey错误";
    public static final String UTIL_DENCRYPT_ERR_RSAKEY   = "初始化密钥对错误";
    public static final String UTIL_DENCRYPT_ERR_RSASIGH  = "数字签名错误";
    public static final String UTIL_DENCRYPT_ERR_RSAVLI   = "验证签名错误";
    public static final String UTIL_ERROR_EXCEL_SAVE      = "保存EXCEL错误！\\n {}";
    public static final String UTIL_IO_FILE_APPEND        = "追加文本出现错误";
    public static final String UTIL_IO_FILE_COPY          = "拷贝文件出现错误";
    public static final String UTIL_IO_FILE_COPYDIR       = "拷贝目录出现错误";
    public static final String UTIL_IO_FILE_READ          = "读取文件出现错误";
    public static final String UTIL_NETWORK_ERROR         = "获取远程数据出现错误";
    public static final String UTIL_PICTURE_DECODE        = "图片解码错误";
    public static final String UTIL_PICTURE_ENCODE        = "图片转码错误";
    public static final String UTIL_QRCODE                = "二维码转码错误";
    public static final String UTIL_TRANSCODE_ERR_GETBYTE = "GETBYTE错误";
    public static final String UTIL_TRANSCODE_ERR_STRING  = "转化为STRING错误";
    public static final String UTIL_TRANSCODE_ERR_TRANS   = "编码转换错误";
    public static final String UTIL_XML_CREATE            = "构建XML模型出现错误";
    public static final String UTIL_XML_GET               = "获取XML属性出错";
    public static final String UTIL_XML_SAVE              = "保存XML文件出错";
    public static final String UTIL_ZIP_DE                = "解压文件出错";
    public static final String UTIL_ZIP_EN                = "压缩文件出错";
    public static final String QRCODE_FORMATERROR         = "Could not write an image of format {} to {}";
    public static final String XML_ERROR                  = "Invalid XML, can not convert to map. Error message: {}. XML content: {}";
    
    private ErrorText() {}
}
