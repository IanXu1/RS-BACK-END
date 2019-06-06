package com.teee.referencestation.api.terminalaccount.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class TerminalIdListVo {

    @ApiModelProperty(value = "接入账户ID数组", required = true)
    private List<Long> ids;
}
