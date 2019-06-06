package com.teee.referencestation.api.user.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhanglei
 */
@Data
public class SysRole {

    private long id;
    private String roleName;
    private String remarks;
    private int deleted;
    private long createdBy;
    private LocalDateTime createdDate;
    private long lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
