package com.teee.referencestation.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 对象操作工具
 *
 * @author LDB
 * @date 2018/11/14 14:25
 */
public class ObjUtil {

    /**
     * 将对象转换为Array
     *
     * @param source Object
     * @return Object[]
     */
    public static Object[] toObjectArray(Object source) {
        if (source == null) {
            return new Object[0];
        } else if (source instanceof Object[]) {
            return ((Object[]) source);
        } else if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        } else {
            int length = Array.getLength(source);
            if (length == 0) {
                return new Object[0];
            } else {
                Class<?> wrapperType = Array.get(source, 0).getClass();
                Object[] newArray = ((Object[]) Array.newInstance(wrapperType, length));

                for (int i = 0; i < length; ++i) {
                    newArray[i] = Array.get(source, i);
                }

                return newArray;
            }
        }
    }

    /**
     * 判断两个对象是否Equals
     *
     * @param o1 Object
     * @param o2 Object
     * @return boolean
     */
    public static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        } else if (o1 != null && o2 != null) {
            if (o1.equals(o2)) {
                return true;
            } else {
                if (o1.getClass().isArray() && o2.getClass().isArray()) {
                    if (o1 instanceof Object[] && o2 instanceof Object[]) {
                        return Arrays.equals(((Object[]) o1), ((Object[]) o2));
                    }

                    if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
                        return Arrays.equals(((boolean[]) o1), ((boolean[]) o2));
                    }

                    if (o1 instanceof byte[] && o2 instanceof byte[]) {
                        return Arrays.equals(((byte[]) o1), ((byte[]) o2));
                    }

                    if (o1 instanceof char[] && o2 instanceof char[]) {
                        return Arrays.equals(((char[]) o1), ((char[]) o2));
                    }

                    if (o1 instanceof double[] && o2 instanceof double[]) {
                        return Arrays.equals(((double[]) o1), ((double[]) o2));
                    }

                    if (o1 instanceof float[] && o2 instanceof float[]) {
                        return Arrays.equals(((float[]) o1), ((float[]) o2));
                    }

                    if (o1 instanceof int[] && o2 instanceof int[]) {
                        return Arrays.equals(((int[]) o1), ((int[]) o2));
                    }

                    if (o1 instanceof long[] && o2 instanceof long[]) {
                        return Arrays.equals(((long[]) o1), ((long[]) o2));
                    }

                    if (o1 instanceof short[] && o2 instanceof short[]) {
                        return Arrays.equals(((short[]) o1), ((short[]) o2));
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断对象是否不为空(取isEmpty非值)
     *
     * @param obj Object
     * @return boolean
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断对象是否为空
     *
     * @param obj Object
     * @return boolean
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else {
            if (obj.getClass().isArray()) {
                if (obj instanceof Object[]) {
                    return ((Object[]) obj).length == 0;
                }

                if (obj instanceof boolean[]) {
                    return ((boolean[]) obj).length == 0;
                }

                if (obj instanceof byte[]) {
                    return ((byte[]) obj).length == 0;
                }

                if (obj instanceof char[]) {
                    return ((char[]) obj).length == 0;
                }

                if (obj instanceof double[]) {
                    return ((double[]) obj).length == 0;
                }

                if (obj instanceof float[]) {
                    return ((float[]) obj).length == 0;
                }

                if (obj instanceof int[]) {
                    return ((int[]) obj).length == 0;
                }

                if (obj instanceof long[]) {
                    return ((long[]) obj).length == 0;
                }

                if (obj instanceof short[]) {
                    return ((short[]) obj).length == 0;
                }
            }

            return StringUtils.isEmpty(obj.toString()) || StringUtils.isEmpty(obj.toString().trim());
        }
    }

}
