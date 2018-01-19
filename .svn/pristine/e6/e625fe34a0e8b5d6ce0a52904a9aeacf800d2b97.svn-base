package com.yhyt.health.app.controller;

import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.PatientDiagnoseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/** 
* localadmin 作者: 
* @version 创建时间：2017年8月18日 下午4:08:00 
* 类说明 
*/
@Api(value="",description="诊断相关接口")
@RestController
public class PatientDiagnoseController {
	
	@Autowired
	private PatientDiagnoseService patientDiagnoseService;

	private Logger logger = LoggerFactory.getLogger(PatientDiagnoseController.class);
	
	@ApiOperation(value="编辑诊断", notes="编辑诊断")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "token", value = "登陆token", paramType="header", required = true, dataType = "String"),
        @ApiImplicitParam(name = "mainDiagnose", value = "主诊断",paramType="query", required = true, dataType = "long"),
        @ApiImplicitParam(name = "id", value = "诊断id",paramType="path", required = true, dataType = "long")
    })
	@PatchMapping("/patientDiagnoseRecords/id/{id}")
	public AppResult patientDiagnoseRecords(
			@PathVariable long id
			,@RequestParam String mainDiagnose
			,@RequestHeader HttpHeaders httpHeaders
			){
		String token=httpHeaders.getFirst("token");
		return patientDiagnoseService.updatePatinetDiagnose(token, id, mainDiagnose);
	}
}
