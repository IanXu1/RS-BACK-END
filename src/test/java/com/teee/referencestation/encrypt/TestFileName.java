package com.teee.referencestation.encrypt;

public class TestFileName {

    public static void main(String[] args) {
        String fileName = "TCH0.5GPS_DS_VB_V1.03.026_20190325.bin";
        System.out.println(fileName.substring(0, fileName.lastIndexOf(".")));
    }
}
