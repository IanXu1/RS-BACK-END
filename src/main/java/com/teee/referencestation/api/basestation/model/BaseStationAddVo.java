package com.teee.referencestation.api.basestation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhanglei
 */
@Data
@ApiModel(value = "地基站增加Vo")
public class BaseStationAddVo {

    @ApiModelProperty(value = "地基站名字", required = true)
    private String name;
    @ApiModelProperty(value = "地基站海拔", required = true)
    private Double alt;
    @ApiModelProperty(value = "地基站经度", required = true)
    private Double lng;
    @ApiModelProperty(value = "地基站纬度", required = true)
    private Double lat;
    @ApiModelProperty(value = "地基站用户名", required = true)
    private String username;
    @ApiModelProperty(value = "地基站密码", required = true)
    private String password;
    @ApiModelProperty(value = "地基站铭牌", required = true)
    private String namePlate;
    @ApiModelProperty(value = "地基站截止时间(起)", required = true)
    private String expireStartTime;
    @ApiModelProperty(value = "地基站截止时间(止)", required = true)
    private String expireEndTime;
    @ApiModelProperty(value = "地基站范围", required = true)
    private String range;
}
