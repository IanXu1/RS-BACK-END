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
// Generated from file `TEEELscpProtocal.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class LscpRTEvent implements Cloneable,
                                    java.io.Serializable
{
    public long devNo;

    public long getDevNo()
    {
        return devNo;
    }

    public void setDevNo(long devNo)
    {
        this.devNo = devNo;
    }

    public long bdSNo;

    public long getBdSNo()
    {
        return bdSNo;
    }

    public void setBdSNo(long bdSNo)
    {
        this.bdSNo = bdSNo;
    }

    public long time;

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public long kmPost;

    public long getKmPost()
    {
        return kmPost;
    }

    public void setKmPost(long kmPost)
    {
        this.kmPost = kmPost;
    }

    public long lon;

    public long getLon()
    {
        return lon;
    }

    public void setLon(long lon)
    {
        this.lon = lon;
    }

    public long lat;

    public long getLat()
    {
        return lat;
    }

    public void setLat(long lat)
    {
        this.lat = lat;
    }

    public long speed;

    public long getSpeed()
    {
        return speed;
    }

    public void setSpeed(long speed)
    {
        this.speed = speed;
    }

    public long bdType;

    public long getBdType()
    {
        return bdType;
    }

    public void setBdType(long bdType)
    {
        this.bdType = bdType;
    }

    public long slotId;

    public long getSlotId()
    {
        return slotId;
    }

    public void setSlotId(long slotId)
    {
        this.slotId = slotId;
    }

    public long eventType;

    public long getEventType()
    {
        return eventType;
    }

    public void setEventType(long eventType)
    {
        this.eventType = eventType;
    }

    public String comment;

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public LscpRTEvent()
    {
        this.devNo = 0L;
        this.bdSNo = 0L;
        this.time = 0L;
        this.kmPost = 0L;
        this.lon = 0L;
        this.lat = 0L;
        this.speed = 0L;
        this.bdType = 0L;
        this.slotId = 0L;
        this.eventType = 0L;
        this.comment = "";
    }

    public LscpRTEvent(long devNo, long bdSNo, long time, long kmPost, long lon, long lat, long speed, long bdType, long slotId, long eventType, String comment)
    {
        this.devNo = devNo;
        this.bdSNo = bdSNo;
        this.time = time;
        this.kmPost = kmPost;
        this.lon = lon;
        this.lat = lat;
        this.speed = speed;
        this.bdType = bdType;
        this.slotId = slotId;
        this.eventType = eventType;
        this.comment = comment;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        LscpRTEvent r = null;
        if(rhs instanceof LscpRTEvent)
        {
            r = (LscpRTEvent)rhs;
        }

        if(r != null)
        {
            if(this.devNo != r.devNo)
            {
                return false;
            }
            if(this.bdSNo != r.bdSNo)
            {
                return false;
            }
            if(this.time != r.time)
            {
                return false;
            }
            if(this.kmPost != r.kmPost)
            {
                return false;
            }
            if(this.lon != r.lon)
            {
                return false;
            }
            if(this.lat != r.lat)
            {
                return false;
            }
            if(this.speed != r.speed)
            {
                return false;
            }
            if(this.bdType != r.bdType)
            {
                return false;
            }
            if(this.slotId != r.slotId)
            {
                return false;
            }
            if(this.eventType != r.eventType)
            {
                return false;
            }
            if(this.comment != r.comment)
            {
                if(this.comment == null || r.comment == null || !this.comment.equals(r.comment))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::LscpRTEvent");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, devNo);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, bdSNo);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, time);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, kmPost);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, lon);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, lat);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, speed);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, bdType);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, slotId);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, eventType);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, comment);
        return h_;
    }

    public LscpRTEvent clone()
    {
        LscpRTEvent c = null;
        try
        {
            c = (LscpRTEvent)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeLong(this.devNo);
        ostr.writeLong(this.bdSNo);
        ostr.writeLong(this.time);
        ostr.writeLong(this.kmPost);
        ostr.writeLong(this.lon);
        ostr.writeLong(this.lat);
        ostr.writeLong(this.speed);
        ostr.writeLong(this.bdType);
        ostr.writeLong(this.slotId);
        ostr.writeLong(this.eventType);
        ostr.writeString(this.comment);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.devNo = istr.readLong();
        this.bdSNo = istr.readLong();
        this.time = istr.readLong();
        this.kmPost = istr.readLong();
        this.lon = istr.readLong();
        this.lat = istr.readLong();
        this.speed = istr.readLong();
        this.bdType = istr.readLong();
        this.slotId = istr.readLong();
        this.eventType = istr.readLong();
        this.comment = istr.readString();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, LscpRTEvent v)
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

    static public LscpRTEvent ice_read(com.zeroc.Ice.InputStream istr)
    {
        LscpRTEvent v = new LscpRTEvent();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<LscpRTEvent> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, LscpRTEvent v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<LscpRTEvent> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(LscpRTEvent.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final LscpRTEvent _nullMarshalValue = new LscpRTEvent();

    public static final long serialVersionUID = -625863023L;
}
