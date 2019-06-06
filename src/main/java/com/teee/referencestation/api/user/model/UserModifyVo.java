package com.teee.referencestation.api.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class UserModifyVo {
    @ApiModelProperty(value = "角色ID", required = true)
    private Long id;
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "姓名", required = true)
    private String realName;
    @ApiModelProperty(value = "电话号码", required = true)
    private String cellPhoneNumber;
    @ApiModelProperty(value = "邮箱")
    private String email;
}
