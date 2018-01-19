package com.yhyt.health.service;
/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月18日 下午2:10:47
 * 类说明
 */

import com.netflix.discovery.converters.Auto;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.DeptGroupPatientMapper;
import com.yhyt.health.mapper.DialogAppointmentMapper;
import com.yhyt.health.mapper.PatientDiagnoseRecordsMapper;
import com.yhyt.health.mapper.SysBlacklistMapper;
import com.yhyt.health.model.DeptGroupPatient;
import com.yhyt.health.model.Dialog;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.SysBlacklist;
import com.yhyt.health.model.vo.PatientVisited;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysBlacklistServiceImpl implements SysBlacklistService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private SysBlacklistMapper sysBlacklistMapper;
    @Autowired
    private DeptGroupPatientMapper deptGroupPatientMapper;
    @Autowired
    private PatientDiagnoseRecordsMapper patientDiagnoseRecordsMapper;
    @Autowired
    private DialogAppointmentMapper dialogAppointmentMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysBlacklistServiceImpl.class);

    @Override
    public AppResult addSysBlacklist(String token,Long patientId,Long doctorId,String state) {
        AppResult appResult = new AppResult();
        //通过token取出当前用户
        Doctor doctor = (Doctor) redisService.get("doctors", token);
        //加入黑名单
        if ("2".equals(state)) {
            List<PatientVisited> patientVisiteds =  patientDiagnoseRecordsMapper.getDepartPatientsVisitedByPatient(patientId,doctor.getDepartmentId());
            if (null != patientVisiteds && patientVisiteds.size() > 0) {
                appResult.setResultCode(ResultCode.FAILED);
                appResult.getStatus().setMessage("患者诊后咨询尚未结束，不能加入黑名单");
                return appResult;
            }

            //有未完成的预约判断
            Map<String, Object> mapDialog = new HashMap<String, Object>();
            mapDialog.put("departmentId", doctor.getDepartmentId());
            mapDialog.put("patientId", patientId);
            mapDialog.put("state", (byte) 2);
            mapDialog.put("appointmentTime", DateUtil.setStartDay(new Date()));
            Long countAll = dialogAppointmentMapper.queryUndiagnoseListCountAll(mapDialog);
            if (countAll > 0) {
                appResult.setResultCode(ResultCode.FAILED);
                appResult.getStatus().setMessage("已预约患者待诊，暂时不能加入黑名单");
                return appResult;
            }
            mapDialog.put("state", (byte) 1);
            Long count = dialogAppointmentMapper.queryUndiagnoseListCountAll(mapDialog);
            if (count > 0) {
                appResult.setResultCode(ResultCode.FAILED);
                appResult.getStatus().setMessage("有预约未处理，暂时不能加入黑名单");
                return appResult;
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("departmentId", doctor.getDepartmentId());
        map.put("patientId", patientId);
        DeptGroupPatient deptGroupPatient = deptGroupPatientMapper.getDeptGroupPatinent(map);
        if (null == deptGroupPatient) {
            appResult.setResultCode(ResultCode.BLACK_PATIENT_NO_EXIST);
            return appResult;
        }
        deptGroupPatient.setIsBlacklist(Byte.valueOf(state));
        deptGroupPatientMapper.updateByPrimaryKeySelective(deptGroupPatient);
        appResult.setResultCode(ResultCode.SUCCESS);
        return appResult;
    }
}
