package com.yhyt.health.app.controller;

import com.yhyt.health.model.*;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.DoctorService;
import com.yhyt.health.service.api.DoctorApi;
import com.yhyt.health.util.DateUtil;
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
import java.util.Date;
import java.util.List;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月15日 下午3:34:22
 * 类说明
 */
@Api(value = "", description = "医生个人信息，审核信息")
@RestController
public class DoctorAppController {

    @Autowired
    private DoctorApi doctorApi;
    @Autowired
    private DoctorService doctorService;

    private static Logger logger = LoggerFactory.getLogger(DoctorAppController.class);

    @ApiOperation(value = "医生擅长", notes = "医生擅长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "doctorId", value = "医生id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "strongpoint", value = "医生擅长", paramType = "query", required = false, dataType = "long"),
            @ApiImplicitParam(name = "summary", value = "医生简介", paramType = "query", required = false, dataType = "long"),
            @ApiImplicitParam(name = "birthday", value = "医生出生日期", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "headImage", value = "头像", paramType = "query", required = false, dataType = "String")
    })
    @PutMapping("/doctorStrongpoint")
    public AppResult doctorStrogpoint(@RequestHeader("token") String token
            , @RequestParam Long doctorId
            , @RequestParam(required = false) String strongpoint
            , @RequestParam(required = false) String summary
            , @RequestParam(required = false) Long birthday
            , @RequestParam(required = false) String headImage
    ) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setSummary(summary);
        doctor.setStrongpoint(strongpoint);
        if (null != birthday)
            doctor.setBirthday(DateUtil.timestampToDate(birthday));
        doctor.setHeadImage(headImage);
        return doctorApi.udpateDoctorStrogpoint(token, doctor);
    }

    @ApiOperation(value = "获取医生个人信息审核中信息", notes = "获取医生个人信息审核中信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "医生id", paramType = "path", required = true, dataType = "long")
    })
    @GetMapping("/doctorReviews/id/{id}")
    public AppResult queryDoctorReview(@RequestHeader("token") String token, @PathVariable Long id) {
        return doctorApi.getDoctorReviewInfo(id);
    }

    @ApiOperation(value = "提交医生个人信息审核", notes = "提交医生个人信息审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "realname", value = "姓名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "doctorId", value = "医生id", paramType = "query", required = false, dataType = "long"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query", required = true, dataType = "int"),
//            @ApiImplicitParam(name = "mobileNumber", value = "手机号",paramType="query", required =  true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "title", value = "职称", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "practiceCertificate", value = "执业证", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "qualificationCertificate", value = "从业资格证", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/doctorReviews")
    public AppResult addDoctorReview(@RequestHeader("token") String token
            , @RequestParam(required = false) Long doctorId
            , @RequestParam String realname
            , @RequestParam int sex
//    		,@RequestParam String mobileNumber
            , @RequestParam Long departmentId
            , @RequestParam String title
            , @RequestParam String practiceCertificate
            , @RequestParam String qualificationCertificate) {
        DoctorReview doctorReview = new DoctorReview();
        doctorReview.setDepartmentId(departmentId);
        doctorReview.setRealname(realname);
        doctorReview.setSex((byte) sex);
        doctorReview.setDoctorId(doctorId);
        doctorReview.setTitle(title);
        doctorReview.setQualificationCertificate(qualificationCertificate);
        doctorReview.setPracticeCertificate(practiceCertificate);
//        doctorReview.setMobileNumber(mobileNumber);
        return doctorApi.addDoctorReview(token, doctorReview);
    }

    @ApiOperation(value = "设置新手机号", notes = "设置新手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "doctorId", value = "医生id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "verificationCode", value = "手机验证码", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "safeCode", value = "安全验证码旧手机验证返回", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/verifyNewPhone")
    public AppResult updateDoctorPhone(
            @RequestHeader("token") String token
            , @RequestParam long doctorId
            , @RequestParam String username
            , @RequestParam String verificationCode
            , @RequestParam String safeCode
    ) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setUsername(username);
        doctor.setMobileNumber(username);
        return doctorApi.updateDoctor(token, doctor, safeCode, verificationCode);
    }

    @ApiOperation(value = "修改手机号", notes = "修改手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "verificationCode", value = "手机验证码", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/verifyOldPhone")
    public AppResult updateDoctorPhone(@RequestHeader("token") String token, @RequestParam String username, @RequestParam String verificationCode) {
        //直接调用实现类方法
        return doctorApi.verifyOldPhone(token, username, verificationCode);
    }

    @ApiOperation(value = "获取医生详细信息", notes = "获取医生详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "医生id", paramType = "path", required = true, dataType = "long")
    })
    @GetMapping("/doctors/id/{id}")
    public AppResult queryDoctor(@RequestHeader("token") String token, @PathVariable("id") Long id) {
        return doctorApi.queryDoctorById(id);
    }

    @ApiOperation(value = "根据手机号获取医生详细信息", notes = "根据手机号获取医生详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "医生账号", paramType = "path", required = true, dataType = "String")
    })
    @GetMapping("/doctors/username/{username}")
    public AppResult queryDoctorByUserName(@RequestHeader("token") String token, @PathVariable("username") String username) {
        Doctor doctor = new Doctor();
        doctor.setUsername(username);
        return doctorApi.queryDoctorByUserName(token, doctor);
    }

    /*@ApiOperation(value="获取求诊列表", notes="获取求诊列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id",paramType="query", required =  true, dataType = "long"),
            @ApiImplicitParam(name = "state", value = "状态（已拒绝）",paramType="query", required =  true, dataType = "int")
    })
    @GetMapping("/patientDiagnoses")*/
    public AppResult queryDiagnoseList(@RequestHeader("token") String token, @RequestParam Long departmentId, int state) {
//        return doctorApi.queryDoctorById(id);
        return null;
    }

    @ApiOperation(value = "获取今日待诊", notes = "获取今日待诊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "time", value = "今日", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = true, dataType = "long"),

    })
