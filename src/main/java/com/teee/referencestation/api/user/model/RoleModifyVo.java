package com.teee.referencestation.api.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class RoleModifyVo {

    @ApiModelProperty(value = "角色ID", required = true)
    private Long id;
    @ApiModelProperty(value = "角色名字", required = true)
    private String roleName;
    @ApiModelProperty(value = "备注")
    private String remarks;
}
