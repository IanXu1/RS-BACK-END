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
public class PermissionGrantVo {

    @ApiModelProperty(value = "角色ID", required = true)
    private Long roleId;
    @ApiModelProperty(value = "权限ID数组")
    private List<Long> permissions;
}
