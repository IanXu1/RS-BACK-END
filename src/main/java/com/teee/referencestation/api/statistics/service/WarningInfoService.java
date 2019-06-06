package com.teee.referencestation.api.statistics.service;

import com.teee.referencestation.api.basestation.service.BaseStationService;
import com.teee.referencestation.api.statistics.model.WarningInfoVo;
import com.teee.referencestation.api.sysmanage.model.WarningLevelVo;
import com.teee.referencestation.api.sysmanage.model.WarningTypeVo;
import com.teee.referencestation.api.sysmanage.model.WarningVisibleVo;
import com.teee.referencestation.api.sysmanage.service.WarningLevelService;
import com.teee.referencestation.api.sysmanage.service.WarningTypeService;
import com.teee.referencestation.api.sysmanage.service.WarningVisibleService;
import com.teee.referencestation.api.user.model.SysUser;
import com.teee.referencestation.api.user.service.UserService;
import com.teee.referencestation.common.base.service.BaseService;
import com.teee.referencestation.common.base.service.MailService;
import com.teee.referencestation.common.base.service.ShortMessageService;
import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.rpc.ice.teee.*;
import com.teee.referencestation.rpc.ice.util.IceUtil;
import com.teee.referencestation.rpc.ice.util.PageInfo;
import com.teee.referencestation.utils.DateUtil;
import com.teee.referencestation.utils.JwtUtil;
import com.teee.referencestation.utils.ObjUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Line;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanglei
 */
