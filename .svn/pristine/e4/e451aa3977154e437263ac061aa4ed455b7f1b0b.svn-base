package com.yhyt.health.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.vo.PatientVisited;
import com.yhyt.health.model.vo.app.PatientAppVO;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.PatientDiagnoseService;
import com.yhyt.health.service.RedisService;
import com.yhyt.health.service.api.DoctorApi;
import com.yhyt.health.util.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(value = "", description = "工作站")
@RestController
public class WorkStationController {

    @Autowired
    private DoctorApi doctorApi;
    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PatientDiagnoseService patientDiagnoseService;
    @Resource(name="loadBalanced")
    private RestTemplate loadBalanced;
    private static Logger logger = LoggerFactory.getLogger(WorkStationController.class);

    @ApiOperation(value = "工作站求诊处理中", notes = "工作站求诊处理中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageIndex", value = "页码", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页容量", paramType = "query", required = false, dataType = "Integer")
    })
    @GetMapping("/diagnose/dealing/")
    public AppResult getDealingDiagnoseList(@RequestHeader("token") String token, @RequestParam(required = false) Integer pageIndex,@RequestParam(required = false)  Integer pageSize) {
        AppResult result = new AppResult();
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        List<PatientAppVO> patientAppVOList = doctorApi.getDealingDiagnoseList(doctor.getDepartmentId(),pageIndex,pageSize);
        result.getBody().put("list", patientAppVOList);
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    @ApiOperation(value = "工作站求诊已拒绝", notes = "工作站求诊已拒绝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageIndex", value = "页码", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "页容量", paramType = "query", required = false, dataType = "Integer")
    })
    @GetMapping("/diagnose/refused/")
    public AppResult getRefusedDiagnoseList(@RequestHeader("token") String token, @RequestParam(required = false)  Integer pageIndex,@RequestParam(required = false)  Integer pageSize) {
        AppResult result = new AppResult();
        try {
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            List<PatientAppVO> patientAppVOList = doctorApi.getRefusedDiagnoseList(doctor.getDepartmentId(),pageIndex,pageSize);
            result.getBody().put("list", patientAppVOList);
            result.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            logger.error("get refused diagnose patient fail,error:{}",e);
            result.setResultCode(ResultCode.EXCEPTION);
        }
        return result;
    }

    @ApiOperation(value = "获取诊后患者列表", notes = "获取诊后患者列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departId", value = "departId", paramType = "query", required = true, dataType = "long")
    })
    @GetMapping("/consults/getpatientsvisited/")
    public AppResult getPatientsVisited(long departId) {
        AppResult appResult = new AppResult();
        List<PatientVisited> patientVisiteds = patientDiagnoseService.getDepartPatientsVisited(departId);
        List<Map<String, Object>> datas = new ArrayList<>();
        for (PatientVisited patientVisited : patientVisiteds) {
            Map<String, Object> item = new HashedMap();
            item.put("id", patientVisited.getId());
            item.put("name", patientVisited.getName());
            item.put("avatar", patientVisited.getAvatar());
            item.put("flag", patientVisited.getFlag());
            item.put("sex", patientVisited.getSex() == 1 ? "男" : "女");
//            item.put("age", DateUtil.getYear(new Date())-DateUtil.getYear(patientVisited.getBirthDay()));
            item.put("age", patientVisited.getAge());
            item.put("msg",getMsgInfo(patientVisited));
            item.put("roomId", patientVisited.getRoomId());
            item.put("dialogId", patientVisited.getDialogId());
            datas.add(item);
        }
        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("data", datas);
        return appResult;
    }

    private Object getMsgInfo(PatientVisited patientVisited) {
        //TODO: 添加消息相关信息
        JSONObject item = new JSONObject();
//        item.put("msg_time",System.currentTimeMillis());
//        item.put("msg_count",3+"");
//        item.put("msg_latest","helloworld");
        return item;
    }

    @ApiOperation(value = "获取工作站人数", notes = "获取工作站人数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String")
            ,@ApiImplicitParam(name = "departmentId", value = "科室Id", paramType = "query", required = false, dataType = "Long")
            ,@ApiImplicitParam(name = "doctorId", value = "医生Id", paramType = "query", required = false, dataType = "Long")
    })
    @GetMapping("/getWorkStationCount")
    public AppResult getWorkStationCount(
            @RequestHeader HttpHeaders httpHeaders
            ,@RequestParam(required = false) Long departmentId
            ,@RequestParam(required = false) Long doctorId) {

        return patientDiagnoseService.getWorkStationCount(httpHeaders.getFirst("token"),departmentId,doctorId);
    }
}
