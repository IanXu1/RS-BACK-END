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
// Generated from file `TEEECallChainLog.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class ChainInfo implements Cloneable,
                                  java.io.Serializable
{
    public long ts;

    public long getTs()
    {
        return ts;
    }

    public void setTs(long ts)
    {
        this.ts = ts;
    }

    public String info;

    public String getInfo()
    {
        return info;
    }

    public void setInfo(String info)
    {
        this.info = info;
    }

    public ChainInfo()
    {
        this.info = "";
    }

    public ChainInfo(long ts, String info)
    {
        this.ts = ts;
        this.info = info;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        ChainInfo r = null;
        if(rhs instanceof ChainInfo)
        {
            r = (ChainInfo)rhs;
        }

        if(r != null)
        {
            if(this.ts != r.ts)
            {
                return false;
            }
            if(this.info != r.info)
            {
                if(this.info == null || r.info == null || !this.info.equals(r.info))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::ChainInfo");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, ts);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, info);
        return h_;
    }

    public ChainInfo clone()
    {
        ChainInfo c = null;
        try
        {
            c = (ChainInfo)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeLong(this.ts);
        ostr.writeString(this.info);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.ts = istr.readLong();
        this.info = istr.readString();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, ChainInfo v)
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

    static public ChainInfo ice_read(com.zeroc.Ice.InputStream istr)
    {
        ChainInfo v = new ChainInfo();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<ChainInfo> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, ChainInfo v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<ChainInfo> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(ChainInfo.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final ChainInfo _nullMarshalValue = new ChainInfo();

    public static final long serialVersionUID = 1893819408L;
}
