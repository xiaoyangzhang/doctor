package com.yhyt.health.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.constant.Constants;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.*;
import com.yhyt.health.model.*;
import com.yhyt.health.model.Dictionary;
import com.yhyt.health.model.vo.DepartmentHospitalVO;
import com.yhyt.health.model.vo.DoctorVO;
import com.yhyt.health.model.vo.app.AppointmentTransferPatientVO;
import com.yhyt.health.model.vo.app.DoctorAppVO;
import com.yhyt.health.model.vo.app.PatientAppVO;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.util.BusinessException;
import com.yhyt.health.util.DateUtil;
import com.yhyt.health.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月17日 下午4:14:42
 * 类说明
 */
@Service
public class DoctorAddServiceImpl implements DoctorAddService {
    @Autowired
    private DialogAppointmentMapper dialogAppointmentMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private DialogAppointmentNoticeMapper dialogAppointmentNoticeMapper;
    @Autowired
    private DialogAppointmentTransferMapper dialogAppointmentTransferMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private DeptPatientSignMapper deptPatientSignMapper;
    @Autowired
    private PatientDiagnosePicsMapper patientDiagnosePicsMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DoctorConstant doctorConstant;

    private static final Logger logger = LoggerFactory.getLogger(DoctorAddServiceImpl.class);

    @Override
    public AppResult getAllTitle() {
        AppResult appResult = new AppResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dictCode", "500");
        List<Dictionary> dd = dictionaryMapper.findDictionaryListdictName(map);
        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("list", dd);
        return appResult;
    }

    @Override
    public AppResult updatepassword(String username, String originpassword, String newpassword) {
        AppResult appResult = new AppResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        Doctor lsd = doctorMapper.getDoctor(map);
        if (lsd == null) {
            appResult.getStatus().setCode(ResultCode.NO_EXSTIS.val());
            appResult.getStatus().setMessage(ResultCode.NO_EXSTIS.msg());
            return appResult;
        }
        if (lsd.getPassword().equals(MD5Util.MD5Password(originpassword))) {
            lsd.setPassword(MD5Util.MD5Password(newpassword));
            doctorMapper.updateByPrimaryKeySelective(lsd);
            appResult.setResultCode(ResultCode.SUCCESS);
        } else {
            appResult.getStatus().setCode(ResultCode.SYS_ERROR.val());
            appResult.getStatus().setMessage("原密码错误");
        }
        return appResult;
    }

