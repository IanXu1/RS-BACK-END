package com.teee.referencestation.rpc.ice.util;

import com.teee.referencestation.common.constant.Constant;
import com.teee.referencestation.rpc.ice.teee.*;
import com.teee.referencestation.utils.JwtUtil;
import com.teee.referencestation.utils.ObjUtil;
import com.zeroc.Ice.EndpointSelectionType;
import org.apache.shiro.SecurityUtils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.*;

/**
 * @author zhanglei
 */
@SuppressWarnings("ALL")
public class IceUtil {

    private static final String NUMBER_REGEX = "^\\d{13}$";
    private static TEEERSDCAPIServantPrx servantPrx;

    public static TEEERSDCAPIServantPrx getApiServantPrx() {
        if (servantPrx == null) {
            java.util.List<String> extraArgs = new java.util.ArrayList<>();
            String[] args = new String[]{};
            com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "ice/config.client", extraArgs);
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("TEEERSDCAPIServant");
            servantPrx = TEEERSDCAPIServantPrx.checkedCast(base);
            servantPrx.ice_connectionCached(false);
            servantPrx.ice_endpointSelection(EndpointSelectionType.Random);
            if (servantPrx == null) {
                throw new Error("Invalid proxy");
            }
        }
        return servantPrx;
    }

    /**
     * 传入的map中注意时间字段修改值为时间戳
     *
     * @param queryParams
     * @return
     */
    public static WhereCond[] generateWhereCond(Map<String, Object> queryParams) {
        List<WhereCond> whereCondList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            String key = entry.getKey();
            if (ObjUtil.isNotEmpty(key) && key.contains("_")) {
                WhereCond whereCond = new WhereCond();
                whereCond.setCondConnectByMode(CondConnectByMode.E_CONNECTBY_AND);
                String[] opAndCol = key.split("_");
                String operator = opAndCol[0];
                whereCond.setWhereCondType(OperatorUtil.getOperator(operator));
                String columnName = "";
                //防止前端传入下划线的值导致数据截断
                for (int i = 1; i < opAndCol.length; i++) {
                    columnName += opAndCol[i];
                }
                whereCond.setDcColumnType(ColumnUtil.getColumnType(columnName));
                if (ObjUtil.isEmpty(entry.getValue())) {
                    continue;
                }
                if (whereCond.getWhereCondType() == WhereCondType.E_COND_LIKE) {
                    whereCond.setValues(ValueUtil.getValue(entry.getValue().toString(), true));
                } else {
                    whereCond.setValues(ValueUtil.getValue(entry.getValue().toString(), false));
                }

                whereCondList.add(whereCond);
            }
        }
        return whereCondList.stream().toArray(WhereCond[]::new);
    }


    /**
     * 生成审计信息，如果auditingInfo为空，则代表新增。反正为更新
     *
     * @param auditingInfo
     * @param isDeleted
     * @return
     */
    public static AuditingInfo generateAuditingInfo(AuditingInfo auditingInfo, boolean isDeleted) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        if (auditingInfo == null) {
            AuditingInfo auditing = new AuditingInfo();
            String userId = ObjUtil.isEmpty(token) ? "-1" : JwtUtil.getClaim(token, Constant.USER_ID);
            auditing.setCreatedBy(ObjUtil.isEmpty(userId) ? -1 : Long.valueOf(userId));
            auditing.setCreatedTimeStamp(System.currentTimeMillis());
            auditing.setIsDeleted(false);
            return auditing;
        } else {
            auditingInfo.setLastModifiedBy(Long.valueOf(JwtUtil.getClaim(token, Constant.USER_ID)));
            auditingInfo.setLastModifiedTimeStamp(System.currentTimeMillis());
            auditingInfo.setIsDeleted(isDeleted);
            return auditingInfo;
        }
    }

    /**
     * 比较两个对象中属性的变化，并输出map
     *
     * @param obj1      原始值
     * @param obj2      变化后的值
     * @param ignoreArr 需要忽略的字段名字
     * @return
     */
    public static Map<String, Object> generateChangeRows(Object obj1, Object obj2, String[] ignoreArr) {
        try {
            Map<String, Object> map = new HashMap<>(8);
            List<String> ignoreList = null;
            if (ignoreArr != null && ignoreArr.length > 0) {
                ignoreList = Arrays.asList(ignoreArr);
            }
            // 只有两个对象都是同一类型的才有可比性
            if (obj1.getClass() == obj2.getClass()) {
                Class clazz = obj1.getClass();
                // 获取object的属性描述
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    // 属性名
                    String name = pd.getName();
                    // 如果当前属性选择忽略比较，跳到下一次循环
                    if (ignoreList != null && ignoreList.contains(name)) {
                        continue;
                    }
                    // get方法
                    Method readMethod = pd.getReadMethod();

                    // 在obj1上调用get方法等同于获得obj1的属性值
                    Object o1 = readMethod.invoke(obj1);
                    // 在obj2上调用get方法等同于获得obj2的属性值
                    Object o2 = readMethod.invoke(obj2);

                    if (o1 == null && o2 == null) {
                        continue;
                    } else if (o1 == null || o2 == null) {
                        map.put(name, o2);
                        continue;
                    }
                    //如果是数组，不论有无改变，都默认为有变化
                    if (o1.getClass().isArray()) {
                        map.put(name, o2);
                        continue;
                    }
                    //通过equal比较异同
                    if (!o1.equals(o2)) {
                        map.put(name, o2);
                    }
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ExpiryCtlInfo generateExpiryCtlInfo(String expireStartTime, String expireEndTime) {
        ExpiryCtlInfo expiryCtlInfo = new ExpiryCtlInfo();
        try {
            long expireStartDate;
            long expireEndDate;
            if (expireStartTime.matches(NUMBER_REGEX) && expireEndTime.matches(NUMBER_REGEX)) {
                // 时间戳数字字符串直接转换
                expireStartDate = Long.valueOf(expireStartTime);
                expireEndDate = Long.valueOf(expireEndTime);
            } else {
                expireStartDate = Instant.parse(expireStartTime).toEpochMilli();
                expireEndDate = Instant.parse(expireEndTime).toEpochMilli();
            }
            expiryCtlInfo.setIsExpiryCtlOn(true);
            expiryCtlInfo.setExpiryCtlStartTimeStamp(expireStartDate);
            expiryCtlInfo.setExpiryCtlEndTimeStamp(expireEndDate);
        } catch (Exception e) {
            expiryCtlInfo.setIsExpiryCtlOn(false);
        }
        return expiryCtlInfo;
    }
}
