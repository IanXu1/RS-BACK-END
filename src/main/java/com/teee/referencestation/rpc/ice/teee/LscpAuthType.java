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

public enum LscpAuthType implements java.io.Serializable
{
    PSW_TYPE_NONE(0),
    PSW_TYPE_PLAIN_LANGUAGE(1),
    PSW_TYPE_MD5(2),
    PSW_TYPE_SHA1(3);

    public int value()
    {
        return _value;
    }

    public static LscpAuthType valueOf(int v)
    {
        switch(v)
        {
        case 0:
            return PSW_TYPE_NONE;
        case 1:
            return PSW_TYPE_PLAIN_LANGUAGE;
        case 2:
            return PSW_TYPE_MD5;
        case 3:
            return PSW_TYPE_SHA1;
        }
        return null;
    }

    private LscpAuthType(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 3);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, LscpAuthType v)
    {
        if(v == null)
        {
            ostr.writeEnum(LscpAuthType.PSW_TYPE_NONE.value(), 3);
        }
        else
        {
            ostr.writeEnum(v.value(), 3);
        }
    }

    public static LscpAuthType ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(3);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<LscpAuthType> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, LscpAuthType v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<LscpAuthType> ice_read(com.zeroc.Ice.InputStream istr, int tag)
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

    private static LscpAuthType validate(int v)
    {
        final LscpAuthType e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}
