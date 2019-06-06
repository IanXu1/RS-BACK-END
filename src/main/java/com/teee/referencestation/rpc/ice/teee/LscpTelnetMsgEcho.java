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

public class LscpTelnetMsgEcho implements Cloneable,
                                          java.io.Serializable
{
    public String msg;

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public LscpTelnetMsgEcho()
    {
        this.msg = "";
    }

    public LscpTelnetMsgEcho(String msg)
    {
        this.msg = msg;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        LscpTelnetMsgEcho r = null;
        if(rhs instanceof LscpTelnetMsgEcho)
        {
            r = (LscpTelnetMsgEcho)rhs;
        }

        if(r != null)
        {
            if(this.msg != r.msg)
            {
                if(this.msg == null || r.msg == null || !this.msg.equals(r.msg))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::LscpTelnetMsgEcho");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, msg);
        return h_;
    }

    public LscpTelnetMsgEcho clone()
    {
        LscpTelnetMsgEcho c = null;
        try
        {
            c = (LscpTelnetMsgEcho)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeString(this.msg);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.msg = istr.readString();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, LscpTelnetMsgEcho v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public LscpTelnetMsgEcho ice_read(com.zeroc.Ice.InputStream istr)
    {
        LscpTelnetMsgEcho v = new LscpTelnetMsgEcho();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<LscpTelnetMsgEcho> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, LscpTelnetMsgEcho v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<LscpTelnetMsgEcho> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(LscpTelnetMsgEcho.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final LscpTelnetMsgEcho _nullMarshalValue = new LscpTelnetMsgEcho();

    public static final long serialVersionUID = 235955058L;
}