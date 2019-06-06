package com.teee.referencestation.api.sysmanage.model;

import com.teee.referencestation.common.base.model.BasePaginationVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel(value = "告警推送分页查询Vo")
public class WarningTypePageQueryVo extends BasePaginationVo {

    private Integer type;
    private Integer subType;
    private Integer level;
}
