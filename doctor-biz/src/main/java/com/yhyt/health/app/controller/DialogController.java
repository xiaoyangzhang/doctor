package com.yhyt.health.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.yhyt.health.HealthConstants;
import com.yhyt.health.constant.Constant;
import com.yhyt.health.mapper.DepartmentMapper;
import com.yhyt.health.mapper.DialogMapper;
import com.yhyt.health.mapper.HospitalMapper;
import com.yhyt.health.model.Department;
import com.yhyt.health.model.Dialog;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.DoctorSetting;
import com.yhyt.health.model.Hospital;
import com.yhyt.health.service.DialogSettingService;
import com.yhyt.health.service.RedisService;
import com.yhyt.health.spring.AppResult;
import com.yhyt.health.util.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 聊天设置 聊天室相关接口
 * @author localadmin
 *
 */

@Api(value="",description ="聊天设置 聊天室相关接口")
@RestController
@RequestMapping("dialog")
@ControllerAdvice
public class DialogController {

	private static Logger logger = LoggerFactory.getLogger(DialogController.class);
	
    @Autowired
    private RedisService redisService;
    
	@Autowired
	private DialogMapper dialogMapper;
    
	@Autowired
	private HospitalMapper hospitalMapper;
	
	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Autowired
    private DialogSettingService settingService;
    
	@Resource
	private RestTemplate loadBalanced;
	
	public RestTemplate getLoadBalanced() {
		return loadBalanced;
	}

	public void setLoadBalanced(RestTemplate loadBalanced) {
		this.loadBalanced = loadBalanced;
	}

