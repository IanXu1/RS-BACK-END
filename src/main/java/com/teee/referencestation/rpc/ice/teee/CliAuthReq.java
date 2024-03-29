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
// Generated from file `TEEENtripProtocal.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class CliAuthReq implements Cloneable,
                                   java.io.Serializable
{
    public String mntPoint;

    public String getMntPoint()
    {
        return mntPoint;
    }

    public void setMntPoint(String mntPoint)
    {
        this.mntPoint = mntPoint;
    }

    public String user;

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String pwd;

    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    public CliAuthReq()
    {
        this.mntPoint = "";
        this.user = "";
        this.pwd = "";
    }

    public CliAuthReq(String mntPoint, String user, String pwd)
    {
        this.mntPoint = mntPoint;
        this.user = user;
        this.pwd = pwd;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        CliAuthReq r = null;
        if(rhs instanceof CliAuthReq)
        {
            r = (CliAuthReq)rhs;
        }

        if(r != null)
        {
            if(this.mntPoint != r.mntPoint)
            {
                if(this.mntPoint == null || r.mntPoint == null || !this.mntPoint.equals(r.mntPoint))
                {
                    return false;
                }
            }
            if(this.user != r.user)
            {
                if(this.user == null || r.user == null || !this.user.equals(r.user))
                {
                    return false;
                }
            }
            if(this.pwd != r.pwd)
            {
                if(this.pwd == null || r.pwd == null || !this.pwd.equals(r.pwd))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::CliAuthReq");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, mntPoint);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, user);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, pwd);
        return h_;
    }

    public CliAuthReq clone()
    {
        CliAuthReq c = null;
        try
        {
            c = (CliAuthReq)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeString(this.mntPoint);
        ostr.writeString(this.user);
        ostr.writeString(this.pwd);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.mntPoint = istr.readString();
        this.user = istr.readString();
        this.pwd = istr.readString();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, CliAuthReq v)
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

    static public CliAuthReq ice_read(com.zeroc.Ice.InputStream istr)
    {
        CliAuthReq v = new CliAuthReq();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<CliAuthReq> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, CliAuthReq v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<CliAuthReq> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(CliAuthReq.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final CliAuthReq _nullMarshalValue = new CliAuthReq();

    public static final long serialVersionUID = -124510212L;
}
