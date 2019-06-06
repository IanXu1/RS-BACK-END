package com.teee.referencestation.api.upgrade.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhanglei
 */
@Data
public class UpgradeResult {

    private long id;
    private long terminalId;
    private long preVersionId;
    private long afterVersionId;
    private long sendUserId;
    private int state;
    private int errorCode;
    private long createdBy;
    private LocalDateTime createdDate;
    private long lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
