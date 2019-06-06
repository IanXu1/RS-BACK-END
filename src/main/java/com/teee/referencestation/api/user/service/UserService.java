package com.teee.referencestation.api.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teee.referencestation.api.statistics.model.OperationLog;
import com.teee.referencestation.api.statistics.service.OperationLogService;
import com.teee.referencestation.api.user.model.*;
import com.teee.referencestation.common.constant.Dictionary;
import com.teee.referencestation.utils.*;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.common.http.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 * @date 2019-1-10 17:28:34
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserService extends BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OperationLogService operationService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    /**
     * 通过用户名查询用户信息
     *
     * @param username
     * @return
     */
    public SysUser findUserByUsername(String username) {
        SysUser user = session.selectOne("user.findUserByUsername", username);
        return user;
    }

    /**
     * 通过用户名查询用户信息f
     *
     * @param id
     * @return
     */
    public SysUser findUserById(long id) {
        SysUser user = session.selectOne("user.findUserById", id);
        return user;
    }

    /**
     * @param request
     * @return
     * @desc 分页查询用户列表信息
     */
    public RestResponse findUserList(Map request, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> resultList = session.selectList("user.getDataList", request);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(resultList);
        return RestResponse.success(pageInfo);
    }

    /**
     * @param addVo
     * @return
     */
    public RestResponse addUser(UserAddVo addVo) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(addVo, user);
        user.setState(0);
        user.setDeleted(0);
        String salt = Md5EncryptionUtil.getSalt(8);
        user.setSalt(salt);
        String encryptPwd = Md5EncryptionUtil.encryptionPwd("123456", salt, "MD5", 2);
        user.setPassword(encryptPwd);
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        user.setCreatedBy(Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
        int effects = session.insert("user.insertUser", user);
        //添加操作日志
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.HIGH);
        log.setContent("新增用户[" + user.getUsername() + "]");
        log.setOperationName("新增用户");
        operationService.addOperationLog(log);
        return effects > 0 ? RestResponse.success("用户添加成功") : RestResponse.error("用户添加失败");
    }

    public RestResponse modifyUser(UserModifyVo modifyVo) {
        //获取审计信息
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        SysUser user = findUserById(modifyVo.getId());
        user.setLastModifiedBy(Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
        BeanUtils.copyProperties(modifyVo, user);
        int effects = session.update("user.updateUser", user);
        //添加操作日志
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.MID);
        log.setContent("修改用户[" + user.getUsername() + "]信息");
        log.setOperationName("修改用户");
        operationService.addOperationLog(log);
        return effects > 0 ? RestResponse.success("更新成功") : RestResponse.error("无效更新");
    }

    public int modifyUserOnly(SysUser user) {
        int effects = session.update("user.updateUser", user);
        return effects;
    }

    public RestResponse deleteUser(UserIdVo idVo) {
        SysUser user = findUserById(idVo.getId());
        //逻辑删除位置为已删除
        user.setDeleted(Constant.DATA_STATUS.DELETED);
        //获取审计信息
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        long currentUserId = Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID));
        // 判断是否是当前登录用户
        if (user.getId() == currentUserId) {
            return RestResponse.error("不能删除当前登录用户[" + user.getUsername() + "]");
        }
        // 判断是否是系统内置用户
        if (user.getUsername().equalsIgnoreCase(Constant.BUILD_IN_ACCOUNT1)) {
            return RestResponse.error("不能删除系统内置账户[admin]");
        }
        if (user.getUsername().equalsIgnoreCase(Constant.BUILD_IN_ACCOUNT2)) {
            return RestResponse.error("不能删除系统内置账户[letmein]");
        }
        user.setLastModifiedBy(currentUserId);
        int effects = session.update("user.updateUser", user);
        if (effects > 0) {
            //清空用户登录次数限制
            RedisUtil.delKey(Constant.PREFIX_SHIRO_RETRY_LIMIT + user.getUsername());
            //添加操作日志
            OperationLog log = new OperationLog();
            log.setLogLevel(Dictionary.LogLevel.HIGH);
            log.setContent("删除用户[" + user.getUsername() + "]信息");
            log.setOperationName("删除用户");
            operationService.addOperationLog(log);
        }
        return effects > 0 ? RestResponse.success("删除成功") : RestResponse.error("删除无效");
    }

    public RestResponse loadLockedUserList(Map request, Integer pageNum, Integer pageSize) {
        //设置锁定标志位
        request.put("state", 1);
        return findUserList(request, pageNum, pageSize);
    }

    /**
     * 根据用户名 解锁用户
     *
     * @param userId
     * @return
     */
    public RestResponse unLockedUser(long userId) {
        SysUser lockUser = findUserById(userId);
        int effects = 0;
        if (lockUser != null) {
            lockUser.setState(0);
            effects = modifyUserOnly(lockUser);
            RedisUtil.delKey(Constant.PREFIX_SHIRO_RETRY_LIMIT + lockUser.getUsername());
        }
        //添加操作日志
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.HIGH);
        log.setContent("解锁用户[" + lockUser.getUsername() + "]锁定");
        log.setOperationName("解锁用户");
        operationService.addOperationLog(log);
        return effects > 0 ? RestResponse.success("解锁成功") : RestResponse.error("解锁失败");
    }

    /**
     * 判断密码是否匹配
     * @param request
     * @return
     */
    public boolean isPwdRight(Map request) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        SysUser user = findUserById(Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
        String encryptPwd = Md5EncryptionUtil.encryptionPwd(request.get("password").toString(), user.getSalt(),
                "MD5", 2);
        return user.getPassword().equalsIgnoreCase(encryptPwd);
    }

    /**
     * 更改密码
     * @param request
     * @return
     */
    public RestResponse modifyPwd(Map request) {
        String password = request.get("password").toString();
        String passwordNew = request.get("newPwd").toString();
        //获取当前登录用户信息
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        if (ObjUtil.isEmpty(token)) {
            return RestResponse.error("匿名用户，请先登陆!");
        }
        SysUser user = findUserById(Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
        String encryptPwd = Md5EncryptionUtil.encryptionPwd(password, user.getSalt(), "MD5", 2);
        if (!user.getPassword().equalsIgnoreCase(encryptPwd)) {
            return RestResponse.error("原始密码不匹配");
        }
        String encryptPwdNew = Md5EncryptionUtil.encryptionPwd(passwordNew, user.getSalt(), "MD5", 2);
        user.setPassword(encryptPwdNew);
        user.setLastModifiedBy(user.getId());
        int effects = session.update("user.updateUser", user);
        if (effects == 0) {
            return RestResponse.error("无效操作");
        }
        //修改成功后退出登录并重定向到登录页面
        SecurityUtils.getSubject().logout();
        RedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + user.getUsername());
        //添加操作日志
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.MID);
        log.setContent("修改用户[" + user.getUsername() + "]密码");
        log.setOperationName("修改密码");
        operationService.addOperationLog(log);
        return RestResponse.success();
    }

    public RestResponse getUserInfo() {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        long userId = Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID));
        Map<String, Object> response = new HashMap<>(8);
        SysUser user = findUserById(userId);
        response.put("avatar", "favicon.ico");
        response.put("name", user.getRealName());
        String[] roles = roleService.loadRolesByUserId(userId);
        response.put("roles", roles);
        String[] permissions = permissionService.loadPermissionsByUserId(userId);
        response.put("permissions", permissions);
        Map<String, List<SysPermission>> menuInfo = permissionService.loadMenuInfoByUserId(userId);
        response.put("menuList", menuInfo);
        return RestResponse.success(response);
    }

    public RestResponse deleteUserBatch(UserIdListVo idListVo) {
        //获取审计信息
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        long currentUserId = Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID));
        // 操作日志记录账户名
        List<String> deletedUsers = new ArrayList<>();
        for (long userId : idListVo.getIds()) {
            SysUser user = findUserById(userId);
            // 判断是否是当前登录用户
            if (user.getId() == currentUserId) {
                return RestResponse.error("不能删除当前登录用户[" + user.getUsername() + "]");
            }
            // 判断是否是系统内置用户
            if (user.getUsername().equalsIgnoreCase(Constant.BUILD_IN_ACCOUNT1)) {
                return RestResponse.error("不能删除系统内置账户[admin]");
            }
            if (user.getUsername().equalsIgnoreCase(Constant.BUILD_IN_ACCOUNT2)) {
                return RestResponse.error("不能删除系统内置账户[letmein]");
            }
            deletedUsers.add(user.getUsername());
            //清空用户登录次数限制
            RedisUtil.delKey(Constant.PREFIX_SHIRO_RETRY_LIMIT + user.getUsername());
        }
        int effects = session.update("user.deleteUserBatch", JsonUtil.vo2map(idListVo));
        //添加操作日志
        OperationLog log = new OperationLog();
        log.setLogLevel(Dictionary.LogLevel.HIGH);
        log.setContent("批量删除用户[" + String.join(",", deletedUsers) + "]信息");
        log.setOperationName("删除用户");
        operationService.addOperationLog(log);
        return effects > 0 ? RestResponse.success("删除成功") : RestResponse.error("删除无效");
    }

    public RestResponse unLockedUserBatch(UserIdListVo idListVo) {
        try{
            List<Long> ids = idListVo.getIds();
            for (long id : ids) {
                SysUser lockUser = findUserById(id);
                if (lockUser != null) {
                    lockUser.setState(0);
                    modifyUserOnly(lockUser);
                    RedisUtil.delKey(Constant.PREFIX_SHIRO_RETRY_LIMIT + lockUser.getUsername());
                }
            }
            return RestResponse.success("批量删除成功");
        } catch (Exception e) {
            log.error("BaseStationService", e);
        }
        return RestResponse.error("批量删除失败");
    }

    public boolean isPwdEqualUsername(Map request) {
        String password = request.get("password").toString();
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String username = JwtUtil.getClaim(token, Constant.ACCOUNT);
        return password.equals(username);
    }
}
