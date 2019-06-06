package com.teee.referencestation.encrypt;

import com.teee.referencestation.utils.AESUtil;
import com.teee.referencestation.utils.FileUtil;
import com.teee.referencestation.utils.Md5EncryptionUtil;
import org.apache.shiro.codec.Hex;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class TestFileHead {

    public static void main(String[] args) throws Exception {

        String filePath = "D:\\ice\\TCH05GPS_DS_VD_V0.92B_20190527.bin";
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));

        System.out.println("file length: " + bytes.length);

        byte[] fileNameBytes = FileUtil.subBytes(bytes, 0, 20);
        System.out.println("file name: " + new String(fileNameBytes, "UTF-8").trim());
        byte[] versionBytes = FileUtil.subBytes(bytes, 20, 12);
        System.out.println("version num: " + new String(versionBytes, "UTF-8").trim());
        byte[] dateBytes = FileUtil.subBytes(bytes, 32, 8);
        System.out.println("date: " + new String(dateBytes, "UTF-8").trim());
        byte[] md5Bytes = FileUtil.subBytes(bytes, 40, 16);
        System.out.println("md5: " + Hex.encodeToString(md5Bytes));
        byte[] fileTypeBytes = FileUtil.subBytes(bytes, 56, 4);
        System.out.println("file type: " + Hex.encodeToString(fileTypeBytes));
        byte[] fileKeys = FileUtil.subBytes(bytes, 40, 20);
        System.out.println("file key: " + Hex.encodeToString(fileKeys));
        System.out.println("file key base64: " + Base64.getEncoder().encodeToString(fileKeys));
        byte[] fileSizeBytes = FileUtil.subBytes(bytes, 60, 4);
        System.out.println("file size: " + FileUtil.bytesToInt(fileSizeBytes, 0));
        byte[] resBytes = FileUtil.subBytes(bytes, 64, 36);
        System.out.println("reserved byte: " + new String(resBytes, "UTF-8"));

        // 解析文件内容md5
        String md5 = Md5EncryptionUtil.md5WithNoSalt(FileUtil.subBytes(bytes, 100, bytes.length - 100));
        System.out.println("real md5: " + md5);
        System.out.println("md5 check: " + md5.equalsIgnoreCase(Hex.encodeToString(md5Bytes)));


        byte[] fileKeyBytes = Base64.getDecoder().decode("0z5db5RLeqO4caNbDqBd4gEAAQg=");
        String md5Str = org.apache.commons.codec.binary.Hex.encodeHexString(FileUtil.subBytes(fileKeyBytes, 0, 16));
        System.out.println(md5Str);

        System.out.println(AESUtil.getInstance().decrypt("qQx2WFReYNzkdQSi685YM2ZZOZbMF0CE8Ab7STNgod2QxILtGVueVnoOBxxgVePXWH0xw/eqF9dWJQSqFyX/Z+SiI0miCbxK02QA5NjQTMze7tgadw2lGU5p2pAxNVk6"));
    }
}
