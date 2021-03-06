package com.yhyt.health.service.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yhyt.health.app.controller.ItemApi;
import com.yhyt.health.config.PathConfiguration;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.*;
import com.yhyt.health.model.*;
import com.yhyt.health.model.vo.DepartmentHospitalVO;
import com.yhyt.health.model.vo.DepartmentPatientVo;
import com.yhyt.health.model.vo.ItemDialogVo;
import com.yhyt.health.model.vo.app.PatientDiagnosePicsCaseVo;
import com.yhyt.health.model.vo.app.PatientDiagnosePicsVo;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.RedisService;
import com.yhyt.health.util.BusinessException;
import com.yhyt.health.util.DialogRoomUtil;
import com.yhyt.health.util.PatientDiagnosePicsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.beans.Transient;
import java.util.*;


/**
 * @author gsh
 * @create 2018-01-03 11:13
 **/
@Service
public class ItemApiImpl implements ItemApi{

    static Logger logger = LoggerFactory.getLogger(ItemApiImpl.class);

    @Autowired
    @Qualifier("loadBalanced")
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PathConfiguration pathConfiguration;

    @Autowired
    private DoctorConstant doctorConstant;

    @Autowired
    private DialogMapper dialogMapper;

    @Autowired
    private PatientDialogCaseMapper patientDialogCaseMapper;

    @Autowired
    private PatientDiagnosePicsMapper patientDiagnosePicsMapper;

    @Autowired
    protected DialogAppointmentMapper dialogAppointmentMapper;

    @Autowired
    private DoctorRequestpatientMapper doctorRequestpatientMapper;

    @Autowired
    private DoctorCareMapper doctorCareMapper;

    @Autowired
    private DeptGroupPatientMapper deptGroupPatientMapper;

    @Autowired
    private DeptGroupMapper deptGroupMapper;

    @Override
    public AppResult getItems(String token, Long userId, String doctorState) {
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        if (null == userId) {
            userId = doctor.getId();
        }
        Map<String, Object> mapPara = new HashMap<String, Object>();
        mapPara.put("departmentId", doctor.getDepartmentId());
        mapPara.put("doctorState", doctorState);
        com.yhyt.health.spring.AppResult result
                =restTemplate.getForObject(pathConfiguration.getSystemUrl()+"/item/items/?departmentId={departmentId}&doctorState={doctorState}", com.yhyt.health.spring.AppResult.class, mapPara);
        AppResult appResult = new AppResult();
        if (null != result && "200".equals(result.getStatus().getCode())) {
            appResult.setResultCode(ResultCode.SUCCESS);
            appResult.setBody((Map<String, Object> ) result.getBody());
        } else {
            throw new BusinessException(ResultCode.EXCEPTION.val(), "调用服务包列表接口失败");
        }
        return appResult;
    }

    @Override
    public AppResult getItem(String token, Long id) {
        com.yhyt.health.spring.AppResult result
                =restTemplate.getForObject(pathConfiguration.getSystemUrl()+"/item/item/"+id, com.yhyt.health.spring.AppResult.class);
        AppResult appResult = new AppResult();
        if (null != result && "200".equals(result.getStatus().getCode())) {
            appResult.setResultCode(ResultCode.SUCCESS);
            appResult.setBody((Map<String, Object> ) result.getBody());
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            //病情资料
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("departmentId", doctor.getDepartmentId());
            map.put("patientId", appResult.getBody().get("patientId"));
            appResult.getBody().remove("patientId");
            //查询病例
            PatientDialogCase patientDialogCase = patientDialogCaseMapper.selectPatientDialogCase(map);
            List<PatientDiagnosePicsVo> patientDiagnosePicsVos = new ArrayList<>();
            appResult.getBody().put("diagnoseDescribePics", patientDiagnosePicsVos);
            if (null == patientDialogCase) {
                return appResult;
            }
            List<PatientDiagnosePicsCaseVo> patientDiagnosePicsCaseVos = PatientDiagnosePicsUtil.getPatientDiagnsePics(patientDiagnosePicsMapper.selectByCaseIdAndType(patientDialogCase.getCaseId(), (byte) 6));
            for (PatientDiagnosePicsCaseVo patientDiagnosePicsCaseVo : patientDiagnosePicsCaseVos) {
                if (null != patientDiagnosePicsCaseVo.getPatientDiagnosePicsVos() && patientDiagnosePicsCaseVo.getPatientDiagnosePicsVos().size() > 0) {
                    patientDiagnosePicsVos.addAll(patientDiagnosePicsCaseVo.getPatientDiagnosePicsVos());
                }
            }


        } else {
            throw new BusinessException(ResultCode.EXCEPTION.val(), "调用服务包详情接口失败");
        }
        return appResult;
    }

