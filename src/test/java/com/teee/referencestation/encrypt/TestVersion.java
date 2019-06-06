package com.teee.referencestation.encrypt;

import com.teee.referencestation.utils.FileUtil;
import org.apache.shiro.codec.Hex;

import java.util.Base64;

public class TestVersion {

    public static void main(String[] args) {
        String versionStr = "BvGjB1Mmpru+yXYgX+Az6gAAAQc=";
        byte[] bytes = Base64.getDecoder().decode(versionStr);
        System.out.println(Hex.encodeToString(FileUtil.subBytes(bytes,0, 16)));
        System.out.println("06f1a3075326a6bbbec976205fe033ea");
    }
}
