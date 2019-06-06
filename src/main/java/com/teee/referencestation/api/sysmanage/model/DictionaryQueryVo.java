package com.teee.referencestation.api.sysmanage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class DictionaryQueryVo {
    @ApiModelProperty(value = "词典一级code")
    private int dicCode;
    @ApiModelProperty(value = "词典二级code")
    private int itemCode;
}
