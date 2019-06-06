// **********************************************************************
//
// Copyright (c) 2003-2018 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.7.1
//
// <auto-generated>
//
// Generated from file `TEEEDCMeta.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public enum DCColumnType implements java.io.Serializable
{
    E_COLUMN_ERROR(0),
    E_AUDITINGINFO_MIN(10),
    E_AUDITINGINFO_ISDELETED(11),
    E_AUDITINGINFO_CREATED_BY(12),
    E_AUDITINGINFO_CREATED_TIMESTAMP(13),
    E_AUDITINGINFO_LAST_MODIFIED_BY(14),
    E_AUDITINGINFO_LAST_MODIFIED_TIMESTAMP(15),
    E_AUDITINGINFO_MAX(16),
    E_EXPIRYCTLINFO_MIN(20),
    E_EXPIRYCTLINFO_ISEXPIRYCTLON(21),
    E_EXPIRYCTLINFO_EXPIRY_CTL_START_TIMESTAMP(22),
    E_EXPIRYCTLINFO_EXPIRY_CTL_END_TIMESTAMP(23),
    E_EXPIRYCTLINFO_MAX(24),
    E_TERMINAL_ACCOUNT_MIN(100),
    E_TERMINAL_ACCOUNT_ID(101),
    E_TERMINAL_ACCOUNT_USERNAME(102),
    E_TERMINAL_ACCOUNT_PASSWORD(103),
    E_TERMINAL_ACCOUNT_TYPE(104),
    E_TERMINAL_ACCOUNT_MAX(105),
    E_AUTH_LOG_AUTH_MIN(200),
    E_AUTH_LOG_AUTH_ID(201),
    E_AUTH_LOG_AUTH_TIMESTAMP(202),
    E_AUTH_LOG_AUTH_USERNAME(203),
    E_AUTH_LOG_AUTH_IP(204),
    E_AUTH_LOG_AUTH_PORT(205),
    E_AUTH_LOG_AUTH_RESULT(206),
    E_AUTH_LOG_AUTH_MAX(207),
    E_BASE_STATION_INFO_MIN(300),
    E_BASE_STATION_INFO_ID(301),
    E_BASE_STATION_INFO_USERNAME(302),
    E_BASE_STATION_INFO_PASSWORD(303),
    E_BASE_STATION_INFO_NAME(304),
    E_BASE_STATION_INFO_NAMEPLATE(305),
    E_BASE_STATION_INFO_POSITION(306),
    E_BASE_STATION_INFO_RANGE(307),
    E_BASE_STATION_INFO_MAX(308),
    E_WARNING_INFO_MIN(400),
    E_WARNING_INFO_ID(401),
    E_WARNING_INFO_ST(402),
    E_WARNING_INFO_LVL(403),
    E_WARNING_INFO_TYPE(404),
    E_WARNING_INFO_SUB_TYPE(405),
    E_WARNING_INFO_DEV_ID(406),
    E_WARNING_INFO_DEV_SNO(407),
    E_WARNING_INFO_BD_SNO(408),
    E_WARNING_INFO_BD_TYPE(409),
    E_WARNING_INFO_BD_SLOT_ID(410),
    E_WARNING_INFO_LOCOMOTIVE_ID(411),
    E_WARNING_INFO_LOCOMOTIVE_SNO(412),
    E_WARNING_INFO_OCCURED_SNO(413),
    E_WARNING_INFO_OCCURED_RECV_TIME_TS(414),
    E_WARNING_INFO_OCCURED_TIME_TS(415),
    E_WARNING_INFO_OCCURED_CONTENT(416),
    E_WARNING_INFO_OCCURED_LON(417),
    E_WARNING_INFO_OCCURED_LAT(418),
    E_WARNING_INFO_OCCURED_KM_POST(419),
    E_WARNING_INFO_OCCURED_SPEED(420),
    E_WARNING_INFO_CLR_SNO(421),
    E_WARNING_INFO_CLR_RECV_TIME_TS(422),
    E_WARNING_INFO_CLR_TIME_TS(423),
    E_WARNING_INFO_CLR_CONTENT(424),
    E_WARNING_INFO_CLR_LON(425),
    E_WARNING_INFO_CLR_LAT(426),
    E_WARNING_INFO_CLR_KM_POST(427),
    E_WARNING_INFO_CLR_SPEED(428),
    E_WARNING_INFO_IS_DELETED(429),
    E_WARNING_INFO_MAX(430),
    E_EVENTINFO_MIN(500),
    E_EVENTINFO_ID(501),
    E_EVENTINFO_LVL(502),
    E_EVENTINFO_TYPE(503),
    E_EVENTINFO_DEV_ID(504),
    E_EVENTINFO_DEV_SNO(505),
    E_EVENTINFO_BD_SNO(506),
    E_EVENTINFO_BD_TYPE(507),
    E_EVENTINFO_BD_SLOT_ID(508),
    E_EVENTINFO_LOCOMOTIVE_ID(509),
    E_EVENTINFO_LOCOMOTIVE_SNO(510),
    E_EVENTINFO_OCCURED_SNO(511),
    E_EVENTINFO_OCCURED_RECV_TIME_TS(512),
    E_EVENTINFO_OCCURED_TIME_TS(513),
    E_EVENTINFO_OCCURED_CONTENT(514),
    E_EVENTINFO_OCCURED_LON(515),
    E_EVENTINFO_OCCURED_LAT(516),
    E_EVENTINFO_OCCURED_KM_POST(517),
    E_EVENTINFO_OCCURED_SPEED(518),
    E_EVENTINFO_IS_DELETED(519),
    E_EVENTINFO_MAX(520);

    public int value()
    {
        return _value;
    }

    public static DCColumnType valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return E_COLUMN_ERROR;
        case 10:
            return E_AUDITINGINFO_MIN;
        case 11:
            return E_AUDITINGINFO_ISDELETED;
        case 12:
            return E_AUDITINGINFO_CREATED_BY;
        case 13:
            return E_AUDITINGINFO_CREATED_TIMESTAMP;
        case 14:
            return E_AUDITINGINFO_LAST_MODIFIED_BY;
        case 15:
            return E_AUDITINGINFO_LAST_MODIFIED_TIMESTAMP;
        case 16:
            return E_AUDITINGINFO_MAX;
        case 20:
            return E_EXPIRYCTLINFO_MIN;
        case 21:
            return E_EXPIRYCTLINFO_ISEXPIRYCTLON;
        case 22:
            return E_EXPIRYCTLINFO_EXPIRY_CTL_START_TIMESTAMP;
        case 23:
            return E_EXPIRYCTLINFO_EXPIRY_CTL_END_TIMESTAMP;
        case 24:
            return E_EXPIRYCTLINFO_MAX;
        case 100:
            return E_TERMINAL_ACCOUNT_MIN;
        case 101:
            return E_TERMINAL_ACCOUNT_ID;
        case 102:
            return E_TERMINAL_ACCOUNT_USERNAME;
        case 103:
            return E_TERMINAL_ACCOUNT_PASSWORD;
        case 104:
            return E_TERMINAL_ACCOUNT_TYPE;
        case 105:
            return E_TERMINAL_ACCOUNT_MAX;
        case 200:
            return E_AUTH_LOG_AUTH_MIN;
        case 201:
            return E_AUTH_LOG_AUTH_ID;
        case 202:
            return E_AUTH_LOG_AUTH_TIMESTAMP;
        case 203:
            return E_AUTH_LOG_AUTH_USERNAME;
        case 204:
            return E_AUTH_LOG_AUTH_IP;
        case 205:
            return E_AUTH_LOG_AUTH_PORT;
        case 206:
            return E_AUTH_LOG_AUTH_RESULT;
        case 207:
            return E_AUTH_LOG_AUTH_MAX;
        case 300:
            return E_BASE_STATION_INFO_MIN;
        case 301:
            return E_BASE_STATION_INFO_ID;
        case 302:
            return E_BASE_STATION_INFO_USERNAME;
        case 303:
            return E_BASE_STATION_INFO_PASSWORD;
        case 304:
            return E_BASE_STATION_INFO_NAME;
        case 305:
            return E_BASE_STATION_INFO_NAMEPLATE;
        case 306:
            return E_BASE_STATION_INFO_POSITION;
        case 307:
            return E_BASE_STATION_INFO_RANGE;
        case 308:
            return E_BASE_STATION_INFO_MAX;
        case 400:
            return E_WARNING_INFO_MIN;
        case 401:
            return E_WARNING_INFO_ID;
        case 402:
            return E_WARNING_INFO_ST;
        case 403:
            return E_WARNING_INFO_LVL;
        case 404:
            return E_WARNING_INFO_TYPE;
        case 405:
            return E_WARNING_INFO_SUB_TYPE;
        case 406:
            return E_WARNING_INFO_DEV_ID;
        case 407:
            return E_WARNING_INFO_DEV_SNO;
        case 408:
            return E_WARNING_INFO_BD_SNO;
        case 409:
            return E_WARNING_INFO_BD_TYPE;
        case 410:
            return E_WARNING_INFO_BD_SLOT_ID;
        case 411:
            return E_WARNING_INFO_LOCOMOTIVE_ID;
        case 412:
            return E_WARNING_INFO_LOCOMOTIVE_SNO;
        case 413:
            return E_WARNING_INFO_OCCURED_SNO;
        case 414:
            return E_WARNING_INFO_OCCURED_RECV_TIME_TS;
        case 415:
            return E_WARNING_INFO_OCCURED_TIME_TS;
        case 416:
            return E_WARNING_INFO_OCCURED_CONTENT;
        case 417:
            return E_WARNING_INFO_OCCURED_LON;
        case 418:
            return E_WARNING_INFO_OCCURED_LAT;
        case 419:
            return E_WARNING_INFO_OCCURED_KM_POST;
        case 420:
            return E_WARNING_INFO_OCCURED_SPEED;
        case 421:
            return E_WARNING_INFO_CLR_SNO;
        case 422:
            return E_WARNING_INFO_CLR_RECV_TIME_TS;
        case 423:
            return E_WARNING_INFO_CLR_TIME_TS;
        case 424:
            return E_WARNING_INFO_CLR_CONTENT;
        case 425:
            return E_WARNING_INFO_CLR_LON;
        case 426:
            return E_WARNING_INFO_CLR_LAT;
        case 427:
            return E_WARNING_INFO_CLR_KM_POST;
        case 428:
            return E_WARNING_INFO_CLR_SPEED;
        case 429:
            return E_WARNING_INFO_IS_DELETED;
        case 430:
            return E_WARNING_INFO_MAX;
        case 500:
            return E_EVENTINFO_MIN;
        case 501:
            return E_EVENTINFO_ID;
        case 502:
            return E_EVENTINFO_LVL;
        case 503:
            return E_EVENTINFO_TYPE;
        case 504:
            return E_EVENTINFO_DEV_ID;
        case 505:
            return E_EVENTINFO_DEV_SNO;
        case 506:
            return E_EVENTINFO_BD_SNO;
        case 507:
            return E_EVENTINFO_BD_TYPE;
        case 508:
            return E_EVENTINFO_BD_SLOT_ID;
        case 509:
            return E_EVENTINFO_LOCOMOTIVE_ID;
        case 510:
            return E_EVENTINFO_LOCOMOTIVE_SNO;
        case 511:
            return E_EVENTINFO_OCCURED_SNO;
        case 512:
            return E_EVENTINFO_OCCURED_RECV_TIME_TS;
        case 513:
            return E_EVENTINFO_OCCURED_TIME_TS;
        case 514:
            return E_EVENTINFO_OCCURED_CONTENT;
        case 515:
            return E_EVENTINFO_OCCURED_LON;
        case 516:
            return E_EVENTINFO_OCCURED_LAT;
        case 517:
            return E_EVENTINFO_OCCURED_KM_POST;
        case 518:
            return E_EVENTINFO_OCCURED_SPEED;
        case 519:
            return E_EVENTINFO_IS_DELETED;
        case 520:
            return E_EVENTINFO_MAX;
        }
        return null;
    }

    private DCColumnType(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 520);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, DCColumnType v)
    {
        if(v == null)
        {
            ostr.writeEnum(DCColumnType.E_COLUMN_ERROR.value(), 520);
        }
        else
        {
            ostr.writeEnum(v.value(), 520);
        }
    }

    public static DCColumnType ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(520);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<DCColumnType> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, DCColumnType v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<DCColumnType> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            return java.util.Optional.of(ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static DCColumnType validate(int v)
    {
        final DCColumnType e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}