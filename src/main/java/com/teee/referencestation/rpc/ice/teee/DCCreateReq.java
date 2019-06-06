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

public class DCCreateReq implements Cloneable,
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

    public boolean isUpdate;

    public boolean getIsUpdate()
    {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate)
    {
        this.isUpdate = isUpdate;
    }

    public boolean isIsUpdate()
    {
        return isUpdate;
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

    public DCCreateReq()
    {
        this.rpcHeader = new RPCHeader();
        this.dcTableType = DCTableType.E_TABLE_ERROR;
        this.isUpdate = false;
    }

    public DCCreateReq(RPCHeader rpcHeader, DCTableType dcTableType, boolean isUpdate, byte[] body)
    {
        this.rpcHeader = rpcHeader;
        this.dcTableType = dcTableType;
        this.isUpdate = isUpdate;
        this.body = body;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        DCCreateReq r = null;
        if(rhs instanceof DCCreateReq)
        {
            r = (DCCreateReq)rhs;
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
            if(this.isUpdate != r.isUpdate)
            {
                return false;
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::DCCreateReq");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, rpcHeader);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, dcTableType);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, isUpdate);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, body);
        return h_;
    }

    public DCCreateReq clone()
    {
        DCCreateReq c = null;
        try
        {
            c = (DCCreateReq)super.clone();
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
        ostr.writeBool(this.isUpdate);
        ostr.writeByteSeq(this.body);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.rpcHeader = RPCHeader.ice_read(istr);
        this.dcTableType = DCTableType.ice_read(istr);
        this.isUpdate = istr.readBool();
        this.body = istr.readByteSeq();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, DCCreateReq v)
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

    static public DCCreateReq ice_read(com.zeroc.Ice.InputStream istr)
    {
        DCCreateReq v = new DCCreateReq();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<DCCreateReq> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, DCCreateReq v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<DCCreateReq> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(DCCreateReq.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final DCCreateReq _nullMarshalValue = new DCCreateReq();

    public static final long serialVersionUID = 312670331L;
}