package com.yhyt.health.service.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.constant.Constants;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.*;
import com.yhyt.health.model.*;
import com.yhyt.health.model.Dictionary;
import com.yhyt.health.model.vo.DepartmentVO;
import com.yhyt.health.model.vo.DialogAppointmentTransferVo;
import com.yhyt.health.model.vo.DialogAppointmentVo;
import com.yhyt.health.model.vo.PatientDetailVo;
import com.yhyt.health.model.vo.app.*;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.RedisService;
import com.yhyt.health.util.PatientDiagnosePicsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 病人详情实现类
 *
 * @author gsh
 * @create 2017-09-01 15:29
 **/
@Service
public class PatientCasearchivesApiImpl implements PatientCasearchivesApi {

    @Autowired
    private RedisService redisService;
    @Autowired
    private DialogAppointmentMapper dialogAppointmentMapper;
    @Autowired
    private PatientCasearchivesMapper patientCasearchivesMapper;
    @Autowired
    private PatientDialogCaseMapper patientDialogCaseMapper;
    @Autowired
    private DialogAppointmentTransferMapper dialogAppointmentTransferMapper;
    @Autowired
    private PatientDiagnosePicsMapper patientDiagnosePicsMapper;
    @Autowired
    private DoctorRequestpatientMapper doctorRequestpatientMapper;
    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private DeptPatientSignMapper deptPatientSignMapper;
    @Autowired
    private DepartmentObstetricsMapper departmentObstetricsMapper;
    @Autowired
    private DepartmentObstetricsDangersMapper departmentObstetricsDangersMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;

    private static Logger logger = LoggerFactory.getLogger(HospitalApiImpl.class);

    @Override
    public AppResult getPatientCasearchives(String token, PatientCasearchives patientCasearchives) {
        AppResult appResult = new AppResult();
        //预约 转诊 求诊的id
        Long id = patientCasearchives.getId();
        appResult.setResultCode(ResultCode.SUCCESS);
        if (null == patientCasearchives.getDepartmentId()) {
            //获取当前医生
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            patientCasearchives.setDepartmentId(doctor.getDepartmentId());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == patientCasearchives.getDialogId()) {
            map.put("departmentId", patientCasearchives.getDepartmentId());
            map.put("patientId", patientCasearchives.getPatientId());
        } else {
            map.put("dialogId", patientCasearchives.getDialogId());
        }
        //查询患者基本信息
        PatientDetailVo patientDetailVo = patientCasearchivesMapper.selectPatientCasearchives(map);
        if (null == patientDetailVo) {
            appResult.setResultCode(ResultCode.SUCCESS);
            return appResult;
        }
        //没有社保，不显示参保地
        if (!"1".equals(patientDetailVo.getHasInsurance())) {
            patientDetailVo.setInsuranceLocation("");
        }
        //主诉显示空问题
        if (null != patientDetailVo.getMainsuit() && patientDetailVo.getMainsuit().endsWith(",")) {
            patientDetailVo.setMainsuit(patientDetailVo.getMainsuit().substring(0,patientDetailVo.getMainsuit().length()-1));
        }
        //求诊
        if ("1".equals(String.valueOf(patientCasearchives.getType()))) {
            patientDetailVo.setId(id);
        }
        //预约
        if ("2".equals(String.valueOf(patientCasearchives.getType()))) {
            Map<String, Object> mapAppointMent = new HashMap<String, Object>();
            mapAppointMent.put("id", patientCasearchives.getId());
            DialogAppointmentVo dialogAppointmentVo = dialogAppointmentMapper.getDialogAppointment(mapAppointMent);
            patientDetailVo.setId(dialogAppointmentVo.getId());
            patientDetailVo.setAppointmentName(dialogAppointmentVo.getDoctorNameAppointment());
            patientDetailVo.setAppointmentDepartment(dialogAppointmentVo.getDepartmentName());
            patientDetailVo.setAppointmentHospital(dialogAppointmentVo.getHospitalName());

        }
        //转诊
        if ("3".equals(String.valueOf(patientCasearchives.getType()))) {
            Map<String, Object> mapTransfer = new HashMap<String, Object>();
            mapTransfer.put("id", patientCasearchives.getId());
            mapTransfer.put("departmentId", patientCasearchives.getDepartmentId());
            DialogAppointmentTransferVo dialogAppointmentTransferVo = dialogAppointmentTransferMapper.getDialogAppointmentTransfers(mapTransfer);
            patientDetailVo.setId(dialogAppointmentTransferVo.getId());
            patientDetailVo.setAppointmentName(dialogAppointmentTransferVo.getLaunchDoctorName());
            patientDetailVo.setAppointmentDepartment(dialogAppointmentTransferVo.getDepartmentName());
            patientDetailVo.setAppointmentHospital(dialogAppointmentTransferVo.getHospitalName());
            //查询转诊医生
            if (null != dialogAppointmentTransferVo.getLaunchDoctorId()) {
                Doctor doctor = doctorMapper.selectByPrimaryKey(dialogAppointmentTransferVo.getLaunchDoctorId());
                patientDetailVo.setAppointmentName(doctor.getRealname());
                patientDetailVo.setTitle(doctor.getTitle());
            }
        }
        //查询病例
        PatientDialogCase patientDialogCase = patientDialogCaseMapper.selectPatientDialogCase(map);
        if (null == patientDialogCase) {
            appResult.getBody().put("patientDetail", patientDetailVo);
            return appResult;
        }
        patientDetailVo.setDiagnoseDescribePics(PatientDiagnosePicsUtil.getPatientDiagnsePics(patientDiagnosePicsMapper.selectByCaseIdAndType(patientDialogCase.getCaseId(), (byte) 5)));
        patientDetailVo.setDiagnoseCasePics(PatientDiagnosePicsUtil.getPatientDiagnsePics(patientDiagnosePicsMapper.selectCasePicsByCaseIdAndType(patientDialogCase.getCaseId(), (byte) 2)));
        patientDetailVo.setDiagnoseInspectPics(PatientDiagnosePicsUtil.getPatientDiagnsePics(patientDiagnosePicsMapper.selectCasePicsByCaseIdAndType(patientDialogCase.getCaseId(), (byte) 3)));
        appResult.getBody().put("patientDetail", patientDetailVo);
        return appResult;
    }

