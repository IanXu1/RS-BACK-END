package com.teee.referencestation.api.terminalaccount.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class TerminalModifyVo {

    @ApiModelProperty(value = "接入账户ID", required = true)
    private Long terminalAccountId;
    @ApiModelProperty(value = "接入账户用户名", required = true)
    private String username;
    @ApiModelProperty(value = "接入账户密码", required = true)
    private String password;
    @ApiModelProperty(value = "接入账户类型", required = true)
    private Integer accountType;
    @ApiModelProperty(value = "接入账户过期时间(起)")
    private String expireStartTime;
    @ApiModelProperty(value = "接入账户过期时间(止)")
    private String expireEndTime;
}
