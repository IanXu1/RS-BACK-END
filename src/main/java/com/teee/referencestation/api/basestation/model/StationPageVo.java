package com.teee.referencestation.api.basestation.model;

import com.teee.referencestation.common.base.model.BasePaginationVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel(value = "地基站分页查询VO")
public class StationPageVo extends BasePaginationVo {

    @ApiModelProperty(value = "地基站名字", required = true)
    private String eq_stationName;
}
