package com.yhyt.health.app.controller;

import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.model.*;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.DialogService;
import com.yhyt.health.service.DoctorAddService;
import com.yhyt.health.service.RedisService;
import com.yhyt.health.service.api.DepartmentApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "", description = "新医疗新增相关接口")
public class DoctorTransferController {
    @Autowired
    private DoctorAddService doctorAddService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private DepartmentApi departmentApi;
    @Autowired
    private DialogService dialogService;

    private static Logger logger = LoggerFactory.getLogger(DoctorTransferController.class);

    @ApiOperation(value = "获取职称列表", notes = "1.获取职称列表")
    @PostMapping("/doctortitle")
    public AppResult addDialogAppointments() {
        Dictionary dn = new Dictionary();
        return doctorAddService.getAllTitle();
    }

    @ApiOperation(value = "诊后扫码签到列表", notes = "3.诊后扫码签到列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = false, dataType = "String")
            ,@ApiImplicitParam(name = "pageIndex", value = "页码", paramType = "query", required = false, dataType = "Integer")
            ,@ApiImplicitParam(name = "pageSize", value = "页容量", paramType = "query", required = false, dataType = "Integer")
    })
    @GetMapping("/dialogsignlist")
    public AppResult dialogsignlist(
            @RequestHeader("token") String token
            ,@RequestParam(required = false) String departmentId
            ,@RequestParam(required = false) Integer pageIndex
            ,@RequestParam(required = false)  Integer pageSize) {
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        return doctorAddService.dialogsignlist(doctor.getDepartmentId().toString(),pageIndex,pageSize);
    }

    @ApiOperation(value = "签到详情", notes = "签到详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "patientId", value = "患者id", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/dialogsigndetail")
    public AppResult dialogsigndetail(@RequestHeader("token") String token
            , @RequestParam(required = false) String departmentId
            , @RequestParam String patientId) {

        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        return doctorAddService.dialogsigndetail(doctor.getDepartmentId().toString(), patientId);
    }

    @ApiOperation(value = "过期预约", notes = "5.过期预约")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "doctorid", value = "医生id", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/appointmentexpire")
    public AppResult appointmentexpire(@RequestParam String doctorid, @RequestParam String departmentId) {
        return doctorAddService.appointmentexpire(doctorid, departmentId);
    }

    @ApiOperation(value = "确认分诊", notes = "7.确认分诊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "appointmentId", value = "预约id", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "doctorIdLaunch", value = "医生id", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "treatRoom", value = "诊室", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "treatNum", value = "就诊号", paramType = "query", required = false, dataType = "String")
    })
    @PostMapping("/triageconfirm")
    public AppResult triageconfirm(
            @RequestHeader HttpHeaders httpHeaders
            , @RequestParam Long appointmentId
            , @RequestParam Long doctorIdAppointment
            , @RequestParam(required = false) String treatRoom
            , @RequestParam(required = false) String treatNum) {
        String token = httpHeaders.getFirst("token");
        DialogAppointment dialogAppointment = new DialogAppointment();
        dialogAppointment.setId(appointmentId);
        dialogAppointment.setDoctorIdAppointment(doctorIdAppointment);
        dialogAppointment.setTreatRoom(treatRoom);
        dialogAppointment.setTreatNum(treatNum);

        return doctorAddService.triageconfirm(token, dialogAppointment);
    }

    @ApiOperation(value = "撤销分诊", notes = "撤销分诊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "appointmentId", value = "预约id", paramType = "query", required = true, dataType = "Long")
    })
    @PatchMapping("/dismissAssignDoctor")
    public AppResult dismissAssignDoctor(@RequestHeader("token") String token, @RequestParam Long appointmentId) {
        return dialogService.dismissAssignDoctor(token, appointmentId);
    }


    @ApiOperation(value = "全部待诊医师列表", notes = "8.全部待诊医师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageIndex", value = "页码", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页容量", paramType = "query", required = false, dataType = "Integer")
    })
    @GetMapping("/appointmentdoctorlist")
    public AppResult appointmentdoctorlist(@RequestHeader("token") String token
            , @RequestParam(required = false) String departmentId
            , @RequestParam(required = false) Integer pageIndex
            , @RequestParam(required = false)  Integer pageSize) {
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        return doctorAddService.appointmentdoctorlist(token,doctor.getDepartmentId().toString(),pageIndex,pageSize);
    }
   /* @ApiOperation(value = "按日期获取未分诊患者", notes = "10.按日期获取未分诊患者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchdate", value = "日期", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/searchdepartmentpatient")
    public AppResult searchdepartmentpatient(@RequestParam String searchdate, @RequestParam String departmentId) {
        return doctorAddService.searchdepartmentpatient(searchdate, departmentId);
    }*/

    @ApiOperation(value = "转诊患者列表", notes = "11.转诊患者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageIndex", value = "页码", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页容量", paramType = "query", required = false, dataType = "Integer")
    })
    @GetMapping("/transferpatient")
    public AppResult transferpatient(@RequestHeader("token") String token
            , @RequestParam(required = false) Integer pageIndex
            , @RequestParam(required = false)  Integer pageSize) {
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        return doctorAddService.transferpatient(doctor.getDepartmentId().toString(),pageIndex,pageSize);
    }

    /*@ApiOperation(value = "转诊患者接诊/拒绝", notes = "转诊患者接诊/拒绝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "patientId", value = "患者id", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "state", value = "转诊状态", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "appointmentId", value = "转诊id", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/receive/transferpatient")
    public AppResult receiveTransferpatient(@RequestHeader("token") String token, @RequestParam String patientId, @RequestParam String state, @RequestParam String appointmentId) {
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        return departmentApi.receiveTransferPatient(Long.parseLong(appointmentId), Long.parseLong(patientId), Byte.parseByte(state));
    }*/

    @ApiOperation(value = "转诊患者接诊/拒绝", notes = "转诊患者接诊/拒绝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "state", value = "转诊状态:4接诊 5拒绝", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "appointmentId", value = "转诊id", paramType = "query", required = true, dataType = "String")
    })
    @PatchMapping("/check/transferpatient")
    public AppResult receiveTransferpatient(@RequestHeader("token") String token,
                                            @RequestParam String state, @RequestParam String appointmentId) {

        return departmentApi.receiveTransferPatient(token,Long.parseLong(appointmentId), Byte.parseByte(state));
    }

    @ApiOperation(value = "确认接诊/拒绝", notes = "确认接诊/拒绝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "state", value = "转诊状态:4接诊5拒绝", paramType = "query", required = true, dataType = "String"),

            @ApiImplicitParam(name = "appointmentId", value = "转诊id", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/confirm/transferpatient")
    public AppResult checkTransferpatient(@RequestHeader("token") String token, @RequestParam String appointmentId, @RequestParam String state) {
        return departmentApi.confirmTransferPatient(token,Long.parseLong(appointmentId), Byte.parseByte(state));
    }


    @ApiOperation(value = "求诊详情", notes = "6.求诊详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientId", value = "科室id", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "caseId", value = "科室id", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/casedetail")
    public AppResult casedetail(@RequestParam String patientId, @RequestParam String caseId) {
        return doctorAddService.casedetail(patientId, caseId);
    }

	 /*@ApiOperation(value="转诊患者接诊/拒绝", notes="转诊患者接诊/拒绝")
	  @ApiImplicitParams({
			  @ApiImplicitParam(name = "token", value = "登陆token", paramType="header", required = true, dataType = "String"),
			  @ApiImplicitParam(name = "patientId", value = "患者id", paramType="query", required =  true, dataType = "String"),
			  @ApiImplicitParam(name = "state", value = "转诊状态2接诊3拒绝", paramType="query", required =  true, dataType = "String"),
			  @ApiImplicitParam(name = "appointmentId", value = "转诊id", paramType="query", required =  true, dataType = "String")
			    	  })
	 @GetMapping("/receive/transferpatient")
	public AppResult receiveTransferpatient(@RequestHeader("token")String token,@RequestParam String patientId,
											@RequestParam String state,@RequestParam String appointmentId) {

		  Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
		 return departmentApi.receiveTransferPatient(Long.parseLong(appointmentId),Long.parseLong(patientId),Byte.parseByte(state));
	 }*/

	 
	 /*@ApiOperation(value="求诊详情", notes="6.求诊详情")
	  @ApiImplicitParams({
		    @ApiImplicitParam(name = "patientId", value = "科室id", paramType="query", required = true, dataType = "String"),
		    @ApiImplicitParam(name = "caseId", value = "科室id", paramType="query", required = true, dataType = "String")
			    	  })
	@PostMapping("/casedetail")
	public AppResult casedetail(@RequestParam String patientId,@RequestParam String caseId) {
		 
		 
	return doctorAddService.casedetail(patientId,caseId);
	 }
	
    @ApiOperation(value = "求诊详情", notes = "6.求诊详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientId", value = "科室id", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "caseId", value = "科室id", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/casedetail")
    public AppResult casedetail(@RequestParam String patientId, @RequestParam String caseId) {
        return doctorAddService.casedetail(patientId, caseId);
    }*/
}
