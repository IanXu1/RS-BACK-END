package com.teee.referencestation.api.sysmanage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class IdVo {

    @ApiModelProperty(value = "ID", required = true)
    private long id;
}
