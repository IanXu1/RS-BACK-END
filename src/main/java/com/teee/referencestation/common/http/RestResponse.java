package com.teee.referencestation.common.http;

import java.io.Serializable;

/**
 * Rest统一响应结果封装，所有的请求，均以该类为响应体返回
 * 业务层只需要关心对应结果即可，其中的响应码，由框架或基础组件关心
 *
 * @author LDB
 * @date 2018/11/6 11:39
 */
public class RestResponse<T> implements Serializable {
    private int code;
    private String msg;
    private T result;

    public RestResponse(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    /**
     * result可以为空，直接返回new RestResponse<T>()
     *
     * @return RestResponse<T>
     */
    public static <T> RestResponse<T> success() {
        return new RestResponse<T>();
    }

    public static <T> RestResponse<T> success(T result) {
        RestResponse response = new RestResponse<T>();
        response.setResult(result);
        return response;
    }


    public static <T> RestResponse<T> error(RestCode code) {
        return new RestResponse<T>(code.code, code.msg);
    }

    public static <T> RestResponse<T> error(String msg) {
        RestResponse<T> response = new RestResponse<>(RestCode.BIZ_ERROR.code, null);
        response.setMsg(msg);
        return response;
    }

    public static <T> RestResponse<T> success(String msg) {
        RestResponse<T> response = new RestResponse<>(RestCode.OK.code, null);
        response.setMsg(msg);
        return response;
    }

    public RestResponse() {
        this(RestCode.OK.code, RestCode.OK.msg);
    }

    public RestResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 判断请求是否成功
     *
     * @return boolean
     */
    public boolean isSuccess() {
        return getCode() == RestCode.OK.code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


}
