package com.yhyt.health.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.mapper.DepartmentMapper;
import com.yhyt.health.mapper.DialogMapper;
import com.yhyt.health.mapper.DoctorMapper;
import com.yhyt.health.mapper.PatientMapper;
import com.yhyt.health.model.Dialog;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.Patientl;
import com.yhyt.health.model.vo.DepartmentHospitalVO;
import com.yhyt.health.result.AppResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author gsh
 * @create 2018-01-05 10:06
 **/
@Component
public class DialogRoomUtil {

    static Logger logger = LoggerFactory.getLogger(DialogRoomUtil.class);

    private static DialogRoomUtil dialogRoomUtil;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DialogMapper dialogMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorConstant doctorConstant;

    @Resource(name = "loadBalanced")
    private RestTemplate loadBalanced;

    @PostConstruct
    public void init() {
        dialogRoomUtil = this;
        if (dialogRoomUtil.departmentMapper == null) {
            dialogRoomUtil.departmentMapper = this.departmentMapper;
        }
        if (dialogRoomUtil.dialogMapper == null) {
            dialogRoomUtil.dialogMapper = this.dialogMapper;
        }
        if (dialogRoomUtil.patientMapper == null) {
            dialogRoomUtil.patientMapper = this.patientMapper;
        }
        if (dialogRoomUtil.doctorMapper == null) {
            dialogRoomUtil.doctorMapper = this.doctorMapper;
        }
        if (dialogRoomUtil.doctorConstant == null) {
            dialogRoomUtil.doctorConstant = this.doctorConstant;
        }
        if (dialogRoomUtil.loadBalanced == null) {
            dialogRoomUtil.loadBalanced = this.loadBalanced;
        }
    }

    public static Dialog createDialogRoom(String token, Long departmentId, Long patientId) {
        //查询dialog表
        Dialog dialog = dialogRoomUtil.dialogMapper.queryBydeptIdAndpatientId(departmentId, patientId);
        //如果不存在聊天室则新建
        if (null == dialog) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("token", token);
            DepartmentHospitalVO departmentHospitalVO = dialogRoomUtil.departmentMapper.selectByPrimaryKeyForApp(departmentId);
            //添加参数
            Dialog dialogTrans = new Dialog();
            dialogTrans.setPatientId(patientId);
            dialogTrans.setDepartmentId(departmentId);
            dialogTrans.setHospitalId(departmentHospitalVO.getHospitalId());
            dialogTrans.setDepartmentname(departmentHospitalVO.getName());
            Patientl patientl = dialogRoomUtil.patientMapper.selectPatientlById(patientId);
            dialogTrans.setPatientname(patientl.getRealname());
            dialogTrans.setCreateTime(new Date());
            dialogTrans.setCreateType("1");//张衡说固定传1
            //房间成员doctors
            List<Doctor> doctorList = dialogRoomUtil.doctorMapper.getDoctorByDepartmentId(departmentId);
            //添加医生信息
            List<Map> doctors = new ArrayList<>();
            for (Doctor doctorDialog : doctorList) {
                Map<String, Object> map = new HashMap<>();
                map.put("userId", "1" + doctorDialog.getId());
                map.put("realname", doctorDialog.getRealname());
                map.put("password", doctorDialog.getImPassword());
                doctors.add(map);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dialog", dialogTrans);
            map.put(dialogRoomUtil.doctorConstant.getRedis().get("global"), doctors);
            if (null == patientl.getImPassword() || "".equals(patientl.getImPassword())) {
                patientl = dialogRoomUtil.patientMapper.selectPatientlById(patientId);
            }
            map.put("patientpwd", patientl.getImPassword());
            map.put("patientUserId", "2" + patientl.getId());
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> httpEntity = new HttpEntity<String>(JSONObject.toJSONString(map), headers);
            try {
                logger.info("创建房间入参：" + JSON.toJSONString(httpEntity));
                com.yhyt.health.spring.AppResult appResultTrans =
                        dialogRoomUtil.loadBalanced.postForObject(dialogRoomUtil.doctorConstant.getIm().get("roomAdd"), httpEntity, com.yhyt.health.spring.AppResult.class);
                logger.info("创建房间出参：" + JSON.toJSONString(appResultTrans));
                //如果创建房间成功
                if (null != appResultTrans && "200".equals(appResultTrans.getStatus().getCode())) {
                    logger.info("房间创建成功");
                     JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(appResultTrans.getBody()));


                    dialog = dialogRoomUtil.dialogMapper.selectByPrimaryKey(jsonObject.getLong("id") );
                } else {
                    throw new BusinessException("100018", "调用创建房间失败");
                }
            } catch (Exception e) {
                throw new BusinessException("", "调用创建房间失败");
            }
        }
        return dialog;
    }
}
