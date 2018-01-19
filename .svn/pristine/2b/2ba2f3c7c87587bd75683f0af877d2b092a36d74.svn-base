package com.yhyt.health.service.api;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.constant.Constants;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.*;
import com.yhyt.health.model.*;
import com.yhyt.health.model.vo.*;
import com.yhyt.health.model.vo.app.UserListVo;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.MessageService;
import com.yhyt.health.service.RedisService;
import com.yhyt.health.service.XmppMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DepartmentApiImpl implements DepartmentApi {

    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private DeptDoctorMapper deptDoctorMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private DeptGroupPatientMapper deptGroupPatientMapper;
    @Autowired
    private DeptGroupMapper deptGroupMapper;
    @Autowired
    private DialogAppointmentTransferMapper dialogAppointmentTransferMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private DialogAppointmentNoticeMapper dialogAppointmentNoticeMapper;
    @Autowired
    private XmppMessageService xmppMessageService;
    @Autowired
    private DeptPatientSignMapper deptPatientSignMapper;

    @Autowired
    private DialogMapper dialogMapper;

    private static Logger logger = LoggerFactory.getLogger(DepartmentApiImpl.class);

    @Override
    public AppResult addDeptDoctor(String token,DeptDoctor deptDoctor) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentId", deptDoctor.getDepartmentId());
        //获取当前医生信息
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        map.put("doctorId", doctor.getId());
        DeptDoctor deptDoctor1 = deptDoctorMapper.getDeptDoctorByDepartIdAndDoctorId(map);
        if (null != deptDoctor1 && (byte) 2 != deptDoctor1.getIsAdmin()) {
            appResult.setResultCode(ResultCode.FAILED);
            appResult.getStatus().setMessage("非管理员不能添加医生");
            return appResult;
        }
        //查询是否已经加入科室

        map.put("doctorId", deptDoctor.getDoctorId());
        if (null != deptDoctorMapper.getDeptDoctorByDepartIdAndDoctorId(map)) {
            appResult.getStatus().setCode("10006");
            appResult.getStatus().setMessage("已经加入，无需重复操作");
            return appResult;
        }

        deptDoctor.setCreateTime(new Date());
        deptDoctorMapper.insertSelective(deptDoctor);

        //查询科室信息
        DepartmentHospitalVO departmentHospitalVO =departmentMapper.selectByPrimaryKeyForApp(deptDoctor.getDepartmentId());
        //查询加入科室医生信息
        DoctorAppInfoVo doctorInfoVo = doctorMapper.selectDoctorForAppById(deptDoctor.getDoctorId());
        //通知科室成员
        messageService.sendMessage(departmentHospitalVO.getId(),null
                ,doctorInfoVo.getRealname()+"加入本科室",doctorInfoVo.getRealname()+"加入本科室","2");
        //通知加入成员
        messageService.sendMessage(deptDoctor.getDoctorId(), "2"
                , "已经成为"+departmentHospitalVO.getHospital()+"-"+departmentHospitalVO.getName()+"成员"
                , "如果有任何疑问，请拨打客服电话:95132","2");
        return appResult;
    }

    @Override
    public AppResult getDeptDoctorList(long departId,Integer pageIndex,Integer pageSize) {
        if (null == pageIndex || pageIndex == 0){
            pageIndex= Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize==0){
            pageSize=Constants.PAGESIZE;
        }
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Page page = PageHelper.startPage(pageIndex,pageSize);
        PageInfo<Doctor> pageInfo = new PageInfo<Doctor>(doctorMapper.getDoctorByDepartmentId(departId));
        appResult.getBody().put("list", pageInfo.getList());
        return appResult;
    }

    @Override
    public AppResult updateDeptNotice(long id, String notice) {
        AppResult result = new AppResult();
        result.setResultCode(ResultCode.SUCCESS);
        Department dept = new Department();
        try {
            dept.setId(id);
            dept.setNotice(notice);
            dept.setUpdateTime(new Date());
            departmentMapper.updateByPrimaryKeySelective(dept);
        } catch (Exception e) {
            result.setResultCode(ResultCode.EXCEPTION);
        }
        return result;
    }

    @Override
    @Transactional
    public AppResult updatePatientGroup(String token, DeptGroupPatient deptGroupPatient) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        //获取当前医生信息
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        //查询分组信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentId", doctor.getDepartmentId());
        map.put("patientId", deptGroupPatient.getPatientId());
        DeptGroupPatient deptGroupPatientY = deptGroupPatientMapper.getDeptGroupPatinent(map);
        deptGroupPatient.setId(deptGroupPatientY.getId());
        deptGroupPatientMapper.updateByPrimaryKeySelective(deptGroupPatient);
