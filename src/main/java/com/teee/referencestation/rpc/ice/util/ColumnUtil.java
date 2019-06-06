package com.teee.referencestation.rpc.ice.util;

import com.teee.referencestation.rpc.ice.teee.DCColumnType;

/**
 * @author zhanglei
 */

@SuppressWarnings("ALL")
public enum ColumnUtil {

    ISDELETED("isDeleted"),
    CREATED_BY("createdBy"),
    CREATED_TIMESTAMP("createdTimeStamp"),
    LAST_MODIFIED_BY("lastModifiedBy"),
    LAST_MODIFIED_TIMESTAMP("lastModifiedTimeStamp"),
    ACCOUNT_ID("terminalAccountId"),
    ACCOUNT_USERNAME("accountUsername"),
    ACCOUNT_PASSWORD("accountPassword"),
    AUTH_ID("authLogId"),
    AUTH_TIMESTAMP("authTimeStamp"),
    AUTH_USERNAME("authUsername"),
    AUTH_IP("authIp"),
    AUTH_PORT("authPort"),
    AUTH_RESULT("authResult"),
    STATION_INFO_ID("stationId"),
    STATION_INFO_USERNAME("stationUsername"),
    STATION_INFO_PASSWORD("stationPassword"),
    STATION_INFO_NAME("stationName"),
    WARNING_INFO_ID("warningInfoId"),
    WARNING_INFO_MODE("warningInfoMode"),
    WARNING_BS_ID("warningBasestationId"),
    WARNING_INFO_TYPE("warningType"),
    SUB_WARNING_INFO_TYPE("subWarningType"),
    WARNING_OCCURED_TIME("warningOccurredTime"),
    WARNING_CLEAR_TIME("warningClearTime"),
    WARNING_INFO_LEVEL("warningLevel");

    private String colName;

    ColumnUtil(String colName) {
        this.colName = colName;
    }

    public static DCColumnType getColumnType(String colName) {
        if (ISDELETED.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_AUDITINGINFO_ISDELETED;
        } else if (CREATED_BY.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_AUDITINGINFO_CREATED_BY;
        } else if (CREATED_TIMESTAMP.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_AUDITINGINFO_CREATED_TIMESTAMP;
        } else if (LAST_MODIFIED_BY.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_AUDITINGINFO_LAST_MODIFIED_BY;
        } else if (LAST_MODIFIED_TIMESTAMP.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_AUDITINGINFO_LAST_MODIFIED_TIMESTAMP;
        } else if (ACCOUNT_ID.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_TERMINAL_ACCOUNT_ID;
        } else if (ACCOUNT_USERNAME.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_TERMINAL_ACCOUNT_USERNAME;
        } else if (ACCOUNT_PASSWORD.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_TERMINAL_ACCOUNT_PASSWORD;
        } else if (AUTH_ID.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_AUTH_LOG_AUTH_ID;
        } else if (AUTH_PORT.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_AUTH_LOG_AUTH_PORT;
        } else if (STATION_INFO_ID.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_BASE_STATION_INFO_ID;
        } else if (STATION_INFO_USERNAME.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_BASE_STATION_INFO_USERNAME;
        } else if (STATION_INFO_PASSWORD.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_BASE_STATION_INFO_PASSWORD;
        } else if (STATION_INFO_NAME.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_BASE_STATION_INFO_NAME;
        } else if (WARNING_INFO_ID.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_WARNING_INFO_ID;
        } else if (WARNING_INFO_MODE.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_WARNING_INFO_ST;
        } else if (WARNING_BS_ID.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_WARNING_INFO_DEV_ID;
        } else if (WARNING_INFO_TYPE.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_WARNING_INFO_TYPE;
        } else if (SUB_WARNING_INFO_TYPE.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_WARNING_INFO_SUB_TYPE;
        } else if (WARNING_OCCURED_TIME.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_WARNING_INFO_OCCURED_TIME_TS;
        } else if (WARNING_CLEAR_TIME.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_WARNING_INFO_CLR_TIME_TS;
        } else if (WARNING_INFO_LEVEL.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_WARNING_INFO_LVL;
        } else if (AUTH_TIMESTAMP.valueOf().equalsIgnoreCase(colName)) {
            return DCColumnType.E_AUTH_LOG_AUTH_TIMESTAMP;
        }
        return DCColumnType.E_COLUMN_ERROR;
    }

    public String valueOf() {
        return this.colName;
    }
}
