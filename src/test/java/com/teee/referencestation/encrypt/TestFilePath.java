package com.teee.referencestation.encrypt;

import com.teee.referencestation.utils.AESUtil;

public class TestFilePath {

    public static void main(String[] args) throws Exception {
        String path = AESUtil.getInstance().encrypt("/usr/local/src/rs/TCH05GPS_DS_VC_V1.00_20190403.bin");
        System.out.println(path);
        System.out.println(AESUtil.getInstance().decrypt(path));
        String path2 = AESUtil.getInstance().encrypt("D:\\ice\\TCH05GPS_DS_VC_V1.00_20190403.bin");
        System.out.println(path2);
        System.out.println(AESUtil.getInstance().decrypt(path2));
        System.out.println(0x01);
        System.out.println(0x07ffffff);
    }
}
