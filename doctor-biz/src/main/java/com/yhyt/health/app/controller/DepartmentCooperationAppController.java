package com.yhyt.health.app.controller;

import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.model.DeptCooperation;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.DeptCooperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: gsh
 *
 * @version 创建时间：2017年8月15日 下午3:54:03
 * 科室相关接口
 */
@Api(value = "", description = "合作科室相关操作")
@RestController
public class DepartmentCooperationAppController {

    @Autowired
    private DeptCooperationService deptCooperationService;

    private static Logger logger = LoggerFactory.getLogger(DepartmentCooperationAppController.class);

    @ApiOperation(value = "添加合作机构", notes = "添加合作机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "doctorIdOriginator", value = "医生id", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "hospitalIdOriginator", value = "发起的医院id", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "deparmentIdOriginator", value = "发起的科室id", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "hospitalIdCooperation", value = "合作的医院id", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "deparmentIdCooperation", value = "合作的科室id", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "doctorIdCooperation", value = "合作方的医生id", paramType = "query", required = true, dataType = "Long")
    })
    @PostMapping("/cooperations")
    public AppResult cooperations(@RequestHeader("token") String token,
                                  @RequestParam(required = false) Long doctorIdOriginator,
                                  @RequestParam(required = false) Long hospitalIdOriginator,
                                  @RequestParam(required = false) Long deparmentIdOriginator,
                                  @RequestParam(required = false) Long hospitalIdCooperation,
                                  @RequestParam Long deparmentIdCooperation,
                                  @RequestParam Long doctorIdCooperation
    ) {
        //组装科室合作表
        DeptCooperation cooperation = new DeptCooperation();
        cooperation.setDoctorIdOriginator(doctorIdOriginator);
        cooperation.setHospitalIdOriginator(hospitalIdOriginator);
        cooperation.setDeparmentIdOriginator(deparmentIdOriginator);
        cooperation.setHospitalIdCooperation(hospitalIdCooperation);
        cooperation.setDeparmentIdCooperation(deparmentIdCooperation);
        cooperation.setDoctorIdCooperation(doctorIdCooperation);
        return deptCooperationService.addDeptCooperation(cooperation, token);
    }

    @ApiOperation(value = "获取科室合作审批表详情", notes = "获取科室合作审批表详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "合作表id", paramType = "path", required = true, dataType = "long"),
    })
    @GetMapping("/deptCooperationReviews/id/{id}")
    public AppResult deptCooperationReviews(@RequestHeader("token") String token, @PathVariable long id) {
        //查询合作审核信息
        return deptCooperationService.getDeptCooperationReviewByid(token, id);
    }

    @ApiOperation(value = "审核科室合作审批表", notes = "审核科室合作审批表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "合作审批表id", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "state", value = "2通过3拒绝", paramType = "query", required = true, dataType = "String")
    })
    @PatchMapping("/deptCooperationReviews/id/{id}")
    public AppResult audtDeptCooperationReviews(@RequestHeader("token") String token
            , @PathVariable long id, @RequestParam String state) {
        return deptCooperationService.auditDeptCooperationReviews(token, id, state);
    }


    @ApiOperation(value = "获取合作机构", notes = "获取合作机构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = true, dataType = "long")
            , @ApiImplicitParam(name = "state", value = "合作状态 1-待审核  2-审核中  3-合作 4-拒绝(多个以逗号隔开)", paramType = "query", required = false, dataType = "String")
    })
    @GetMapping("/cooperations")
    public AppResult cooperations(
            @RequestHeader HttpHeaders httpHeaders
            , @RequestParam long departmentId
            , @RequestParam(required = false) String state
    ) {
        String token = httpHeaders.getFirst("token");
        Map<String, Object> map = new HashMap<>();
        //组装状态
        if (null != state && !"".equals(state)) {
            String[] stateStr = state.split(",");
            List<Byte> states = new ArrayList<Byte>();
            for (int i = 0; i < stateStr.length; i++) {
                states.add(Byte.valueOf(stateStr[i]));
            }
            map.put("states", states);
        }
        map.put("departmentId", departmentId);
        AppResult appResult = new AppResult();
        //返回app
        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("list", deptCooperationService.getDeptCooperations(map));
        return appResult;
    }


    @ApiOperation(value = "获取合作机构消息列表", notes = "获取合作机构消息列表,返回状态(审核状态 1-未审核 2-通过 3-拒绝)")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"), @ApiImplicitParam(name = "pageIndex", value = "页码", paramType = "query", required = false, dataType = "Integer")
            , @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", required = false, dataType = "Integer")})
    @GetMapping("/cooperationsMessages")
    public AppResult cooperationsMessages(@RequestHeader HttpHeaders httpHeaders, @RequestParam(required = false) Integer pageIndex
            , @RequestParam(required = false) Integer pageSize) {
        String token = httpHeaders.getFirst("token");
        AppResult appResult = deptCooperationService.getCooperationsMessages(token, pageIndex, pageSize);
        return appResult;
    }
}
