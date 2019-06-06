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
// Generated from file `TEEEWarningInfo.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public enum E_WARNING_LVL implements java.io.Serializable
{
    E_WARNING_LVL_BAD_NOTDEFINE(0),
    E_WARNING_LVL_INFO(1),
    E_WARNING_LVL_WARN(2),
    E_WARNING_LVL_ERROR(3),
    E_WARNING_LVL_FATAL(4);

    public int value()
    {
        return _value;
    }

    public static E_WARNING_LVL valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return E_WARNING_LVL_BAD_NOTDEFINE;
        case 1:
            return E_WARNING_LVL_INFO;
        case 2:
            return E_WARNING_LVL_WARN;
        case 3:
            return E_WARNING_LVL_ERROR;
        case 4:
            return E_WARNING_LVL_FATAL;
        }
        return null;
    }

    private E_WARNING_LVL(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 4);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, E_WARNING_LVL v)
    {
        if(v == null)
        {
            ostr.writeEnum(E_WARNING_LVL.E_WARNING_LVL_BAD_NOTDEFINE.value(), 4);
        }
        else
        {
            ostr.writeEnum(v.value(), 4);
        }
    }

    public static E_WARNING_LVL ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(4);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<E_WARNING_LVL> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, E_WARNING_LVL v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<E_WARNING_LVL> ice_read(com.zeroc.Ice.InputStream istr, int tag)
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

    private static E_WARNING_LVL validate(int v)
    {
        final E_WARNING_LVL e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}