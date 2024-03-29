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
// Generated from file `TEEETerminalAccount.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.teee.referencestation.rpc.ice.teee;

public final class TerminalAccountSeqHelper
{
    public static void write(com.zeroc.Ice.OutputStream ostr, TerminalAccount[] v)
    {
        if(v == null)
        {
            ostr.writeSize(0);
        }
        else
        {
            ostr.writeSize(v.length);
            for(int i0 = 0; i0 < v.length; i0++)
            {
                TerminalAccount.ice_write(ostr, v[i0]);
            }
        }
    }

    public static TerminalAccount[] read(com.zeroc.Ice.InputStream istr)
    {
        final TerminalAccount[] v;
        final int len0 = istr.readAndCheckSeqSize(64);
        v = new TerminalAccount[len0];
        for(int i0 = 0; i0 < len0; i0++)
        {
            v[i0] = TerminalAccount.ice_read(istr);
        }
        return v;
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<TerminalAccount[]> v)
    {
        if(v != null && v.isPresent())
        {
            write(ostr, tag, v.get());
        }
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, TerminalAccount[] v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            TerminalAccountSeqHelper.write(ostr, v);
            ostr.endSize(pos);
        }
    }

    public static java.util.Optional<TerminalAccount[]> read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            TerminalAccount[] v;
            v = TerminalAccountSeqHelper.read(istr);
            return java.util.Optional.of(v);
        }
        else
        {
            return java.util.Optional.empty();
        }
    }
}
