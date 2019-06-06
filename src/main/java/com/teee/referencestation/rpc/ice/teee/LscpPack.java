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

public class LscpPack implements Cloneable,
                                 java.io.Serializable
{
    public RPCHeader rpcHeader;

    public RPCHeader getRpcHeader()
    {
        return rpcHeader;
    }

    public void setRpcHeader(RPCHeader rpcHeader)
    {
        this.rpcHeader = rpcHeader;
    }

    public LscpExtendHeader lscpExtendHeader;

    public LscpExtendHeader getLscpExtendHeader()
    {
        return lscpExtendHeader;
    }

    public void setLscpExtendHeader(LscpExtendHeader lscpExtendHeader)
    {
        this.lscpExtendHeader = lscpExtendHeader;
    }

    public LscpHeader lscpHeader;

    public LscpHeader getLscpHeader()
    {
        return lscpHeader;
    }

    public void setLscpHeader(LscpHeader lscpHeader)
    {
        this.lscpHeader = lscpHeader;
    }

    public byte[] body;

    public byte[] getBody()
    {
        return body;
    }

    public void setBody(byte[] body)
    {
        this.body = body;
    }

    public byte getBody(int index)
    {
        return this.body[index];
    }

    public void setBody(int index, byte val)
    {
        this.body[index] = val;
    }

    public LscpPack()
    {
        this.rpcHeader = new RPCHeader();
        this.lscpExtendHeader = new LscpExtendHeader();
        this.lscpHeader = new LscpHeader();
    }

    public LscpPack(RPCHeader rpcHeader, LscpExtendHeader lscpExtendHeader, LscpHeader lscpHeader, byte[] body)
    {
        this.rpcHeader = rpcHeader;
        this.lscpExtendHeader = lscpExtendHeader;
        this.lscpHeader = lscpHeader;
        this.body = body;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        LscpPack r = null;
        if(rhs instanceof LscpPack)
        {
            r = (LscpPack)rhs;
        }

        if(r != null)
        {
            if(this.rpcHeader != r.rpcHeader)
            {
                if(this.rpcHeader == null || r.rpcHeader == null || !this.rpcHeader.equals(r.rpcHeader))
                {
                    return false;
                }
            }
            if(this.lscpExtendHeader != r.lscpExtendHeader)
            {
                if(this.lscpExtendHeader == null || r.lscpExtendHeader == null || !this.lscpExtendHeader.equals(r.lscpExtendHeader))
                {
                    return false;
                }
            }
            if(this.lscpHeader != r.lscpHeader)
            {
                if(this.lscpHeader == null || r.lscpHeader == null || !this.lscpHeader.equals(r.lscpHeader))
                {
                    return false;
                }
            }
            if(!java.util.Arrays.equals(this.body, r.body))
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::LscpPack");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, rpcHeader);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, lscpExtendHeader);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, lscpHeader);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, body);
        return h_;
    }

    public LscpPack clone()
    {
        LscpPack c = null;
        try
        {
            c = (LscpPack)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        RPCHeader.ice_write(ostr, this.rpcHeader);
        LscpExtendHeader.ice_write(ostr, this.lscpExtendHeader);
        LscpHeader.ice_write(ostr, this.lscpHeader);
        ostr.writeByteSeq(this.body);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.rpcHeader = RPCHeader.ice_read(istr);
        this.lscpExtendHeader = LscpExtendHeader.ice_read(istr);
        this.lscpHeader = LscpHeader.ice_read(istr);
        this.body = istr.readByteSeq();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, LscpPack v)
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

    static public LscpPack ice_read(com.zeroc.Ice.InputStream istr)
    {
        LscpPack v = new LscpPack();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<LscpPack> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, LscpPack v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<LscpPack> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(LscpPack.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final LscpPack _nullMarshalValue = new LscpPack();

    public static final long serialVersionUID = 101104839L;
}