    @Override
    public AppResult appointmentexpire(String doctorid, String departmentid) {
        AppResult appResult = new AppResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("doctorid", doctorid);
            map.put("departmentid", departmentid);
            List<DialogAppointmentMapper> ld = dialogAppointmentMapper.appointmentexpire(map);
            appResult.setResultCode(ResultCode.SUCCESS);
            appResult.getBody().put("list", ld);
        } catch (Exception e) {
            logger.error("接口出错",e);
            throw new BusinessException(e);
        }
        return appResult;
    }

    @Override
    public AppResult triagedoctorlist(String departmentid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional
    public AppResult triageconfirm(String token, DialogAppointment dialogAppointment) {
        AppResult appResult = new AppResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appointmentId", dialogAppointment.getId());
        map.put("doctorIdAppointment", dialogAppointment.getDoctorIdAppointment());
        map.put("treatRoom", dialogAppointment.getTreatRoom());
        map.put("treatNum", dialogAppointment.getTreatNum());
        dialogAppointmentMapper.triageconfirm(map);
        appResult.setResultCode(ResultCode.SUCCESS);

        //保存通知表
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        DialogAppointmentNotice appointmentNotice = new DialogAppointmentNotice();
        appointmentNotice.setDoctorId(doctor.getId());
        appointmentNotice.setDepartmentId(doctor.getDepartmentId());
        appointmentNotice.setPatientId(dialogAppointment.getPatientIdAppointment());
        appointmentNotice.setDialogAppointmentId(dialogAppointment.getId());
        appointmentNotice.setCreateTime(new Date());
        appointmentNotice.setState((byte) 1);//预约
        appointmentNotice.setMessage("分诊成功");
        appointmentNotice.setType((byte) 1);
        dialogAppointmentNoticeMapper.insertSelective(appointmentNotice);
        //发推送给预约的医生
        if (dialogAppointment.getDoctorIdAppointment() != doctor.getId()) {
            Doctor doctorAppointment = doctorMapper.selectByPrimaryKey(dialogAppointment.getDoctorIdAppointment());
            messageService.sendMessage(dialogAppointment.getPatientIdAppointment(), "2", "新健康", "已预约"+doctorAppointment.getRealname()+"医生，请及时查看预约信息","6");
        }
        messageService.sendMessage(dialogAppointment.getDoctorIdAppointment(), "1", "新医疗", "有新预约","6");
        return appResult;
    }

    @Override
    @Transactional
    public AppResult dismissAssignDoctor(long appointmentId) {
        AppResult appResult = new AppResult();
        Map<String, Object> map = new HashMap<String, Object>();
//		Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        map.put("appointmentId", appointmentId);
        dialogAppointmentMapper.dismissAssignDoctor(map);
        //修改通知表
        Map<String, Object> mapNotice = new HashMap<String, Object>();
        mapNotice.put("dialogAppointmentId", appointmentId);
        mapNotice.put("type", (byte) 1);//预约
        mapNotice.put("state", (byte) 1);
        dialogAppointmentNoticeMapper.updateDialogAppointmentNoticeState(mapNotice);
        appResult.setResultCode(ResultCode.SUCCESS);
        return appResult;
    }

    @Override
    @Transactional
    public AppResult appointmentdoctorlist(String token, String departmentid, Integer pageIndex, Integer pageSize) {
//        if (null == pageIndex || pageIndex == 0) {
//            pageIndex = Constants.PAGEINDEX;
//        }
//        if (null == pageSize || pageSize == 0) {
//            pageSize = Constants.PAGESIZE;
//        }
        AppResult appResult = new AppResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentId", departmentid);
        map.put("appointmentTime", DateUtil.setStartDay(new Date()));
        DepartmentHospitalVO departmentHospitalVO = departmentMapper.selectDeptInfoForApp(Long.parseLong(departmentid));
        int patientNum = dialogAppointmentMapper.queryPatientNumNoAssignDoctor(map);
        departmentHospitalVO.setNoAssignDoctor(patientNum);

        //查询未分组的数量
        map.put("state", (byte) 2);
        int patientNumNotGrep = dialogAppointmentMapper.queryDiagnoseByDoctorListCountAll(map);
        DoctorAppVO vo = new DoctorAppVO();
        vo.setName("未分诊");
        vo.setAppointmentCount(patientNumNotGrep);

        List<DoctorAppVO> ld = new ArrayList<DoctorAppVO>();
        ld.add(vo);
        //查询分组的医生列表
//        if (pageIndex == 1) {
//            pageSize = pageSize - 1;
//        }
        //Page page = PageHelper.startPage(pageIndex, pageSize);
        List<DoctorAppVO> ldGrep = doctorMapper.appointmentdoctorlist(map);
        //PageInfo<DoctorAppVO> pageInfo = new PageInfo<DoctorAppVO>(ldGrep);
        if (null != ldGrep && ldGrep.size() > 0) {
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            for (int i=0;i<ldGrep.size();i++) {
                DoctorAppVO doctorAppVO = ldGrep.get(i);
                if (doctorAppVO.getId().longValue() == doctor.getId().longValue()) {
                    ld.add(doctorAppVO);
                    ldGrep.remove(doctorAppVO);
                    i--;
                }
            }
//            for (DoctorAppVO doctorAppVO : ldGrep) {
//                if (doctorAppVO.getId().longValue() == doctor.getId().longValue()) {
//                    ld.add(doctorAppVO);
//                    ldGrep.remove(doctorAppVO);
//                }
//            }
            ld.addAll(ldGrep);
        }
        departmentHospitalVO.setDoctorAppVOList(ld);
        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("deptDoctor", departmentHospitalVO);
        return appResult;
    }

    @Override
    public AppResult searchpatient(String key, String departmentid) {
        AppResult appResult = new AppResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("key", key);
            map.put("departmentid", departmentid);
            List<Patientl> ld = patientMapper.searchpatient(map);
            appResult.setResultCode(ResultCode.SUCCESS);
            appResult.getBody().put("list", ld);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            appResult.setResultCode(ResultCode.SYS_ERROR);
        }
        return appResult;
    }

    @Override
    public AppResult searchdepartmentpatient(String searchdate, String departmentid) {
        AppResult appResult = new AppResult();
        try {


            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = null;
            if (null != searchdate) {
                try {
                    dt = formatter.parse(searchdate);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    appResult.setResultCode(ResultCode.SYS_ERROR);
                    return appResult;
                }
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("searchdate", searchdate);
            map.put("departmentid", departmentid);
            List<Patientl> ld = patientMapper.searchdepartmentpatient(map);
            appResult.setResultCode(ResultCode.SUCCESS);
            appResult.getBody().put("list", ld);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            appResult.setResultCode(ResultCode.SYS_ERROR);
        }
        return appResult;
    }

    @Override
    public AppResult transferpatient(String departmentid,Integer pageIndex,Integer pageSize) {

        if (null == pageIndex || pageIndex == 0){
            pageIndex= Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize==0){
            pageSize=Constants.PAGESIZE;
        }

        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentid", departmentid);
        PageHelper.startPage(pageIndex,pageSize);
        PageInfo<AppointmentTransferPatientVO> pageInfo = new PageInfo<AppointmentTransferPatientVO>(dialogAppointmentTransferMapper.transferpatientList(map));
        appResult.getBody().put("list", pageInfo.getList());
        return appResult;
    }

    @Override
    public AppResult dialogsignlist(String departmentid,Integer pageIndex,Integer pageSize) {
        if (null == pageIndex || pageIndex == 0){
            pageIndex= Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize==0){
            pageSize=Constants.PAGESIZE;
        }
        AppResult appResult = new AppResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentid", departmentid);
//			List<Patientl> ld=patientMapper.dialogsignlist(map);
        Page page = PageHelper.startPage(pageIndex,pageSize);
        PageInfo<PatientAppVO> pageInfo = new PageInfo<PatientAppVO>(deptPatientSignMapper.getSignApplyWithScanCode(Long.parseLong(departmentid)));
        appResult.getBody().put("list", pageInfo.getList());
        appResult.setResultCode(ResultCode.SUCCESS);
        return appResult;
    }

    @Override
    public AppResult dialogsigndetail(String departmentid, String patientid) {
        AppResult appResult = new AppResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("departmentid", departmentid);
            map.put("patientid", patientid);
//			List<Patientl> ld=patientMapper.dialogsigndetail(map);
            PatientAppVO signDetail = deptPatientSignMapper.getSignDetail(Long.parseLong(departmentid), Long.parseLong(patientid));
            if (signDetail != null) {
                List<PatientDiagnosePics> patientDiagnosePics = patientDiagnosePicsMapper.selectBySignId(signDetail.getDeptSignId());
                signDetail.setDiseaseInfo(patientDiagnosePics);
            }
            appResult.setResultCode(ResultCode.SUCCESS);
            appResult.getBody().put("detail", signDetail);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            appResult.setResultCode(ResultCode.SYS_ERROR);
        }
        return appResult;
    }

    @Override
    public AppResult casedetail(String patientid, String caseid) {
        AppResult appResult = new AppResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("caseid", caseid);
            map.put("patientid", patientid);
            //	List<Patientl> ld=patientMapper.dialogsigndetail(map);
            appResult.setResultCode(ResultCode.SUCCESS);
            //	appResult.getBody().put("list", ld.get(0));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            appResult.setResultCode(ResultCode.SYS_ERROR);
        }
        return appResult;
    }


}
