package com.teee.referencestation.api.terminalaccount.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class TerminalIdVo {

    @ApiModelProperty(value = "接入账户ID", required = true)
    private Long id;
}
