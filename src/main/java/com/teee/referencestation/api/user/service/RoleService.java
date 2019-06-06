package com.teee.referencestation.api.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teee.referencestation.api.user.model.*;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.BeanUtil;
import com.teee.referencestation.utils.JsonUtil;
import com.teee.referencestation.utils.JwtUtil;
import com.teee.referencestation.utils.ObjUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 * @date 2019-1-10 17:42:44
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService extends BaseService {
    /**
     * 通过用户ID查询用户具有的角色信息
     *
     * @param userId
     * @return
     */
    public List<SysRole> findRoleByUserId(long userId) {
        List<SysRole> roles = session.selectList("role.findRoleByUserId", userId);
        return roles;
    }

    public RestResponse loadRoleList(Map request, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> resultList = session.selectList("role.getDataList", request);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(resultList);
        return RestResponse.success(pageInfo);
    }

    public RestResponse loadAssignedUserByRoleId(Long roleId) {
        List<Long> userIds = session.selectList("role.findUserIdByRoleId", roleId);
        return RestResponse.success(userIds);
    }

    public RestResponse assignUser(RoleAssignVo assignVo) {
        List<Long> userIds = assignVo.getAssignUsers();
        Map condition = new HashMap(4);
        condition.put("roleId", assignVo.getRoleId());
        for (long userId : userIds) {
            condition.put("userId", userId);
            Map existsMap = session.selectOne("role.findMappingByRoleAndUser", condition);
            if (ObjUtil.isNotEmpty(existsMap)) {
                return RestResponse.error("该用户已绑定");
            }
        }
        int effects = session.insert("role.insertAssignUser", JsonUtil.vo2map(assignVo));
        return effects > 0 ? RestResponse.success() : RestResponse.error("绑定失败");
    }

    public RestResponse unbindUser(RoleUnbindVo unbindVo) {
        Map condition = new HashMap(4);
        condition.put("roleId", unbindVo.getRoleId());
        condition.put("userId", unbindVo.getUserId());
        Map existsMap = session.selectOne("role.findMappingByRoleAndUser", condition);
        if (ObjUtil.isEmpty(existsMap)) {
            return RestResponse.error("该用户未绑定");
        }
        int effects = session.delete("role.deleteAssignUser", JsonUtil.vo2map(unbindVo));
        return effects > 0 ? RestResponse.success() : RestResponse.error("解绑失败");
    }

    public RestResponse addRole(RoleAddVo addVo) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(addVo, role);
        //获取审计信息
        role.setDeleted(Constant.DATA_STATUS.AVAILABLE);
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        role.setCreatedBy(Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
        int effects = session.insert("role.insertRole", role);
        return effects > 0 ? RestResponse.success("新增角色成功") : RestResponse.error("无效的操作");
    }

    public RestResponse modifyRole(RoleModifyVo modifyVo) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(modifyVo, role);
        //获取审计信息
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        role.setLastModifiedBy(Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
        int effects = session.update("role.updateRole", role);
        return effects > 0 ? RestResponse.success("更新角色成功") : RestResponse.error("无效的操作");
    }

    public boolean isRoleNameExists(Map request) {
        List<Map<String, Object>> resultList = session.selectList("role.getRoleByName", request);
        return ObjUtil.isNotEmpty(resultList);
    }

    public RestResponse loadRoleById(long roleId) {
        SysRole role = session.selectOne("role.selectRoleById", roleId);
        return RestResponse.success(role);
    }

    public RestResponse deleteRole(long roleId) {
        if (roleId == 1) {
            return RestResponse.error("超级管理员角色不允许删除");
        }
        SysRole role = new SysRole();
        role.setId(roleId);
        role.setDeleted(Constant.DATA_STATUS.DELETED);
        int effects = session.delete("role.updateRole", role);
        return effects > 0 ? RestResponse.success("删除角色成功") : RestResponse.error("无效操作");
    }

    public String[] loadRolesByUserId(long userId) {
        List<String> roles = session.selectList("role.findRolesByUserId", userId);
        return roles.stream().toArray(String[]::new);
    }

    public RestResponse deleteRoleBatch(RoleIdListVo idListVo) {
        for (long id : idListVo.getIds()) {
            if (id == 1) {
                return RestResponse.error("超级管理员角色不允许删除");
            }
        }
        int effects = session.delete("role.deleteRoleBatch", JsonUtil.vo2map(idListVo));
        return effects > 0 ? RestResponse.success() : RestResponse.error("批量删除失败");
    }
}
