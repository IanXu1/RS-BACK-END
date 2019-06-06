package com.teee.referencestation.api.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class PermissionDelVo {

    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;
}
