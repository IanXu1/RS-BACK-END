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
// Generated from file `TEEEWarningInfo.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class WarningConent implements Cloneable,
                                      java.io.Serializable
{
    public String content;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public double lon;

    public double getLon()
    {
        return lon;
    }

    public void setLon(double lon)
    {
        this.lon = lon;
    }

    public double lat;

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public String km_post;

    public String getKm_post()
    {
        return km_post;
    }

    public void setKm_post(String km_post)
    {
        this.km_post = km_post;
    }

    public int speed_km_hour;

    public int getSpeed_km_hour()
    {
        return speed_km_hour;
    }

    public void setSpeed_km_hour(int speed_km_hour)
    {
        this.speed_km_hour = speed_km_hour;
    }

    public WarningConent()
    {
        this.content = "";
        this.lon = 0;
        this.lat = 0;
        this.km_post = "";
        this.speed_km_hour = 0;
    }

    public WarningConent(String content, double lon, double lat, String km_post, int speed_km_hour)
    {
        this.content = content;
        this.lon = lon;
        this.lat = lat;
        this.km_post = km_post;
        this.speed_km_hour = speed_km_hour;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        WarningConent r = null;
        if(rhs instanceof WarningConent)
        {
            r = (WarningConent)rhs;
        }

        if(r != null)
        {
            if(this.content != r.content)
            {
                if(this.content == null || r.content == null || !this.content.equals(r.content))
                {
                    return false;
                }
            }
            if(this.lon != r.lon)
            {
                return false;
            }
            if(this.lat != r.lat)
            {
                return false;
            }
            if(this.km_post != r.km_post)
            {
                if(this.km_post == null || r.km_post == null || !this.km_post.equals(r.km_post))
                {
                    return false;
                }
            }
            if(this.speed_km_hour != r.speed_km_hour)
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::WarningConent");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, content);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, lon);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, lat);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, km_post);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, speed_km_hour);
        return h_;
    }

    public WarningConent clone()
    {
        WarningConent c = null;
        try
        {
            c = (WarningConent)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeString(this.content);
        ostr.writeDouble(this.lon);
        ostr.writeDouble(this.lat);
        ostr.writeString(this.km_post);
        ostr.writeInt(this.speed_km_hour);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.content = istr.readString();
        this.lon = istr.readDouble();
        this.lat = istr.readDouble();
        this.km_post = istr.readString();
        this.speed_km_hour = istr.readInt();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, WarningConent v)
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

    static public WarningConent ice_read(com.zeroc.Ice.InputStream istr)
    {
        WarningConent v = new WarningConent();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<WarningConent> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, WarningConent v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<WarningConent> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(WarningConent.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final WarningConent _nullMarshalValue = new WarningConent();

    public static final long serialVersionUID = -1038940553L;
}
