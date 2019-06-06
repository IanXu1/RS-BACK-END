package com.teee.referencestation.api.basestation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class BaseStationIdArrayVo {
    @ApiModelProperty(value = "地基站ID数组", required = true)
    private long[] ids;
}
