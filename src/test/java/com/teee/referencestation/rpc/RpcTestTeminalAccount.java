package com.teee.referencestation.rpc;

import com.teee.referencestation.rpc.ice.teee.*;
import com.zeroc.Ice.EndpointSelectionType;
import com.zeroc.Ice.InputStream;
import com.zeroc.Ice.OutputStream;
import com.zeroc.Ice.ValueWriter;
import io.netty.buffer.ByteBuf;
import org.apache.shiro.codec.Hex;
import org.springframework.cache.support.SimpleValueWrapper;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class RpcTestTeminalAccount {

    public static void main(String[] args) throws UnsupportedEncodingException {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "ice/config.client", extraArgs)) {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("TEEERSDCAPIServant");
            TEEERSDCAPIServantPrx teeersdcapiServantPrx = TEEERSDCAPIServantPrx.checkedCast(base);
            teeersdcapiServantPrx.ice_connectionCached(false);
            teeersdcapiServantPrx.ice_endpointSelection(EndpointSelectionType.Random);
            if (teeersdcapiServantPrx == null) {
                throw new Error("Invalid proxy");
            }



            RPCHeader rpcHeader = new RPCHeader();
            rpcHeader.reqNo = UUID.randomUUID().toString();
            rpcHeader.reqTimeStamp = System.currentTimeMillis();


            TerminalAccount account = new TerminalAccount();
            account.username = "1";
            account.password = "1";
            account.auditingInfo = new AuditingInfo();


//            Position position = new Position();
//            position.altitude = 0;
//            position.latitude = 0;
//            position.longitude = 0;


            OutputStream outputStream = new OutputStream(communicator);
            outputStream.startEncapsulation();
//            Position.ice_write(outputStream, position);
//            TerminalAccount.ice_write(outputStream, account);
            outputStream.writeInt(300);
//            outputStream.startSlice();
            outputStream.endEncapsulation();
            byte[] accountBytes = outputStream.finished();

            System.out.println(outputStream.getEncoding().major);
            System.out.println(outputStream.getEncoding().minor);
            System.out.println(Arrays.toString(accountBytes));
            System.out.println(Hex.encodeToString(accountBytes));

            byte[] b2 = new byte[4];
            ByteBuffer byteBuffer = ByteBuffer.allocate(4);
            byteBuffer.putInt(0);
            byteBuffer.flip();
            if (byteBuffer.hasArray()) {
                byteBuffer.get(b2);
                System.out.println(Arrays.toString(b2));
            }
            System.out.println(byteBuffer.getInt(0));

//            InputStream inputStream = new InputStream(communicator, accountBytes);
//            inputStream.startEncapsulation();
//
//            TerminalAccount terminalAccount = TerminalAccount.ice_read(inputStream);
//            inputStream.endEncapsulation();
//            System.out.println(terminalAccount);
//
            DCCreateReq dcCreateReq = new DCCreateReq(rpcHeader, DCTableType.E_TABLE_TERMINAL_ACCOUNT, true, accountBytes);

            TEEERSDCAPIServant.DCCreateResult dcCreateResult = teeersdcapiServantPrx.DCCreate(dcCreateReq);
            System.out.println(dcCreateResult.resp.rpcHeader.retCode);
        }
    }
}
