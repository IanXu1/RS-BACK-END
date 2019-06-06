package com.teee.referencestation.api.sysmanage.model;

import com.teee.referencestation.common.base.model.BasePaginationVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class PushUserPageQueryVo extends BasePaginationVo {

    @ApiModelProperty(value = "告警类型ID", required = true)
    private int warningTypeId;
    @ApiModelProperty(value = "用户真实姓名")
    private String realName;
    @ApiModelProperty(value = "电话号码")
    private String cellPhoneNumber;
}
