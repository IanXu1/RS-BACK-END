package com.teee.referencestation.config.shiro;

import com.teee.referencestation.api.user.model.SysPermission;
import com.teee.referencestation.api.user.model.SysRole;
import com.teee.referencestation.api.user.model.SysUser;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.config.shiro.jwt.JwtToken;
import com.teee.referencestation.utils.JwtUtil;
import com.teee.referencestation.utils.ObjUtil;
import com.teee.referencestation.utils.RedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义Realm
 * @author zhanglei
 */
@Service
public class UserRealm extends AuthorizingRealm {

    private final SqlSessionTemplate session;

    @Autowired
    public UserRealm(SqlSessionTemplate session) {
        this.session = session;
        setCachingEnabled(false);
    }

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        long userId = Long.valueOf(JwtUtil.getClaim(principals.toString(), Constant.USER_ID));
        List<SysRole> roles = session.selectList("role.findRoleByUserId", userId);
        // 查询用户角色
        roles.forEach(
                role -> {
                    authorizationInfo.addRole(role.getRoleName());
                    List<SysPermission> permissions = session.selectList("permission.findPermissionByRoleId", role.getId());
                    permissions.forEach(
                            permission -> authorizationInfo.addStringPermission(permission.getName())
                    );
                }
        );
        return authorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        // 帐号为空
        if (ObjUtil.isEmpty(account)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        // 查询用户是否存在
        SysUser user = session.selectOne("user.findUserByUsername", account);
        if (user == null) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && RedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = RedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }
}
