package com.teee.referencestation.common.http;

/**
 * Rest请求响应码，包含所有的成功code和失败code的定义，均在此定义
 *
 * @author LDB
 * @date 2018/11/6 11:39
 */
public enum RestCode {
    /**
     * 成功
     */
    OK(200, "OK"),
    /**
     * 未知异常
     */
    UNKNOWN_ERROR(1, "未知异常"),
    /**
     * TOKEN失效
     */
    TOKEN_INVALID(201, "用户未登录，或登录已失效，请重新登录"),
    /**
     * 票据被篡改
     */
    WRONG_TOKEN(202, "票据信息异常，请重新登录"),
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(3, "用户不存在"),
    /**
     *
     */
    MODIFY_PWD(1005, "密码修改成功"),
    /**
     * 登录密码错误
     */
    WRONG_PWD(4, "登录密码错误"),
    /**
     * 页码不合法
     */
    WRONG_PAGE(10100, "页码不合法"),
    /**
     * 缺少参数
     */
    LACK_PARAMS(10101, "缺少参数"),
    /**
     * 数据写入异常
     */
    DATA_ADD_FAILED(10102, "数据写入异常"),
    /**
     * 未查询到相关信息
     */
    MSG_NOT_FOUND(10103, "未查询到相关信息"),
    /**
     * 参数错误
     */
    PARAM_ERROR(10104, "参数错误"),

    /**
     * 操作的条目不存在或已被处理
     */
    ITEM_MISSED(10105, "操作的条目不存在或已被处理"),
    /**
     * 业务逻辑错误
     */
    BIZ_ERROR(10106, "参数错误");

    public final int code;
    public final String msg;

    RestCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
