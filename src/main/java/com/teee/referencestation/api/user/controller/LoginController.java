package com.teee.referencestation.api.user.controller;

import com.teee.referencestation.api.statistics.model.OperationLog;
import com.teee.referencestation.api.statistics.service.OperationLogService;
import com.teee.referencestation.api.user.model.SysUser;
import com.teee.referencestation.api.user.model.UserLoginVo;
import com.teee.referencestation.api.user.service.UserService;
import com.teee.referencestation.common.constant.Dictionary;
import com.teee.referencestation.common.http.RestCode;
import com.teee.referencestation.utils.*;
import com.teee.referencestation.common.base.controller.BaseController;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.common.http.RestResponse;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhanglei
 * @date 2019-1-10 17:02:27
 */
@RestController
@PropertySource("classpath:config.properties")
public class LoginController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private OperationLogService logService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录", notes = "参数必填")
    @PostMapping(value = "/login")
    public RestResponse loginUser(@RequestBody @Validated UserLoginVo userLoginVo, HttpServletResponse response) {

        // 查询数据库中的帐号信息
        SysUser userInfo = userService.findUserByUsername(userLoginVo.getUsername());
        if (userInfo == null) {
            throw new UnknownAccountException("未知的帐号");
        }

        // 记录用户登录次数，判断是否超过错误次数
        AtomicInteger retryCount = (AtomicInteger)RedisUtil.getObject(Constant.PREFIX_SHIRO_RETRY_LIMIT + userInfo.getUsername());
        if (retryCount == null) {
            //如果用户没有登陆过,登陆次数加1 并放入缓存
            retryCount = new AtomicInteger(0);
        }

        if (retryCount.incrementAndGet() > 3) {
            //如果用户登陆失败次数大于3次 抛出锁定用户异常  并修改数据库字段
            if (userInfo.getState() == 0) {
                userInfo.setState(1);
                userService.modifyUserOnly(userInfo);
                LOGGER.info("锁定用户: {}", userInfo.getUsername());
                //添加操作日志
                logLockUser(userInfo.getUsername());
            }

            PropertiesUtil.readProperties("config.properties");
            long shiroRetryLimitTimeout = Long.valueOf(PropertiesUtil.getProperty("shiroRetryLimitTimeout"));
            long diff = DateUtil.until(userInfo.getLastModifiedDate(), ChronoUnit.MINUTES);
            if (diff <= shiroRetryLimitTimeout) {
                //抛出用户锁定异常
                throw new LockedAccountException("登录错误次数过多，账户被暂时锁定");
            } else {
                //超时解锁
                userInfo.setState(0);
                userService.modifyUserOnly(userInfo);
                LOGGER.info("超时解锁用户: {}", userLoginVo.getUsername());
                retryCount = new AtomicInteger(1);
                //添加操作日志
                logAutoUnLock(userLoginVo.getUsername());
            }
        }

        // 原始密码进行MD5+salt加密
        String key = Md5EncryptionUtil.encryptionPwd(userLoginVo.getPassword(), userInfo.getSalt(), "MD5", 2);
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(userInfo.getPassword())) {
            // 清除可能存在的Shiro权限信息缓存
            if (RedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + userInfo.getUsername())) {
                RedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + userInfo.getUsername());
            }
            // 清除账户登录次数缓存
            if (RedisUtil.exists(Constant.PREFIX_SHIRO_RETRY_LIMIT + userInfo.getUsername())) {
                RedisUtil.delKey(Constant.PREFIX_SHIRO_RETRY_LIMIT + userInfo.getUsername());
            }
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(Instant.now().toEpochMilli());
            PropertiesUtil.readProperties("config.properties");
            String refreshTokenExpireTime = PropertiesUtil.getProperty("refreshTokenExpireTime");
            RedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + userInfo.getUsername(), currentTimeMillis, Long.valueOf(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(userInfo.getUsername(), userInfo.getId(), currentTimeMillis);
            response.setHeader("Authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            // 记录鉴权成功日志
            logAuthSuccess(userInfo.getRealName());
            return new RestResponse(HttpStatus.OK.value(), "登录成功(Login Success.)", token);
        } else {
            // 记录鉴权失败日志
            logAuthFail(userLoginVo.getUsername());
            // 登录失败，记录登录次数缓存
            RedisUtil.setObjectNoExpire(Constant.PREFIX_SHIRO_RETRY_LIMIT + userInfo.getUsername(), retryCount);
            throw new IncorrectCredentialsException("帐号或密码错误");
        }
    }

    private void logAuthFail(String username) {
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.MID);
        log.setContent("用户[" + username + "]账号或密码错误！");
        log.setOperationName("登入系统");
        logService.addOperationLog(log);
    }

    private void logAuthSuccess(String realName) {
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.MID);
        log.setContent("用户[" + realName + "]登入系统！");
        log.setOperationName("登入系统");
        logService.addOperationLog(log);
    }

    private void logAutoUnLock(String username) {
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.HIGH);
        log.setContent("超时自动解锁用户[" + username + "]锁定");
        log.setOperationName("解锁用户");
        logService.addOperationLog(log);
    }

    private void logLockUser(String username) {
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.HIGH);
        log.setContent("锁定用户[" + username + "]");
        log.setOperationName("锁定用户");
        logService.addOperationLog(log);
    }

    /**
     * 登出系统
     * @return
     */
    @ApiOperation(value = "用户登出", notes = "无参数")
    @GetMapping(value = "/logout")
    public RestResponse logout() {
        // 获取审计信息
        Subject currentUser = SecurityUtils.getSubject();
        String token = (String) currentUser.getPrincipal();
        String username = JwtUtil.getClaim(token, Constant.ACCOUNT);
        // 清除缓存
        RedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + username);

        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.MID);
        log.setContent("用户[" + username + "]登出系统！");
        log.setOperationName("登出系统");
        logService.addOperationLog(log);

        currentUser.logout();
        return RestResponse.error(RestCode.TOKEN_INVALID);
    }
}