    @Override
    public AppResult getDoctorRequestpatient(String token, DoctorRequestpatient doctorRequestpatient,Integer pageIndex,Integer pageSize) {
        if (null == pageIndex || pageIndex == 0){
            pageIndex= Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize==0){
            pageSize=Constants.PAGESIZE;
        }
        if (null == doctorRequestpatient.getDepartmentId()) {
            //获取当前医生
            Doctor doctor = (Doctor) redisService.get("doctors", token);
            doctorRequestpatient.setDepartmentId(doctor.getDepartmentId());
        }
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Page page = PageHelper.startPage(pageIndex,pageSize);
        PageInfo<PatientAppVO> pageInfo = new PageInfo<PatientAppVO>(doctorRequestpatientMapper.getNewRequestPatientList(doctorRequestpatient.getDepartmentId()));
        appResult.getBody().put("list", pageInfo.getList());
        return appResult;
    }

    @Override
    public AppResult updateDoctorRequestpatient(String token, DoctorRequestpatient doctorRequestpatient) {
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        doctorRequestpatient.setOperator(doctor.getId()+"");
        doctorRequestpatient.setUpdateTime(new Date());
        doctorRequestpatientMapper.updateByPrimaryKeySelective(doctorRequestpatient);
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        return appResult;
    }



    @Override
    public AppResult getPatientUserDetail(String token, Long patientId, Long departmentId) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Patientl patientl = patientMapper.selectPatientlById(patientId);

        PatientUserDetailVo patientUserDetailVo = new PatientUserDetailVo();
        patientUserDetailVo.setRealName(patientl.getRealname());
        patientUserDetailVo.setBirthday(patientl.getBirthday());
        patientUserDetailVo.setIdno(patientl.getIdno());
        patientUserDetailVo.setSex(String.valueOf(patientl.getSex()));
        patientUserDetailVo.setUsername(patientl.getUsername());
        appResult.getBody().put("patient", patientUserDetailVo);
        PatientAppVO deptPatientSign = deptPatientSignMapper.getSignDetail(departmentId,patientId);
        if (null == deptPatientSign) {
            return appResult;
        }
        patientUserDetailVo.setDeptPatientSignId(deptPatientSign.getDeptSignId());

