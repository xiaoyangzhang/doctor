package com.yhyt.health.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.yhyt.health.HealthConstants;
import com.yhyt.health.dao.BaseDao;
import com.yhyt.health.mapper.DialogMapper;
import com.yhyt.health.mapper.DoctorSettingMapper;
import com.yhyt.health.model.Dialog;
import com.yhyt.health.model.DoctorSetting;
import com.yhyt.health.service.DialogSettingService;
import com.yhyt.health.util.BusinessException;

@Service
public class DialogSettingServiceImpl extends BaseServiceImpl<DoctorSetting> implements DialogSettingService{

	static Logger logger = LoggerFactory.getLogger(DialogSettingServiceImpl.class);

	@Value("${im.server.roomUrl}")
	private String im_server_roomUrl = "";

	@Value("${im.server.userAdd}")
	private String im_server_userAdd = "";
	
	@Value("${im.server.prefix}")
	private String prefix = "muc.114.113.117.246";
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getIm_server_userAdd() {
		return im_server_userAdd;
	}

	public void setIm_server_userAdd(String im_server_userAdd) {
		this.im_server_userAdd = im_server_userAdd;
	}

	public String getIm_server_roomUrl() {
		return im_server_roomUrl;
	}

	public void setIm_server_roomUrl(String im_server_roomUrl) {
		this.im_server_roomUrl = im_server_roomUrl;
	}

	@Autowired
	private DoctorSettingMapper settingMapper;
	
	@Autowired
	private DialogMapper dialogMapper;
	
	@Override
	public BaseDao getBaseDao() {
		return this.settingMapper;
	}
	
	public DoctorSetting edit(DoctorSetting setting){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("doctorId", setting.getDoctorId());
		params.put("departmentId", setting.getDepartmentId());
		List<DoctorSetting> result = this.settingMapper.findPersistableList(params);
		if(result!=null&&result.size()>0){
			setting.setId(result.get(0).getId());
			setting.setUpdateTime(new Date());
			this.settingMapper.updateByPrimaryKeySelective(setting);
		}else{
			setting.setCreateTime(new Date());
			setting.setUpdateTime(new Date());
			this.settingMapper.insert(setting);
		}
		return setting;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public Dialog addDialog(Dialog dialog) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("patientId", dialog.getPatientId());
		params.put("departmentId", dialog.getDepartmentId());
		List<Dialog> dialogs = this.dialogMapper.findPersistableList(params);
		Dialog result = null;
		if(dialogs!=null&&dialogs.size()>0){
			result = dialogs.get(0);
			if(StringUtils.isBlank(result.getDepartmentname())){
				result.setDepartmentname(dialog.getDepartmentname());
			}
			if(StringUtils.isBlank(result.getPatientname())){
				result.setPatientname(dialog.getPatientname());
			}
		}else{
			result = dialog;
		}
		if(StringUtils.isNotBlank(result.getRoomId())){
			return result;
		}else{
			//TODO 创建聊天室
			try {
				params.clear();
				params.put("myNickName", result.getPatientname());
				params.put("roomName", result.getDepartmentname());
				String response = this.get(this.im_server_roomUrl, params, String.class);
				if(response.contains("resultCode")){
					Map<String,Object> temp = JSONObject.parseObject(response);
					throw new BusinessException(HealthConstants.EXCEPTION_OUTINTEFACE, "创建聊天室失败:"+temp.get("resultMsg"));
				}
				response = response.replaceAll("\"", "");
				result.setRoomId(response);
			} catch (Exception e) {
				throw new BusinessException(HealthConstants.EXCEPTION_OUTINTEFACE, e.getMessage());
			}
		}
		if(result.getId()!=null){
			int i = this.dialogMapper.updateRoom(result);
			if(i==0){
				throw new BusinessException(HealthConstants.EXCEPTION_OUTINTEFACE,"操作过快,请稍后重试");
			}
		}else{
			result.setCreateTime(new Date());
			this.dialogMapper.insertSelective(result);
		}
		return result;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED)
	@Override
	public Dialog addDialog(Dialog dialog, List<Map> doctors, String patientpwd) {
		dialog = this.addDialog(dialog);
		String roomId = dialog.getRoomId()+"@"+this.prefix;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", dialog.getPatientId());
		params.put("passWord",patientpwd);
		params.put("roomJid",roomId);
		params.put("NickName", dialog.getPatientname());
		logger.info("创建房间接口患者入参："+JSON.toJSONString(params));
		String code = this.get(im_server_userAdd, params, String.class);
		logger.info("创建房间接口患者出参："+code);
		code = code.replaceAll("\"", "");
		if(!"1001200".equals(code)){
			throw new BusinessException(HealthConstants.EXCEPTION_OUTINTEFACE, "创建聊天室失败["+params.get("userId")+"]");
		}
		for (Map map : doctors) {
			params.put("userId",map.get("id"));
			params.put("passWord",map.get("imPassword"));
			params.put("NickName", map.get("realname"));
			logger.info("创建房间接口医生入参："+JSON.toJSONString(params));
			code = this.get(im_server_userAdd, params, String.class);
			logger.info("创建房间接口医生出参："+code);
			code = code.replaceAll("\"", "");
			if(!"1001200".equals(code)){
				throw new BusinessException(HealthConstants.EXCEPTION_OUTINTEFACE, "创建聊天室失败["+params.get("userId")+"]");
			}
		}
		return dialog;
	}
}
