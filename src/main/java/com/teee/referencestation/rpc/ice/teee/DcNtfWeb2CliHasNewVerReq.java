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

public class DcNtfWeb2CliHasNewVerReq implements Cloneable,
                                                 java.io.Serializable
{
    public long toCliId;

    public long getToCliId()
    {
        return toCliId;
    }

    public void setToCliId(long toCliId)
    {
        this.toCliId = toCliId;
    }

    public DcNtfWeb2CliHasNewVerReq()
    {
        this.toCliId = 0L;
    }

    public DcNtfWeb2CliHasNewVerReq(long toCliId)
    {
        this.toCliId = toCliId;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        DcNtfWeb2CliHasNewVerReq r = null;
        if(rhs instanceof DcNtfWeb2CliHasNewVerReq)
        {
            r = (DcNtfWeb2CliHasNewVerReq)rhs;
        }

        if(r != null)
        {
            if(this.toCliId != r.toCliId)
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::DcNtfWeb2CliHasNewVerReq");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, toCliId);
        return h_;
    }

    public DcNtfWeb2CliHasNewVerReq clone()
    {
        DcNtfWeb2CliHasNewVerReq c = null;
        try
        {
            c = (DcNtfWeb2CliHasNewVerReq)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeLong(this.toCliId);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.toCliId = istr.readLong();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, DcNtfWeb2CliHasNewVerReq v)
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

    static public DcNtfWeb2CliHasNewVerReq ice_read(com.zeroc.Ice.InputStream istr)
    {
        DcNtfWeb2CliHasNewVerReq v = new DcNtfWeb2CliHasNewVerReq();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<DcNtfWeb2CliHasNewVerReq> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, DcNtfWeb2CliHasNewVerReq v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            ostr.writeSize(8);
            ice_write(ostr, v);
        }
    }

    static public java.util.Optional<DcNtfWeb2CliHasNewVerReq> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            istr.skipSize();
            return java.util.Optional.of(DcNtfWeb2CliHasNewVerReq.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final DcNtfWeb2CliHasNewVerReq _nullMarshalValue = new DcNtfWeb2CliHasNewVerReq();

    public static final long serialVersionUID = 1489566707L;
}
