package com.teee.referencestation.api.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class RoleNameVo {

    @ApiModelProperty(value = "角色名字", required = true)
    private String roleName;
}
