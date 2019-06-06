package com.teee.referencestation.api.upgrade.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zhanglei
 */
@Data
public class UpgradeVersionVo {

    private long id;
    private String versionNum;
    private String fileName;
    private String sendUserName;
    private String createUserName;
    private int state;
    private int deleted;
    private LocalDateTime createTime;
    private LocalDateTime sendTime;
}
