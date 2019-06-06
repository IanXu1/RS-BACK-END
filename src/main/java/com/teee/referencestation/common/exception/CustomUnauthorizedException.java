package com.teee.referencestation.common.exception;

/**
 * 自定义401无权限异常(UnauthorizedException)
 * @author zhanglei
 */
public class CustomUnauthorizedException extends RuntimeException {

    public CustomUnauthorizedException(String msg){
        super(msg);
    }

    public CustomUnauthorizedException() {
        super();
    }
}
