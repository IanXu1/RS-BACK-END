package com.teee.referencestation.api.sysmanage.service;

import com.teee.referencestation.api.sysmanage.model.WarningLevelVo;
import com.teee.referencestation.common.base.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanglei
 */
@Service
public class WarningLevelService extends BaseService {


    public WarningLevelVo loadByLevel(int level) {
        return session.selectOne("warningLevel.loadByLevel", level);
    }

    public List<WarningLevelVo> loadAllLevel() {
        return session.selectList("warningLevel.loadAllLevel");
    }
}
