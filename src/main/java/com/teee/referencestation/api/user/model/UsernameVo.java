package com.teee.referencestation.api.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class UsernameVo {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
}
