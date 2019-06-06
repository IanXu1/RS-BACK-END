package com.teee.referencestation.api.statistics.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teee.referencestation.api.statistics.model.OperationLog;
import com.teee.referencestation.api.sysmanage.service.DictionaryService;
import com.teee.referencestation.api.user.model.SysUser;
import com.teee.referencestation.api.user.service.UserService;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.common.constant.Dictionary;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JwtUtil;
import com.teee.referencestation.utils.ObjUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

/**
 * @author zhanglei
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OperationLogService extends BaseService {

    @Autowired
    private DictionaryService dicService;
    @Autowired
    private UserService userService;

    private static final String QUERY_KEY_LESS = "lessOperateDate";
    private static final String QUERY_KEY_MORE = "moreOperateDate";

    /**
     * @param request
     * @return
     * @desc 分页查询操作日志列表信息
     */
    public RestResponse findOperationLogList(Map request, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        handleQueryParam(request);
        List<Map<String, Object>> resultList = session.selectList("operationLog.getDataList", request);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(resultList);
        convert2Vo(pageInfo);
        return RestResponse.success(pageInfo);
    }

    private void handleQueryParam(Map request) {
        if (ObjUtil.isNotEmpty(request.get(QUERY_KEY_LESS))) {
            long lessOperateDate = Long.valueOf(request.get(QUERY_KEY_LESS).toString());
            LocalDateTime lessTime = Instant.ofEpochMilli(lessOperateDate).atZone(ZoneId.systemDefault()).toLocalDateTime();
            request.put(QUERY_KEY_LESS, lessTime.toString());
        }
        if (ObjUtil.isNotEmpty(request.get(QUERY_KEY_MORE))) {
            long moreOperateDate = Long.valueOf(request.get(QUERY_KEY_MORE).toString());
            LocalDateTime moreTime = Instant.ofEpochMilli(moreOperateDate).atZone(ZoneId.systemDefault()).toLocalDateTime();
            request.put(QUERY_KEY_MORE, moreTime.toString());
        }
    }

    private void convert2Vo(PageInfo<Map<String, Object>> pageInfo) {
        if (ObjUtil.isNotEmpty(pageInfo.getList())) {
            for (Map<String, Object> map : pageInfo.getList()) {
                int logLevel = Integer.valueOf(map.get("logLevel").toString());
                String itemName = dicService.loadItemNameByDicCodeAndItemCode(Dictionary.LogLevel.DIC_CODE, logLevel);
                map.put("logLevel", itemName);
                long createBy = Long.valueOf(map.get("createdBy").toString());
                SysUser user = userService.findUserById(createBy);
                map.put("createdBy", user == null ? "" : user.getRealName());
            }
        }
    }


    /**
     * 保存操作日志
     * @param operationLog
     */
    public int addOperationLog(OperationLog operationLog) {
        String token =  (String) SecurityUtils.getSubject().getPrincipal();
        operationLog.setCreatedBy(token == null ? -1 : Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
        operationLog.setCreatedDate(LocalDateTime.now());
        return session.insert("operationLog.insertOperationLog", operationLog);
    }

    /**
     *
     * @param name
     * @param level
     * @param content
     * @return
     */
    public int addOperationLog(String name, int level, String content) {
        OperationLog log = new OperationLog();
        log.setLogLevel(level);
        log.setOperationName(name);
        log.setContent(content);
        return addOperationLog(log);
    }
}
