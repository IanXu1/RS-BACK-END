package com.teee.referencestation.api.basestation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel(value = "终端账户查询VO")
public class AccountVo {
    @ApiModelProperty(value = "终端名称", required = true)
    private String like_stationName;
}
