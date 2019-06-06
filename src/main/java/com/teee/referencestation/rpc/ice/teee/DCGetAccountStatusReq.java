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

public class DCGetAccountStatusReq implements Cloneable,
                                              java.io.Serializable
{
    public GetAccountStatusMode mode;

    public GetAccountStatusMode getMode()
    {
        return mode;
    }

    public void setMode(GetAccountStatusMode mode)
    {
        this.mode = mode;
    }

    public long[] ids;

    public long[] getIds()
    {
        return ids;
    }

    public void setIds(long[] ids)
    {
        this.ids = ids;
    }

    public long getIds(int index)
    {
        return this.ids[index];
    }

    public void setIds(int index, long val)
    {
        this.ids[index] = val;
    }

    public DCGetAccountStatusReq()
    {
        this.mode = GetAccountStatusMode.E_GETACCOUNTSTATUSMODE_CUSTOM;
    }

    public DCGetAccountStatusReq(GetAccountStatusMode mode, long[] ids)
    {
        this.mode = mode;
        this.ids = ids;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        DCGetAccountStatusReq r = null;
        if(rhs instanceof DCGetAccountStatusReq)
        {
            r = (DCGetAccountStatusReq)rhs;
        }

        if(r != null)
        {
            if(this.mode != r.mode)
            {
                if(this.mode == null || r.mode == null || !this.mode.equals(r.mode))
                {
                    return false;
                }
            }
            if(!java.util.Arrays.equals(this.ids, r.ids))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::DCGetAccountStatusReq");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, mode);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, ids);
        return h_;
    }

    public DCGetAccountStatusReq clone()
    {
        DCGetAccountStatusReq c = null;
        try
        {
            c = (DCGetAccountStatusReq)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        GetAccountStatusMode.ice_write(ostr, this.mode);
        ostr.writeLongSeq(this.ids);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.mode = GetAccountStatusMode.ice_read(istr);
        this.ids = istr.readLongSeq();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, DCGetAccountStatusReq v)
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

    static public DCGetAccountStatusReq ice_read(com.zeroc.Ice.InputStream istr)
    {
        DCGetAccountStatusReq v = new DCGetAccountStatusReq();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<DCGetAccountStatusReq> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, DCGetAccountStatusReq v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<DCGetAccountStatusReq> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(DCGetAccountStatusReq.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final DCGetAccountStatusReq _nullMarshalValue = new DCGetAccountStatusReq();

    public static final long serialVersionUID = 382582821L;
}
