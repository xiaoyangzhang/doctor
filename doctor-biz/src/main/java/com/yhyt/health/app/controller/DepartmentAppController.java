package com.yhyt.health.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yhyt.health.mapper.DepartmentMapper;
import com.yhyt.health.controller.DepartmentController;
import com.yhyt.health.model.DeptDoctor;
import com.yhyt.health.model.DeptGroup;
import com.yhyt.health.model.DeptGroupPatient;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.DepartmentService;
import com.yhyt.health.service.api.DepartmentApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月18日 上午9:49:05
 * 类说明
 */
@Api(value = "", description = "app科室相关操作")
@RestController
public class DepartmentAppController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentApi departmentApi;
    
    @Autowired
	private DepartmentMapper departmentMapper;

    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @ApiOperation(value = "获取医生执业单位", notes = "获取医生执业单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "doctorId", value = "医生id", paramType = "query", required = false, dataType = "Long")
    })
    @GetMapping("/departments")
    public AppResult getDepartments(@RequestHeader("token") String token, @RequestParam(required = false) Long doctorId) {
        Map<String, Object> map = new HashMap<>();
        map.put("doctorId", doctorId);
        return departmentService.practiceDepartments(token, doctorId);
    }

    @ApiOperation(value = "改变医生执业单位", notes = "改变医生执业单位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = true, dataType = "Long")
    })
    @PatchMapping("/departments")
    public AppResult updateDoctorDepartments(@RequestHeader("token") String token
            , @RequestParam long departmentId) {
        return departmentService.updateDoctorDepartments(token, departmentId);

    }

    @ApiOperation(value = "提交就诊须知", notes = "提交就诊须知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "notice", value = "就诊须知", paramType = "query", required = true, dataType = "String")
    })
    @PatchMapping("/departments/departmentId/{departmentId}")
    public AppResult updateNotice(@RequestHeader("token") String token
            , @PathVariable("departmentId") long deptId
            , @RequestParam("notice") String notice) {

        return departmentApi.updateDeptNotice(deptId, notice);
    }

    @ApiOperation(value = "获取当前科室医生列表", notes = "获取当前科室医生列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = true, dataType = "long")
            , @ApiImplicitParam(name = "pageIndex", value = "页码", paramType = "query", required = false, dataType = "Integer")
            , @ApiImplicitParam(name = "pageSize", value = "页容量", paramType = "query", required = false, dataType = "Integer")
    })
    @GetMapping("/deptDoctor")
    public AppResult updateNotice(@RequestHeader("token") String token
            , @RequestParam long departmentId
            , @RequestParam(required = false) Integer pageIndex
            , @RequestParam(required = false)  Integer pageSize) {
        return departmentApi.getDeptDoctorList(departmentId,pageIndex,pageSize);
    }

    @ApiOperation(value = "获取当前科室医生列表(为患者端提供)", notes = "获取当前科室医生列表（患者端返回数据与医生端有区别，所以单独给患者端提供一个接口）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = true, dataType = "long")
            , @ApiImplicitParam(name = "pageIndex", value = "页码", paramType = "query", required = false, dataType = "Integer")
            , @ApiImplicitParam(name = "pageSize", value = "页容量", paramType = "query", required = false, dataType = "Integer")
    })
    @GetMapping("/deptDoctorForPatient")
    public AppResult deptDoctor(@RequestHeader("token") String token
            , @RequestParam long departmentId
            , @RequestParam(required = false) Integer pageIndex
            , @RequestParam(required = false)  Integer pageSize) {
        return departmentApi.getDoctorByDepartmentIdForPatient(token, departmentId,pageIndex,pageSize);
    }

    @ApiOperation(value = "根据医生获取科室列表", notes = "根据医生获取科室列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "doctorId", value = "医生id", paramType = "query", required = true, dataType = "long")
    })
    @GetMapping("/doctorDept")
    public AppResult doctorDept(@RequestHeader("token") String token, @RequestParam long doctorId) {
        return departmentApi.getDoctorDeptList(token, doctorId);
    }


    @ApiOperation(value = "确认加入科室", notes = "确认加入科室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "id", value = "被邀请医生id", paramType = "path", required = true, dataType = "long")
    })
    @PatchMapping("/doctors/id/{id}")
    public AppResult joinDept(@RequestHeader("token") String token, @RequestParam long departmentId, @PathVariable("id") long id) {
        DeptDoctor deptDoctor = new DeptDoctor();
        deptDoctor.setDoctorId(id);
        deptDoctor.setDepartmentId(departmentId);
        return departmentApi.addDeptDoctor(token,deptDoctor);
    }

    @ApiOperation(value = "已分组用户修改分组/修改分组用户备注", notes = "已分组用户修改分组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "deptGroupId", value = "科室分组id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "id", value = "患者id", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "remark", value = "分组备注", paramType = "query", required = false, dataType = "String")
    })
    @PatchMapping("/deptGroupPatients/id/{id}")
    public AppResult updatePatientGroup(@RequestHeader("token") String token
            , @RequestParam long deptGroupId
            , @PathVariable("id") long id
            , @RequestParam(required = false) String remark
    ) {
        DeptGroupPatient deptGroupPatient = new DeptGroupPatient();
        deptGroupPatient.setDeptGroupId(deptGroupId);
        deptGroupPatient.setPatientId(id);
        deptGroupPatient.setRemark(remark);
        return departmentApi.updatePatientGroup(token, deptGroupPatient);
    }

    @ApiOperation(value = "患者分组并备注", notes = "患者分组并备注")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "分组备注", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "deptGroupId", value = "科室分组id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "patientId", value = "患者id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "flag", value = "小旗子", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/deptGroupPatients")
    public AppResult addPatientGroup(@RequestHeader("token") String token
            , @RequestParam Long deptGroupId
            , @RequestParam Long patientId
            , @RequestParam String remark
            , @RequestParam String flag) {
        DeptGroupPatient deptGroupPatient = new DeptGroupPatient();
        deptGroupPatient.setRemark(remark);
        deptGroupPatient.setPatientId(patientId);
        deptGroupPatient.setDeptGroupId(deptGroupId);
        if(null != flag && !"".equals(flag))
          deptGroupPatient.setFlag(Byte.valueOf(flag));
        else
          deptGroupPatient.setFlag(null);
        return departmentApi.addPatientGroup(token,deptGroupPatient);
    }

    @ApiOperation(value = "患者添加科室默认分组", notes = "患者添加科室默认分组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentId", value = "科室分组id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "patientId", value = "患者id", paramType = "query", required = true, dataType = "long"),
    })
    @PostMapping("/addPatientDefaultGroup")
    public AppResult addPatientDefaultGroup(@RequestParam Long departmentId, @RequestParam Long patientId) {
        return departmentApi.addPatientDefaultGroup(departmentId, patientId);
    }

    @ApiOperation(value = "创建分组", notes = "创建分组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = false, dataType = "long"),
            @ApiImplicitParam(name = "name", value = "科室分组名称", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/addDeptGroup")
    public AppResult addDeptGroup(@RequestHeader("token") String token, @RequestParam(required = false) Long departmentId, @RequestParam String name) {
        DeptGroup deptGroup = new DeptGroup();
        deptGroup.setName(name);
        deptGroup.setDepartmentId(departmentId);
        deptGroup.setIsDefault((byte) 2);
        return departmentApi.addDeptGroup(token, deptGroup);
    }


    @ApiOperation(value = "编辑分组", notes = "编辑分组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "科室分组id", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "name", value = "科室分组名称", paramType = "query", required = true, dataType = "String")
    })
    @PatchMapping("/deptGroups/id/{id}")
    public AppResult updateDeptGroup(@RequestHeader("token") String token, @PathVariable("id") long id, @RequestParam String name) {
        DeptGroup deptGroup = new DeptGroup();
        deptGroup.setId(id);
        deptGroup.setName(name);
        return departmentApi.updateDeptGroup(deptGroup);
    }

    @ApiOperation(value = "删除分组", notes = "删除分组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "科室分组id", paramType = "path", required = true, dataType = "long")
    })
    @DeleteMapping("/deptGroups/{id}")
    public AppResult deleteDeptGroups(@RequestHeader("token") String token, @PathVariable("id") long id) {
        return departmentApi.deleteDeptGroups(id);
    }

    @ApiOperation(value = "获取分组列表(分组管理、患者管理)", notes = "获取分组列表(分组管理、患者管理) ,当为分组管理时 isDefault=2 不显示,总数统计不包含未分组的, 当为患者管理时 isDefault=2 显示,总数统计包含未分组的 。 总数由客户端统计。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = false, dataType = "long")
    })
    @GetMapping("/deptGroups")
    public AppResult queryDeptGroups(@RequestHeader("token") String token, @RequestParam(value = "departmentId", required = false) Long departmentId) {
        return departmentApi.queryDeptGroups(token, departmentId);
    }

    @ApiOperation(value = "获取科室详情", notes = "获取科室详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "科室id", paramType = "path", required = true, dataType = "long"),
    })
    @GetMapping("/departments/id/{id}")
    public AppResult queryDepartment(@RequestHeader("token") String token, @PathVariable("id") Long id) {
        return departmentApi.queryDepartmentById(token,id);
    }
    
	@ApiOperation(value="根据科室ID集合获取科室集合信息", notes="获取科室")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "hospitals", value = "医院id集合",paramType="body", required = true, dataType = "List<Long>"),
    })
	@PostMapping("/department/")
	public com.yhyt.health.spring.AppResult findHospitalList(@RequestBody List<Long> hospitals){
		com.yhyt.health.spring.AppResult result = new com.yhyt.health.spring.AppResult();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("departments", hospitals);
		result.setBody(this.departmentMapper.findDepartmentList(params));
		return result;
	}

    @ApiOperation(value = "群发消息获取分组列表", notes = "群发消息获取分组列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", dataType = "long")
    })
    @GetMapping("/queryDeptGroupPatients")
    public AppResult queryDeptGroupPatients(@RequestParam(value = "departmentId") Long departmentId) {
        return departmentApi.queryDeptGroupPatients(departmentId);
    }

    @ApiOperation(value = "@情况下，获取聊天室对应科室下医生列表（患者端使用）", notes = "@情况下，获取聊天室对应科室下医生列表（患者端使用）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roomId", value = "聊天室id", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "查询类型 1 医生端@查询 2 患者端@查询 ", paramType = "query",defaultValue = "2",required = false, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", required = false, dataType = "String")
    })
    @GetMapping("/departments/roomMembers")
    public AppResult roomMembers(@RequestParam(required = true) String roomId,
                                 @RequestParam(required = false,defaultValue = "2") String type,
                                 @RequestParam(required = false) String userId) {
        return departmentApi.getDoctorsByRoomId(roomId,type,userId);
    }


}
