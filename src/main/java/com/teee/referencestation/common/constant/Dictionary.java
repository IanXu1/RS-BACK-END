package com.teee.referencestation.common.constant;

/**
 * @author zhanglei
 */
public interface Dictionary {

    /**
     * 操作日志级别类型
     */
    interface LogLevel{
        int DIC_CODE = 1;

        int LOW = 1;
        int MID = 2;
        int HIGH = 3;
    }

    /**
     * 接入账户类型
     */
    interface AccountType{
        int DIC_CODE = 2;
    }
}
