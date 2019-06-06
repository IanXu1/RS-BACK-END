package com.teee.referencestation.api.sysmanage.service;

import com.teee.referencestation.api.sysmanage.model.WarningVisibleVo;
import com.teee.referencestation.common.base.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@Service
public class WarningVisibleService extends BaseService {

    public List<Integer> findAllTypeByDuty(int duty) {
        return session.selectList("warningVisible.findAllTypeByDuty", duty);
    }

    public WarningVisibleVo findByTypeId(Map<String, Object> request) {
        return session.selectOne("warningVisible.findByTypeId", request);
    }
}
