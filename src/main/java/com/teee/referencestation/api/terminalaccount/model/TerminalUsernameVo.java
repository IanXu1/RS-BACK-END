package com.teee.referencestation.api.terminalaccount.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class TerminalUsernameVo {

    @ApiModelProperty(value = "接入账户用户名", required = true)
    private String username;
}
