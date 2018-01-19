package com.yhyt.health.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yhyt.health.model.DeptLabel;
import com.yhyt.health.model.DeptPatientLable;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.api.DepartmentLabelApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月25日 下午3:27:37
 * 类说明
 */
@Api(value = "", description = "科室标签")
@RestController
public class DepartmentLabelAppController {

    @Autowired
    private DepartmentLabelApi departmentLabelApi;

    private static Logger logger = LoggerFactory.getLogger(DepartmentLabelAppController.class);

    @ApiOperation(value = "获取标签", notes = "获取标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = false, dataType = "long")
    })
    @GetMapping("/departmentLabels")
    public AppResult queryDepartmentLabels(
            @RequestHeader HttpHeaders httpHeaders
            , @RequestParam(required = false) Long departmentId) {
        String token = httpHeaders.getFirst("token");
        DeptLabel deptLabel = new DeptLabel();
        deptLabel.setDepartmentId(departmentId);
        return departmentLabelApi.queryDepartmentLabels(token, deptLabel);
    }

    @ApiOperation(value = "添加标签", notes = "添加标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = false, dataType = "long"),
            @ApiImplicitParam(name = "name", value = "标签名称", paramType = "query", required = false, dataType = "String")
    })
    @PostMapping("/departmentLabels")
    public AppResult addDepartmentLabels(
            @RequestHeader HttpHeaders httpHeaders
            , @RequestParam(required = false) Long departmentId
            , @RequestParam String name) {
        String token = httpHeaders.getFirst("token");
        DeptLabel deptLabel = new DeptLabel();
        deptLabel.setName(name);
        deptLabel.setDepartmentId(departmentId);
        return departmentLabelApi.addDepartmentLabels(token, deptLabel);
    }

    @ApiOperation(value = "修改标签", notes = "修改标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "标签id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "name", value = "标签名称", paramType = "query", required = true, dataType = "String")
    })
    @PatchMapping("/departmentLabels")
    public AppResult updateDepartmentLabels(
            @RequestHeader HttpHeaders httpHeaders
            , @RequestParam Long id
            , @RequestParam String name) {
        String token = httpHeaders.getFirst("token");
        DeptLabel deptLabel = new DeptLabel();
        deptLabel.setName(name);
        deptLabel.setId(id);
        return departmentLabelApi.updateDepartmentLabels(token, deptLabel);
    }


    @ApiOperation(value = "删除标签", notes = "删除标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "标签id", paramType = "query", required = false, dataType = "String")
    })
    @DeleteMapping("/departmentLabels")
    public AppResult deleteDepartmentLabels(
            @RequestHeader HttpHeaders httpHeaders
            , @RequestParam Long id) {
        String token = httpHeaders.getFirst("token");
        DeptLabel deptLabel = new DeptLabel();
        deptLabel.setId(id);
        return departmentLabelApi.deleteDepartmentLabels(token, deptLabel);
    }

    @ApiOperation(value = "获取科室下患者标签", notes = "获取科室下患者标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = false, dataType = "long"),
            @ApiImplicitParam(name = "patientId", value = "患者id", paramType = "query", required = true, dataType = "long")
    })
    @GetMapping("/departmentPatientLabels")
    public AppResult queryDepartmentPatientLabels(
            @RequestHeader HttpHeaders httpHeaders
            , @RequestParam(required = false) Long departmentId
            , @RequestParam Long patientId
    ) {
        String token = httpHeaders.getFirst("token");
        DeptPatientLable deptPatientLable = new DeptPatientLable();
        deptPatientLable.setPatientId(patientId);
        return departmentLabelApi.queryDepartmentPatientLabels(token, deptPatientLable);
    }


    @ApiOperation(value = "修改科室下患者标签", notes = "修改科室下患者标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = false, dataType = "long"),
            @ApiImplicitParam(name = "patientId", value = "患者id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "ids", value = "标签id多个以逗号隔开", paramType = "query", required = false, dataType = "String")
    })
    @PostMapping("/departmentPatientLabels")
    public AppResult updateDepartmentPatientLabels(
            @RequestHeader HttpHeaders httpHeaders
            , @RequestParam(required = false) Long departmentId
            , @RequestParam Long patientId
            , @RequestParam(required = false) String ids
    ) {
        String token = httpHeaders.getFirst("token");
        return departmentLabelApi.updateDepartmentPatientLabels(token, ids, departmentId, patientId);
    }
}
