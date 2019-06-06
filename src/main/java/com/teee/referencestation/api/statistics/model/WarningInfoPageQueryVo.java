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
public class WarningInfoPageQueryVo extends BasePaginationVo {

    @ApiModelProperty(value = "告警时间(起)")
    private String gte_appearTimeStamp;
    @ApiModelProperty(value = "告警时间(止)")
    private String lte_appearTimeStamp;
    @ApiModelProperty(value = "告警类型")
    private Integer eq_warningType;
    @ApiModelProperty(value = "告警级别")
    private Integer eq_warningLevel;
    @ApiModelProperty(value = "0即当前告警")
    private Integer eq_warningInfoMode;
    @ApiModelProperty(value = "非0即历史告警")
    private Integer neq_warningInfoMode;
    @ApiModelProperty(value = "地基站ID")
    private Long eq_warningBasestationId;
}
