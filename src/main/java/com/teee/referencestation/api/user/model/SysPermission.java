package com.teee.referencestation.api.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhanglei
 */
@Data
public class SysPermission {

    private int id;
    private int parentId;
    private String name;
    private String title;
    private String icon;
    private String url;
    private String component;
    @JsonIgnore
    private int deleted;
    @JsonIgnore
    private int createdBy;
    @JsonIgnore
    private LocalDateTime createdDate;
    @JsonIgnore
    private int lastModifiedBy;
    @JsonIgnore
    private LocalDateTime lastModifiedDate;
}
