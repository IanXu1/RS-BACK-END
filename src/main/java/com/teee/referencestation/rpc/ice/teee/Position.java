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
// Generated from file `TEEEPosition.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class Position implements Cloneable,
                                 java.io.Serializable
{
    public double longitude;

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double latitude;

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double altitude;

    public double getAltitude()
    {
        return altitude;
    }

    public void setAltitude(double altitude)
    {
        this.altitude = altitude;
    }

    public Position()
    {
        this.longitude = 0;
        this.latitude = 0;
        this.altitude = 0;
    }

    public Position(double longitude, double latitude, double altitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        Position r = null;
        if(rhs instanceof Position)
        {
            r = (Position)rhs;
        }

        if(r != null)
        {
            if(this.longitude != r.longitude)
            {
                return false;
            }
            if(this.latitude != r.latitude)
            {
                return false;
            }
            if(this.altitude != r.altitude)
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::Position");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, longitude);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, latitude);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, altitude);
        return h_;
    }

    public Position clone()
    {
        Position c = null;
        try
        {
            c = (Position)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeDouble(this.longitude);
        ostr.writeDouble(this.latitude);
        ostr.writeDouble(this.altitude);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.longitude = istr.readDouble();
        this.latitude = istr.readDouble();
        this.altitude = istr.readDouble();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, Position v)
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

    static public Position ice_read(com.zeroc.Ice.InputStream istr)
    {
        Position v = new Position();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<Position> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, Position v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            ostr.writeSize(24);
            ice_write(ostr, v);
        }
    }

    static public java.util.Optional<Position> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            istr.skipSize();
            return java.util.Optional.of(Position.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final Position _nullMarshalValue = new Position();

    public static final long serialVersionUID = -1898163609L;
}
