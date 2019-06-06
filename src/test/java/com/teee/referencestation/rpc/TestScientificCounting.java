package com.teee.referencestation.rpc;

import java.text.NumberFormat;

public class TestScientificCounting {

    public static void main(String[] args) {
        double x = 0.000000007;
        System.out.println(x);
        System.out.println(formatDouble(x));
    }

    private static String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getInstance();
        //设置保留多少位小数
        nf.setMaximumFractionDigits(20);
        // 取消科学计数法
        nf.setGroupingUsed(false);
        //返回结果
        return nf.format(d);
    }
}
