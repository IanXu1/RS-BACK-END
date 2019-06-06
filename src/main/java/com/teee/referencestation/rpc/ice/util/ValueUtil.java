package com.teee.referencestation.rpc.ice.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhanglei
 */
public class ValueUtil {

    public static String[] getValue(String value, boolean isLike) {
        if (value != null) {
            if (value.contains(",")) {
                String[] values = value.split(",");
                if (isLike) {
                    List<String> valueList = new ArrayList<>( Arrays.asList(values));
                    String[] newVal = valueList.stream().map(a -> "%" + a + "%").toArray(String[]::new);
                    return newVal;
                }
                return values;
            } else {
                if (isLike) {
                    return new String[]{"%" + value + "%"};
                } else {
                    return new String[]{value};
                }

            }
        }
        return new String[]{""};
    }
}
