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
// Generated from file `TEEEDCMeta.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public class WhereCond implements Cloneable,
                                  java.io.Serializable
{
    public CondConnectByMode condConnectByMode;

    public CondConnectByMode getCondConnectByMode()
    {
        return condConnectByMode;
    }

    public void setCondConnectByMode(CondConnectByMode condConnectByMode)
    {
        this.condConnectByMode = condConnectByMode;
    }

    public DCColumnType dcColumnType;

    public DCColumnType getDcColumnType()
    {
        return dcColumnType;
    }

    public void setDcColumnType(DCColumnType dcColumnType)
    {
        this.dcColumnType = dcColumnType;
    }

    public boolean ignoreCase;

    public boolean getIgnoreCase()
    {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase)
    {
        this.ignoreCase = ignoreCase;
    }

    public boolean isIgnoreCase()
    {
        return ignoreCase;
    }

    public WhereCondType whereCondType;

    public WhereCondType getWhereCondType()
    {
        return whereCondType;
    }

    public void setWhereCondType(WhereCondType whereCondType)
    {
        this.whereCondType = whereCondType;
    }

    public String[] values;

    public String[] getValues()
    {
        return values;
    }

    public void setValues(String[] values)
    {
        this.values = values;
    }

    public String getValues(int index)
    {
        return this.values[index];
    }

    public void setValues(int index, String val)
    {
        this.values[index] = val;
    }

    public WhereCond()
    {
        this.condConnectByMode = CondConnectByMode.E_CONNECTBY_AND;
        this.dcColumnType = DCColumnType.E_COLUMN_ERROR;
        this.ignoreCase = false;
        this.whereCondType = WhereCondType.E_COND_ERROR;
    }

    public WhereCond(CondConnectByMode condConnectByMode, DCColumnType dcColumnType, boolean ignoreCase, WhereCondType whereCondType, String[] values)
    {
        this.condConnectByMode = condConnectByMode;
        this.dcColumnType = dcColumnType;
        this.ignoreCase = ignoreCase;
        this.whereCondType = whereCondType;
        this.values = values;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        WhereCond r = null;
        if(rhs instanceof WhereCond)
        {
            r = (WhereCond)rhs;
        }

        if(r != null)
        {
            if(this.condConnectByMode != r.condConnectByMode)
            {
                if(this.condConnectByMode == null || r.condConnectByMode == null || !this.condConnectByMode.equals(r.condConnectByMode))
                {
                    return false;
                }
            }
            if(this.dcColumnType != r.dcColumnType)
            {
                if(this.dcColumnType == null || r.dcColumnType == null || !this.dcColumnType.equals(r.dcColumnType))
                {
                    return false;
                }
            }
            if(this.ignoreCase != r.ignoreCase)
            {
                return false;
            }
            if(this.whereCondType != r.whereCondType)
            {
                if(this.whereCondType == null || r.whereCondType == null || !this.whereCondType.equals(r.whereCondType))
                {
                    return false;
                }
            }
            if(!java.util.Arrays.equals(this.values, r.values))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::WhereCond");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, condConnectByMode);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, dcColumnType);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, ignoreCase);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, whereCondType);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, values);
        return h_;
    }

    public WhereCond clone()
    {
        WhereCond c = null;
        try
        {
            c = (WhereCond)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        CondConnectByMode.ice_write(ostr, this.condConnectByMode);
        DCColumnType.ice_write(ostr, this.dcColumnType);
        ostr.writeBool(this.ignoreCase);
        WhereCondType.ice_write(ostr, this.whereCondType);
        ostr.writeStringSeq(this.values);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.condConnectByMode = CondConnectByMode.ice_read(istr);
        this.dcColumnType = DCColumnType.ice_read(istr);
        this.ignoreCase = istr.readBool();
        this.whereCondType = WhereCondType.ice_read(istr);
        this.values = istr.readStringSeq();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, WhereCond v)
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

    static public WhereCond ice_read(com.zeroc.Ice.InputStream istr)
    {
        WhereCond v = new WhereCond();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<WhereCond> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, WhereCond v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<WhereCond> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(WhereCond.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final WhereCond _nullMarshalValue = new WhereCond();

    public static final long serialVersionUID = -1360908872L;
}
