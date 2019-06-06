package com.teee.referencestation.api.sysmanage.model;

import lombok.Data;

@Data
public class WarningType {

    private long id;
    private int type;
    private int subType;
    private int level;
    private String name;
}
