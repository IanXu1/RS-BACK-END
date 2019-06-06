package com.teee.referencestation.api.sysmanage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class AccountLockedVo {
    @ApiModelProperty(value = "锁定ID", required = true)
    private long id;
    @ApiModelProperty(value = "终端账户", required = true)
    private String username;
}
