package com.teee.referencestation.api.sysmanage.model;

import lombok.Data;

@Data
public class WarningVisibleVo {

    private int id;
    private long warningTypeId;
    private int duty;
    private int visible;
    private String content;
}