	@ApiOperation(value="聊天设置 消息免打扰", notes="聊天室设置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "登陆token", paramType="header", required = true, dataType = "String"),
			@ApiImplicitParam(name = "departmentId", value = "科室id",paramType="query", required =  true, dataType = "Long"),
			@ApiImplicitParam(name = "doctorId", value = "医生id（不传默认登录用户）",paramType="query", required =  false, dataType = "Long"),
			@ApiImplicitParam(name = "type", value = "医生端设置（不传默认 2） 患者端操作 1",paramType="query", required =  false, dataType = "Integer"),
			@ApiImplicitParam(name="askforDiagnoseOption",value="求诊信息免打扰 1-关闭 2-打开",paramType="query", required =  false, dataType = "Byte"),
			@ApiImplicitParam(name="preDiagnoseOption",value="诊前信息免打扰 1-关闭 2-打开",paramType="query", required =  false, dataType = "Byte"),
			@ApiImplicitParam(name="afterDiagnoseOption",value="诊后信息免打扰 1-关闭 2-打开",paramType="query", required =  false, dataType = "Byte"),
			@ApiImplicitParam(name="nightDiagnoseOption",value="夜间信息免打扰 1-关闭 2-打开",paramType="query", required =  false, dataType = "Byte")
	})
	@PostMapping("/setting/add")
	public AppResult dialogSetting(@RequestHeader("token")String token,DoctorSetting dialogSetting){
		AppResult result = new AppResult();
		try {
			if(dialogSetting.getDoctorId()==null){
				try {
					Doctor doctor = (Doctor) redisService.get("doctors", token);
					if(doctor==null||doctor.getId()==null){
						throw new BusinessException(HealthConstants.EXCEPTION_TOKEN,"获取用户信息错误,请重新登录");
					}
					dialogSetting.setDoctorId(doctor.getId());
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw new BusinessException(HealthConstants.EXCEPTION_TOKEN,"获取用户信息错误,请重新登录");
				}
			}
			if(dialogSetting.getType()==null){
				dialogSetting.setType(2);
			}
			if(dialogSetting.getDepartmentId()==null){
				throw new BusinessException(HealthConstants.EXCEPTION_PARAMERROR,"参数科室信息不能为空");
			}
			this.settingService.edit(dialogSetting);
			result.setCode(HealthConstants.REQUEST_SuccessCode);
		} catch (Exception e) {
			result.setCode("500");
			result.setMessage(e.getMessage());
		}
		return result;
	} 
	
	@ApiOperation(value="获取聊天设置", notes="聊天室设置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "登陆token", paramType="header", required = true, dataType = "String"),
			@ApiImplicitParam(name = "departmentId", value = "科室id",paramType="query", required =  true, dataType = "Long"),
			@ApiImplicitParam(name = "type", value = "医生端设置（不传默认 2） 患者端操作 1",paramType="query", required =  false, dataType = "Integer"),
			@ApiImplicitParam(name = "doctorId", value = "医生id（不传默认登录用户）",paramType="path", required =  true, dataType = "Long")
	})
	@GetMapping("/setting/{doctorId}")
	public AppResult findDialogSettingList(@RequestHeader("token")String token,DoctorSetting dialogSetting){
		AppResult result = new AppResult();
		try {
			if(dialogSetting.getDoctorId()==null){
				try {
					Doctor doctor = (Doctor) redisService.get("doctors", token);
					if(doctor==null||doctor.getId()==null){
						throw new BusinessException(HealthConstants.EXCEPTION_TOKEN,"获取用户信息错误,请重新登录");
					}
					dialogSetting.setDoctorId(doctor.getId());
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw new BusinessException(HealthConstants.EXCEPTION_TOKEN,"获取用户信息错误,请重新登录");
				}
			}
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("doctorId", dialogSetting.getDoctorId());
			if(null!=dialogSetting.getDepartmentId()){
				params.put("departmentId", dialogSetting.getDepartmentId());
			}
			if(dialogSetting.getType()==null){
				dialogSetting.setType(2);
			}
			params.put("type", dialogSetting.getType());
			List<DoctorSetting> dialogList = this.settingService.findPersistableList(params);
			if(null==dialogSetting.getDepartmentId()){
				result.setBody(dialogList);
			}else{
				if(dialogList==null||dialogList.size()==0){
					DoctorSetting setting = new DoctorSetting();
					setting.setDoctorId(dialogSetting.getDoctorId());
					setting.setDepartmentId(dialogSetting.getDepartmentId());
					result.setBody(setting);
				}else{
					result.setBody(dialogList.get(0));
				}
					
			}
			result.setCode(HealthConstants.REQUEST_SuccessCode);
		} catch (Exception e) {
			result.setCode("500");
			result.setMessage(e.getMessage());
		}
		return result;
	} 
	
	@ApiOperation(value="创建聊天室", notes="聊天室设置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "登陆token", paramType="header", required = false, dataType = "String"),
			@ApiImplicitParam(name = "dialogInfo", value = "{\"dialog\":{\"patientId\":\"\",\"departmentId\":\"\",\"hospitalId\":\"\",\"patientname\":\"\",\"departmentname\":\"\"},\"doctors\":[{\"userId\":\"\",\"realname\":\"\",\"password\":\"\"}],\"patientpwd\":\"\"}",paramType="body", required=true, dataType = "Json")
	})
	@PostMapping("/room/add")
	public AppResult createRoom(@RequestHeader("token")String token,@RequestBody Map<String,Object> dialogInfo){
		AppResult result = new AppResult();
		try {
			Dialog dialog = new Dialog() ;
			org.apache.commons.beanutils.BeanUtils.populate(dialog, (Map<String, ? extends Object>) dialogInfo.get("dialog"));  
			
			if(dialog.getPatientId()==null){
				throw new BusinessException(HealthConstants.EXCEPTION_PARAMERROR,"参数用户ID不能为空");
			}
			if(dialog.getDepartmentId()==null){
				throw new BusinessException(HealthConstants.EXCEPTION_PARAMERROR,"参数科室信息不能为空");
			}
			if(dialog.getHospitalId()==null){
				throw new BusinessException(HealthConstants.EXCEPTION_PARAMERROR,"参数医院信息不能为空");
			}
			List<Map> doctors = (List<Map>) dialogInfo.get("doctors");
			String patientpwd = dialogInfo.get("patientpwd")+"";
			dialog = this.settingService.addDialog(dialog,doctors,patientpwd);
			result.setCode(HealthConstants.REQUEST_SuccessCode);
			result.setBody(dialog);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(HealthConstants.EXCEPTION_OUTINTEFACE, e.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings("all")
	@ApiOperation(value="拉取聊天室列表", notes="聊天室设置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "token", value = "登陆token", paramType="header", required = true, dataType = "String"),
			@ApiImplicitParam(name = "patientId", value = "病人ID",paramType="query", required =  false, dataType = "Long"),
			@ApiImplicitParam(name = "departmentId", value = "科室id",paramType="query", required =  false, dataType = "Long")
	})
	@PostMapping("/room")
	public AppResult findRoomList(@RequestHeader("token")String token,@RequestParam(value="patientId",required=false)Long patientId,@RequestParam(value="departmentId",required=false)Long departmentId,@RequestParam(value="departments",required=false)String departments){
		AppResult result = new AppResult();
		Long begin = System.currentTimeMillis();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			if(patientId!=null){
				params.put("patientId", patientId);
			}
			if(departmentId!=null){
				params.put("departmentId", departmentId);
			}
			Map<Long,List<Map>> patientMap = new HashMap<Long,List<Map>>();
			Map<Long,List<Map>> hospitalMap = new HashMap<Long,List<Map>>();
			Map<Long,List<Map>> departmentMap = new HashMap<Long,List<Map>>();
			List<Map> resultList = new ArrayList<Map>();
			List<Dialog> list = this.dialogMapper.findPersistableList(params);
			if(list!=null&&list.size()>0){
				Set<Long> patients = new HashSet<Long>(list.size());
				HashSet<Long> hospitals = new HashSet<Long>(list.size());
				Set<Long> department = new HashSet<Long>(list.size());
				for (Dialog dialog : list) {
//					Map dialogMap = new org.apache.commons.beanutils.BeanMap(dialog);
					Map<String,Object> dialogMap = new HashMap<String,Object>();
					dialogMap.put("id",dialog.getId() );
					dialogMap.put("roomId",dialog.getRoomId());
					resultList.add(dialogMap);
					if(!patientMap.containsKey(dialog.getPatientId())){
						patientMap.put(dialog.getPatientId(),new ArrayList<Map>());
					}
					patientMap.get(dialog.getPatientId()).add(dialogMap);
					
					if(!hospitalMap.containsKey(dialog.getHospitalId())){
						hospitalMap.put(dialog.getHospitalId(),new ArrayList<Map>());
					}
					hospitalMap.get(dialog.getHospitalId()).add(dialogMap);
					
					if(!departmentMap.containsKey(dialog.getDepartmentId())){
						departmentMap.put(dialog.getDepartmentId(),new ArrayList<Map>());
					}
					departmentMap.get(dialog.getDepartmentId()).add(dialogMap);
					patients.add(dialog.getPatientId());
					hospitals.add(dialog.getHospitalId());
					department.add(dialog.getDepartmentId());
				}
				params.clear();
				params.put("hospitals", hospitals);
				List<Hospital> hospitalList = this.hospitalMapper.findHospitalList(params);
				for (Hospital hospital : hospitalList) {
					List<Map> temp = hospitalMap.get(hospital.getId());
					for (Map map : temp) {
						map.put("hospital", hospital);
					}
				}
				params.clear();
				params.put("departments", department);
				List<Department> departmentList = this.departmentMapper.findDepartmentList(params);
				for (Department depart : departmentList) {
					List<Map> temp = departmentMap.get(depart.getId());
					for (Map map : temp) {
						map.put("department", depart);
					}
				}
				try {
					HttpHeaders headers = new HttpHeaders();
					MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
					headers.setContentType(type);
					headers.add("Accept", MediaType.APPLICATION_JSON.toString());
					headers.add("token", token);
					HttpEntity httpEntity = new HttpEntity(null, headers);
					String str = "";
					for (Long temp : patients) {
						str = str + temp+",";
					}
					if(str.length()>0){
						str = str.substring(0, str.length()-1);
					}
					Long start =  System.currentTimeMillis();
					Map patientTemp = this.loadBalanced.postForObject(Constant.PATIENT_SERVICE+"patient/impatientlist?patientlist="+str,httpEntity, Map.class);
					logger.info("/dialog/room/(patient/impatientlist):"+(System.currentTimeMillis()-start)/1000+"s");
					for (Object obj : (List)((Map)patientTemp.get("body")).get("patientlist")) {
						List<Map> temp = patientMap.get(Long.valueOf(((Map)obj).get("id")+""));
						for (Map map : temp) {
							map.put("patient", obj);
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
			result.setCode(HealthConstants.REQUEST_SuccessCode);
			logger.info("/dialog/room/:"+(System.currentTimeMillis()-begin)/1000+"s");
			result.setBody(resultList);
		} catch (Exception e) {
			throw new BusinessException(HealthConstants.EXCEPTION_OUTINTEFACE, e.getMessage());
		}
		return result;
	}

}
