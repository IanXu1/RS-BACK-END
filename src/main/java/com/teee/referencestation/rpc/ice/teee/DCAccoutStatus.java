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

public class DCAccoutStatus implements Cloneable,
                                       java.io.Serializable
{
    public IdInfo idInfo;

    public IdInfo getIdInfo()
    {
        return idInfo;
    }

    public void setIdInfo(IdInfo idInfo)
    {
        this.idInfo = idInfo;
    }

    public LoginStatus loginSatusNtrip;

    public LoginStatus getLoginSatusNtrip()
    {
        return loginSatusNtrip;
    }

    public void setLoginSatusNtrip(LoginStatus loginSatusNtrip)
    {
        this.loginSatusNtrip = loginSatusNtrip;
    }

    public LoginStatus loginSatusLscp;

    public LoginStatus getLoginSatusLscp()
    {
        return loginSatusLscp;
    }

    public void setLoginSatusLscp(LoginStatus loginSatusLscp)
    {
        this.loginSatusLscp = loginSatusLscp;
    }

    public DCPostionStatus dcPostionStatus;

    public DCPostionStatus getDcPostionStatus()
    {
        return dcPostionStatus;
    }

    public void setDcPostionStatus(DCPostionStatus dcPostionStatus)
    {
        this.dcPostionStatus = dcPostionStatus;
    }

    public DCFatalFaultStatus dcFatalFaultStatus;

    public DCFatalFaultStatus getDcFatalFaultStatus()
    {
        return dcFatalFaultStatus;
    }

    public void setDcFatalFaultStatus(DCFatalFaultStatus dcFatalFaultStatus)
    {
        this.dcFatalFaultStatus = dcFatalFaultStatus;
    }

    public DCAccoutStatus()
    {
        this.idInfo = new IdInfo();
        this.loginSatusNtrip = new LoginStatus();
        this.loginSatusLscp = new LoginStatus();
        this.dcPostionStatus = new DCPostionStatus();
        this.dcFatalFaultStatus = new DCFatalFaultStatus();
    }

    public DCAccoutStatus(IdInfo idInfo, LoginStatus loginSatusNtrip, LoginStatus loginSatusLscp, DCPostionStatus dcPostionStatus, DCFatalFaultStatus dcFatalFaultStatus)
    {
        this.idInfo = idInfo;
        this.loginSatusNtrip = loginSatusNtrip;
        this.loginSatusLscp = loginSatusLscp;
        this.dcPostionStatus = dcPostionStatus;
        this.dcFatalFaultStatus = dcFatalFaultStatus;
    }

    public boolean equals(Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        DCAccoutStatus r = null;
        if(rhs instanceof DCAccoutStatus)
        {
            r = (DCAccoutStatus)rhs;
        }

        if(r != null)
        {
            if(this.idInfo != r.idInfo)
            {
                if(this.idInfo == null || r.idInfo == null || !this.idInfo.equals(r.idInfo))
                {
                    return false;
                }
            }
            if(this.loginSatusNtrip != r.loginSatusNtrip)
            {
                if(this.loginSatusNtrip == null || r.loginSatusNtrip == null || !this.loginSatusNtrip.equals(r.loginSatusNtrip))
                {
                    return false;
                }
            }
            if(this.loginSatusLscp != r.loginSatusLscp)
            {
                if(this.loginSatusLscp == null || r.loginSatusLscp == null || !this.loginSatusLscp.equals(r.loginSatusLscp))
                {
                    return false;
                }
            }
            if(this.dcPostionStatus != r.dcPostionStatus)
            {
                if(this.dcPostionStatus == null || r.dcPostionStatus == null || !this.dcPostionStatus.equals(r.dcPostionStatus))
                {
                    return false;
                }
            }
            if(this.dcFatalFaultStatus != r.dcFatalFaultStatus)
            {
                if(this.dcFatalFaultStatus == null || r.dcFatalFaultStatus == null || !this.dcFatalFaultStatus.equals(r.dcFatalFaultStatus))
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
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::teee::DCAccoutStatus");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, idInfo);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, loginSatusNtrip);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, loginSatusLscp);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, dcPostionStatus);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, dcFatalFaultStatus);
        return h_;
    }

    public DCAccoutStatus clone()
    {
        DCAccoutStatus c = null;
        try
        {
            c = (DCAccoutStatus)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        IdInfo.ice_write(ostr, this.idInfo);
        LoginStatus.ice_write(ostr, this.loginSatusNtrip);
        LoginStatus.ice_write(ostr, this.loginSatusLscp);
        DCPostionStatus.ice_write(ostr, this.dcPostionStatus);
        DCFatalFaultStatus.ice_write(ostr, this.dcFatalFaultStatus);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.idInfo = IdInfo.ice_read(istr);
        this.loginSatusNtrip = LoginStatus.ice_read(istr);
        this.loginSatusLscp = LoginStatus.ice_read(istr);
        this.dcPostionStatus = DCPostionStatus.ice_read(istr);
        this.dcFatalFaultStatus = DCFatalFaultStatus.ice_read(istr);
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, DCAccoutStatus v)
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

    static public DCAccoutStatus ice_read(com.zeroc.Ice.InputStream istr)
    {
        DCAccoutStatus v = new DCAccoutStatus();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<DCAccoutStatus> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, DCAccoutStatus v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<DCAccoutStatus> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(DCAccoutStatus.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final DCAccoutStatus _nullMarshalValue = new DCAccoutStatus();

    public static final long serialVersionUID = -113352005L;
}
