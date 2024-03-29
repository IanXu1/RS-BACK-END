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
// Generated from file `TEEEFilePack.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class FsGetReq implements Cloneable,
                                 java.io.Serializable
{
    public String[] keys;

    public String[] getKeys()
    {
        return keys;
    }

    public void setKeys(String[] keys)
    {
        this.keys = keys;
    }

    public String getKeys(int index)
    {
        return this.keys[index];
    }

    public void setKeys(int index, String val)
    {
        this.keys[index] = val;
    }

    public FsGetReq()
    {
    }

    public FsGetReq(String[] keys)
    {
        this.keys = keys;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        FsGetReq r = null;
        if(rhs instanceof FsGetReq)
        {
            r = (FsGetReq)rhs;
        }

        if(r != null)
        {
            if(!java.util.Arrays.equals(this.keys, r.keys))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::FsGetReq");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, keys);
        return h_;
    }

    public FsGetReq clone()
    {
        FsGetReq c = null;
        try
        {
            c = (FsGetReq)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeStringSeq(this.keys);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.keys = istr.readStringSeq();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, FsGetReq v)
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

    static public FsGetReq ice_read(com.zeroc.Ice.InputStream istr)
    {
        FsGetReq v = new FsGetReq();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<FsGetReq> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, FsGetReq v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<FsGetReq> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(FsGetReq.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final FsGetReq _nullMarshalValue = new FsGetReq();

    public static final long serialVersionUID = -1881695780L;
}
