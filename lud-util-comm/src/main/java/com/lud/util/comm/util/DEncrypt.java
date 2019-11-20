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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lud.util.content.util.error.ErrorText;
import com.lud.util.content.util.text.CT;
import com.lud.util.content.util.type.EncodeType;

/**
 * 数据的加密解密算法
 * @author sunqinqiu 
 * @date   2018-05-06 23:49
 */
public final class DEncrypt {
    
    public static final String  ALGORITHM_MD5       = "MD5";
    public static final String  FIELD_SIGN          = "sign";
    public static final String  ALGORITHM_SHA       = "SHA1";
    public static final String  ALGORITHM_DES       = "DES";
    public static final String  HMAC_SHA256         = "HMACSHA256";
    public static final String  HMAC_MD5            = "HmacMD5";
    public static final String  KEY_ALGORITHM       = "RSA";
    public static final String  KEY_AES             = "AES";
    
    private static final String ALGORITHM_PBE       = "PBEWITHMD5andDES";
    private static final String ALGORITHM_AES       = "AES/CBC/PKCS5Padding";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String RSAPUBLIC_KEY       = "RSAPublicKey";
    private static final String RSAPRIVATE_KEY      = "RSAPrivateKey";
    
    private static final Logger loger               = LoggerFactory.getLogger(DEncrypt.class);
    
