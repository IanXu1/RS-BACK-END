package com.teee.referencestation.api.upgrade.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhanglei
 */
@Data
public class UpgradeFile {
    private long id;
    private String fileName;
    private long fileSize;
    private String filePath;
    private String fileType;
    private String md5;
    private int state;
    private int deleted;
    private long createdBy;
    private LocalDateTime createdDate;
    private long lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
