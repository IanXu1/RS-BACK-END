package com.teee.referencestation.api.statistics.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhanglei
 */
@Data
public class OperationLog {
    private long id;
    private int logLevel;
    private String operationName;
    private String content;
    private long createdBy;
    private LocalDateTime createdDate;
    private long lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