    /**
     * 信息摘要算法
     * @author sunqinqiu
     * @param algorithm 要加密的字符串
     * @param data 返回加密后的摘要信息
     * @return
     */
    private static String encryptEncode(String algorithm, String data) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            return Transcode.byteToHex(md.digest(data.getBytes())).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_ENCODE, e);
            return CT.EMPTY;
        }
    }
    
    /**
     * 生成AESkey
     * @author sunqinqiu
     * @param keySize
     * @param seed
     * @return
     */
    public static String generateAESKey(int keySize, String seed) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            kgen.init(keySize, new SecureRandom(seed.getBytes()));
            SecretKey key = kgen.generateKey();
            return Transcode.byteToBase64(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_AESKEY, e);
            return CT.EMPTY;
        }
    }
    
    /**
     * 生成DES密钥
     * @author sunqinqiu
     * @param seed
     * @return
     */
    public static String generateDESKey(String seed) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_DES);
            kg.init(new SecureRandom(seed.getBytes()));
            SecretKey secretKey = kg.generateKey();
            return Transcode.byteToBase64(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_DES, e);
            return CT.EMPTY;
        }
    }
    
    /**
     * 生成HMAC密钥
     * @author sunqinqiu
     * @return
     */
    public static String generateHMACKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(HMAC_MD5);
            SecretKey secretKey = keyGenerator.generateKey();
            return Transcode.byteToBase64(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_MACKEY, e);
            return CT.EMPTY;
        }
    }
    
    /**
     * 获取MD5签名
     * @author sunqinqiu 
     * @date   2019-09-14 02:42
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String generateSignature(final Map<String, String> data, String key) {
        return generateSignature(data, key, ALGORITHM_MD5);
    }
    
    /**
     * 获取签名
     * @author sunqinqiu 
     * @date   2019-09-14 02:14
     * @param data
     * @param key
     * @param signType
     * @return
     * @throws Exception
     */
    public static String generateSignature(final Map<String, String> data, String key, String signType) {
        String[] keyArray = data.keySet().toArray(new String[data.size()]);
        Arrays.sort(keyArray);
        StringBuilder stringBuilder = new StringBuilder();
        for (String k : keyArray) {
            if ((!k.equals(FIELD_SIGN)) && data.get(k).trim().length() > 0) {
                stringBuilder.append(k).append("=").append(data.get(k).trim()).append("&");
            }
        }
        stringBuilder.append("key=").append(key);
        return ALGORITHM_MD5.equals(signType) ? getMD5Encode(stringBuilder.toString()) : getMacSha256(stringBuilder.toString(), key);
    }
    
    /**
     * 实现AES加密解密
     * @author sunqinqiu
     * @param data
     * @param key
     * @param algorithmParameter
     * @param mode
     * @return
     */
    private static String getAESCipher(String data, String key, String algorithmParameter, int mode) {
        try {
            Key k = toKey(key, KEY_AES);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(algorithmParameter.getBytes());
            Cipher ecipher = Cipher.getInstance(ALGORITHM_AES);
            ecipher.init(mode, k, paramSpec);
            return mode == Cipher.DECRYPT_MODE ? new String(ecipher.doFinal(Transcode.base64ToByte(data))) : Transcode.byteToBase64(ecipher.doFinal(data.getBytes()));
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_AES, e);
            return CT.EMPTY;
        }
    }
    
    /**
     * AES解密
     * @author sunqinqiu
     * @param data
     * @param key
     * @param algorithmParameter
     * @return
     */
    public static String getAESDecrypt(String data, String key, String algorithmParameter) {
        return getAESCipher(data, key, algorithmParameter, Cipher.DECRYPT_MODE);
    }
    
    /**
     * AES加密
     * @author sunqinqiu
     * @param data
     * @param key
     * @param algorithmParameter
     * @return
     */
    public static String getAESEncrypt(String data, String key, String algorithmParameter) {
        return getAESCipher(data, key, algorithmParameter, Cipher.ENCRYPT_MODE);
    }
    
    /**
     * DES的加密解密
     * @author sunqinqiu
     * @param data
     * @param key
     * @param mode
     * @return
     */
    private static String getDESCipher(String data, String key, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(mode, toKey(key, ALGORITHM_DES));
            return mode == Cipher.DECRYPT_MODE ? new String(cipher.doFinal(Transcode.base64ToByte(data))) : Transcode.byteToBase64(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_DESCIP, e);
            return CT.EMPTY;
        }
    }
    
    /**
     *  DES解密
     * @author sunqinqiu
     * @param data
     * @param key
     * @return
     */
    public static String getDESDecrypt(String data, String key) {
        return getDESCipher(data, key, Cipher.DECRYPT_MODE);
    }
    
    /**
     * DES加密
     * @author sunqinqiu
     * @param data
     * @param key
     * @return
     */
    public static String getDESEncrypt(String data, String key) {
        return getDESCipher(data, key, Cipher.ENCRYPT_MODE);
    }
    
    /**
     * 使用HMAC加密
     * @author sunqinqiu
     * @param data
     * @param key
     * @return
     */
    public static String getHMACEncode(String data, String key) {
        Key k = toKey(key, HMAC_MD5);
        try {
            Mac mac = Mac.getInstance(k.getAlgorithm());
            mac.init(k);
            return Transcode.byteToBase64(mac.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_HMAC, e);
        }
        return CT.EMPTY;
    }
    
    /**
     * HMACSHA256
     * @author sunqinqiu 
     * @date   2019-09-14 01:44
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String getMacSha256(String data, String key) {
        try {
            Mac hmac = Mac.getInstance(HMAC_SHA256);
            hmac.init(new SecretKeySpec(key.getBytes(EncodeType.ENCODE_UTF8), HMAC_SHA256));
            byte[] array = hmac.doFinal(data.getBytes(EncodeType.ENCODE_UTF8));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte item : array) {
                stringBuilder.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return stringBuilder.toString().toUpperCase();
        } catch (Exception e) {
            return CT.EMPTY;
        }
    }
    
    /**
     * 对文件进行MD5加密
     * @author sunqinqiu
     * @param file
     * @return
     * @throws IOException
     */
    public static String getMd5ByFile(File file) {
        String value = null;
        try (FileInputStream in = new FileInputStream(file); FileChannel channel = in.getChannel()) {
            MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
            byteBuffer = null;
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_MD5, e);
        }
        return value;
    }
    
    /**
     * 
     * @author sunqinqiu 
     * @date   2019-10-25 04:24
     * @param data
     * @return
     */
    public static String getMD5Encode(String data) {
        return encryptEncode(ALGORITHM_MD5, data);
    }
    
    /**
     * PBE加密解密
     * @author sunqinqiu
     * @param data
     * @param password
     * @param salt
     * @param mode
     * @return
     */
    private static String getPBECipher(String data, String password, String salt, int mode) {
        try {
            Key secretKey = toPBEKey(password);
            PBEParameterSpec paramSpec = new PBEParameterSpec(Transcode.base64ToByte(salt), 100);
            Cipher cipher = Cipher.getInstance(ALGORITHM_PBE);
            cipher.init(mode, secretKey, paramSpec);
            return mode == Cipher.DECRYPT_MODE ? new String(cipher.doFinal(Transcode.base64ToByte(data))) : Transcode.byteToBase64(cipher.doFinal(data.getBytes()));
        } catch (BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_PBE, e);
            return CT.EMPTY;
        }
    }
    
    /**
     * PBE(Password-based encryption基于密码加密)解密
     * @author sunqinqiu
     * @param data
     * @param password
     * @param salt
     * @return
     */
    public static String getPBEDecrypt(String data, String password, String salt) {
        return getPBECipher(data, password, salt, Cipher.DECRYPT_MODE);
    }
    
    /**
     * PBE(Password-based encryption基于密码加密)加密
     * @author sunqinqiu
     * @param data
     * @param password
     * @param salt
     * @return
     */
    public static String getPBEEncrypt(String data, String password, String salt) {
        return getPBECipher(data, password, salt, Cipher.ENCRYPT_MODE);
    }
    
    /**
     * 获得私钥
     * @author sunqinqiu
     * @param keyMap
     * @return
     */
    public static String getRSAPrivateKey(Map<String, Object> keyMap) {
        return Transcode.byteToBase64(((Key) keyMap.get(RSAPRIVATE_KEY)).getEncoded());
    }
    
    /**
     * 获得公钥(base64编码)
     * @author sunqinqiu
     * @param keyMap
     * @return
     */
    public static String getRSAPublicKey(Map<String, Object> keyMap) {
        return Transcode.byteToBase64(((Key) keyMap.get(RSAPUBLIC_KEY)).getEncoded());
    }
    
    /**
     * 数字签名
     * @author sunqinqiu
     * @param data
     * @param privateKey
     * @return
     */
    public static String getRSASign(String data, String privateKey) {
        try {
            byte[] keyBytes = Transcode.base64ToByte(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(priKey);
            signature.update(Transcode.base64ToByte(data));
            return Transcode.byteToBase64(signature.sign());
        } catch (Exception e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_RSASIGH, e);
            return "ERROR";
        }
    }
    
    /**
     * 验证签名
     * @author sunqinqiu
     * @param data
     * @param publicKey
     * @param sign
     * @return
     */
    public static boolean getRSAVerify(String data, String publicKey, String sign) {
        try {
            byte[] keyBytes = Transcode.base64ToByte(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            Signature signature;
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(Transcode.base64ToByte(data));
            return signature.verify(Transcode.base64ToByte(sign));
        } catch (SignatureException | InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_RSAVLI, e);
            return false;
        }
    }
    
    /**
     * 获取签名，西南交大财务处
     * @author sunqinqiu 
     * @date   2018-09-07 14:45
     * @param token
     * @param map
     * @return
     */
    public static String getSHA512(String data) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            sha.update(data.getBytes());
            return Transcode.byteToBase64(sha.digest());
        } catch (NoSuchAlgorithmException e) {
            return "ERROR";
        }
    }
    
    /**
     * 使用SHA加密
     * @author sunqinqiu
     * @param data
     * @return
     */
    public static String getSHAEncode(String data) {
        return encryptEncode(ALGORITHM_SHA, data).toUpperCase();
    }
    
    /**
     * 初始化密钥对
     * @author sunqinqiu
     * @return
     */
    public static Map<String, Object> initRSAKey() {
        Map<String, Object> keyMap = new HashMap<>(2);
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            keyMap.put(RSAPUBLIC_KEY, publicKey);
            keyMap.put(RSAPRIVATE_KEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_RSAKEY, e);
        }
        return keyMap;
    }
    
    /**
     * MD5签名验证
     * @author sunqinqiu 
     * @date   2019-09-14 02:46
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key) {
        return isSignatureValid(data, key, ALGORITHM_MD5);
    }
    
    /**
     * 签名验证
     * @author sunqinqiu 
     * @date   2019-09-14 02:43
     * @param data
     * @param key
     * @param signType
     * @return
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key, String signType) {
        if (!data.containsKey(FIELD_SIGN)) { return false; }
        return generateSignature(data, key, signType).equals(data.get(FIELD_SIGN));
    }
    
    /**
     * 将base64编码后的密钥字符串转换成密钥对象
     * @author sunqinqiu
     * @param key
     * @param algorithm
     * @return
     */
    private static Key toKey(String key, String algorithm) {
        return new SecretKeySpec(Transcode.base64ToByte(key), algorithm);
    }
    
    /**
     * 生成PBEkey
     * @author sunqinqiu
     * @param password
     * @return
     */
    private static Key toPBEKey(String password) {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        try {
            return SecretKeyFactory.getInstance(ALGORITHM_PBE).generateSecret(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            loger.error(ErrorText.UTIL_DENCRYPT_ERR_PBEKEY, e);
            return null;
        }
    }
    
    private DEncrypt() {}
    
}
