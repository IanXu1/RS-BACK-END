package com.teee.referencestation.api.basestation.model;

import com.teee.referencestation.rpc.ice.teee.DCFatalFaultStatus;
import com.teee.referencestation.rpc.ice.teee.LoginStatus;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
public class MapPosition {

    private long id;
    private int type;
    private double lng;
    private double lat;
    private boolean isValid;
    private String name;
    private String username;
    private long latestReportTime;
    private String currentVersion;
    private LoginStatus scpStatus;
    private LoginStatus ntripStatus;
    private DCFatalFaultStatus faultStatus;
}
