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

public class DCGetLscpTurnOnSTReq implements Cloneable,
                                             java.io.Serializable
{
    public long[] uuids;

    public long[] getUuids()
    {
        return uuids;
    }

    public void setUuids(long[] uuids)
    {
        this.uuids = uuids;
    }

    public long getUuids(int index)
    {
        return this.uuids[index];
    }

    public void setUuids(int index, long val)
    {
        this.uuids[index] = val;
    }

    public DCGetLscpTurnOnSTReq()
    {
    }

    public DCGetLscpTurnOnSTReq(long[] uuids)
    {
        this.uuids = uuids;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        DCGetLscpTurnOnSTReq r = null;
        if(rhs instanceof DCGetLscpTurnOnSTReq)
        {
            r = (DCGetLscpTurnOnSTReq)rhs;
        }

        if(r != null)
        {
            if(!java.util.Arrays.equals(this.uuids, r.uuids))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::DCGetLscpTurnOnSTReq");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, uuids);
        return h_;
    }

    public DCGetLscpTurnOnSTReq clone()
    {
        DCGetLscpTurnOnSTReq c = null;
        try
        {
            c = (DCGetLscpTurnOnSTReq)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeLongSeq(this.uuids);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.uuids = istr.readLongSeq();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, DCGetLscpTurnOnSTReq v)
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

    static public DCGetLscpTurnOnSTReq ice_read(com.zeroc.Ice.InputStream istr)
    {
        DCGetLscpTurnOnSTReq v = new DCGetLscpTurnOnSTReq();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<DCGetLscpTurnOnSTReq> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, DCGetLscpTurnOnSTReq v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<DCGetLscpTurnOnSTReq> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(DCGetLscpTurnOnSTReq.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final DCGetLscpTurnOnSTReq _nullMarshalValue = new DCGetLscpTurnOnSTReq();

    public static final long serialVersionUID = 1615727523L;
}
