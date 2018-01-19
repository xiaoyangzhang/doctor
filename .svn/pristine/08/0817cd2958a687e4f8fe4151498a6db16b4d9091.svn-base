package com.yhyt.health.app.controller;

import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.PatientDiagnoseRecords;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.RedisService;
import com.yhyt.health.service.api.DoctorApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "",description = "签到")
@RestController
public class SignController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private DoctorApi doctorApi;

    private Logger logger = LoggerFactory.getLogger(SignController.class);

    @ApiOperation(value="编辑当前诊断", notes="编辑当前诊断")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "doctorId", value = "医生id",paramType="query", required =  true, dataType = "long"),
            @ApiImplicitParam(name = "patientId", value = "患者id",paramType="query", required =  true, dataType = "long"),
            @ApiImplicitParam(name = "mainDiagnose", value = "主诊断",paramType="query", required =  true, dataType = "String"),
            @ApiImplicitParam(name = "subDiagnose", value = "子诊断",paramType="query", required =  false, dataType = "String")
    })
    @PostMapping("/diagnose/record/add")
    public AppResult doctorStrogpoint(@RequestHeader("token")String token
            , @RequestParam Long doctorId
            , @RequestParam Long patientId
            , @RequestParam String mainDiagnose
            , @RequestParam(required = false) String subDiagnose
    ) {
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        PatientDiagnoseRecords patientDiagnoseRecords = new PatientDiagnoseRecords();
        patientDiagnoseRecords.setMainDiagnose(mainDiagnose);
        patientDiagnoseRecords.setDoctorId(doctorId);
        patientDiagnoseRecords.setSubDiagnose(subDiagnose);
        return doctorApi.addDiagnoseRecords(patientDiagnoseRecords,doctor.getDepartmentId(),patientId);
    }
}
