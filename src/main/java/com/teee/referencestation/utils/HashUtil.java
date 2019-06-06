package com.teee.referencestation.utils;

/**
 * 哈希计算工具
 *
 * @author LDB
 * @date 2016/7/8
 */
public class HashUtil {

    /**
     * 计算对象哈希值
     *
     * @param objects []
     * @return int
     */
    public static int hashCode(Object... objects) {
        if (objects == null) {
            return 0;
        }
        int result = 1;
        for (Object element : objects) {
            result = 31 * result + (element == null ? 0 : element.hashCode());
        }
        return result;
    }
}
