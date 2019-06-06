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
public class RoleIdListVo {

    @ApiModelProperty(value = "角色ID数组", required = true)
    private List<Long> ids;
}
