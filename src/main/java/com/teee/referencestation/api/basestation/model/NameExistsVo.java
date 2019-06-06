package com.teee.referencestation.api.basestation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel(value = "地基站名称查重VO")
public class NameExistsVo {
    @ApiModelProperty(value = "地基站名称", required = true)
    private String name;
}
