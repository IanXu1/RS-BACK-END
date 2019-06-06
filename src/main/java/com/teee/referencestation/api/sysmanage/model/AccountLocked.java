package com.teee.referencestation.api.sysmanage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@AllArgsConstructor
public class AccountLocked {

    private long id;
    private String username;
    private int type;
    private long createdDate;
}
