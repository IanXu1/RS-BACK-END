package com.teee.referencestation.common.base.controller;

import com.teee.referencestation.common.exception.CustomException;
import com.teee.referencestation.common.exception.CustomUnauthorizedException;
import com.teee.referencestation.common.http.RestResponse;
import com.zeroc.Ice.InvocationTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕捉所有Shiro异常
     *
     * @param e ShiroException
     * @return RestResponse
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ShiroException.class)
    public RestResponse handle401(ShiroException e) {
        return new RestResponse(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), e.getMessage(), e.toString());
    }

    /**
     * 单独捕捉Shiro(UnauthorizedException)异常
     * 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
     *
     * @param e UnauthorizedException
     * @return RestResponse
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UnauthorizedException.class)
    public RestResponse handle401(UnauthorizedException e) {
        return new RestResponse(HttpStatus.UNAUTHORIZED.value(), "未授权", e.toString());
    }

    /**
     * 抛出request body为空的异常
     *
     * @param e HttpMessageNotReadableException
     * @return RestResponse
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestResponse handle401(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "参数不能为空或参数格式错误", e);
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     *
     * @param e UnauthenticatedException
     * @return RestResponse
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public RestResponse handle401(UnauthenticatedException e) {
        return new RestResponse(HttpStatus.UNAUTHORIZED.value(), "无权访问，请先登录", e.toString());
    }

    /**
     * 处理ICE超时异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InvocationTimeoutException.class)
    public RestResponse handleIceTimeout(InvocationTimeoutException e) {
        return new RestResponse(HttpStatus.BAD_REQUEST.value(), "ICE访问超时", e.toString());
    }

    /**
     * 处理ICE超时异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(com.zeroc.Ice.Exception.class)
    public RestResponse handleIce(com.zeroc.Ice.Exception e) {
        return new RestResponse(HttpStatus.BAD_REQUEST.value(), "ICE不可用", e.toString());
    }

    /**
     * 捕捉UnauthorizedException自定义异常
     *
     * @param e CustomUnauthorizedException
     * @return RestResponse
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthorizedException.class)
    public RestResponse handle401(CustomUnauthorizedException e) {
        return new RestResponse(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + e.getMessage(), null);
    }

    /**
     * 捕捉校验异常(BindException)
     *
     * @param e BindException
     * @return RestResponse
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public RestResponse validException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return new RestResponse(HttpStatus.BAD_REQUEST.value(), result.get("errorMsg").toString(), result.get("errorList"));
    }

    /**
     * 捕捉校验异常(MethodArgumentNotValidException)
     *
     * @param e MethodArgumentNotValidException
     * @return RestResponse
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse validException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return new RestResponse(HttpStatus.BAD_REQUEST.value(), result.get("errorMsg").toString(), result.get("errorList"));
    }

    /**
     * 捕捉其他所有自定义异常
     *
     * @param e CustomException
     * @return RestResponse
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    public RestResponse handle(CustomException e) {
        return new RestResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    /**
     * 捕捉404异常
     *
     * @param e NoHandlerFoundException
     * @return RestResponse
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public RestResponse handle(NoHandlerFoundException e) {
        return new RestResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
    }

    /**
     * 捕捉其他所有异常
     *
     * @param request HttpServletRequest
     * @param ex      Exception
     * @return RestResponse
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestResponse globalException(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        return new RestResponse(this.getStatus(request).value(), ex.toString() + ": " + ex.getMessage(), null);
    }

    /**
     * 获取状态码
     *
     * @param request HttpServletRequest
     * @return RestResponse
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 获取校验错误信息
     *
     * @param fieldErrors List<FieldError>
     * @return RestResponse
     */
    private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        Map<String, Object> result = new HashMap<>(16);
        List<String> errorList = new ArrayList<>();
        StringBuffer errorMsg = new StringBuffer("校验异常:");
        for (FieldError error : fieldErrors) {
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField() + "-" + error.getDefaultMessage() + ".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }
}
