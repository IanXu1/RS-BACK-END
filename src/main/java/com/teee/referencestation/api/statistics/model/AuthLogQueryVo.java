package com.teee.referencestation.api.statistics.model;

import com.teee.referencestation.common.base.model.BasePaginationVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class AuthLogQueryVo extends BasePaginationVo {

    @ApiModelProperty(value = "鉴权时间(起)-时间戳格式")
    private String gte_authTimeStamp;
    @ApiModelProperty(value = "鉴权时间(止)-时间戳格式")
    private String let_authTimeStamp;
}
