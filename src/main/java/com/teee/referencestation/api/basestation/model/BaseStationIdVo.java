package com.teee.referencestation.api.basestation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class BaseStationIdVo {
    @ApiModelProperty(value = "地基站ID", required = true)
    private long id;
}
