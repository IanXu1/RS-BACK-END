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
public class OperationLogVo extends BasePaginationVo {

    @ApiModelProperty(value = "日志级别")
    private Integer logLevel;
    @ApiModelProperty(value = "日志级别(起)")
    private String moreOperateDate;
    @ApiModelProperty(value = "操作时间(止)")
    private String lessOperateDate;
    @ApiModelProperty(value = "操作名字")
    private String operationName;
}
