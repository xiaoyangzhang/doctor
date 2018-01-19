package com.yhyt.health.app.controller;

import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.DoctorMapper;
import com.yhyt.health.mapper.PatientMapper;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.Patientl;
import com.yhyt.health.model.SysFeedback;
import com.yhyt.health.result.AppResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * 意见反馈
 *
 * @author gsh
 * @create 2017-09-06 20:17
 **/
@Api(value = "", description = "意见反馈相关接口")
@RestController
public class FeedbackAppController {
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    @Qualifier("loadBalanced")
    private RestTemplate restTemplate;

    private static Logger logger = LoggerFactory.getLogger(FeedbackAppController.class);

    @ApiOperation(value = "意见反馈", notes = "意见反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "query", required = false, dataType = "Long"),
            @ApiImplicitParam(name = "content", value = "反馈内容", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userType", value = "反馈用户类型1:患者2:医生", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/sysFeedbacks")
    public AppResult updateDoctorPhone(@RequestHeader HttpHeaders httpHeaders
            , @RequestParam Long userId
            , @RequestParam String content
            , @RequestParam String userType) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        String token = httpHeaders.getFirst("token");
        SysFeedback feeback = new SysFeedback();
        if ("2".equals(userType)) {
            Doctor doctor = doctorMapper.selectByPrimaryKey(userId);
            if (doctor == null) {
                appResult.setResultCode(ResultCode.SUCCESS);
                return appResult;
            }
            feeback.setRealname(doctor.getRealname());
            feeback.setUsername(doctor.getUsername());
        } else {
            Patientl patientl = patientMapper.selectPatientlById(userId);
            if (null == patientl) {
                appResult.setResultCode(ResultCode.SUCCESS);
                return appResult;
            }
            feeback.setRealname(patientl.getRealname());
            feeback.setUsername(patientl.getUsername());
        }
        feeback.setDeviceInfo(httpHeaders.getFirst("deviceName"));
        feeback.setAppVersion(httpHeaders.getFirst("appVersion"));
        feeback.setDealState((byte) 1);
        feeback.setUserId(userId);
        feeback.setUseType(Byte.valueOf(userType));
        feeback.setContent(content);
        feeback.setCreateTime(new Date());
        restTemplate.postForObject("http://system/sysfeedback/add", feeback, Integer.class);
        return appResult;
    }
}
