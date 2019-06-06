package com.teee.referencestation.common.exception;

/**
 * 自定义异常(CustomException)
 * @author zhanglei
 */
public class CustomException extends RuntimeException {

    public CustomException(String msg){
        super(msg);
    }

    public CustomException() {
        super();
    }
}
