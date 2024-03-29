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
// Generated from file `TEEEAuthLog.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class AuthLog implements Cloneable,
                                java.io.Serializable
{
    public long authLogId;

    public long getAuthLogId()
    {
        return authLogId;
    }

    public void setAuthLogId(long authLogId)
    {
        this.authLogId = authLogId;
    }

    public long authTimeStamp;

    public long getAuthTimeStamp()
    {
        return authTimeStamp;
    }

    public void setAuthTimeStamp(long authTimeStamp)
    {
        this.authTimeStamp = authTimeStamp;
    }

    public String username;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String ip;

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int port;

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String result;

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public AuthLog()
    {
        this.authLogId = 0L;
        this.authTimeStamp = 0L;
        this.username = "";
        this.ip = "";
        this.port = 0;
        this.result = "";
    }

    public AuthLog(long authLogId, long authTimeStamp, String username, String ip, int port, String result)
    {
        this.authLogId = authLogId;
        this.authTimeStamp = authTimeStamp;
        this.username = username;
        this.ip = ip;
        this.port = port;
        this.result = result;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        AuthLog r = null;
        if(rhs instanceof AuthLog)
        {
            r = (AuthLog)rhs;
        }

        if(r != null)
        {
            if(this.authLogId != r.authLogId)
            {
                return false;
            }
            if(this.authTimeStamp != r.authTimeStamp)
            {
                return false;
            }
            if(this.username != r.username)
            {
                if(this.username == null || r.username == null || !this.username.equals(r.username))
                {
                    return false;
                }
            }
            if(this.ip != r.ip)
            {
                if(this.ip == null || r.ip == null || !this.ip.equals(r.ip))
                {
                    return false;
                }
            }
            if(this.port != r.port)
            {
                return false;
            }
            if(this.result != r.result)
            {
                if(this.result == null || r.result == null || !this.result.equals(r.result))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::AuthLog");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, authLogId);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, authTimeStamp);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, username);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, ip);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, port);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, result);
        return h_;
    }

    public AuthLog clone()
    {
        AuthLog c = null;
        try
        {
            c = (AuthLog)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeLong(this.authLogId);
        ostr.writeLong(this.authTimeStamp);
        ostr.writeString(this.username);
        ostr.writeString(this.ip);
        ostr.writeInt(this.port);
        ostr.writeString(this.result);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.authLogId = istr.readLong();
        this.authTimeStamp = istr.readLong();
        this.username = istr.readString();
        this.ip = istr.readString();
        this.port = istr.readInt();
        this.result = istr.readString();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, AuthLog v)
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

    static public AuthLog ice_read(com.zeroc.Ice.InputStream istr)
    {
        AuthLog v = new AuthLog();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<AuthLog> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, AuthLog v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<AuthLog> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(AuthLog.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final AuthLog _nullMarshalValue = new AuthLog();

    public static final long serialVersionUID = -1976811250L;
}
