package com.yhyt.health.app.controller;

import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.DeptPatientSignMapper;
import com.yhyt.health.mapper.DialogMapper;
import com.yhyt.health.mapper.DoctorCareMapper;
import com.yhyt.health.mapper.PatientDiagnosePicsMapper;
import com.yhyt.health.model.*;
import com.yhyt.health.model.vo.app.PatientAppVO;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.DeptPatientSignService;
import com.yhyt.health.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by shensh on 2017/9/6.
 */
@Api(value = "", description = "科室诊后签到审核")
@RestController
public class PatientDeptSignController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private DeptPatientSignMapper deptPatientSignMapper;
    @Autowired
    private PatientDiagnosePicsMapper patientDiagnosePicsMapper;
    @Autowired
    private DeptPatientSignService deptPatientSignService;
    @Autowired
    private DialogMapper dialogMapper;


    private Logger logger = LoggerFactory.getLogger(PatientDeptSignController.class);

    @ApiOperation(value = "同意科室签到检查", notes = "同意科室签到检查")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "科室补签id", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/agreecheck")
    public AppResult agreeCheck(@RequestHeader("token") String token, String id) {
        //Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        return deptPatientSignService.agreeCheck(Long.valueOf(id));
    }

    @ApiOperation(value = "同意科室签到", notes = "同意科室签到")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "科室补签id", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/agree")
    public AppResult agree(@RequestHeader("token") String token, String id) {

        AppResult appResult = deptPatientSignService.agree(token, Long.valueOf(id));
        return appResult;
    }

    @ApiOperation(value = "拒绝科室签到", notes = "拒绝科室签到")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "科室补签id", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "refuseReason", value = "拒绝原因", paramType = "query", required = true, dataType = "String"),
    })
    @PostMapping("/refuse")
    public AppResult refuse(@RequestHeader("token") String token
            , String id
            , String refuseReason) {
        int replenishSignId = Integer.parseInt(id);
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        return deptPatientSignService.refuse(Long.valueOf(replenishSignId), doctor.getRealname(),refuseReason);
        // TODO: 2017/9/6  发送拒绝的系统消息
    }

    @ApiOperation(value = "获取科室签到详情", notes = "获取科室签到详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "deptSignId", value = "科室补签id", paramType = "query", required = true, dataType = "String")

    })
    @GetMapping("/getPatientSignDetail")
    public AppResult getPatientSignDetail(String deptSignId, @RequestHeader("token") String token) throws Exception {
        AppResult appResult = new AppResult();
        //Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        PatientAppVO patientAppVO = deptPatientSignMapper.getSignDetailBydeptSignId(Long.valueOf(deptSignId));
        if (patientAppVO != null) {
            Dialog dialog = dialogMapper.queryBydeptIdAndpatientId(patientAppVO.getDepartmentId(),patientAppVO.getId());
            if (null != dialog) {
                List<PatientDiagnosePics> patientDiagnosePicss = patientDiagnosePicsMapper.selectByDialogId(dialog.getId());
                if (null != patientDiagnosePicss && patientDiagnosePicss.size() > 0) {

                    for (PatientDiagnosePics patientDiagnosePics : patientDiagnosePicss) {
                        if (6L != patientDiagnosePics.getType().longValue()) {
                            continue;
                        }
                        if (patientDiagnosePics.getPicUrl().indexOf(",") > 0) {
                            String[] urls = patientDiagnosePics.getPicUrl().split(",");
                            for (String str : urls) {
                                PatientDiagnosePics p = new PatientDiagnosePics();
                                p.setPicUrl(str);
                                patientAppVO.getDiseaseInfo().add(p);
                            }
                        }else{
                            patientAppVO.getDiseaseInfo().add(patientDiagnosePics);
                        }
                    }
                }
            }
        }
        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("data", patientAppVO);
        return appResult;
    }
}
