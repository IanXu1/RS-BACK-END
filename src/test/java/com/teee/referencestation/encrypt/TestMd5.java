package com.teee.referencestation.encrypt;

import com.teee.referencestation.utils.FileUtil;
import com.teee.referencestation.utils.Md5EncryptionUtil;
import org.apache.shiro.codec.Hex;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMd5 {

    public static void main(String[] args) {
        byte[] bytes = FileUtil.getBytesByFile("D:\\ice\\TCH05GPS_DS_VC_V1.00_20190403.bin");
        String base64 = Base64.getEncoder().encodeToString(bytes);
        System.out.println(base64.length());
        System.out.println(Base64.getDecoder().decode(base64));
        System.out.println(bytes.length);
        String md5 = Md5EncryptionUtil.md5WithNoSalt(bytes);
        System.out.println(md5);

        System.out.println("======================");
        System.out.println("[MTIzNDU2Nzg5MDEyMzQ1Njc4OTA=]".replaceAll("\\[", "").replaceAll("\\]", ""));
        System.out.println(new String(Base64.getDecoder().decode("MTIzNDU2Nzg5MDEyMzQ1Njc4OTA=")));

        byte[] bytes1 = Hex.decode("c69f823725234bc2f7dac5f2f32c257312000107");
        System.out.println(Base64.getEncoder().encodeToString(bytes1));

        System.out.println("=====================================");
        Pattern p1 = Pattern.compile("[^\\x00-\\xff]");
        Matcher m1 = p1.matcher("1231213123@!#@!#@!@#@@");
        System.out.println(m1.find());
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = p.matcher("1231213123！@@@#！@！#！@12中文321");
        System.out.println(m.find());
    }
}
