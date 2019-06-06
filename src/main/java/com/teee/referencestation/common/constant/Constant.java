package com.teee.referencestation.common.constant;

/**
 * 公共常量类
 *
 * @author lixu
 */
public class Constant {

    /**
     * 数据逻辑删除状态 0-未删除 9-已删除
     */
    public interface DATA_STATUS {
        int AVAILABLE = 0;
        int DELETED = 9;
    }

    /**
     * 数据逻辑删除状态 0-未删除 9-已删除
     */
    public interface UPGRADE_RESULT_STATE {
        int UN_UP = 0;
        int UP_SUCCESS = 1;
        int UP_FAIL = 2;
    }

    /**
     * ICE缓存相关
     * RS:数据逻辑删除状态 0-未删除 9-已删除
     * PAGINATION:ce分页查询缓存Id list分区大小
     */
    public interface ICE_CACHE {
        String RS = "referenceStation";
        Integer PAGINATION = 10000;
    }


    /**
     * redis-OK
     */
    public final static String OK = "OK";

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public final static String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * redis-key-前缀-shiro:retry_limit:
     */
    public final static String PREFIX_SHIRO_RETRY_LIMIT = "shiro:retry_limit:";

    /**
     * JWT-account:
     */
    public final static String ACCOUNT = "account";

    /**
     * JWT-userId:
     */
    public final static String USER_ID = "userId";

    /**
     * JWT-currentTimeMillis:
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;

    public static final String BUILD_IN_ACCOUNT1 = "admin";
    public static final String BUILD_IN_ACCOUNT2 = "letmein";
}
