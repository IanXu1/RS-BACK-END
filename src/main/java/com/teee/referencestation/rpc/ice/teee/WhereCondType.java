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

public enum WhereCondType implements java.io.Serializable
{
    E_COND_ERROR(0),
    E_COND_EQ(1),
    E_COND_NEQ(2),
    E_COND_LIKE(3),
    E_COND_GT(4),
    E_COND_LT(5),
    E_COND_GTE(6),
    E_COND_LTE(7),
    E_COND_IN(8),
    E_COND_NOT_IN(9),
    E_COND_BETWEEN(12);

    public int value()
    {
        return _value;
    }

    public static WhereCondType valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return E_COND_ERROR;
        case 1:
            return E_COND_EQ;
        case 2:
            return E_COND_NEQ;
        case 3:
            return E_COND_LIKE;
        case 4:
            return E_COND_GT;
        case 5:
            return E_COND_LT;
        case 6:
            return E_COND_GTE;
        case 7:
            return E_COND_LTE;
        case 8:
            return E_COND_IN;
        case 9:
            return E_COND_NOT_IN;
        case 12:
            return E_COND_BETWEEN;
        }
        return null;
    }

    private WhereCondType(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 12);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, WhereCondType v)
    {
        if(v == null)
        {
            ostr.writeEnum(WhereCondType.E_COND_ERROR.value(), 12);
        }
        else
        {
            ostr.writeEnum(v.value(), 12);
        }
    }

    public static WhereCondType ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(12);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<WhereCondType> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, WhereCondType v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<WhereCondType> ice_read(com.zeroc.Ice.InputStream istr, int tag)
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

    private static WhereCondType validate(int v)
    {
        final WhereCondType e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}