//        try {
//            if (null != deptGroupPatient.getRemark() && !"".equals(deptGroupPatient.getRemark())) {
//                deptGroupPatientMapper.updateByPrimaryKeySelective(deptGroupPatient);
//                return appResult;
//            }
//            //获取当前医生信息
//            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
//            //查询患者当前所在分组,如果存在就删除调
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("departmentId", doctor.getDepartmentId());
//            map.put("patientId", deptGroupPatient.getPatientId());
//            DeptGroupPatient deptGroupPatientY = deptGroupPatientMapper.getDeptGroupPatinent(map);
//            if (null != deptGroupPatientY) {
//                deptGroupPatientMapper.deleteByPrimaryKey(deptGroupPatientY.getId());
//            }
//            //保存改变后的分组
//            deptGroupPatient.setIsBlacklist((byte) 1);
//            deptGroupPatient.setCreateTime(new Date());
//            deptGroupPatientMapper.insert(deptGroupPatient);
//        } catch (Exception e) {
//            appResult.setResultCode(ResultCode.EXCEPTION);
//        }
        return appResult;
    }

    @Override
    public AppResult addPatientGroup(String token,DeptGroupPatient deptGroupPatient) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        //获取当前医生信息
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentId", doctor.getDepartmentId());
        map.put("patientId", deptGroupPatient.getPatientId());
        DeptGroupPatient deptGroupPatientData = deptGroupPatientMapper.getDeptGroupPatinent(map);
        //如果存在就是修改
        if (null != deptGroupPatientData) {
            deptGroupPatientData.setRemark(deptGroupPatient.getRemark());
            deptGroupPatientData.setFlag(deptGroupPatient.getFlag());
            if(null!=deptGroupPatient.getDeptGroupId())
               deptGroupPatientData.setDeptGroupId(deptGroupPatient.getDeptGroupId());
            deptGroupPatientMapper.updateByPrimaryKey(deptGroupPatientData);
            return appResult;
        }
        deptGroupPatient.setIsBlacklist((byte)1);
        deptGroupPatient.setCreateTime(new Date());
        deptGroupPatientMapper.insertSelective(deptGroupPatient);
        return appResult;
    }

    @Override
    public AppResult queryGroupPatients(long deptGroupId) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        try {
            List<DeptGroupPatient> patientList = deptGroupPatientMapper.queryGroupPatients(deptGroupId);
            appResult.getBody().put("list", patientList);
        } catch (Exception e) {
            appResult.setResultCode(ResultCode.EXCEPTION);
        }
        return appResult;
    }

    @Override
    public AppResult queryDeptGroups(String token, Long deptId) {

        //获取当前医生信息
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        long id = (null == deptId ? doctor.getDepartmentId() : deptId);
        List<DeptGroupVo> deptGroups = deptGroupMapper.queryDeptGroups(id);
        DepartmentHospitalVO department = departmentMapper.selectByPrimaryKeyForApp(id);
        int totalCount = 0;
        if (deptGroups != null && deptGroups.size() > 0) {
            for (DeptGroupVo deptGroupVo : deptGroups) {
                totalCount = totalCount + Integer.valueOf(deptGroupVo.getPatientCount());
            }
        }
        //返回app结果
        appResult.getBody().put("list", deptGroups);
        if (department != null) {
            appResult.getBody().put("departmentName", department.getName());
            appResult.getBody().put("hospitalName", department.getHospital());
        } else {
            appResult.getBody().put("departmentName", "");
            appResult.getBody().put("hospitalName", "");
        }
//        appResult.getBody().put("totalCount", totalCount);
        return appResult;
    }

    @Override
    public AppResult queryDeptGroupPatients(Long deptId) {

        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);

        List<Map> maps = new ArrayList<>();
        List<DeptGroupVo> deptGroups = deptGroupMapper.queryDeptGroups(deptId);
        if (deptGroups != null && deptGroups.size() > 0) {
            for (DeptGroupVo deptGroupVo : deptGroups) {
//                if (2 == deptGroupVo.getIsDefault()) {
                    Map map = new HashMap();
                    map.put("groupName", deptGroupVo.getName());
                    List<DeptGroupPatinentsVo> deptGroupPatinents = deptGroupPatientMapper.getDeptGroupPatinents(deptGroupVo.getId());
                    map.put("patientCount", deptGroupPatinents.size());
                    map.put("patients", deptGroupPatinents);
                    maps.add(map);
//                }
            }
        }
        //返回app结果
        appResult.getBody().put("data", maps);
        return appResult;
    }




    @Override
    public AppResult deleteDeptGroups(long id) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);

        DeptGroup preDeptGroup = deptGroupMapper.selectByPrimaryKey(id);
        if (preDeptGroup != null) {
            DeptGroup deptGroup = deptGroupMapper.queryDefaultGroup(preDeptGroup.getDepartmentId());
            if (deptGroup != null) {
                deptGroupPatientMapper.updateDeptGroupId(deptGroup.getId(), id);
            }
        }
        deptGroupMapper.deleteByPrimaryKey(id);
        return appResult;
    }

    @Override
    public AppResult addPatientDefaultGroup(Long departmentId, Long patientId) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);

        DeptGroup deptGroup = deptGroupMapper.queryDefaultGroup(departmentId);
        if (deptGroup != null) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("departmentId", departmentId);
            map.put("patientId", patientId);
            DeptGroupPatient deptGroupPatient = deptGroupPatientMapper.getDeptGroupPatinent(map);
            if (null == deptGroupPatient) {
                deptGroupPatient = new DeptGroupPatient();
                deptGroupPatient.setRemark("");
                deptGroupPatient.setPatientId(patientId);
                deptGroupPatient.setDeptGroupId(deptGroup.getId());
                deptGroupPatient.setCreateTime(new Date());
                deptGroupPatientMapper.insertSelective(deptGroupPatient);
            }
        }
        return appResult;
    }

    @Override
    public AppResult updateDeptGroup(DeptGroup deptGroup) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        deptGroup.setUpdateTime(new Date());
        deptGroupMapper.updateByPrimaryKeySelective(deptGroup);
        return appResult;
    }

    @Override
    public AppResult addDeptGroup(String token, DeptGroup deptGroup) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);

        Date currentTime = new Date();
        deptGroup.setCreateTime(currentTime);
        deptGroup.setUpdateTime(currentTime);

        //查询名称 产品确认, 名称可重 不做限制
        // Map<String, Object> map = new HashMap<String, Object>();
        //map.put("name", deptGroup.getName().trim());
        //List<DeptGroup> deptGroups = deptGroupMapper.queryDeptGroupsByMap(map);
        //if (null != deptGroups && deptGroups.size() > 0) {
        //   appResult.setResultCode(ResultCode.NAMEEXIST_ALREADY);
        //   return appResult;
        // }
        if (null == deptGroup.getDepartmentId()) {
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            deptGroup.setDepartmentId(doctor.getDepartmentId());
        }
        deptGroupMapper.insertSelective(deptGroup);
        appResult.getBody().put("id", deptGroup.getId());
        return appResult;
    }

    @Override
    public AppResult queryDepartmentById(String token,long id) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        DepartmentHospitalVO department = departmentMapper.selectByPrimaryKeyForApp(id);

        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        if (null != doctor) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("departmentId", department.getId());
            map.put("doctorId", doctor.getId());
            DeptDoctor deptDoctor = deptDoctorMapper.getDeptDoctorByDepartIdAndDoctorId(map);
            if (null != deptDoctor) {
                department.setAdmin(String.valueOf(deptDoctor.getIsAdmin()));
            }

        }
        appResult.getBody().put("department", department);
        return appResult;
    }

    @Override
    public AppResult getDoctorDeptList(String token, long doctorId) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("department", departmentMapper.getDepartmentByDoctorId(doctorId));
        return appResult;
    }

    @Override
    public AppResult getDoctorByDepartmentIdForPatient(String token, Long departmentId,Integer pageIndex,Integer pageSize) {
        if (null == pageIndex || pageIndex == 0){
            pageIndex= Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize==0){
            pageSize=Constants.PAGESIZE;
        }
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Page page = PageHelper.startPage(pageIndex,pageSize);
        PageInfo<DoctorInfoVo> pageInfo = new PageInfo<DoctorInfoVo>(doctorMapper.getDoctorByDepartmentIdForPatient(departmentId));
        appResult.getBody().put("department", pageInfo.getList());
        return appResult;
    }

    @Override
    @Transactional
    public AppResult receiveTransferPatient(String token,Long appointmentId, byte state) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        //TODO 获取当前医生 id
        Doctor doctor = (Doctor) redisService.get("doctors", token);
        DialogAppointmentTransfer dialogAppointmentTransfer = dialogAppointmentTransferMapper.selectByPrimaryKey(appointmentId);
        //接诊
        if (dialogAppointmentTransfer != null) {
            String operName = "";
            if (null != dialogAppointmentTransfer.getOperatorDoctorId()) {
                //Doctor doctor = doctorMapper.selectByPrimaryKey(dialogAppointmentTransfer.getOperatorDoctorId());
                operName = doctor.getRealname();
            }
            //推送消息给转诊发起科室的所有医生
            List<Long> idList = departmentMapper.findDoctorIdsByDoctorId(dialogAppointmentTransfer.getLaunchDoctorId());
//                DepartmentVO toDepartment = departmentMapper.vewDepartmentRelateInfo(dialogAppointmentTransfer.getDepartmentId());
            DepartmentHospitalVO departmentHospitalVO = departmentMapper.selectDeptInfoForApp(dialogAppointmentTransfer.getDepartmentId());

            if (state == Constants.TRANS_CONFIRM) {
                //已被其他医生接诊
                if (dialogAppointmentTransfer != null && dialogAppointmentTransfer.getState() == Constants.TRANS_CONFIRM) {
                    appResult.setResultCode(ResultCode.SUCCESS);
                    appResult.getStatus().setMessage("该患者已被 " + operName + " 医生接诊");
                    return appResult;
                }
                //被其他医生拒绝
                if (dialogAppointmentTransfer != null && dialogAppointmentTransfer.getState() == Constants.TRANS_REFUSED) {
                    appResult.setResultCode(ResultCode.REFUSED);
                    appResult.getStatus().setMessage("该患者已被 " + operName + " 医生拒绝，确认接诊吗？");
                    return appResult;
                }
                dialogAppointmentTransfer.setState(state);
                //TODO 获取当前医生 id
                dialogAppointmentTransfer.setOperatorDoctorId(doctor.getId());
                dialogAppointmentTransferMapper.updateByPrimaryKeySelective(dialogAppointmentTransfer);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("departmentId", dialogAppointmentTransfer.getDepartmentId());
                map.put("patientId", dialogAppointmentTransfer.getPatientId());
                DeptGroupPatient deptGroupPatient = deptGroupPatientMapper.getDeptGroupPatinent(map);
                if (null == deptGroupPatient) {
                    deptGroupPatient = new DeptGroupPatient();
                    DeptGroup deptGroup = deptGroupMapper.queryDefaultGroup(dialogAppointmentTransfer.getDepartmentId());
                    deptGroupPatient.setPatientId(dialogAppointmentTransfer.getPatientId());
                    deptGroupPatient.setCreateTime(new Date());
                    deptGroupPatient.setDeptGroupId(deptGroup.getId());
                    deptGroupPatientMapper.insertSelective(deptGroupPatient);
                }
                DialogAppointmentNotice dialogAppointmentNotice = new DialogAppointmentNotice();
                dialogAppointmentNotice.setCreateTime(new Date());
                dialogAppointmentNotice.setType((byte) 2);
                dialogAppointmentNotice.setState(state);
                dialogAppointmentNotice.setDialogAppointmentId(dialogAppointmentTransfer.getId());
                dialogAppointmentNotice.setPatientId(dialogAppointmentTransfer.getPatientId());
                dialogAppointmentNotice.setDoctorId(doctor.getId());
                dialogAppointmentNotice.setDepartmentId(dialogAppointmentTransfer.getLaunchDepartmentId());
                //查询转诊科室信息
//                DepartmentHospitalVO departmentHospitalVO = departmentMapper.selectDeptInfoForApp(dialogAppointmentTransfer.getDepartmentId());
                dialogAppointmentNotice.setMessage("转诊成功:已转至" + departmentHospitalVO.getHospital() + departmentHospitalVO.getName());
                dialogAppointmentNoticeMapper.insertSelective(dialogAppointmentNotice);
                messageService.sendMessage(dialogAppointmentTransfer.getPatientId(), "2", "新健康", "转诊申请通过。");
                messageService.sendMessage(idList, "1", "新医疗", departmentHospitalVO.getHospital() + departmentHospitalVO.getName() + "拒绝了转诊");

                //推送xmpp消息
                XmppMessageBody body = new XmppMessageBody();
                body.setType("7");
                body.setAppointmentInfo("2:"+dialogAppointmentTransfer.getId()+":"+state);
                xmppMessageService.sendXmppMessage(null,null,"2"+dialogAppointmentTransfer.getPatientId()
                        ,dialogAppointmentTransfer.getDepartmentId(),dialogAppointmentTransfer.getLaunchDepartmentId(),body);

            } else if (state == Constants.TRANS_REFUSED) {//拒绝
                //已被其他医生接诊
                if (dialogAppointmentTransfer != null && dialogAppointmentTransfer.getState() == Constants.TRANS_CONFIRM) {
                    appResult.setResultCode(ResultCode.RECEIVED);
                    appResult.getStatus().setMessage("该患者已被 " + operName + " 医生接诊，不能拒绝！");
                    return appResult;
                }
                //已被其他医生拒绝
                if (dialogAppointmentTransfer != null && dialogAppointmentTransfer.getState() == Constants.TRANS_REFUSED) {
                    appResult.setResultCode(ResultCode.SUCCESS);
                    appResult.getStatus().setMessage("拒绝成功");
                    return appResult;
                }
                dialogAppointmentTransfer.setState(state);
                dialogAppointmentTransfer.setOperatorDoctorId(doctor.getId());
                dialogAppointmentTransferMapper.updateByPrimaryKeySelective(dialogAppointmentTransfer);
                DialogAppointmentNotice dialogAppointmentNotice = new DialogAppointmentNotice();
                dialogAppointmentNotice.setCreateTime(new Date());
                dialogAppointmentNotice.setType((byte) 2);
                dialogAppointmentNotice.setState(state);
                dialogAppointmentNotice.setDialogAppointmentId(dialogAppointmentTransfer.getId());
                dialogAppointmentNotice.setPatientId(dialogAppointmentTransfer.getPatientId());
                dialogAppointmentNotice.setDoctorId(dialogAppointmentTransfer.getLaunchDoctorId());
                dialogAppointmentNotice.setDepartmentId(dialogAppointmentTransfer.getDepartmentId());
                dialogAppointmentNotice.setMessage("转诊失败");
                dialogAppointmentNoticeMapper.insertSelective(dialogAppointmentNotice);
                //推送消息给患者
                messageService.sendMessage(dialogAppointmentTransfer.getPatientId(), "2", "新健康", "转诊申请未通过。");
                messageService.sendMessage(idList, "1", "新医疗", departmentHospitalVO.getHospital() + departmentHospitalVO.getName() + "拒绝了转诊");
                //推送消息给转诊发起科室的所有医生
//                messageService.sendToDeptOtherDoctorsMsg(doctor.getId(), doctor.getId(), "新医疗", "转诊申请未通过，" + toDepartment.getHospital() + "医院" + toDepartment.getName() + "科拒绝了转诊");
            }
        } else {
            appResult.getStatus().setMessage("转诊信息不存在");
            return appResult;
        }
        //推送xmpp消息
        XmppMessageBody body = new XmppMessageBody();
        body.setType("8");
        body.setAppointmentInfo("2:"+dialogAppointmentTransfer.getId()+":"+state);
        xmppMessageService.sendXmppMessage(null,null,"2"+dialogAppointmentTransfer.getPatientId()
                ,dialogAppointmentTransfer.getLaunchDepartmentId(),body);
        return appResult;
    }

    @Override
    @Transactional
    public AppResult confirmTransferPatient(String token, long appointmentId, byte state) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Doctor doctor = (Doctor) redisService.get("doctors", token);
        DialogAppointmentTransfer dialogAppointmentTransfer = dialogAppointmentTransferMapper.selectByPrimaryKey(appointmentId);
        if ((byte) 6 == state && (byte) 2 != dialogAppointmentTransfer.getState()) {
            return appResult;
        }
        if (dialogAppointmentTransfer != null) {
            DialogAppointmentTransfer dialogAppointmentTransfer1 = new DialogAppointmentTransfer();
            dialogAppointmentTransfer1.setId(appointmentId);
            dialogAppointmentTransfer1.setState(state);
            dialogAppointmentTransfer1.setOperatorDoctorId(doctor.getId());
            dialogAppointmentTransferMapper.updateByPrimaryKeySelective(dialogAppointmentTransfer1);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("departmentId", dialogAppointmentTransfer.getDepartmentId());
            map.put("patientId", dialogAppointmentTransfer.getPatientId());
            DeptGroupPatient deptGroupPatient = deptGroupPatientMapper.getDeptGroupPatinent(map);
            if (null == deptGroupPatient) {
                deptGroupPatient = new DeptGroupPatient();
                deptGroupPatient.setPatientId(dialogAppointmentTransfer.getPatientId());
                deptGroupPatient.setCreateTime(new Date());
                deptGroupPatient.setDeptGroupId( deptGroupMapper.queryDefaultGroup(dialogAppointmentTransfer.getDepartmentId()).getId());
                deptGroupPatientMapper.insertSelective(deptGroupPatient);
            }
            DeptPatientSign deptPatientSign = deptPatientSignMapper.selectByPatientIdAndDeptId(dialogAppointmentTransfer.getPatientId(), dialogAppointmentTransfer.getDepartmentId());
            if (null == deptPatientSign) {
                deptPatientSign.setPatientId(dialogAppointmentTransfer.getPatientId());
                deptPatientSign.setDepartmentId(dialogAppointmentTransfer.getDepartmentId());
                deptPatientSign.setCreateTime(new Date());
                deptPatientSign.setState((byte) 1);
                deptPatientSign.setSource((byte) 1);
                deptPatientSign.setOperator(doctor.getId() + "");
                deptPatientSign.setIsRepeat((byte) 1);
                deptPatientSign.setReceiveDoctor(doctor.getId() + "");
                deptPatientSignMapper.insertSelective(deptPatientSign);
            } else {
                deptPatientSign.setReceiveDoctor(doctor.getId()+"");
                deptPatientSign.setOperator(doctor.getId()+"");
                deptPatientSignMapper.updateByPrimaryKeySelective(deptPatientSign);
            }
            DialogAppointmentNotice dialogAppointmentNotice = new DialogAppointmentNotice();
            dialogAppointmentNotice.setCreateTime(new Date());
            dialogAppointmentNotice.setType((byte)2);
            dialogAppointmentNotice.setState((byte)2);
            dialogAppointmentNotice.setDialogAppointmentId(dialogAppointmentTransfer.getId());
            dialogAppointmentNotice.setPatientId(dialogAppointmentTransfer.getPatientId());
            dialogAppointmentNotice.setDoctorId(dialogAppointmentTransfer.getLaunchDoctorId());
            dialogAppointmentNotice.setDepartmentId(dialogAppointmentTransfer.getDepartmentId());
            //查询转诊科室信息
            DepartmentHospitalVO departmentHospitalVO = departmentMapper.selectDeptInfoForApp(dialogAppointmentTransfer.getDepartmentId());
            dialogAppointmentNotice.setMessage("转诊成功:已转至"+departmentHospitalVO.getHospital()+departmentHospitalVO.getName());
            dialogAppointmentNoticeMapper.insertSelective(dialogAppointmentNotice);
            //推送消息给患者
            messageService.sendMessage(dialogAppointmentTransfer.getPatientId(),"2","新健康","转诊申请已通过，可继续和科室医生沟通。");
            //推送消息给转诊发起科室的所有医生
            List<Long> idList = departmentMapper.findDoctorIdsByDoctorId(dialogAppointmentTransfer.getLaunchDoctorId());
            messageService.sendMessage(idList,"1","新医疗","患者成功转诊至"+departmentHospitalVO.getHospital()+departmentHospitalVO.getName());
            //推送xmpp消息
            XmppMessageBody body = new XmppMessageBody();
            body.setType("8");
            body.setAppointmentInfo("2:"+dialogAppointmentTransfer.getId()+":"+state);
            xmppMessageService.sendXmppMessage(null,null,"2"+dialogAppointmentTransfer.getPatientId()
                    ,dialogAppointmentTransfer.getDepartmentId(),dialogAppointmentTransfer.getLaunchDepartmentId(),body);
            appResult.setResultCode(ResultCode.SUCCESS);
            return appResult;
        }
        appResult.setResultCode(ResultCode.SUCCESS);
        return appResult;
    }

    /**
     * 患者端根据部门id获取科室医生列表
     * @param roomId 房间id
     * @param type   @查询类型 医生端1 患者端2
     * @param userId 谁发起的查询（用户id）
     * @return
     */
    @Override
    public AppResult getDoctorsByRoomId(final String roomId,final String type,final String userId) {
        AppResult appResult = new AppResult();
        try{
            /**
             * 患者端@查询的时候 查询的是当前科室的医生列表
             * 医生端@查询的时候 查询的是当前聊天室的用户 管理员 以及当前科室的医生列表（前提是type为1）
             */
            //查询聊天表
            Dialog dialog = dialogMapper.selectByRoomId(roomId);
            Long departmentId;  //科室id
            List<UserListVo> users = new ArrayList<>();
            List<UserListVo> doctors = new ArrayList<>();
            if(null != dialog){

                departmentId = dialog.getDepartmentId();
                doctors = doctorMapper.getDoctorListVoByDepartmentId(departmentId);
                //查询
                if(StringUtils.equals(type,"1")){
                    users.add(dialogMapper.getUserListVoByRoomId(roomId)); //将患者存入列表
                    for(int i = 0;i<doctors.size();i++){
                        if(StringUtils.equals(doctors.get(i).getIsAdmin(),"2")){
                            users.add(doctors.get(i));   //将管理员存入列表
                            UserListVo vo = doctors.get(i);
                            doctors.remove(i);
                        }
                    }
                }
                users.addAll(doctors);
            }
            appResult.setResultCode(ResultCode.SUCCESS);
            appResult.getBody().put("userLists",users);
            return appResult;
        }catch (Exception e){
            e.printStackTrace();
            appResult.getBody().put("userLists",new ArrayList<UserListVo>());
            appResult.setResultCode(ResultCode.FAILED);
            return appResult;
        }
    }
}
