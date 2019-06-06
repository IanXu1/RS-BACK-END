package com.teee.referencestation.utils;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author zhanglei
 */
public class Md5EncryptionUtil {

    private static final SecureRandom random = new SecureRandom();

    /**
     * 根据用户名随机生成盐值
     *
     * @param byteNum 用户名
     * @return
     */
    public static String getSalt(int byteNum) {
        byte[] bytes = new byte[byteNum];
        random.nextBytes(bytes);
        return Hex.encodeToString(bytes);
    }

    /**
     * 对原始密码进行加密
     *
     * @param originalPassword
     * @param salt
     * @return
     */
    public static String encryptionPwd(String originalPassword, String salt, String hashAlgorithm, int hashIteration) {
        SimpleHash newPassword = new SimpleHash(hashAlgorithm, originalPassword, salt, hashIteration);
        return newPassword.toString();
    }

    public static void main(String[] args) {
        String password = "123456";
        String hashAlgorithm = "md5";
        String salt = "78d92ba9477b3661bc8be4bd2e8dd8c0";
        int hashIteration = 2;

        System.out.println(encryptionPwd(password, salt, hashAlgorithm, hashIteration));
    }

    /**
     * @desc 计算字符串md5返回16进制字符串
     * @param plainText
     * @return
     */
    public static String md5WithNoSalt(String plainText) {
       return md5WithNoSalt(plainText.getBytes());
    }

    /**
     * @desc 计算字符串md5返回16进制字符串
     * @param bytes
     * @return
     */
    public static String md5WithNoSalt(byte[] bytes) {
        //定义一个字节数组
        byte[] secretBytes = md5WithByte(bytes);
        //将加密后的数据转换为16进制数字
        return Hex.encodeToString(secretBytes);
    }

    /**
     * @desc 计算byte数组md5，返回长度为16的byte数组
     * @param bytes
     * @return
     */
    public static byte[] md5WithByte(byte[] bytes) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(bytes);
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        return secretBytes;
    }
}
