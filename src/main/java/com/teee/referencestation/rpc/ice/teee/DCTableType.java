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

public enum DCTableType implements java.io.Serializable
{
    E_TABLE_ERROR(0),
    E_TABLE_TERMINAL_ACCOUNT(1),
    E_TABLE_AUTH_LOG(2),
    E_TABLE_BASE_STATION_INFO(3),
    E_TABLE_WARNING_INFO(4),
    E_TABLE_EVENT_INFO(5);

    public int value()
    {
        return _value;
    }

    public static DCTableType valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return E_TABLE_ERROR;
        case 1:
            return E_TABLE_TERMINAL_ACCOUNT;
        case 2:
            return E_TABLE_AUTH_LOG;
        case 3:
            return E_TABLE_BASE_STATION_INFO;
        case 4:
            return E_TABLE_WARNING_INFO;
        case 5:
            return E_TABLE_EVENT_INFO;
        }
        return null;
    }

    private DCTableType(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 5);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, DCTableType v)
    {
        if(v == null)
        {
            ostr.writeEnum(DCTableType.E_TABLE_ERROR.value(), 5);
        }
        else
        {
            ostr.writeEnum(v.value(), 5);
        }
    }

    public static DCTableType ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(5);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<DCTableType> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, DCTableType v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<DCTableType> ice_read(com.zeroc.Ice.InputStream istr, int tag)
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

    private static DCTableType validate(int v)
    {
        final DCTableType e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}
