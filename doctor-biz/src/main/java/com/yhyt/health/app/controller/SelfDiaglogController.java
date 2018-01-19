package com.yhyt.health.app.controller;

import com.alibaba.fastjson.JSON;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.SelfDiagnosisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "", description = "自诊")
@RestController
public class SelfDiaglogController {

    @Autowired
    private SelfDiagnosisService diagnosisService;

    private static Logger logger = LoggerFactory.getLogger(SelfDiaglogController.class);

    @ApiOperation(value = "自诊确诊", notes = "自诊确诊")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登陆token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "patientId", value = "患者Id", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "caseId", value = "病例id(请一直保持)", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "diseases", value = "疾病详情[{\"diseaseId\": \"JB030006\", \"isMaster\": \"1\", \"types\": [{\"id\": \"232323\", \"typeId\": \"item11111\"},{\"id\": \"232323\", \"typeId\": \"item11111\"},{\"id\": \"232323\", \"typeId\": \"item11111\"}]},{\"diseaseId\": \"JB030006\", \"isMaster\": \"0\"}]", paramType = "query", required = true, dataType = "String"),
    })
    @PostMapping("/confirm/{caseId}")
    public AppResult confirm(@RequestHeader(name = "token") String token, Long patientId, @PathVariable("caseId") String caseId, String diseases) {
        List diseaseList = JSON.parseObject(diseases, List.class);
        return diagnosisService.confirm(token, patientId, caseId, diseaseList);
    }
}
