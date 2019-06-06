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

public class WarningInfo implements Cloneable,
                                    java.io.Serializable
{
    public long warning_info_id;

    public long getWarning_info_id()
    {
        return warning_info_id;
    }

    public void setWarning_info_id(long warning_info_id)
    {
        this.warning_info_id = warning_info_id;
    }

    public E_WARNING_ST warning_st;

    public E_WARNING_ST getWarning_st()
    {
        return warning_st;
    }

    public void setWarning_st(E_WARNING_ST warning_st)
    {
        this.warning_st = warning_st;
    }

    public E_WARNING_LVL warning_lvl;

    public E_WARNING_LVL getWarning_lvl()
    {
        return warning_lvl;
    }

    public void setWarning_lvl(E_WARNING_LVL warning_lvl)
    {
        this.warning_lvl = warning_lvl;
    }

    public long warning_type;

    public long getWarning_type()
    {
        return warning_type;
    }

    public void setWarning_type(long warning_type)
    {
        this.warning_type = warning_type;
    }

    public long warning_sub_type;

    public long getWarning_sub_type()
    {
        return warning_sub_type;
    }

    public void setWarning_sub_type(long warning_sub_type)
    {
        this.warning_sub_type = warning_sub_type;
    }

    public WarningDevInfo warningDevInfo;

    public WarningDevInfo getWarningDevInfo()
    {
        return warningDevInfo;
    }

    public void setWarningDevInfo(WarningDevInfo warningDevInfo)
    {
        this.warningDevInfo = warningDevInfo;
    }

    public String occured_sno;

    public String getOccured_sno()
    {
        return occured_sno;
    }

    public void setOccured_sno(String occured_sno)
    {
        this.occured_sno = occured_sno;
    }

    public long occured_recv_time_ts;

    public long getOccured_recv_time_ts()
    {
        return occured_recv_time_ts;
    }

    public void setOccured_recv_time_ts(long occured_recv_time_ts)
    {
        this.occured_recv_time_ts = occured_recv_time_ts;
    }

    public long occured_time_ts;

    public long getOccured_time_ts()
    {
        return occured_time_ts;
    }

    public void setOccured_time_ts(long occured_time_ts)
    {
        this.occured_time_ts = occured_time_ts;
    }

    public WarningContent warningContentOfOccured;

    public WarningContent getWarningContentOfOccured()
    {
        return warningContentOfOccured;
    }

    public void setWarningContentOfOccured(WarningContent warningContentOfOccured)
    {
        this.warningContentOfOccured = warningContentOfOccured;
    }

    public String clr_sno;

    public String getClr_sno()
    {
        return clr_sno;
    }

    public void setClr_sno(String clr_sno)
    {
        this.clr_sno = clr_sno;
    }

    public long clr_recv_time_ts;

    public long getClr_recv_time_ts()
    {
        return clr_recv_time_ts;
    }

    public void setClr_recv_time_ts(long clr_recv_time_ts)
    {
        this.clr_recv_time_ts = clr_recv_time_ts;
    }

    public long clr_time_ts;

    public long getClr_time_ts()
    {
        return clr_time_ts;
    }

    public void setClr_time_ts(long clr_time_ts)
    {
        this.clr_time_ts = clr_time_ts;
    }

    public WarningContent warningContentOfClr;

    public WarningContent getWarningContentOfClr()
    {
        return warningContentOfClr;
    }

    public void setWarningContentOfClr(WarningContent warningContentOfClr)
    {
        this.warningContentOfClr = warningContentOfClr;
    }

    public boolean is_deleted;

    public boolean getIs_deleted()
    {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted)
    {
        this.is_deleted = is_deleted;
    }

    public boolean isIs_deleted()
    {
        return is_deleted;
    }

    public WarningInfo()
    {
        this.warning_info_id = 0L;
        this.warning_st = E_WARNING_ST.E_WARNING_ST_OCCURED;
        this.warning_lvl = E_WARNING_LVL.E_WARNING_LVL_INFO;
        this.warning_type = 0L;
        this.warning_sub_type = 0L;
        this.warningDevInfo = new WarningDevInfo();
        this.occured_sno = "0";
        this.occured_recv_time_ts = 0L;
        this.occured_time_ts = 0L;
        this.warningContentOfOccured = new WarningContent();
        this.clr_sno = "0";
        this.clr_recv_time_ts = 0L;
        this.clr_time_ts = 0L;
        this.warningContentOfClr = new WarningContent();
        this.is_deleted = false;
    }

    public WarningInfo(long warning_info_id, E_WARNING_ST warning_st, E_WARNING_LVL warning_lvl, long warning_type, long warning_sub_type, WarningDevInfo warningDevInfo, String occured_sno, long occured_recv_time_ts, long occured_time_ts, WarningContent warningContentOfOccured, String clr_sno, long clr_recv_time_ts, long clr_time_ts, WarningContent warningContentOfClr, boolean is_deleted)
    {
        this.warning_info_id = warning_info_id;
        this.warning_st = warning_st;
        this.warning_lvl = warning_lvl;
        this.warning_type = warning_type;
        this.warning_sub_type = warning_sub_type;
        this.warningDevInfo = warningDevInfo;
        this.occured_sno = occured_sno;
        this.occured_recv_time_ts = occured_recv_time_ts;
        this.occured_time_ts = occured_time_ts;
        this.warningContentOfOccured = warningContentOfOccured;
        this.clr_sno = clr_sno;
        this.clr_recv_time_ts = clr_recv_time_ts;
        this.clr_time_ts = clr_time_ts;
        this.warningContentOfClr = warningContentOfClr;
        this.is_deleted = is_deleted;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        WarningInfo r = null;
        if(rhs instanceof WarningInfo)
        {
            r = (WarningInfo)rhs;
        }

        if(r != null)
        {
            if(this.warning_info_id != r.warning_info_id)
            {
                return false;
            }
            if(this.warning_st != r.warning_st)
            {
                if(this.warning_st == null || r.warning_st == null || !this.warning_st.equals(r.warning_st))
                {
                    return false;
                }
            }
            if(this.warning_lvl != r.warning_lvl)
            {
                if(this.warning_lvl == null || r.warning_lvl == null || !this.warning_lvl.equals(r.warning_lvl))
                {
                    return false;
                }
            }
            if(this.warning_type != r.warning_type)
            {
                return false;
            }
            if(this.warning_sub_type != r.warning_sub_type)
            {
                return false;
            }
            if(this.warningDevInfo != r.warningDevInfo)
            {
                if(this.warningDevInfo == null || r.warningDevInfo == null || !this.warningDevInfo.equals(r.warningDevInfo))
                {
                    return false;
                }
            }
            if(this.occured_sno != r.occured_sno)
            {
                if(this.occured_sno == null || r.occured_sno == null || !this.occured_sno.equals(r.occured_sno))
                {
                    return false;
                }
            }
            if(this.occured_recv_time_ts != r.occured_recv_time_ts)
            {
                return false;
            }
            if(this.occured_time_ts != r.occured_time_ts)
            {
                return false;
            }
            if(this.warningContentOfOccured != r.warningContentOfOccured)
            {
                if(this.warningContentOfOccured == null || r.warningContentOfOccured == null || !this.warningContentOfOccured.equals(r.warningContentOfOccured))
                {
                    return false;
                }
            }
            if(this.clr_sno != r.clr_sno)
            {
                if(this.clr_sno == null || r.clr_sno == null || !this.clr_sno.equals(r.clr_sno))
                {
                    return false;
                }
            }
            if(this.clr_recv_time_ts != r.clr_recv_time_ts)
            {
                return false;
            }
            if(this.clr_time_ts != r.clr_time_ts)
            {
                return false;
            }
            if(this.warningContentOfClr != r.warningContentOfClr)
            {
                if(this.warningContentOfClr == null || r.warningContentOfClr == null || !this.warningContentOfClr.equals(r.warningContentOfClr))
                {
                    return false;
                }
            }
            if(this.is_deleted != r.is_deleted)
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::WarningInfo");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, warning_info_id);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, warning_st);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, warning_lvl);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, warning_type);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, warning_sub_type);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, warningDevInfo);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, occured_sno);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, occured_recv_time_ts);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, occured_time_ts);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, warningContentOfOccured);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, clr_sno);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, clr_recv_time_ts);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, clr_time_ts);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, warningContentOfClr);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, is_deleted);
        return h_;
    }

    public WarningInfo clone()
    {
        WarningInfo c = null;
        try
        {
            c = (WarningInfo)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeLong(this.warning_info_id);
        E_WARNING_ST.ice_write(ostr, this.warning_st);
        E_WARNING_LVL.ice_write(ostr, this.warning_lvl);
        ostr.writeLong(this.warning_type);
        ostr.writeLong(this.warning_sub_type);
        WarningDevInfo.ice_write(ostr, this.warningDevInfo);
        ostr.writeString(this.occured_sno);
        ostr.writeLong(this.occured_recv_time_ts);
        ostr.writeLong(this.occured_time_ts);
        WarningContent.ice_write(ostr, this.warningContentOfOccured);
        ostr.writeString(this.clr_sno);
        ostr.writeLong(this.clr_recv_time_ts);
        ostr.writeLong(this.clr_time_ts);
        WarningContent.ice_write(ostr, this.warningContentOfClr);
        ostr.writeBool(this.is_deleted);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.warning_info_id = istr.readLong();
        this.warning_st = E_WARNING_ST.ice_read(istr);
        this.warning_lvl = E_WARNING_LVL.ice_read(istr);
        this.warning_type = istr.readLong();
        this.warning_sub_type = istr.readLong();
        this.warningDevInfo = WarningDevInfo.ice_read(istr);
        this.occured_sno = istr.readString();
        this.occured_recv_time_ts = istr.readLong();
        this.occured_time_ts = istr.readLong();
        this.warningContentOfOccured = WarningContent.ice_read(istr);
        this.clr_sno = istr.readString();
        this.clr_recv_time_ts = istr.readLong();
        this.clr_time_ts = istr.readLong();
        this.warningContentOfClr = WarningContent.ice_read(istr);
        this.is_deleted = istr.readBool();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, WarningInfo v)
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

    static public WarningInfo ice_read(com.zeroc.Ice.InputStream istr)
    {
        WarningInfo v = new WarningInfo();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<WarningInfo> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, WarningInfo v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<WarningInfo> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(WarningInfo.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final WarningInfo _nullMarshalValue = new WarningInfo();

    public static final long serialVersionUID = 1044072369L;
}