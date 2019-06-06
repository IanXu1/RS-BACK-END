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
// Generated from file `TEEEDCCURD.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class DCDeleteReq implements Cloneable,
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

    public DCTableType dcTableType;

    public DCTableType getDcTableType()
    {
        return dcTableType;
    }

    public void setDcTableType(DCTableType dcTableType)
    {
        this.dcTableType = dcTableType;
    }

    public long[] deledIds;

    public long[] getDeledIds()
    {
        return deledIds;
    }

    public void setDeledIds(long[] deledIds)
    {
        this.deledIds = deledIds;
    }

    public long getDeledIds(int index)
    {
        return this.deledIds[index];
    }

    public void setDeledIds(int index, long val)
    {
        this.deledIds[index] = val;
    }

    public DCDeleteReq()
    {
        this.rpcHeader = new RPCHeader();
        this.dcTableType = DCTableType.E_TABLE_ERROR;
    }

    public DCDeleteReq(RPCHeader rpcHeader, DCTableType dcTableType, long[] deledIds)
    {
        this.rpcHeader = rpcHeader;
        this.dcTableType = dcTableType;
        this.deledIds = deledIds;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        DCDeleteReq r = null;
        if(rhs instanceof DCDeleteReq)
        {
            r = (DCDeleteReq)rhs;
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
            if(this.dcTableType != r.dcTableType)
            {
                if(this.dcTableType == null || r.dcTableType == null || !this.dcTableType.equals(r.dcTableType))
                {
                    return false;
                }
            }
            if(!java.util.Arrays.equals(this.deledIds, r.deledIds))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::DCDeleteReq");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, rpcHeader);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, dcTableType);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, deledIds);
        return h_;
    }

    public DCDeleteReq clone()
    {
        DCDeleteReq c = null;
        try
        {
            c = (DCDeleteReq)super.clone();
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
        DCTableType.ice_write(ostr, this.dcTableType);
        ostr.writeLongSeq(this.deledIds);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.rpcHeader = RPCHeader.ice_read(istr);
        this.dcTableType = DCTableType.ice_read(istr);
        this.deledIds = istr.readLongSeq();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, DCDeleteReq v)
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

    static public DCDeleteReq ice_read(com.zeroc.Ice.InputStream istr)
    {
        DCDeleteReq v = new DCDeleteReq();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<DCDeleteReq> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, DCDeleteReq v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<DCDeleteReq> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(DCDeleteReq.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final DCDeleteReq _nullMarshalValue = new DCDeleteReq();

    public static final long serialVersionUID = 125810952L;
}