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
// Generated from file `TEEEKeyValueCache.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class SetCacheReq implements Cloneable,
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

    public CacheKey cacheKey;

    public CacheKey getCacheKey()
    {
        return cacheKey;
    }

    public void setCacheKey(CacheKey cacheKey)
    {
        this.cacheKey = cacheKey;
    }

    public long expiryInMilliseconds;

    public long getExpiryInMilliseconds()
    {
        return expiryInMilliseconds;
    }

    public void setExpiryInMilliseconds(long expiryInMilliseconds)
    {
        this.expiryInMilliseconds = expiryInMilliseconds;
    }

    public String value;

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public SetCacheReq()
    {
        this.rpcHeader = new RPCHeader();
        this.cacheKey = new CacheKey();
        this.expiryInMilliseconds = 0L;
        this.value = "";
    }

    public SetCacheReq(RPCHeader rpcHeader, CacheKey cacheKey, long expiryInMilliseconds, String value)
    {
        this.rpcHeader = rpcHeader;
        this.cacheKey = cacheKey;
        this.expiryInMilliseconds = expiryInMilliseconds;
        this.value = value;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        SetCacheReq r = null;
        if(rhs instanceof SetCacheReq)
        {
            r = (SetCacheReq)rhs;
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
            if(this.cacheKey != r.cacheKey)
            {
                if(this.cacheKey == null || r.cacheKey == null || !this.cacheKey.equals(r.cacheKey))
                {
                    return false;
                }
            }
            if(this.expiryInMilliseconds != r.expiryInMilliseconds)
            {
                return false;
            }
            if(this.value != r.value)
            {
                if(this.value == null || r.value == null || !this.value.equals(r.value))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::SetCacheReq");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, rpcHeader);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, cacheKey);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, expiryInMilliseconds);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, value);
        return h_;
    }

    public SetCacheReq clone()
    {
        SetCacheReq c = null;
        try
        {
            c = (SetCacheReq)super.clone();
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
        CacheKey.ice_write(ostr, this.cacheKey);
        ostr.writeLong(this.expiryInMilliseconds);
        ostr.writeString(this.value);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.rpcHeader = RPCHeader.ice_read(istr);
        this.cacheKey = CacheKey.ice_read(istr);
        this.expiryInMilliseconds = istr.readLong();
        this.value = istr.readString();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, SetCacheReq v)
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

    static public SetCacheReq ice_read(com.zeroc.Ice.InputStream istr)
    {
        SetCacheReq v = new SetCacheReq();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<SetCacheReq> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, SetCacheReq v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<SetCacheReq> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(SetCacheReq.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final SetCacheReq _nullMarshalValue = new SetCacheReq();

    public static final long serialVersionUID = 314545242L;
}
