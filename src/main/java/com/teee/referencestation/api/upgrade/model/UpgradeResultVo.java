package com.teee.referencestation.api.upgrade.model;

import lombok.Data;

/**
 * @author zhanglei
 */
@Data
public class UpgradeResultVo {

    private long terminalId;
    private String terminalName;
    private String preVersion;
    private String afterVersion;
    private String sendUserName;
    private int state;
    private int errorCode;
}
