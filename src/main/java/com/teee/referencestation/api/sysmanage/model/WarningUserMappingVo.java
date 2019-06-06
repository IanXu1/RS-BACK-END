package com.teee.referencestation.api.sysmanage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class WarningUserMappingVo {

    @ApiModelProperty(value = "告警类型ID", required = true)
    private long warningTypeId;
    @ApiModelProperty(value = "用户ID", required = true)
    private long userId;
}
