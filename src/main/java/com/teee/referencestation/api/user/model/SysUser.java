package com.teee.referencestation.api.user.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhanglei
 */
@Data
public class SysUser {

    private long id;
    private String username;
    private String password;
    private String realName;
    private String salt;
    private String cellPhoneNumber;
    private String email;
    private int duty;
    private int state;
    private int deleted;
    private long createdBy;
    private LocalDateTime createdDate;
    private long lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
