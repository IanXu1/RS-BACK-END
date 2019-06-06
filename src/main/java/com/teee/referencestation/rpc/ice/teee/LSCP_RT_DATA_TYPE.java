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
// Generated from file `TEEELscpProtocal.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public enum LSCP_RT_DATA_TYPE implements java.io.Serializable
{
    E_RT_DATA_TYPE_WARNING(0),
    E_RT_DATA_TYPE_EVENT(1),
    E_RT_DATA_TYPE_ROADRECOARD(2);

    public int value()
    {
        return _value;
    }

    public static LSCP_RT_DATA_TYPE valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return E_RT_DATA_TYPE_WARNING;
        case 1:
            return E_RT_DATA_TYPE_EVENT;
        case 2:
            return E_RT_DATA_TYPE_ROADRECOARD;
        }
        return null;
    }

    private LSCP_RT_DATA_TYPE(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 2);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, LSCP_RT_DATA_TYPE v)
    {
        if(v == null)
        {
            ostr.writeEnum(LSCP_RT_DATA_TYPE.E_RT_DATA_TYPE_WARNING.value(), 2);
        }
        else
        {
            ostr.writeEnum(v.value(), 2);
        }
    }

    public static LSCP_RT_DATA_TYPE ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(2);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<LSCP_RT_DATA_TYPE> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, LSCP_RT_DATA_TYPE v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<LSCP_RT_DATA_TYPE> ice_read(com.zeroc.Ice.InputStream istr, int tag)
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

    private static LSCP_RT_DATA_TYPE validate(int v)
    {
        final LSCP_RT_DATA_TYPE e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}
