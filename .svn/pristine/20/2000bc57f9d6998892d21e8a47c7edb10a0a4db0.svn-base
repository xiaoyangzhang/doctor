package com.yhyt.health.app.controller;

import com.yhyt.health.model.DeptGroupPatient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yhyt.health.model.SysBlacklist;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.SysBlacklistService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月18日 下午2:05:37
 * 类说明
 */
@Api(value = "", description = "黑名单相关接口")
@RestController
public class SysBlacklistAppController {

    @Autowired
    private SysBlacklistService sysBlacklistService;

    private Logger logger = LoggerFactory.getLogger(SignController.class);

    @ApiOperation(value = "科室拉黑患者", notes = "科室拉黑患者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String")
            , @ApiImplicitParam(name = "patientId", value = "患者id", paramType = "query", required = true, dataType = "Long")
            , @ApiImplicitParam(name = "doctorId", value = "医生id", paramType = "query", required = false, dataType = "Long")
            , @ApiImplicitParam(name = "state", value = "1:移出 2：移入", paramType = "query", required = true, dataType = "Long")
    })
    @PostMapping("/sysBlacklists")
    public AppResult addSysBlacklist(@RequestHeader HttpHeaders headers
            ,@RequestParam Long patientId
            ,@RequestParam(required = false) Long doctorId
            ,@RequestParam String state
    ) {
        String token = headers.getFirst("token");

        return sysBlacklistService.addSysBlacklist(token, patientId,doctorId,state);
    }
}
