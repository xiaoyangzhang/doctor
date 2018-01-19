package com.yhyt.health.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yhyt.health.config.PathConfiguration;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.DialogMapper;
import com.yhyt.health.mapper.DoctorCareMapper;
import com.yhyt.health.mapper.PatientDiagnoseRecordsMapper;
import com.yhyt.health.model.Dialog;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.DoctorCare;
import com.yhyt.health.model.PatientDiagnoseRecords;
import com.yhyt.health.model.vo.ImMessageVo;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.ImService;
import com.yhyt.health.service.MessageService;
import com.yhyt.health.service.RedisService;
import com.yhyt.health.service.SelfDiagnosisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SelfDiagnosisServiceImpl implements SelfDiagnosisService {

    @Autowired
    @Qualifier("loadBalanced")
    private RestTemplate restTemplate;
    @Autowired
    private ImService imService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private DoctorCareMapper doctorCareMapper;
    @Autowired
    private PatientDiagnoseRecordsMapper patientDiagnoseRecordsMapper;
    @Autowired
    private DialogMapper dialogMapper;
    @Autowired
    private PathConfiguration pathConfiguration;

    @Override
    public AppResult confirm(String token, Long patientId, String caseId, List<Map> diseases) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        //获取当前用户
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        if (doctor == null) {
            appResult.getBody().put("msg", "未找到医生信息");
            appResult.setResultCode(ResultCode.FAILED);
            return appResult;
        }

        //ai 确诊
        Map map = restTemplate.postForObject(pathConfiguration.getPatientUrl()+"/selfDiagnosis/confirm/{1}", diseases, Map.class, caseId);

        String masterDiseaseName = "";
        List<String> subDiseaseNames = new ArrayList<>();
        for (Map diseaseMap : diseases) {
            if ("1".equals(diseaseMap.get("isMaster"))) {
                masterDiseaseName = diseaseMap.get("diseaseId").toString();
                break;
            }
        }

        for (Map diseaseMap : diseases) {
            subDiseaseNames.add(diseaseMap.get("diseaseId").toString());
        }
        //向患者发送推送“医生已为您确诊”
        messageService.sendMessage(patientId, "2", "新健康", "医生已为您确诊！","8");

        Dialog dialog = dialogMapper.queryBydeptIdAndpatientId(doctor.getDepartmentId(), patientId);
        if (dialog != null) {
            List<String> roomIds = new ArrayList<>();
            roomIds.add(dialog.getRoomId());
            //发送系统消息：确诊为“XXX”（XXX＝主诊断疾病名）
            ImMessageVo imMessageVo = new ImMessageVo();
            imMessageVo.setFromUserLogo(doctor.getHeadImage());
            imMessageVo.setFromUserName(doctor.getRealname());
            imMessageVo.setFromUserId(doctor.getId());
            imMessageVo.setTimeSend(new Date().getTime());
            imMessageVo.setType("0");
            String roomIdsJson = JSONObject.toJSON(roomIds).toString();
            imMessageVo.setContent("确诊为" + masterDiseaseName);
            Object msg = JSONObject.toJSON(imMessageVo);
            imService.sendGroupMessage(null, msg.toString(), roomIdsJson);

            //该医生默认打开“关注”该患者
            DoctorCare doctorCare = doctorCareMapper.queryDoctorCare(doctor.getId(), patientId, doctor.getDepartmentId());
            if (doctorCare == null) {
                DoctorCare doctorCareTemp = new DoctorCare();
                doctorCareTemp.setDoctorId(doctor.getId());
                doctorCareTemp.setPatientId(patientId);
                doctorCareTemp.setDepartmentId(doctor.getDepartmentId());
                doctorCareTemp.setCreateTime(new Date());
                doctorCareMapper.insert(doctorCareTemp);
            }
            //更新对话导航栏疾病名、更新诊断记录
            PatientDiagnoseRecords patientDiagnoseRecords = new PatientDiagnoseRecords();
            patientDiagnoseRecords.setDoctorId(doctor.getId());
            patientDiagnoseRecords.setDialogId(dialog.getId());
            patientDiagnoseRecords.setMainDiagnose(masterDiseaseName);
            patientDiagnoseRecords.setSubDiagnose(StringUtils.join(subDiseaseNames, ","));
            patientDiagnoseRecords.setCreateTime(new Date());
            patientDiagnoseRecords.setUpdateTime(new Date());
            patientDiagnoseRecordsMapper.insertSelective(patientDiagnoseRecords);
        }
        return appResult;
    }
}
