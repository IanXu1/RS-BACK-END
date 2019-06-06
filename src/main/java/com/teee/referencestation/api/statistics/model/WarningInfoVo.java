package com.teee.referencestation.api.statistics.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel
public class WarningInfoVo {

    private long id;
    private long warningType;
    private long subWarningType;
    private String typeName;
    private int warningLevel;
    private String levelName;
    private long occurredTime;
    private String stationName;
    private String occurredContent;
    private long clearTime;
    private String clearContent;
}
