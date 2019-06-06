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
// Generated from file `TEEENtripProtocal.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class CliGetSourcetableResp implements Cloneable,
                                              java.io.Serializable
{
    public String sSourcetable;

    public String getSSourcetable()
    {
        return sSourcetable;
    }

    public void setSSourcetable(String sSourcetable)
    {
        this.sSourcetable = sSourcetable;
    }

    public CliGetSourcetableResp()
    {
        this.sSourcetable = "";
    }

    public CliGetSourcetableResp(String sSourcetable)
    {
        this.sSourcetable = sSourcetable;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        CliGetSourcetableResp r = null;
        if(rhs instanceof CliGetSourcetableResp)
        {
            r = (CliGetSourcetableResp)rhs;
        }

        if(r != null)
        {
            if(this.sSourcetable != r.sSourcetable)
            {
                if(this.sSourcetable == null || r.sSourcetable == null || !this.sSourcetable.equals(r.sSourcetable))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::CliGetSourcetableResp");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, sSourcetable);
        return h_;
    }

    public CliGetSourcetableResp clone()
    {
        CliGetSourcetableResp c = null;
        try
        {
            c = (CliGetSourcetableResp)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeString(this.sSourcetable);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.sSourcetable = istr.readString();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, CliGetSourcetableResp v)
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

    static public CliGetSourcetableResp ice_read(com.zeroc.Ice.InputStream istr)
    {
        CliGetSourcetableResp v = new CliGetSourcetableResp();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<CliGetSourcetableResp> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, CliGetSourcetableResp v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<CliGetSourcetableResp> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(CliGetSourcetableResp.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final CliGetSourcetableResp _nullMarshalValue = new CliGetSourcetableResp();

    public static final long serialVersionUID = -837714262L;
}