@Service
public class WarningInfoService extends BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarningInfoService.class);

    @Autowired
    private MailService mailService;
    @Autowired
    private ShortMessageService smsService;
    @Autowired
    private BaseStationService stationService;
    @Autowired
    private WarningTypeService typeService;
    @Autowired
    private WarningLevelService levelService;
    @Autowired
    private WarningVisibleService visibleService;
    @Autowired
    private UserService userService;

    public RestResponse findWarningInfoList(Map<String, Object> queryParams, Integer pageNum, Integer pageSize) {
        //判断当前登录用户duty
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        long userId = Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID));
        SysUser user = userService.findUserById(userId);
        List<Integer> subTypeList = visibleService.findAllTypeByDuty(user.getDuty());
        if (ObjUtil.isEmpty(subTypeList)) {
            PageInfo pageInfo = new PageInfo<>(1, pageSize, 0);
            pageInfo.initPageInfo(Collections.emptyList());
            return RestResponse.success(pageInfo);
        }
        // 根据duty过滤客户能看到的告警
        queryParams.put("in_subWarningType", StringUtils.join(subTypeList, ","));
        //构造排序
        OrderByPair pair = new OrderByPair();
        pair.setDcColumnType(DCColumnType.E_WARNING_INFO_OCCURED_TIME_TS);
        pair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{pair};

        RestResponse restResponse = new RestResponse();
        try {
            PageInfo pageInfo = super.queryPageInfoNoCache(queryParams, pageNum, pageSize, DCTableType.E_TABLE_WARNING_INFO,
                    orderByPairs, WarningInfo.class);
            restResponse.setCode(HttpStatus.OK.value());
            handlePageInfo(pageInfo, user.getDuty());
            restResponse.setResult(pageInfo);
        } catch (Exception e) {
            LOGGER.error("WarningInfoService Exception", e);
            restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            restResponse.setResult(null);
            restResponse.setMsg("分页查询出错");
        }
        return restResponse;
    }

    private void handlePageInfo(PageInfo pageInfo, int duty) {
        List<WarningInfoVo> warningInfoVoList = handleWarningInfoVo(pageInfo.getList(), duty);
        pageInfo.setList(warningInfoVoList);
    }

    private List<WarningInfoVo> handleWarningInfoVo(List<WarningInfo> warningInfoList, int duty) {
        List<WarningInfoVo> warningInfoVoList = new ArrayList<>();
        for (WarningInfo warningInfo : warningInfoList) {
            WarningInfoVo vo = new WarningInfoVo();
            vo.setId(warningInfo.getWarning_info_id());
            vo.setWarningType(warningInfo.getWarning_type());
            vo.setSubWarningType(warningInfo.getWarning_sub_type());
            WarningTypeVo typeVo = typeService.loadWarningTypeBySubType(-1, warningInfo.getWarning_type());
            WarningTypeVo subTypeVo = typeService.loadWarningTypeBySubType(warningInfo.getWarning_type(), warningInfo.getWarning_sub_type());
            if (ObjUtil.isEmpty(typeVo)) {
                vo.setTypeName("");
            } else if (ObjUtil.isEmpty(subTypeVo)) {
                vo.setTypeName(typeVo.getName());
                vo.setWarningLevel(warningInfo.getWarning_lvl().value());
                vo.setLevelName("未知");
            } else {
                Map<String, Object> request = new HashMap<>(4);
                request.put("typeId", subTypeVo.getId());
                request.put("duty", duty);
                WarningVisibleVo visibleVo = visibleService.findByTypeId(request);
                if (ObjUtil.isEmpty(visibleVo) || ObjUtil.isEmpty(visibleVo.getContent())) {
                    vo.setTypeName(typeVo.getName() + "/" + subTypeVo.getName());
                } else {
                    vo.setTypeName(visibleVo.getContent());
                }
                WarningLevelVo levelVo = levelService.loadByLevel(subTypeVo.getLevel());
                vo.setWarningLevel(subTypeVo.getLevel());
                vo.setLevelName(levelVo.getName());
            }
            BaseStationInfo stationInfo = stationService.findById(warningInfo.getWarningDevInfo().dev_id);
            vo.setStationName(ObjUtil.isEmpty(stationInfo) ? "" : stationInfo.getName());
            vo.setOccurredTime(warningInfo.getOccured_time_ts());
            vo.setOccurredContent(warningInfo.getWarningContentOfOccured().content);
            vo.setClearTime(warningInfo.getClr_time_ts());
            vo.setClearContent(warningInfo.getWarningContentOfClr().content);
            warningInfoVoList.add(vo);
        }
        return warningInfoVoList;
    }

    public RestResponse pushWarning2User(Map<String, String> request) {

        String warningBase64 = request.get("warninfo");
        byte[] bytes = Base64.getDecoder().decode(warningBase64);
        WarningInfo warningInfo = super.deserialize4Ice(IceUtil.getApiServantPrx().ice_getCommunicator(), bytes, WarningInfo.class);
        long type = warningInfo.getWarning_type();
        long subType = warningInfo.getWarning_sub_type();
        Map queryReq = new HashMap(4);
        queryReq.put("type", type);
        queryReq.put("subType", subType);
        List<Map<String, Object>> resultList = session.selectList("warningType.getDataList", queryReq);
        if (ObjUtil.isNotEmpty(resultList)) {
            long typeId = Long.valueOf(resultList.get(0).get("id").toString());
            List<Long> userIdList = session.selectList("warningUserMapping.selectMappingByTypeId", typeId);
            if (ObjUtil.isEmpty(userIdList)) {
                return RestResponse.success("not found mapping users");
            }
            List<SysUser> userList = session.selectList("user.selectByIdList", userIdList);
            String phoneNumbers = userList.stream().map(e -> e.getCellPhoneNumber()).collect(Collectors.joining(","));
            // 发送邮件，如果抛出异常，不做处理
            sendEmail(userList, warningInfo);
            // 发送短信，短信发送失败则告诉调用方错误原因
            int retCode = sendSMS(phoneNumbers, warningInfo);
            if (retCode >= 0) {
                if (retCode != userList.size()) {
                    LOGGER.error("消息发送错误! 电话号码: {}, 错误原因: 部分号码未收到，实际发送成功数量: {}", phoneNumbers, retCode);
                }
            } else {
                LOGGER.error("消息发送错误! 电话号码: {}, 错误原因: {}", phoneNumbers, getErrorMsg(retCode));
                return new RestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), getErrorMsg(retCode));
            }
            return RestResponse.success();
        }
        return RestResponse.success();
    }

    private int sendSMS(String phoneNumbers, WarningInfo warningInfo) {
        int retCode = 0;
        if (warningInfo.getWarning_st().equals(E_WARNING_ST.E_WARNING_ST_OCCURED)) {
            retCode = smsService.sendMsgUtf8("【告警产生】" + warningInfo.getWarningContentOfOccured().content,
                    phoneNumbers);
        } else {
            retCode = smsService.sendMsgUtf8("【告警消除】" + warningInfo.getWarningContentOfClr().content,
                    phoneNumbers);
        }
        return retCode;
    }

    private void sendEmail(List<SysUser> userList, WarningInfo warningInfo) {
        try{
            if (warningInfo.getWarning_st().equals(E_WARNING_ST.E_WARNING_ST_OCCURED)) {
                for (SysUser sysUser : userList) {
                    if (ObjUtil.isNotEmpty(sysUser.getEmail())) {
                        mailService.sendSimpleMail(sysUser.getEmail(), "主题：地基增强告警产生",
                                warningInfo.getWarningContentOfOccured().content);
                    }
                }
            } else {
                for (SysUser sysUser : userList) {
                    if (ObjUtil.isNotEmpty(sysUser.getEmail())) {
                        mailService.sendSimpleMail(sysUser.getEmail(), "主题：地基增强告警消除",
                                warningInfo.getWarningContentOfClr().content);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("告警推送发生异常: [" + warningInfo + "]", e);
        }
    }

    public String getErrorMsg(int errorCode) {
        if (errorCode == -1) {
            return "没有该用户账户";
        } else if (errorCode == -2) {
            return "接口密钥不正确";
        } else if (errorCode == -3) {
            return "短信数量不足";
        } else if (errorCode == -4) {
            return "手机号格式不正确";
        } else if (errorCode == -21) {
            return "MD5接口密钥加密不正确";
        } else if (errorCode == -11) {
            return "该用户被禁用";
        } else if (errorCode == -14) {
            return "短信内容出现非法字符";
        } else if (errorCode == -41) {
            return "手机号码为空";
        } else if (errorCode == -42) {
            return "短信内容为空";
        } else if (errorCode == -51) {
            return "短信签名格式不正确";
        } else if (errorCode == -6) {
            return "IP限制";
        } else {
            return "未知原因" + errorCode;
        }
    }

    public RestResponse loadWarningInfoList4admin(Map queryParams, int pageNum, int pageSize) {
        //判断当前登录用户duty
        //构造排序
        OrderByPair pair = new OrderByPair();
        pair.setDcColumnType(DCColumnType.E_WARNING_INFO_OCCURED_TIME_TS);
        pair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{pair};

        RestResponse restResponse = new RestResponse();
        try {
            PageInfo pageInfo = super.queryPageInfoNoCache(queryParams, pageNum, pageSize, DCTableType.E_TABLE_WARNING_INFO,
                    orderByPairs, WarningInfo.class);
            restResponse.setCode(HttpStatus.OK.value());
            handlePageInfo(pageInfo);
            restResponse.setResult(pageInfo);
        } catch (Exception e) {
            LOGGER.error("WarningInfoService Exception", e);
            restResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            restResponse.setResult(null);
            restResponse.setMsg("分页查询出错");
        }
        return restResponse;
    }

    private void handlePageInfo(PageInfo pageInfo) {
        List<WarningInfoVo> warningInfoVoList = handleWarningInfoVo(pageInfo.getList());
        pageInfo.setList(warningInfoVoList);
    }

    private List<WarningInfoVo> handleWarningInfoVo(List<WarningInfo> warningInfoList) {
        List<WarningInfoVo> warningInfoVoList = new ArrayList<>();
        for (WarningInfo warningInfo : warningInfoList) {
            WarningInfoVo vo = new WarningInfoVo();
            vo.setId(warningInfo.getWarning_info_id());
            vo.setWarningType(warningInfo.getWarning_type());
            vo.setSubWarningType(warningInfo.getWarning_sub_type());
            WarningTypeVo typeVo = typeService.loadWarningTypeBySubType(-1, warningInfo.getWarning_type());
            WarningTypeVo subTypeVo = typeService.loadWarningTypeBySubType(warningInfo.getWarning_type(), warningInfo.getWarning_sub_type());
            if (ObjUtil.isEmpty(typeVo)) {
                vo.setTypeName("");
            } else if (ObjUtil.isEmpty(subTypeVo)) {
                vo.setTypeName(typeVo.getName());
                vo.setWarningLevel(warningInfo.getWarning_lvl().value());
                vo.setLevelName("未知");
            } else {
                vo.setTypeName(typeVo.getName() + "/" + subTypeVo.getName());
                WarningLevelVo levelVo = levelService.loadByLevel(subTypeVo.getLevel());
                vo.setWarningLevel(subTypeVo.getLevel());
                vo.setLevelName(levelVo.getName());
            }
            BaseStationInfo stationInfo = stationService.findById(warningInfo.getWarningDevInfo().dev_id);
            vo.setStationName(ObjUtil.isEmpty(stationInfo) ? "" : stationInfo.getName());
            vo.setOccurredTime(warningInfo.getOccured_time_ts());
            vo.setOccurredContent(warningInfo.getWarningContentOfOccured().content);
            vo.setClearTime(warningInfo.getClr_time_ts());
            vo.setClearContent(warningInfo.getWarningContentOfClr().content);
            warningInfoVoList.add(vo);
        }
        return warningInfoVoList;
    }

    public List<Map<String, Object>> loadExportInfo(Map queryParams) {
        List<Map<String, Object>> response = new ArrayList<>(64);
        //构造排序
        OrderByPair pair = new OrderByPair();
        pair.setDcColumnType(DCColumnType.E_WARNING_INFO_OCCURED_TIME_TS);
        pair.setOrderByMode(OrderByMode.E_ORDERBYMODE_DESC);
        OrderByPair[] orderByPairs = new OrderByPair[]{pair};
        PageInfo<WarningInfo> pageInfo = super.queryPageInfoNoCache(queryParams, 0, 1000, DCTableType.E_TABLE_WARNING_INFO,
                orderByPairs, WarningInfo.class);
        // 遍历构造数据
        List<WarningInfo> warningInfoList = pageInfo.getList();
        for (int i = 0; i < warningInfoList.size(); i++) {
            Map<String, Object> infoMap = new HashMap<>(8);
            WarningInfo warningInfo = warningInfoList.get(i);
            infoMap.put("seq", i + 1);
            infoMap.put("typeName", getTypeName(warningInfo.getWarning_type(), warningInfo.getWarning_sub_type()));

            WarningTypeVo subTypeVo = typeService.loadWarningTypeBySubType(warningInfo.getWarning_type(), warningInfo.getWarning_sub_type());
            if (ObjUtil.isNotEmpty(subTypeVo)) {
                WarningLevelVo levelVo = levelService.loadByLevel(subTypeVo.getLevel());
                infoMap.put("levelName", levelVo.getName());
                infoMap.put("level", subTypeVo.getLevel());
            } else {
                infoMap.put("levelName", "未知");
                infoMap.put("level", -1);
            }
            BaseStationInfo stationInfo = stationService.findById(warningInfo.getWarningDevInfo().dev_id);
            infoMap.put("stationName", ObjUtil.isEmpty(stationInfo) ? "" : stationInfo.getName());
            infoMap.put("occurredTime", DateUtil.formatDate(DateUtil.getDateTimeOfTimestamp(warningInfo.getOccured_time_ts())));
            infoMap.put("content", warningInfo.getWarningContentOfOccured().content);
            response.add(infoMap);
        }
        return response;
    }

    private String getTypeName(long warningType, long subType) {
        WarningTypeVo typeVo = typeService.loadWarningTypeBySubType(-1, warningType);
        WarningTypeVo subTypeVo = typeService.loadWarningTypeBySubType(warningType, subType);
        if (ObjUtil.isEmpty(typeVo)) {
            return "";
        } else if (ObjUtil.isEmpty(subTypeVo)) {
            return typeVo.getName();
        } else {
           return typeVo.getName() + "/" + subTypeVo.getName();
        }
    }
}
