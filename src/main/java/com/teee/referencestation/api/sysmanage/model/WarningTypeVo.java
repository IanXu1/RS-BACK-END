package com.teee.referencestation.api.sysmanage.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author zhanglei
 */
@Data
@ApiModel(value = "告警类型下拉列表Vo")
public class WarningTypeVo {
    private long id;
    private int level;
    private int type;
    private String name;
    private List<WarningTypeVo> children;
}