//    @GetMapping("/dialogAppointments")
    public AppResult queryUndiagnoseListToday(@RequestHeader("token") String token, @RequestParam Long departmentId, @RequestParam String time) {
//        return doctorApi.queryDoctorById(id);
        return null;
    }

    @ApiOperation(value = "撤销预约", notes = "撤销预约")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "预约id", paramType = "path", required = true, dataType = "long"),

    })
    @DeleteMapping("/dialogAppointments/id/{id}")
    public AppResult dismissAppointment(@RequestHeader("token") String token, @RequestParam Long departmentId, @RequestParam String time) {
//        return doctorApi.queryDoctorById(id);
        return null;
    }

    //	@ApiOperation(value="发起预约", notes="发起预约")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dialogId", value = "对话id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "doctorLaunchId", value = "发起预约的医生id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "doctorAppointmentId", value = "预约的医生id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "appointmentTime", value = "预约时间", paramType = "path", required = true, dataType = "Date")

    })
    //  @PostMapping("/appointments")
    public AppResult createAppointment(@RequestHeader("token") String token, @RequestParam Long dialogId, @RequestParam Long doctorLaunchId,
                                       @RequestParam Long doctorAppointmentId, @RequestParam Date appointmentTime) {
        DialogAppointment dialogAppointment = new DialogAppointment();
        dialogAppointment.setAppointmentTime(appointmentTime);
        dialogAppointment.setDialogDetailId(dialogId);
        dialogAppointment.setDoctorIdAppointment(doctorAppointmentId);
        dialogAppointment.setDoctorIdLaunch(doctorLaunchId);
        return doctorApi.addAppointment(dialogAppointment);
    }

    @ApiOperation(value = "加入科室", notes = "加入科室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "departmentId", value = "科室id", paramType = "query", required = true, dataType = "long"),
            @ApiImplicitParam(name = "doctorId", value = "医生id", paramType = "query", required = true, dataType = "long")
    })
    @PostMapping("/deptDoctors")
    public AppResult joinDepartment(
            @RequestHeader HttpHeaders httpHeaders
            , @RequestParam long departmentId
            , @RequestParam long doctorId
    ) {
        String token = httpHeaders.getFirst("token");
        DeptDoctor deptDoctor = new DeptDoctor();
        deptDoctor.setDepartmentId(departmentId);
        deptDoctor.setDoctorId(doctorId);
        deptDoctor.setIsAdmin((byte) 1);
        return doctorService.joinDepartment(token, deptDoctor);
    }

    @ApiOperation(value = "退出科室／管理员删除", notes = "退出科室／管理员删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "科室id", paramType = "path", required = true, dataType = "long"),
            @ApiImplicitParam(name = "doctorId", value = "医生id", paramType = "query", required = true, dataType = "String")
    })
    @DeleteMapping("deptDoctors/id/{id}")
    public AppResult quitDepartment(
            @RequestHeader HttpHeaders httpHeaders
            , @PathVariable long id
            , @RequestParam String doctorId
    ) {
        String token = httpHeaders.getFirst("token");
        List<Long> doctorIds = new ArrayList<Long>();
        //组装批量删除医生id
        if (null != doctorId && !"".equals(doctorId)) {
            String[] stateStr = doctorId.split(",");
            for (int i = 0; i < stateStr.length; i++) {
                doctorIds.add(Long.valueOf(stateStr[i]));
            }
        }
        DeptDoctor deptDoctor = new DeptDoctor();
        deptDoctor.setDepartmentId(id);
        deptDoctor.setIsAdmin((byte) 1);
        return doctorService.quitDepartment(token, deptDoctor, doctorIds);
    }

    @ApiOperation(value = "医生关注/取消关注患者", notes = "医生关注/取消关注患者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "doctorId", value = "医生id", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "departmentId", value = "医生id", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "patientId", value = "患者id", paramType = "query", required = true, dataType = "Long")
    })
    @PatchMapping("doctorCare")
    public AppResult doctorCarePatient(
            @RequestHeader HttpHeaders httpHeaders
            ,@RequestParam(required = false) Long doctorId
            ,@RequestParam(required = false) Long departmentId
            ,@RequestParam Long patientId
    ) {
        String token = httpHeaders.getFirst("token");

        DoctorCare doctorCare = new DoctorCare();
        doctorCare.setDoctorId(doctorId);
        doctorCare.setDepartmentId(departmentId);
        doctorCare.setPatientId(patientId);
        return doctorApi.doctorCarePatient(token, doctorCare);
    }





}
