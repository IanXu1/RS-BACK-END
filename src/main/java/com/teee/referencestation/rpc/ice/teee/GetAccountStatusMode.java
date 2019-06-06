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
// Generated from file `TEEEDCExtd.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public enum GetAccountStatusMode implements java.io.Serializable
{
    E_GETACCOUNTSTATUSMODE_CUSTOM(1),
    E_GETACCOUNTSTATUSMODE_ALL_TM(2),
    E_GETACCOUNTSTATUSMODE_ALL_BS(3),
    E_GETACCOUNTSTATUSMODE_ALL_TM_BS(4);

    public int value()
    {
        return _value;
    }

    public static GetAccountStatusMode valueOf(int v)
    {
        switch(v)
        {
        case 1:
            return E_GETACCOUNTSTATUSMODE_CUSTOM;
        case 2:
            return E_GETACCOUNTSTATUSMODE_ALL_TM;
        case 3:
            return E_GETACCOUNTSTATUSMODE_ALL_BS;
        case 4:
            return E_GETACCOUNTSTATUSMODE_ALL_TM_BS;
        }
        return null;
    }

    private GetAccountStatusMode(int v)
    {
        _value = v;
    }

    public void ice_write(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeEnum(_value, 4);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, GetAccountStatusMode v)
    {
        if(v == null)
        {
            ostr.writeEnum(GetAccountStatusMode.E_GETACCOUNTSTATUSMODE_CUSTOM.value(), 4);
        }
        else
        {
            ostr.writeEnum(v.value(), 4);
        }
    }

    public static GetAccountStatusMode ice_read(com.zeroc.Ice.InputStream istr)
    {
        int v = istr.readEnum(4);
        return validate(v);
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<GetAccountStatusMode> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    public static void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, GetAccountStatusMode v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.Size))
        {
            ice_write(ostr, v);
        }
    }

    public static java.util.Optional<GetAccountStatusMode> ice_read(com.zeroc.Ice.InputStream istr, int tag)
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

    private static GetAccountStatusMode validate(int v)
    {
        final GetAccountStatusMode e = valueOf(v);
        if(e == null)
        {
            throw new com.zeroc.Ice.MarshalException("enumerator value " + v + " is out of range");
        }
        return e;
    }

    private final int _value;
}