    @Override
    @Transient
    public AppResult updateItemState(String token,Long userId, Long taskId,String doctorState) {
        AppResult appResult = new AppResult();
        //得到当前用户
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        //如果是改为处理中状态
        if ("2".equals(doctorState)) {
            DepartmentPatientVo departmentPatientVo = dialogMapper.getDialogByTaskId(taskId);
            Dialog dialog = null;
            if (departmentPatientVo != null) {
                dialog =DialogRoomUtil.createDialogRoom(token, departmentPatientVo.getDepartmentId(), departmentPatientVo.getPatientId());
                DoctorRequestpatient doctorRequestpatient = doctorRequestpatientMapper.selectByDeptAndPatient(departmentPatientVo.getDepartmentId(), departmentPatientVo.getPatientId());
                if (null != doctorRequestpatient && 3 != doctorRequestpatient.getState()) {
                    doctorRequestpatient.setState((byte) 3);
                    doctorRequestpatientMapper.updateByPrimaryKeySelective(doctorRequestpatient);//求诊接诊
                    //关注该患者
                    DoctorCare doctorCare = doctorCareMapper.queryDoctorCare(doctor.getId(), departmentPatientVo.getPatientId(), departmentPatientVo.getDepartmentId());
                    if (null == doctorCare) {
                        doctorCare=new DoctorCare();
                        doctorCare.setDoctorId(doctor.getId());
                        doctorCare.setDepartmentId(departmentPatientVo.getDepartmentId());
                        doctorCare.setPatientId(departmentPatientVo.getPatientId());
                        doctorCare.setCreateTime(new Date());
                        doctorCareMapper.insert(doctorCare);
                    }
                    //加入默认分组
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("departmentId", departmentPatientVo.getDepartmentId());
                    map.put("patientId", departmentPatientVo.getPatientId());
                    DeptGroupPatient deptGroupPatient = deptGroupPatientMapper.getDeptGroupPatinent(map);
                    if (null == deptGroupPatient) {
                        deptGroupPatient = new DeptGroupPatient();
                        DeptGroup deptGroup = deptGroupMapper.queryDefaultGroup(departmentPatientVo.getDepartmentId());
                        deptGroupPatient.setPatientId(departmentPatientVo.getPatientId());
                        deptGroupPatient.setCreateTime(new Date());
                        deptGroupPatient.setDeptGroupId(deptGroup.getId());
                        deptGroupPatientMapper.insertSelective(deptGroupPatient);
                    }
                }
            }
            if (dialog == null) {
                appResult.setResultCode(ResultCode.FAILED);
                return appResult;
            }
            appResult.getBody().put("roomId", dialog.getRoomId());
        }
        com.yhyt.health.spring.AppResult result
                =restTemplate.patchForObject(pathConfiguration.getSystemUrl()+"/item/"+taskId+"?doctorState="+doctorState
                +"&userId="+doctor.getId(),null, com.yhyt.health.spring.AppResult.class);
        if (null != result && "200".equals(result.getStatus().getCode())) {
            appResult.setResultCode(ResultCode.SUCCESS);
        }else {
            throw new BusinessException(ResultCode.EXCEPTION.val(), "调用改变服务包状态接口失败");
        }
        return appResult;
    }

    @Override
    public AppResult getItemRecords(String token, String roomId) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        List<ItemDialogVo> itemDialogVos = dialogAppointmentMapper.getItemDialogAppointmentVosByRoomId(roomId);
        appResult.getBody().put("dialogs", itemDialogVos);
        return appResult;
    }

    @Override
    public AppResult getTaskServiceState(String token, Long orderId) {
        com.yhyt.health.spring.AppResult result
                =restTemplate.getForObject(pathConfiguration.getSystemUrl()+"/item/order/"+orderId, com.yhyt.health.spring.AppResult.class);
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        if (null != result && "200".equals(result.getStatus().getCode())) {
            appResult.getBody().put("states", result.getBody());
        } else {
            appResult.setResultCode(ResultCode.FAILED);
        }
        return appResult;
    }
}
