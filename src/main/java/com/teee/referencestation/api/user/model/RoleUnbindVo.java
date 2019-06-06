package com.teee.referencestation.api.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class RoleUnbindVo {

    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;
    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;
}