        //获得孕产信息
        PatientObstetricInfoVo departmentObstetrics = departmentObstetricsMapper.getPatientlObsterics(deptPatientSign.getDeptSignId());
        if (null != departmentObstetrics) {
            List<DepartmentObstetricsDangers> departmentObstetricsDangers
                    = departmentObstetricsDangersMapper.getDepartmentObstetricsDangersList(departmentObstetrics.getObstericsId());
            if (null != departmentObstetricsDangers && departmentObstetricsDangers.size() > 0) {
                for (DepartmentObstetricsDangers dangers : departmentObstetricsDangers) {
                    Dictionary dictionary = dictionaryMapper.selectByPrimaryKey(dangers.getDictionaryId());
                    if (null == departmentObstetrics.getDepartmentObstetricsIds()){
                        departmentObstetrics.setDepartmentObstetricsIds(dangers.getDictionaryId()+",");
                        departmentObstetrics.setDepartmentObstetricsNames(dictionary.getItemName()+",");
                    }else{
                        departmentObstetrics.setDepartmentObstetricsIds(departmentObstetrics.getDepartmentObstetricsIds()+dangers.getDictionaryId()+",");
                        departmentObstetrics.setDepartmentObstetricsNames(departmentObstetrics.getDepartmentObstetricsNames()+dictionary.getItemName()+",");
                    }
                }
            }
        }
        appResult.getBody().put("obstetrics", departmentObstetrics);
        //得到就诊信息
        DepartmentVO departmentVO = departmentMapper.getDeptInfo(departmentId);
        DiagnoseInfoVo diagnoseInfoVo = new DiagnoseInfoVo();
        diagnoseInfoVo.setDiagnoseResult(deptPatientSign.getDiagnoseResult());
        diagnoseInfoVo.setMainDoctor(deptPatientSign.getMainDoctor());
        diagnoseInfoVo.setType(String.valueOf(deptPatientSign.getType()));
        diagnoseInfoVo.setHospital(departmentVO.getHospital());
        appResult.getBody().put("diagnoseInfo", diagnoseInfoVo);
        //病情资料
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentId", departmentId);
        map.put("patientId", patientId);
        //查询病例
        PatientDialogCase patientDialogCase = patientDialogCaseMapper.selectPatientDialogCase(map);
        List<PatientDiagnosePicsVo> patientDiagnosePicsVos = new ArrayList<>();
        List<PatientDiagnosePicsCaseVo> patientDiagnosePicsCaseVos = PatientDiagnosePicsUtil.getPatientDiagnsePics(patientDiagnosePicsMapper.selectByCaseIdAndType(patientDialogCase.getCaseId(), (byte) 6));
        for (PatientDiagnosePicsCaseVo patientDiagnosePicsCaseVo : patientDiagnosePicsCaseVos) {
            if (null != patientDiagnosePicsCaseVo.getPatientDiagnosePicsVos() && patientDiagnosePicsCaseVo.getPatientDiagnosePicsVos().size() > 0) {
                patientDiagnosePicsVos.addAll(patientDiagnosePicsCaseVo.getPatientDiagnosePicsVos());
            }
        }
        appResult.getBody().put("diagnoseDescribePics", patientDiagnosePicsVos);
        return appResult;
    }

    @Override
    public AppResult getPatientCasearchivesPics(String token, Long patientId, Long departmentId) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentId", departmentId);
        map.put("patientId", patientId);
        //查询患者基本信息
        PatientDetailVo patientDetailVo = patientCasearchivesMapper.selectPatientCasearchives(map);
        if (null == patientDetailVo) {
            return appResult;
        }
        appResult.getBody().put("diseasedescription",patientDetailVo.getDiseasedescription());
        //查询病例
        PatientDialogCase patientDialogCase = patientDialogCaseMapper.selectPatientDialogCase(map);
        List<PatientDiagnosePicsVo> patientDiagnosePicsVos = new ArrayList<PatientDiagnosePicsVo>();
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
        return appResult;
    }
}
