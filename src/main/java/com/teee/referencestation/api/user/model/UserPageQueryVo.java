package com.teee.referencestation.api.user.model;

import com.teee.referencestation.common.base.model.BasePaginationVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class UserPageQueryVo extends BasePaginationVo {

    @ApiModelProperty(value = "电话号码")
    private String cellPhoneNumber;

    @ApiModelProperty(value = "姓名")
    private String realName;
}
