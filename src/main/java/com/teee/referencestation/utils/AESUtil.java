package com.teee.referencestation.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

/**
 * AES加解密工具类
 *
 * @author lixu
 */
public class AESUtil {

    /**
     * key需要为16位
     */
    private String key = "A@1ds09%ejk589^1";

    /**
     * 算法/模式/补码方式"
     */
    private static String TRANSFORMAT = "AES/ECB/PKCS5Padding";

    public static AESUtil getInstance() {
        return new AESUtil();
    }

    public static AESUtil getInstance(String key) {
        return new AESUtil(key);
    }

    private AESUtil(String key) {
        this.key = key;
    }

    private AESUtil() {

    }

    /**
     * @desc 加密
     * @param sSrc
     * @return
     * @throws Exception
     */
    public String encrypt(String sSrc) throws Exception {

        if (sSrc == null || sSrc.length() == 0) {

            return "";
        }

        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(TRANSFORMAT);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * @desc 解密
     * @param sSrc
     * @return
     * @throws Exception
     */
    public String decrypt(String sSrc) throws Exception {

        if (sSrc == null || sSrc.length() == 0) {

            return "";
        }

        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(TRANSFORMAT);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted = Base64.getDecoder().decode(sSrc);
        byte[] original = cipher.doFinal(encrypted);

        return new String(original, "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        String enString = AESUtil.getInstance().encrypt("D:\\teee\\upgrade\\2019-01-31\\Redis深度历险.zip");
        System.out.println("加密后的字串是：" + enString);
        System.out.println("解密后的字串是：" + AESUtil.getInstance().decrypt(enString));
        enString = AESUtil.getInstance().encrypt("123456");
        System.out.println("加密后的字串是：" + enString);
        System.out.println("解密后的字串是：" + AESUtil.getInstance().decrypt(enString));
    }
}
