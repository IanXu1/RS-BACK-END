package com.teee.referencestation.api.sysmanage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class AccountLockedBatchVo {
    @ApiModelProperty(value = "锁定终端数组", required = true)
    private List<AccountLockedVo> accountLockedList;
}
