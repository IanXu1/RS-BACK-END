package com.teee.referencestation.api.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class UserLoginVo {
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不允许为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不允许为空")
    private String password;
}
