package com.teee.referencestation.api.upgrade.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhanglei
 */
@Data
public class UpgradeVersion {

    private long id;
    private String versionNum;
    private long fileId;
    private long sendUserId;
    private LocalDateTime sendTime;
    private int state;
    private int deleted;
    private long createdBy;
    private LocalDateTime createdDate;
    private long lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
