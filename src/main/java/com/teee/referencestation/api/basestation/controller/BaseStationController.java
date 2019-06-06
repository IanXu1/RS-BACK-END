package com.teee.referencestation.api.basestation.controller;

import com.teee.referencestation.api.basestation.model.*;
import com.teee.referencestation.api.basestation.service.BaseStationService;
import com.teee.referencestation.common.base.controller.BaseController;
import com.teee.referencestation.common.http.RestResponse;
import com.teee.referencestation.utils.JsonUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglei
 * @date 2019-1-21 17:23:42
 */
@RestController
@RequestMapping("/baseStation")
public class BaseStationController extends BaseController {

    @Autowired
    private BaseStationService baseStationService;

    @ApiOperation(value = "获取地基站列表", notes = "根据分页条件查询")
    @PostMapping("/list")
    @RequiresPermissions("stationInfo")
    public RestResponse loadBaseStationList(@RequestBody StationPageVo request) {
        return baseStationService.findStationList(JsonUtil.json2map(JsonUtil.toJSONString(request)), request.getPageNum(),
                request.getPageSize());
    }

    @ApiOperation(value = "添加地基站信息", notes = "输入部分必填参数")
    @PostMapping("/baseStationAdd")
    @RequiresPermissions("stationInfo:add")
    public RestResponse baseStationAdd(@RequestBody BaseStationAddVo request) {
        return baseStationService.addStation(JsonUtil.vo2map(request));
    }

    @ApiOperation(value = "判断基站名称是否存在", notes = "不存在返回true，存在返回false")
    @PostMapping("/isNameExists")
    @RequiresPermissions("stationInfo")
    public RestResponse isNameExists(@RequestBody NameExistsVo request) {
        return RestResponse.success(baseStationService.findBaseStationByName(request.getName()));
    }

    @ApiOperation(value = "判断基站或接入终端用户名是否存在", notes = "不存在返回true，存在返回false")
    @ApiImplicitParam(name = "username", value = "地基站名称", required = true, dataType = "String", paramType = "body")
    @PostMapping("/isUsernameExists")
    @RequiresPermissions("stationInfo")
    public RestResponse isUsernameExists(@RequestBody UsernameExistsVo request) {
        return RestResponse.success(baseStationService.findBaseStationByUsername(request.getUsername()));
    }

    @ApiOperation(value = "根据ID获取地基站详情", notes = "参数必填")
    @PostMapping("/loadBaseStationById")
    @RequiresPermissions("stationInfo")
    public RestResponse loadBaseStationById(@RequestBody BaseStationIdVo request) {
        return baseStationService.loadBaseStationById(request.getId());
    }

    @ApiOperation(value = "修改地基站信息", notes = "输入部分必填参数")
    @PostMapping("/baseStationModify")
    @RequiresPermissions("stationInfo:modify")
    public RestResponse baseStationModify(@RequestBody BaseStationModifyVo request) {
        return baseStationService.modifyStation(JsonUtil.vo2map(request));
    }

    @ApiOperation(value = "根据ID删除指定地基站", notes = "参数必填")
    @PostMapping("/deleteBaseStation")
    @RequiresPermissions("stationInfo:del")
    public RestResponse deleteBaseStation(@RequestBody BaseStationIdVo request) {
        return baseStationService.deleteBaseStation(request.getId());
    }

    @ApiOperation(value = "批量删除指定地基站", notes = "参数必填")
    @PostMapping("/deleteBaseStationBatch")
    @RequiresPermissions("stationInfo:del")
    public RestResponse deleteBaseStationBatch(@RequestBody BaseStationIdArrayVo request) {
        return baseStationService.deleteBaseStationBatch(JsonUtil.vo2map(request));
    }

    @ApiOperation(value = "加载所有未删除的地基站", notes = "无参数")
    @PostMapping("/loadAllStationOption")
    public RestResponse loadAvailableStation() {
        return RestResponse.success(baseStationService.loadAllStationOption());
    }

    @ApiOperation(value = "获取被锁定的地基站账户信息", notes = "根据分页条件查询")
    @PostMapping("/baseStationLockedList")
    @RequiresPermissions("accountLocked")
    public RestResponse baseStationLockedList(@RequestBody StationPageVo request) {
        return baseStationService.findStationList(JsonUtil.vo2map(request), request.getPageNum(), request.getPageSize());
    }

    @ApiOperation(value = "获取所有地基站的位置信息", notes = "为地图显示收集数据")
    @PostMapping("/loadAllPositions")
    public RestResponse loadAllPositions() {
        return baseStationService.loadAllPositions();
    }

    @ApiOperation(value = "根据基站名称获取终端位置信息", notes = "为地图显示收集数据")
    @PostMapping("/getPositionByName")
    public RestResponse getPositionByName(@RequestBody AccountVo request) {
        return baseStationService.getPositionByName(JsonUtil.vo2map(request));
    }
}
