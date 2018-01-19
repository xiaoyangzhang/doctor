package com.yhyt.health.app.controller;
/** 
* localadmin 作者: 
* @version 创建时间：2017年8月18日 上午9:36:46 
* 类说明 
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yhyt.health.model.Hospital;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.api.HospitalApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yhyt.health.mapper.HospitalMapper;


@Api(value="",description="医院相关操作")
@RestController
public class HospitalAppController {
	
	@Autowired
	private HospitalApi hospitalApi;

	private static Logger logger = LoggerFactory.getLogger(HospitalAppController.class);
	
	@Autowired
	private HospitalMapper hospitalMapper;
	
	@ApiOperation(value="获取医院/科室", notes="获取医院/科室")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "token", value = "登陆token", paramType="header", required = true, dataType = "String"),
        @ApiImplicitParam(name = "hospitalId", value = "医院id",paramType="query", required = false, dataType = "long"),
        @ApiImplicitParam(name = "hospitalName", value = "医院名称",paramType="query", required = false, dataType = "String"),
        @ApiImplicitParam(name = "departmentId", value = "科室id",paramType="query", required = false, dataType = "long"),
        @ApiImplicitParam(name = "departmentName", value = "科室名称",paramType="query", required = false, dataType = "String"),
        @ApiImplicitParam(name = "type", value = "1查询医院2查询科室",paramType="query", required = true, dataType = "String")
    })
	@GetMapping("/hospitals")
	public AppResult queryHospitals(
			 @RequestHeader HttpHeaders httpHeaders
    		,@RequestParam(required=false) Long hospitalId
    		,@RequestParam(required=false) String hospitalName
    		,@RequestParam(required=false) String departmentName
    		,@RequestParam(required=false) Long departmentId
    		,@RequestParam String type
			) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		if("1".equalsIgnoreCase(type)) {
			map.put("id", hospitalId);
			map.put("name", hospitalName);
		}else if("2".equalsIgnoreCase(type)) {
			map.put("id", hospitalId);
			map.put("name", departmentName);
		}
		return hospitalApi.queryHospitalDepartment(map);
	}
	@ApiOperation(value="根据医院ID集合获取医院信息", notes="获取医院/科室")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "hospitals", value = "医院id集合",paramType="body", required = true, dataType = "List<Long>"),
    })
	@PostMapping("/hospital/")
	public com.yhyt.health.spring.AppResult findHospitalList(@RequestBody List<Long> hospitals){
		com.yhyt.health.spring.AppResult result = new com.yhyt.health.spring.AppResult();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("hospitals", hospitals);
		List<Hospital> hospitalList = new ArrayList<>();
		for (Long hospitalId:hospitals){
			hospitalList.add(hospitalMapper.selectByPrimaryKey(hospitalId));
		}
		result.setBody(hospitalList);
		return result;
	}
}
