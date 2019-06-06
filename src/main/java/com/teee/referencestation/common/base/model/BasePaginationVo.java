package com.teee.referencestation.common.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
public class BasePaginationVo {

    @ApiModelProperty(value = "分页页码", required = true)
    private int pageNum;
    @ApiModelProperty(value = "分页大小", required = true)
    private int pageSize;
}
