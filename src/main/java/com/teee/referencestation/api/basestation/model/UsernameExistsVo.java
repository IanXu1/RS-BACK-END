package com.teee.referencestation.api.basestation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel(value = "终端用户名查重VO")
public class UsernameExistsVo {
    @ApiModelProperty(value = "终端用户名", required = true)
    private String username;
}
