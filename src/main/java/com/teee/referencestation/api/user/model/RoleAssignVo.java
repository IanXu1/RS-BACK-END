package com.teee.referencestation.api.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class RoleAssignVo {

    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;
    @ApiModelProperty(value = "用户ID数组", required = true)
    private List<Long> assignUsers;
}
