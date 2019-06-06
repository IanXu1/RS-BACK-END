package com.teee.referencestation.api.terminalaccount.model;

import com.teee.referencestation.common.base.model.BasePaginationVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class TerminalQueryVo extends BasePaginationVo {

    @ApiModelProperty(value = "接入账户用户名")
    private String like_accountUsername;
}
