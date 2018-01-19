package com.yhyt.health.app.controller;

import com.yhyt.health.result.AppResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * 工作站服务包
 *
 * @author gsh
 * @create 2018-01-02 11:19
 **/
@Api(value = "", description = "服务包(工作站)")
@RestController
@RequestMapping("/item")
public class ItemAppController {

    @Autowired
    private ItemApi itemApi;

    @ApiOperation(value = "获取服务包申请列表", notes = "获取服务包申请列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value =  "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "doctorState", value = "操作状态 1 待处理 2处理中 3已完成 4 已拒绝", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/items")
    public AppResult getItems(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestParam(name = "userId",required = false) Long userId,
            @RequestParam(name = "doctorState") String doctorState
    ) {
        String token = httpHeaders.getFirst("token");
        return  itemApi.getItems(token,userId,doctorState);
    }

    @ApiOperation(value = "服务包服务记录", notes = "服务包服务记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "roomId", value = "房间id", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/items")
    public AppResult getItemRecords(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestParam(name = "roomId") String roomId
    ) {
        String token = httpHeaders.getFirst("token");
        return  itemApi.getItemRecords(token,roomId);
    }

    @ApiOperation(value = "获取服务包申请详情", notes = "获取服务包申请详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "任务id", paramType = "path", required = true, dataType = "Long")
    })
    @GetMapping("/{id}")
    public AppResult getItem(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable Long id
    ) {
        String token = httpHeaders.getFirst("token");
        return  itemApi.getItem(token,id);
    }

    @ApiOperation(value = "接受/结束 服务包申请服务", notes = "接受/结束 服务包申请服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "id", value = "任务id", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "doctorState", value = "任务状态", paramType = "query", required = true, dataType = "String"),
    })
    @PatchMapping("/{id}")
    public AppResult updateItemState(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestParam(name = "userId",required = false) Long userId,
            @PathVariable Long id,
            @RequestParam String doctorState
    ) {
        String token = httpHeaders.getFirst("token");
        return  itemApi.updateItemState(token,userId,id,doctorState);
    }

    @ApiOperation(value = "服务包服务状态", notes = "服务包服务状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orderId", value = "订单id", paramType = "path", required = false, dataType = "Long")
    })
    @PatchMapping("/order/{orderId}")
    public AppResult getTaskServiceState(
            @RequestHeader HttpHeaders httpHeaders,
            @PathVariable Long orderId
    ) {
        String token = httpHeaders.getFirst("token");
        return  itemApi.getTaskServiceState(token,orderId);
    }



}
