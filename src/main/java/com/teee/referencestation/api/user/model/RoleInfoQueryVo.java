package com.teee.referencestation.api.user.model;

import com.teee.referencestation.common.base.model.BasePaginationVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class RoleInfoQueryVo extends BasePaginationVo {

    @ApiModelProperty(value = "角色名字")
    private String roleName;
}
