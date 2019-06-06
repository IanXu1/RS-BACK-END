package com.teee.referencestation.api.sysmanage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teee.referencestation.api.sysmanage.model.*;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JsonUtil;
import com.teee.referencestation.utils.ObjUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zhanglei
 */
@Service
public class WarningTypeService extends BaseService {

    @Autowired
    private WarningLevelService levelService;

    public List<WarningTypeVo> loadCascadeData() {
        List<WarningTypeVo> root = session.selectList("warningType.loadCascadeData", -1);
        root.forEach(e -> {
            List<WarningTypeVo> children = session.selectList("warningType.loadCascadeData", e.getType());
            e.setChildren(children);
        });
        return root;
    }

    public WarningTypeVo loadWarningTypeBySubType(long type, long subType) {
        Map<String, Object> param = new HashMap<>(4);
        param.put("type", type);
        param.put("subType", subType);
        WarningTypeVo vo = session.selectOne("warningType.loadWarningTypeBySubType", param);
        return vo;
    }

    public RestResponse findWarningTypeList(Map request, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> resultList = session.selectList("warningType.getDataList", request);
        convert2vo(resultList);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(resultList);
        return RestResponse.success(pageInfo);
    }

    private void convert2vo(List<Map<String, Object>> resultList) {
        if (resultList != null) {
            for (Map<String, Object> objectMap : resultList) {
                int type = Integer.valueOf(objectMap.get("type").toString());
                int subType = Integer.valueOf(objectMap.get("subType").toString());
                WarningTypeVo typeVo = loadWarningTypeBySubType(-1, type);
                WarningTypeVo subTypeVo = loadWarningTypeBySubType(type, subType);
                if (ObjUtil.isEmpty(typeVo)) {
                    objectMap.put("typeName", "");
                } else if (ObjUtil.isEmpty(subType)) {
                    objectMap.put("typeName", typeVo.getName());
                } else {
                    objectMap.put("typeName", typeVo.getName() + "/" + subTypeVo.getName());
                }
                WarningLevelVo levelVo = levelService.loadByLevel(Integer.valueOf(objectMap.get("level").toString()));
                objectMap.put("levelName", ObjUtil.isEmpty(levelVo) ? "" : levelVo.getName());
            }
        }
    }

    public Map<String, Object> loadWarningTypeById(long id) {
        return session.selectOne("warningType.findById", id);
    }

    public int updateType(WarningType type) {
        return session.update("warningType.updateType", JsonUtil.vo2map(type));
    }

    public RestResponse loadPushUsers(PushUserPageQueryVo pageQueryVo) {
        PageHelper.startPage(pageQueryVo.getPageNum(), pageQueryVo.getPageSize());
        List<Map<String, Object>> resultList = session.selectList("user.getDataList", JsonUtil.vo2map(pageQueryVo));
        Map extendRequest = new HashMap(4);
        extendRequest.put("warningTypeId", pageQueryVo.getWarningTypeId());
        for (Map<String, Object> userMap : resultList) {
            long userId = Long.valueOf(userMap.get("id").toString());
            extendRequest.put("userId", userId);
            List<Map> extendRes = session.selectList("warningUserMapping.checkUserMappingExists", extendRequest);
            if (ObjUtil.isNotEmpty(extendRes)) {
                userMap.put("isBind", true);
            } else {
                userMap.put("isBind", false);
            }
        }
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(resultList);
        return RestResponse.success(pageInfo);
    }

    public RestResponse bindPushUser(WarningUserMappingVo mappingVo) {
        int effects = session.insert("warningUserMapping.insertUserMapping", mappingVo);
        return effects > 0 ? RestResponse.success() : RestResponse.error("绑定失败");
    }

    public RestResponse unbindPushUser(WarningUserMappingVo mappingVo) {
        int effects = session.delete("warningUserMapping.deleteUserMapping", mappingVo);
        return effects > 0 ? RestResponse.success() : RestResponse.error("解绑失败");
    }
}
