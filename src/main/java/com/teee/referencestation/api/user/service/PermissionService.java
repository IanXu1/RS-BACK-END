package com.teee.referencestation.api.user.service;

import com.teee.referencestation.api.user.model.PermissionGrantVo;
import com.teee.referencestation.api.user.model.PermissionTree;
import com.teee.referencestation.api.user.model.PermissionTreeVo;
import com.teee.referencestation.api.user.model.SysPermission;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JsonUtil;
import com.teee.referencestation.utils.ObjUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanglei
 * @date 2019-1-10 17:42:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionService extends BaseService {


    /**
     * 通过角色ID查询角色具有的权限
     * @param roleId
     * @return
     */
    public List<SysPermission> findPermissionByRoleId(int roleId) {
        List<SysPermission> permissions = session.selectList("permission.findPermissionByRoleId", roleId);
        return permissions;
    }


    /**
     * @desc 根据父级ID查询所有子集权限(菜单)信息
     * @param params
     * @return
     */
    public List<SysPermission> findPermissionByParentId(Map<String, Object> params) {
        List<SysPermission> permissions = session.selectList("permission.findPermissionByParentId", params);
        return permissions;
    }

    /**
     * @desc 构造符合前端要求的json格式
     * @param request
     * @return
     */
    public RestResponse loadAllPermission(Map request) {
        List<PermissionTree> permissions = session.selectList("permission.findAvailablePermission", request);

        // 获取所有节点
        List<PermissionTree> rootTree = permissions.stream().filter(permission -> permission.getPId() == -1).collect(Collectors.toList());
        // TODO 待优化
        List<PermissionTreeVo> treeVos = new ArrayList<>();
        rootTree.forEach(root -> {
            PermissionTreeVo vo = convert2Vo(root);
            List<PermissionTree> secondTrees = permissions.stream().filter(permission -> permission.getPId() == root.getId()).collect(Collectors.toList());
            if (ObjUtil.isNotEmpty(secondTrees)) {
                secondTrees.forEach(secondTree -> {
                    PermissionTreeVo secondVo = convert2Vo(secondTree);
                    vo.getChildren().add(secondVo);
                    List<PermissionTree> thirdTrees = permissions.stream().filter(permission -> permission.getPId() == secondVo.getId()).collect(Collectors.toList());
                    if (ObjUtil.isNotEmpty(thirdTrees)) {
                        thirdTrees.forEach(thirdTree -> {
                            secondVo.getChildren().add(convert2Vo(thirdTree));
                        });
                    }
                });
            }
            treeVos.add(vo);
        });

        // 获取被选中的keys
        long[] checkedKeys = permissions.stream().filter(permission -> permission.isChecked() == true)
                .mapToLong(permission -> permission.getId()).toArray();
        // 获取需要展开的keys
        long[] expandKeys = permissions.stream().filter(permission -> permission.getPId() == -1)
                .mapToLong(permission -> permission.getId()).toArray();
        // 构造返回数据
        Map response = new HashMap(4);
        response.put("list", treeVos);
        response.put("checkedKeys", checkedKeys);
        response.put("expandKeys", expandKeys);
        return RestResponse.success(response);
    }

    private PermissionTreeVo convert2Vo(PermissionTree tree) {
        PermissionTreeVo vo = new PermissionTreeVo();
        vo.setId(tree.getId());
        vo.setLabel(tree.getName());
        return vo;
    }

    public RestResponse grantPermission(PermissionGrantVo grantVo) {
        int delCount = session.delete("permission.deletePermission", JsonUtil.vo2map(grantVo));
        if (ObjUtil.isNotEmpty(grantVo.getPermissions())) {
            int effects = session.insert("permission.insertPermission", JsonUtil.vo2map(grantVo));
        }
        return RestResponse.success("权限赋予成功");
    }

    public RestResponse deletePermission(Map request) {
        int delCount = session.delete("permission.deletePermission", request);
        return RestResponse.success("权限清除成功");
    }

    public Map<String, List<SysPermission>> loadMenuInfoByUserId(long userId) {
        Map<String, List<SysPermission>> menuInfo = new LinkedHashMap<>(64);
        Map<String, Object> params = new HashMap<>(4);
        params.put("userId", userId);
        params.put("menuParentId", -1);

        List<SysPermission> rootMenu = findPermissionByParentId(params);
        rootMenu.forEach(root -> {
            params.put("menuParentId", root.getId());
            List<SysPermission> node = findPermissionByParentId(params);
            menuInfo.put(JsonUtil.toJSONString(root), node);
        });
        return menuInfo;
    }

    public String[] loadPermissionsByUserId(long userId) {
        List<String> permissions = session.selectList("permission.findPermissionsByUserId", userId);
        return permissions.stream().toArray(String[]::new);
    }
}
