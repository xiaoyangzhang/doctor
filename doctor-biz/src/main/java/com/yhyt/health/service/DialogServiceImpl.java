package com.yhyt.health.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.constant.Constants;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.*;
import com.yhyt.health.model.*;
import com.yhyt.health.model.vo.*;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.util.BusinessException;
import com.yhyt.health.util.DateUtil;
import com.yhyt.health.util.DialogRoomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月17日 下午4:14:42
 * 类说明
 */
@Service
public class DialogServiceImpl implements DialogService {
    @Autowired
    private DialogAppointmentMapper dialogAppointmentMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private DialogMapper dialogMapper;
    @Autowired
    private DialogAppointmentNoticeMapper dialogAppointmentNoticeMapper;
    @Autowired
    private DialogAppointmentTransferMapper dialogAppointmentTransferMapper;

    @Autowired
    @Qualifier("loadBalanced")
    private RestTemplate loadBalanced;

    @Autowired
    private DeptCooperationMapper deptCooperationMapper;
    @Autowired
    private DeptPatientSignMapper deptPatientSignMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private PatientCasearchivesMapper patientCasearchivesMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private ImService imService;
    @Autowired
    private XmppMessageService xmppMessageService;
    @Autowired
    private DeptGroupPatientMapper deptGroupPatientMapper;

    private static final Logger logger = LoggerFactory.getLogger(DialogServiceImpl.class);

    @Override
    public int insertDialogAppointment(DialogAppointment dialogAppointment) {
        dialogAppointment.setCreateTime(new Date());
        return dialogAppointmentMapper.insert(dialogAppointment);
    }

    @Override
    public DialogAppointment getDialogAppointmentById(Long id) {
        return dialogAppointmentMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public AppResult confirmDialogAppointments(String token, DialogAppointment dialogAppointment) {
        //返回app成功信息
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        DialogAppointment appointment = dialogAppointmentMapper.selectByPrimaryKey(dialogAppointment.getId());
        //如果修改的状态和数据库状态一致，无需修改
        if (appointment.getState() == dialogAppointment.getState()) {
            return appResult;
        }
        //患者
        Patientl patientl = patientMapper.selectPatientlById(appointment.getPatientIdAppointment());
        if (dialogAppointment.getState() == Constants.CONFIRM) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("departmentId", appointment.getDepartmentId());
            map.put("patientId", appointment.getPatientIdAppointment());
            DeptGroupPatient deptGroupPatient = deptGroupPatientMapper.getDeptGroupPatinent(map);
            if (null != deptGroupPatient && (byte)2==deptGroupPatient.getIsBlacklist()){
                appResult.setResultCode(ResultCode.FAILED);
                appResult.getStatus().setMessage("已被拉黑不能确认");
                return appResult;
            }



        }
//        //患者确认时添加确认
//        if (dialogAppointment.getState() == Constants.CONFIRM) {
//            //校验如果存在预约确认的
//            List<DialogAppointment> dialogAppointments = dialogAppointmentMapper.selectDialogAppointment(appointment.getDepartmentId(),
//                    appointment.getPatientIdAppointment(), appointment.getAppointmentTime(), appointment.getAmpm());
//            if (null != dialogAppointments && dialogAppointments.size() > 0) {
//                appResult.setResultCode(ResultCode.FAILED);
//                appResult.getStatus().setMessage("该时间段已经确认过了，不能再次确认");
//                return appResult;
//            }
//        }
        //获取当前用户
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        if (doctor == null) {
            if (null != appointment.getDoctorIdAppointment())
                doctor = doctorMapper.selectByPrimaryKey(appointment.getDoctorIdAppointment());
        }
        //修改预约表
        int update = dialogAppointmentMapper.updateByPrimaryKeySelective(dialogAppointment);
        //保存通知表
        DialogAppointmentNotice appointmentNotice = new DialogAppointmentNotice();
        if (null != doctor) {
            appointmentNotice.setDoctorId(doctor.getId());
        }
        appointmentNotice.setDepartmentId(appointment.getDepartmentId());
        appointmentNotice.setPatientId(appointment.getPatientIdAppointment());
        appointmentNotice.setDialogAppointmentId(appointment.getId());
        appointmentNotice.setCreateTime(new Date());
        appointmentNotice.setState((byte) 1);//预约
        //推送信息
        if (update > 0) {
            Map finMap = new HashMap();
            finMap.put("departmentId", appointment.getDepartmentId());
            finMap.put("patientId", appointment.getPatientIdAppointment());
            List<Dialog> dialogs = dialogMapper.findPersistableList(finMap);
            List<String> roomIds = new ArrayList<>();
            if (!dialogs.isEmpty()) {
                for (Dialog dialog : dialogs) {
                    roomIds.add(dialog.getRoomId());
                }
            }
            Byte state = dialogAppointment.getState();
            //1-待确认 2已确认 3-拒绝 4-患者取消 5-医生取消
            ImMessageVo imMessageVo = new ImMessageVo();
            String roomIdsJson= null;
            if (state == Constants.CONFIRM || state == Constants.REFUSED || state == Constants.PATIENT_CANCEL) {//如果是患者操作
//                Patientl patientl = patientMapper.selectPatientlById(appointment.getPatientIdAppointment());
                if (null != patientl) {
                    imMessageVo.setFromUserLogo(patientl.getHeadImage());
                    imMessageVo.setFromUserName(patientl.getRealname());
                    imMessageVo.setFromUserId(patientl.getId());
                    imMessageVo.setTimeSend(new Date().getTime());
                    imMessageVo.setType("0");
                    roomIdsJson  = JSONObject.toJSON(roomIds).toString();
                }
            } else{
                imMessageVo.setFromUserLogo(doctor.getHeadImage());
                imMessageVo.setFromUserName(doctor.getRealname());
                imMessageVo.setFromUserId(doctor.getId());
                imMessageVo.setTimeSend(new Date().getTime());
                imMessageVo.setType("0");
                roomIdsJson = JSONObject.toJSON(roomIds).toString();
            }
            Long doctorIdLaunch = appointment.getDoctorIdLaunch();
            Long doctorIdAppointment = appointment.getDoctorIdAppointment();
            XmppMessageBody body = new XmppMessageBody();
            body.setType("7");//预约
            body.setAppointmentInfo("1:"+appointment.getId()+":"+dialogAppointment.getState());
            body.setPatientId(appointment.getPatientIdAppointment());
            if (state == Constants.CONFIRM) {
                appointmentNotice.setMessage("预约成功:" + DateUtil.convert2String(appointment.getAppointmentTime(), "yyyy-MM-dd") + (appointment.getAmpm() == Byte.valueOf("1") ? "上午" : "下午"));
                messageService.sendToDeptOtherDoctorsMsg(appointment.getDepartmentId(), null, "新医疗", "患者接受预约！","6");
                if (!roomIds.isEmpty()) {
                    imMessageVo.setContent("患者同意预约");
                    Object msg = JSONObject.toJSON(imMessageVo);
                    imService.sendGroupMessage(null, msg.toString(), roomIdsJson);
                }
            } else if (state == Constants.REFUSED) {
                appResult.getStatus().setMessage("拒绝成功");
                appointmentNotice.setMessage("预约失败");
                messageService.sendToDeptOtherDoctorsMsg(appointment.getDepartmentId(), null, "新医疗", "患者拒绝预约！","6");
//                if (doctorIdAppointment != null) {
//                    messageService.sendMessage(doctorIdAppointment, "1", "新医疗", "患者拒绝预约！");
//                }
                if (!roomIds.isEmpty()) {
                    imMessageVo.setContent("患者拒绝预约");
                    Object msg = JSONObject.toJSON(imMessageVo);
                    imService.sendGroupMessage(null, msg.toString(), roomIdsJson);
                }
            } else if (state == Constants.PATIENT_CANCEL) {
                appointmentNotice.setMessage("预约已取消");
                messageService.sendToDeptOtherDoctorsMsg(appointment.getDepartmentId(), null, "新医疗", "患者取消预约！","6");
//                if (doctorIdAppointment != null) {
//                    messageService.sendMessage(doctorIdAppointment, "1", "新医疗", "患者取消预约！");
//                }
                if (!roomIds.isEmpty()) {
                    imMessageVo.setContent("患者取消预约");
                    Object msg = JSONObject.toJSON(imMessageVo);
                    imService.sendGroupMessage(null, msg.toString(), roomIdsJson);
                }
            } else if (state == Constants.DOCTOR_CANCEL) {
                appResult.getStatus().setMessage("取消成功");
                appointmentNotice.setMessage("预约已取消");
                messageService.sendMessage(appointment.getPatientIdAppointment(), "2", "新健康", "预约已取消，请及时查看！","6");
//                messageService.sendMessage(doctorIdLaunch, "1", "新医疗", "预约已取消，请及时查看！");
                if (!roomIds.isEmpty()) {
                    imMessageVo.setContent("" + doctor.getRealname() + "取消了预约");
                    Object msg = JSONObject.toJSON(imMessageVo);
                    imService.sendGroupMessage(null, msg.toString(), roomIdsJson);
                }
            }
            //发送xmpp消息
            xmppMessageService.sendXmppMessage(null,null,
                    "2"+appointment.getPatientIdAppointment(),appointment.getDepartmentId(),body);
        }
        appointmentNotice.setType((byte) 1);
        dialogAppointmentNoticeMapper.insertSelective(appointmentNotice);
        return appResult;
    }

    @Override
    public int insertDialogAppointmentTransfer(DialogAppointmentTransfer appointmentTransfer) {

        //根据科室id查询对应医院
        Department department = departmentMapper.selectByPrimaryKey(appointmentTransfer.getDepartmentId());
        appointmentTransfer.setHospitalId(department.getHospitalId());
        appointmentTransfer.setCreateTime(new Date());
        return dialogAppointmentTransferMapper.insert(appointmentTransfer);
    }

    @Override
    public DialogAppointmentTransfer getDialogAppointmentTransferById(Long id) {
        return dialogAppointmentTransferMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public AppResult confirmDialogAppointmentTransfers(String token, DialogAppointmentTransfer appointmentTransfer) {
        //获取当前用户
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        if (null == doctor) {
            doctor = new Doctor();
        }
        appointmentTransfer.setOperatorDoctorId(doctor.getId());
        dialogAppointmentTransferMapper.updateByPrimaryKeySelective(appointmentTransfer);
        //保存通知表
        DialogAppointmentTransfer dialogAppointmentTransfer = dialogAppointmentTransferMapper.selectByPrimaryKey(appointmentTransfer.getId());
        DialogAppointmentNotice appointmentNotice = new DialogAppointmentNotice();
        //查询发起预约医生的信息
        Doctor doctor1 = doctorMapper.selectByPrimaryKey(dialogAppointmentTransfer.getLaunchDoctorId());
        appointmentNotice.setDoctorId(dialogAppointmentTransfer.getLaunchDoctorId());
        appointmentNotice.setDepartmentId(dialogAppointmentTransfer.getLaunchDepartmentId());
        appointmentNotice.setPatientId(dialogAppointmentTransfer.getPatientId());
        appointmentNotice.setDialogAppointmentId(dialogAppointmentTransfer.getId());
        appointmentNotice.setIsDoctorShow("1");
        appointmentNotice.setIsPatientShow("1");
        appointmentNotice.setCreateTime(new Date());
        appointmentNotice.setState((byte) 1);//转诊
        appointmentNotice.setType((byte)1);
//        appointmentNotice.setMessage("处理中");
        //2-患者同意 3-患者拒绝 4-医生同意 5-医生拒绝
        if(appointmentTransfer.getState()==3){
            appointmentNotice.setIsDoctorShow("1");
            appointmentNotice.setIsPatientShow("1");
            appointmentNotice.setMessage("患者拒绝转诊");
            dialogAppointmentNoticeMapper.insertSelective(appointmentNotice);
           // messageService.sendToDeptOtherDoctorsMsg(dialogAppointmentTransfer.getDepartmentId(),null,"新医疗","患者拒绝转诊");
            messageService.sendToDeptOtherDoctorsMsg(doctor1.getDepartmentId(),null,"新医疗","患者拒绝转诊","5");

            messageService.sendMessage(dialogAppointmentTransfer.getPatientId(),"2","新健康","患者拒绝转诊","5");
        }else if (appointmentTransfer.getState()==2){
            appointmentNotice.setIsDoctorShow("1");
            appointmentNotice.setIsPatientShow("1");
            appointmentNotice.setMessage("患者同意转诊");
            dialogAppointmentNoticeMapper.insertSelective(appointmentNotice);
            //转诊科室
            messageService.sendToDeptOtherDoctorsMsg(dialogAppointmentTransfer.getDepartmentId(),null,"新医疗","患者同意转诊","5");
            //发起科室
            messageService.sendToDeptOtherDoctorsMsg(doctor1.getDepartmentId(),null,"新医疗","患者同意转诊","5");
            //转诊患者
            messageService.sendMessage(dialogAppointmentTransfer.getPatientId(),"2","新健康","患者同意转诊","5");
            //创建房间 添加token
            Dialog dialog = dialogMapper.queryBydeptIdAndpatientId(dialogAppointmentTransfer.getDepartmentId(), dialogAppointmentTransfer.getPatientId());
            //如果不存在聊天室则新建
            dialog = DialogRoomUtil.createDialogRoom(token, dialogAppointmentTransfer.getDepartmentId(), dialogAppointmentTransfer.getPatientId());
        }
        //xmpp消息
        XmppMessageBody body = new XmppMessageBody();
        body.setType("7");
        body.setPatientId(dialogAppointmentTransfer.getPatientId());
        body.setAppointmentInfo("2:"+dialogAppointmentTransfer.getId()+":"+dialogAppointmentTransfer.getState());
        //推送消息
        Patientl patientl = patientMapper.selectPatientlById(dialogAppointmentTransfer.getPatientId());
        Dialog dialog = dialogMapper.queryBydeptIdAndpatientId(dialogAppointmentTransfer.getLaunchDepartmentId(), dialogAppointmentTransfer.getPatientId());
        ImMessageVo imMessageVo = new ImMessageVo();
        imMessageVo.setFromUserLogo(patientl.getHeadImage());
        imMessageVo.setFromUserName(patientl.getRealname());
        imMessageVo.setFromUserId(patientl.getId());
        imMessageVo.setTimeSend(new Date().getTime());
        imMessageVo.setType("0");
        JSONArray roomIds = new JSONArray();
        roomIds.add(dialog.getRoomId());
        if(appointmentTransfer.getState()==3){
            imMessageVo.setContent("患者拒绝转诊");
            imService.sendGroupMessage(null,JSON.toJSONString(imMessageVo), roomIds.toJSONString());
            xmppMessageService.sendXmppMessage(null,null,"2"+dialogAppointmentTransfer.getPatientId()
            ,dialogAppointmentTransfer.getLaunchDepartmentId(),body);
        } else if (appointmentTransfer.getState() == 2) {
            imMessageVo.setContent("患者同意转诊");
            imService.sendGroupMessage(null,JSON.toJSONString(imMessageVo),roomIds.toJSONString());
            xmppMessageService.sendXmppMessage(null,null,"2"+dialogAppointmentTransfer.getPatientId()
                    ,dialogAppointmentTransfer.getDepartmentId()
                    ,dialogAppointmentTransfer.getLaunchDepartmentId(),body);
        }
        //返回app信息
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        return appResult;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public AppResult addDialogSign(String token, DeptPatientSign deptPatientSign, long appointmentId) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        //如果科室为空，取token的科室
        if (null == deptPatientSign.getDepartmentId()) {
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            deptPatientSign.setDepartmentId(doctor.getDepartmentId());
        }
        //预约信息改为已签到
        DialogAppointment dialogAppointment = new DialogAppointment();
        dialogAppointment.setId(appointmentId);
        dialogAppointment.setSignState((byte) 2);
        dialogAppointmentMapper.updateByPrimaryKeySelective(dialogAppointment);
        //查看患者是否在此科室签过到
        DialogAppointment dialogAppointment1 = dialogAppointmentMapper.selectByPrimaryKey(appointmentId);
        //查询签到表
        DeptPatientSign deptPatientSign1 = deptPatientSignMapper.selectByPatientIdAndDeptId(deptPatientSign.getPatientId(), deptPatientSign.getDepartmentId());
        //如果没有签过到
        if (null == deptPatientSign1) {
            deptPatientSign.setState((byte) 2);
            deptPatientSign.setCreateTime(new Date());
            deptPatientSign.setReviewTime(new Date());
            deptPatientSign.setDiagnoseDate(dialogAppointment1.getAppointmentTime());
            deptPatientSign.setIsRepeat((byte) 1);
            deptPatientSign.setPatientId(dialogAppointment1.getPatientIdAppointment());
            deptPatientSignMapper.insertSelective(deptPatientSign);
        } else if (!"2".equals(String.valueOf(deptPatientSign1.getState()))) {
            deptPatientSign1.setState((byte) 2);
            deptPatientSign1.setIsRepeat((byte) 2);//复诊
            deptPatientSign1.setReviewTime(new Date());
            deptPatientSignMapper.updateByPrimaryKeySelective(deptPatientSign1);
        } else {
            return appResult;
        }
        //查询患者是否已认证
        Patientl patientl = patientMapper.selectPatientlById(dialogAppointment1.getPatientIdAppointment());
        if (null == patientl.getIsauthenticated() || !"2".equals(patientl.getIsauthenticated())) {
            appResult = loadBalanced.postForObject(doctorConstant.getIm().get("givecardforfree")+"?isReceipt=false&patientId={1}", null, AppResult.class, dialogAppointment1.getPatientIdAppointment());
        }
        //查询对话信息
        Dialog dialog = dialogMapper.queryBydeptIdAndpatientId(deptPatientSign.getDepartmentId(), deptPatientSign.getPatientId());
        if (null != dialog) {
            //发送灰条提示
            ImMessageVo imMessageVo = new ImMessageVo();
            String roomIdsJson= null;
            imMessageVo.setFromUserLogo(patientl.getHeadImage());
            imMessageVo.setFromUserName(patientl.getRealname());
            imMessageVo.setFromUserId(patientl.getId());
            imMessageVo.setTimeSend(new Date().getTime());
            imMessageVo.setType("0");
            JSONArray roomIds = new JSONArray();
            roomIds.add(dialog.getRoomId());
            roomIdsJson  = JSONObject.toJSON(roomIds).toString();
            if (!roomIds.isEmpty()) {
                imMessageVo.setContent("预约签到成功");
                Object msg = JSONObject.toJSON(imMessageVo);
                imService.sendGroupMessage(null, msg.toString(), roomIdsJson);
            }
        }
        //患者改为已认证
        patientl.setId(dialogAppointment1.getPatientIdAppointment());
        patientl.setIsauthenticated("2");//已认证
        patientMapper.updatePatientIsauthenticated(patientl);

        //推送xmpp消息
        XmppMessageBody body = new XmppMessageBody();
        body.setType("9");
        xmppMessageService.sendXmppMessage(null,null,"2"+dialogAppointment1.getPatientIdAppointment()
                ,deptPatientSign.getDepartmentId(),body);
        //返回app信息
        return appResult;
    }

    @Override
    @Transactional
    public AppResult addDialogAppointments(String token, DialogAppointment appointment, String roomId) {
        //查询聊天表
        Dialog dialog = dialogMapper.selectByRoomId(roomId);
        //返回app信息
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        //校验如果存在预约确认的
        List<DialogAppointment> dialogAppointments = dialogAppointmentMapper.selectDialogAppointment(dialog.getDepartmentId(),
                dialog.getPatientId(), appointment.getAppointmentTime(), appointment.getAmpm());
        if (null != dialogAppointments && dialogAppointments.size() > 0) {
            appResult.setResultCode(ResultCode.FAILED);
            appResult.getStatus().setMessage("该时段已发送预约，无需重复预约");
            return appResult;
        }
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        appointment.setCreateTime(new Date());
        appointment.setDepartmentId(dialog.getDepartmentId());
        appointment.setDialogDetailId(dialog.getId());
        appointment.setSignState((byte) 1);
        appointment.setHospitalId(dialog.getHospitalId());
        appointment.setDoctorIdLaunch(doctor.getId());
        appointment.setPatientIdAppointment(dialog.getPatientId());
        int insert = dialogAppointmentMapper.insert(appointment);
        if (insert > 0) {
            DepartmentHospitalVO departmentHospitalVO = departmentMapper.selectDeptInfoForApp(dialog.getDepartmentId());
            Date appointmentTime = appointment.getAppointmentTime();
            String appointDate = null;
            if (appointmentTime.getYear() == Calendar.getInstance().get(Calendar.YEAR)) {
                appointDate = DateUtil.convert2String(appointmentTime, "MM'月'dd'日'");
            } else {
                appointDate = DateUtil.convert2String(appointmentTime, "yyyy'年'MM'月'dd'日'");

            }
            String message = "预约通知:" + departmentHospitalVO.getHospital() + "" + departmentHospitalVO.getName() + "预约您" + appointDate + (appointment.getAmpm() == 1 ? "上午" : "下午") + "就诊，请及时确认";
            messageService.sendMessage(dialog.getPatientId(), "2", "新健康", message);

//            if (appointment.getDoctorIdAppointment() != null) {
//                messageService.sendMessage(appointment.getDoctorIdAppointment(), "1", "新医疗", message);
//
//            }
//            if (appointment.getDoctorIdLaunch() != null) {
//                messageService.sendMessage(appointment.getDoctorIdLaunch(), "1", "新医疗", message);
//
//            }
        }
        //保存通知表
        DialogAppointmentNotice appointmentNotice = new DialogAppointmentNotice();
        appointmentNotice.setDoctorId(doctor.getId());
        appointmentNotice.setPatientId(dialog.getPatientId());
        appointmentNotice.setDialogAppointmentId(appointment.getId());
        appointmentNotice.setCreateTime(new Date());
        appointmentNotice.setState((byte) 1);//预约
        appointmentNotice.setMessage("发起预约");
        appointmentNotice.setType((byte) 1);
        appointmentNotice.setIsDoctorShow("1");//默认展示
        appointmentNotice.setIsPatientShow("1");//默认展示
        appointmentNotice.setCreateTime(new Date());
        appointmentNotice.setDepartmentId(doctor.getDepartmentId());
        dialogAppointmentNoticeMapper.insertSelective(appointmentNotice);

//        ImMessageVo imMessageVo = new ImMessageVo();
//        imMessageVo.setFromUserLogo(doctor.getHeadImage());
//        imMessageVo.setFromUserName(doctor.getRealname());
//        imMessageVo.setFromUserId(doctor.getId());
//        imMessageVo.setTimeSend(new Date().getTime());
//        imMessageVo.setType("0");
//        imMessageVo.setContent("发起预约");
//        Object msg = JSONObject.toJSON(imMessageVo);
//
//        List<String> arrs = new ArrayList<>();
//        arrs.add(roomId);
//        imService.sendGroupMessage(null, msg.toString(), JSONObject.toJSON(arrs).toString());

        appResult.getBody().put("id", appointment.getId());
        appResult.getStatus().setMessage("发起预约成功");
        return appResult;
    }


    @Override
    public AppResult getDialogAppointment(String token, long id) {
        //返回app信息
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        //查询预约信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        DialogAppointmentVo dialogAppointmentVo = dialogAppointmentMapper.getDialogAppointment(map);
        //查询聊天表
        /*DialogDetail dialogDetail = dialogDetailMapper.selectByPrimaryKey(dialogAppointmentVo.getDialogDetailId());
        Dialog dialog=dialogMapper.selectByPrimaryKey(dialogDetail.getDialogId());*/

        //调用患者详情服务
         /*AppResult appResultPatinet = restTemplate.getForObject("http://patient01/patient/user?patientid="+dialog.getPatientId(), AppResult.class);
         JSONObject jsonObject = (JSONObject)JSONObject.toJSON(appResultPatinet.getBody().get("user"));
	     dialogAppointmentVo.setPatientId(dialog.getPatientId());
	     dialogAppointmentVo.setPatientName(jsonObject.get("realname").toString());*/
        appResult.getBody().put("dialogAppointment", dialogAppointmentVo);
        return appResult;
    }

    @Override
    @Transactional
    public AppResult addDialogAppointmentTransfers(String token, DialogAppointmentTransfer appointmentTransfer, String roomId) {
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        //返回app信息
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        //查询是否转诊过
        List<DialogAppointmentTransfer> dialogAppointmentTransfers =
                dialogAppointmentTransferMapper.getDialogTransferByDeptIdAndPatientId(appointmentTransfer.getDepartmentId(), appointmentTransfer.getPatientId(),doctor.getDepartmentId());
        if (null != dialogAppointmentTransfers && dialogAppointmentTransfers.size() > 0) {
            appResult.setResultCode(ResultCode.FAILED);
            appResult.getStatus().setMessage("已为患者提交转诊申请,无需重复提交");
            return appResult;
        }
        //保存转诊信息
        appointmentTransfer.setCreateTime(new Date());
        appointmentTransfer.setSignState((byte) 1);
        //查询科室信息
        DepartmentHospitalVO departmentHospitalVO = departmentMapper.selectByPrimaryKeyForApp(appointmentTransfer.getDepartmentId());
        appointmentTransfer.setHospitalId(departmentHospitalVO.getHospitalId());
        appointmentTransfer.setLaunchDepartmentId(doctor.getDepartmentId());
        dialogAppointmentTransferMapper.insert(appointmentTransfer);


        //保存通知表
        DialogAppointmentNotice appointmentNotice = new DialogAppointmentNotice();
        appointmentNotice.setDoctorId(doctor.getId());
        appointmentNotice.setDepartmentId(doctor.getDepartmentId());
        appointmentNotice.setPatientId(appointmentTransfer.getPatientId());
        appointmentNotice.setDialogAppointmentId(appointmentTransfer.getId());
        appointmentNotice.setCreateTime(new Date());
        appointmentNotice.setState((byte) 2);//转诊
        appointmentNotice.setMessage("发起转诊");
        appointmentNotice.setType((byte) 1);
        dialogAppointmentNoticeMapper.insertSelective(appointmentNotice);
        appResult.getBody().put("id", appointmentTransfer.getId());
        //创建患者详情表
        PatientCasearchives patientCasearchives = patientCasearchivesMapper.selectByPatientAndDept(appointmentTransfer.getDepartmentId(), appointmentTransfer.getPatientId());
        if (null == patientCasearchives) {
            patientCasearchives = new PatientCasearchives();
            patientCasearchives.setCreateTime(new Date());
            patientCasearchives.setDepartmentId(appointmentTransfer.getDepartmentId());
            patientCasearchives.setPatientId(appointmentTransfer.getPatientId());
            patientCasearchives.setUpdateTime(new Date());
            patientCasearchivesMapper.insert(patientCasearchives);
        }
        DepartmentVO fromDepartment = departmentMapper.vewDepartmentRelateInfo(doctor.getDepartmentId());
        DepartmentVO toDepartment = departmentMapper.vewDepartmentRelateInfo(appointmentTransfer.getDepartmentId());
        messageService.sendMessage(appointmentTransfer.getPatientId(), "2", "新医疗", ""
                + fromDepartment.getHospital() + "医院"
                + fromDepartment.getName() + "科帮您转诊至"
                + toDepartment.getHospital() + "医院"
                + toDepartment.getName() + "科，请及时确认");


//        ImMessageVo imMessageVo = new ImMessageVo();
//        imMessageVo.setFromUserLogo(doctor.getHeadImage());
//        imMessageVo.setFromUserName(doctor.getRealname());
//        imMessageVo.setFromUserId(doctor.getId());
//        imMessageVo.setTimeSend(new Date().getTime());
//        imMessageVo.setType("0");
//        imMessageVo.setContent("患者已成功转至" + toDepartment.getHospital() + "医院" + toDepartment.getName() + "科");
//        Object msg = JSONObject.toJSON(imMessageVo);

//        List<String> arrs = new ArrayList<>();
//        arrs.add(roomId);
//        imService.sendGroupMessage(null, msg.toString(), JSONObject.toJSON(arrs).toString());

        return appResult;
    }

    @Override
    public AppResult getDoctorAppointments(String token, Long appointmentTime) {
        AppResult appResult = new AppResult();
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentId", doctor.getDepartmentId());
        if (null != appointmentTime)
            map.put("appointmentTime", DateUtil.timestampToDate(appointmentTime));
        else
            map.put("appointmentTime", DateUtil.setStartDay(new Date()));
        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("list", dialogAppointmentMapper.getDoctorAppointments(map));
        return appResult;
    }

    @Override
    public AppResult getDepartmentTransfers(String token) {
        AppResult appResult = new AppResult();
        //获取当前医生
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        //获取当前执业单位
        Department department = departmentMapper.selectByPrimaryKey(doctor.getDepartmentId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("departmentId", department.getId());
        map.put("hospitalId", department.getHospitalId());
        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("list", departmentMapper.getDepartmentTransfers(map));
        String[] stateStr = new String[]{"3"};
        map.put("states", stateStr);
        map.put("notHospitalId", department.getHospitalId());
        appResult.getBody().put("cooperList", deptCooperationMapper.getDeptCooperations(map));
        return appResult;
    }

    @Override
    @Transactional
    public AppResult getDialogAppointmentTransfers(String token, DialogAppointmentTransfer appointmentTransfer) {
        AppResult appResult = new AppResult();
        //获取当前医生
        /*Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", appointmentTransfer.getId());
        DialogAppointmentTransferVo appointmentTransferVo = dialogAppointmentTransferMapper.getDialogAppointmentTransfers(map);
        //得到预约医生信息
        DepartmentHospitalVO departmentHospitalVO = departmentMapper.selectByPrimaryKeyForApp(appointmentTransferVo.getLaunchDepartmentId());
//        DoctorAppInfoVo doctorInfoVo = doctorMapper.selectDoctorForAppById(appointmentTransferVo.getLaunchDoctorId());
        appointmentTransferVo.setLaunchDepartmentName(departmentHospitalVO.getName());
        appointmentTransferVo.setLaunchHospitalName(departmentHospitalVO.getHospital());
        appointmentTransferVo.setLaunchHospitalLogo(departmentHospitalVO.getHospitalLogo());
        appointmentTransferVo.setLaunchDepartmentLogo(departmentHospitalVO.getLogo());

        appResult.setResultCode(ResultCode.SUCCESS);
        appResult.getBody().put("transfer", appointmentTransferVo);
        return appResult;
    }

    @Override
    @Transactional
    public AppResult queryUndiagnoseList(String token, Map<String, Object> map, Integer pageIndex, Integer pageSize) {
        if (null == pageIndex || pageIndex == 0) {
            pageIndex = Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize == 0) {
            pageSize = Constants.PAGESIZE;
        }
        //如果科室为空
        if (null == map.get("departmentId")) {
            //获取当前医生
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            map.put("departmentId", doctor.getDepartmentId());
        }
        //如果预约时间为空,为查询当前时间
        if (null == map.get("appointmentTime")) {
            map.put("appointmentTime", DateUtil.setStartDay(new Date()));
        } else {
            map.put("appointmentTime", DateUtil.timestampToDate((Long) map.get("appointmentTime")));
        }
        //查询已确认的
        map.put("state", (byte) 2);
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Page page = PageHelper.startPage(pageIndex, pageSize);
        PageInfo<AppointmentPatientVo> pageInfo = new PageInfo<AppointmentPatientVo>(dialogAppointmentMapper.queryUndiagnoseList(map));
        appResult.getBody().put("list", pageInfo.getList());
        map.put("ampm", "1");
        appResult.getBody().put("amCount", dialogAppointmentMapper.queryUndiagnoseListCount(map));
        map.put("ampm", "2");
        appResult.getBody().put("pmCount", dialogAppointmentMapper.queryUndiagnoseListCount(map));
        return appResult;
    }

    @Override
    @Transactional
    public AppResult queryUndiagnoseListDoctor(String token, Map<String, Object> map, Integer pageIndex, Integer pageSize) {
        if (null == pageIndex || pageIndex == 0) {
            pageIndex = Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize == 0) {
            pageSize = Constants.PAGESIZE;
        }
        //如果科室为空
        if (null == map.get("departmentId")) {
            //获取当前医生
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            map.put("departmentId", doctor.getDepartmentId());
        }
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        map.put("state", (byte) 2);//查询已确认的
        if (null != map.get("appointmentTime"))
            map.put("appointmentTime", DateUtil.setStartDay(DateUtil.timestampToDate((Long) map.get("appointmentTime"))));
        else
            map.put("appointmentTime", DateUtil.setStartDay(new Date()));
        Page page = PageHelper.startPage(pageIndex, pageSize);
        PageInfo<AppointmentPatientVo> pageInfo = new PageInfo<AppointmentPatientVo>(dialogAppointmentMapper.queryUndiagnoseListByDoctor(map));
        appResult.getBody().put("list", pageInfo.getList());
        map.put("ampm", "1");
        appResult.getBody().put("amCount", dialogAppointmentMapper.queryUndiagnoseByDoctorListCount(map));
        map.put("ampm", "2");
        appResult.getBody().put("pmCount", dialogAppointmentMapper.queryUndiagnoseByDoctorListCount(map));
        return appResult;
    }

    @Override
    public AppResult queryOverdueUndiagnoseList(String token, Map<String, Object> map, Integer pageIndex, Integer pageSize) {
        if (null == pageIndex || pageIndex == 0) {
            pageIndex = Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize == 0) {
            pageSize = Constants.PAGESIZE;
        }
//        map.put("appointmentTime", DateUtil.setStartDay(new Date()));
        map.put("state", (byte) 6);
        //如果科室为空
        if (null == map.get("departmentId")) {
            //获取当前医生
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            map.put("departmentId", doctor.getDepartmentId());
        }
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);

        Page page = PageHelper.startPage(pageIndex, pageSize);
        PageInfo<OverdueUndiagnoseVo> pageInfo = new PageInfo<OverdueUndiagnoseVo>(dialogAppointmentMapper.queryOverdueUndiagnoseList(map));
        appResult.getBody().put("list", pageInfo.getList());
        return appResult;
    }

    @Override
    public AppResult querypatientRecords(String token, Long patientId, Integer pageIndex, Integer pageSize) {
        if (null == pageIndex || pageIndex == 0) {
            pageIndex = Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize == 0) {
            pageSize = Constants.PAGESIZE;
        }
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        Map<String, Object> map = new HashMap<>();
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Dialog dialog = dialogMapper.queryBydeptIdAndpatientId(doctor.getDepartmentId(), patientId);
        if (null == dialog) {
            return appResult;
        }
        map.put("dialogId", dialog.getId());
        map.put("patientId", patientId);
        Page page = PageHelper.startPage(pageIndex, pageSize);
        PageInfo<DialogRecordVo> pageInfo = new PageInfo<DialogRecordVo>(dialogMapper.querypatientRecords(map));
        appResult.getBody().put("list", pageInfo.getList());
        return appResult;
    }

    @Override
    @Transactional
    public AppResult dismissAssignDoctor(String token, Long appointmentId) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        DialogAppointment dialogAppointment = dialogAppointmentMapper.selectByPrimaryKey(appointmentId);
        Long doctorIdAppointment =dialogAppointment.getDoctorIdAppointment();
        if (null == doctorIdAppointment) {
            return appResult;
        }
        //如果已签到不允许撤销分诊
        if ("2".equals(String.valueOf(dialogAppointment.getSignState()))) {
            appResult.setResultCode(ResultCode.ALREAD_SIGN);
            return appResult;
        }
        if (null != dialogAppointment) {
            dialogAppointment.setDoctorIdAppointment(null);
            dialogAppointmentMapper.updateByPrimaryKey(dialogAppointment);
        }
        //发推送给科室所有的医生
        Doctor doctorAppointment = doctorMapper.selectByPrimaryKey(doctorIdAppointment);
        messageService.sendToDeptOtherDoctorsMsg(dialogAppointment.getDepartmentId(), null, "新医疗", doctorAppointment.getRealname()+"医生撤销了分诊，请为患者重新预约医生","6");
        return appResult;
    }

    @Override
    public AppResult queryAppointsCountByDate(String token,Long departmentId, Date beginTime, Date endTime, Long doctorId) {
        AppResult appResult = new AppResult();
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
//        if (doctorId == null || doctorId == 0) {
//            appResult.setResultCode(ResultCode.FAILED);
//            appResult.getBody().put("msg", "参数异常");
//            return appResult;
//        }
        if (departmentId == null || departmentId == 0) {
            departmentId = doctor.getDepartmentId();
        }
        if (beginTime == null || endTime == null) {
            appResult.setResultCode(ResultCode.FAILED);
            appResult.getBody().put("msg", "参数异常");
            return appResult;
        }
        Map parms = new HashMap();
        parms.put("departmentId", departmentId);
        parms.put("beginTime", beginTime);
        parms.put("endTime", endTime);
        parms.put("doctorId", doctorId);
        List<AppointCountsVo> appointCountsVos = dialogAppointmentMapper.queryAppointsCountByDate(parms);
        appResult.setResultCode(ResultCode.SUCCESS);
        Map map = new HashMap();
        map.put("data", appointCountsVos);
        appResult.setBody(map);
        return appResult;
    }

    @Override
    public AppResult searchyUndiagnoseAllpatient(String token, String key, Long departmentId) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (null == departmentId) {
            //获取当前医生
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            map.put("departmentId", doctor.getDepartmentId());
        } else {
            map.put("departmentId", departmentId);
        }
        map.put("key", key);
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        map.put("state", (byte) 2);//查询已确认的
        map.put("appointmentTime", DateUtil.setStartDay(new Date()));
        appResult.getBody().put("list", dialogAppointmentMapper.searchyUndiagnoseAllpatient(map));
        return appResult;
    }

    @Override
    public AppResult getMyPatientDialog(String token, Long patientId,String type,Integer pageIndex,Integer pageSize) {
        if (null == pageIndex || pageIndex == 0) {
            pageIndex = Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize == 0) {
            pageSize = Constants.PAGESIZE;
        }
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        List<Byte> states = new ArrayList<Byte>();
        Byte signState = (byte)1;
        Date appointmentTimeBegin =null;
        Date appointmentTimeEnd =null;
        if ("1".equals(type)) {
            states.add(Constants.NO_CONFIRM);
            signState = Constants.NOSIGN;
        } else if ("2".equals(type)) {
            states.add(Constants.CONFIRM);
            states.add(Constants.DEALING);
            signState = Constants.NOSIGN;
            appointmentTimeBegin =DateUtil.setStartDay(new Date());
        }else if ("3".equals(type)) {
            signState = Constants.NOSIGN;
            states.add(Constants.CONFIRM);
            states.add(Constants.DEALING);
            appointmentTimeEnd =DateUtil.addDays(DateUtil.setStartDay(new Date()),-1);
        } else if ("4".equals(type)) {
            signState = Constants.SIGNED;
        } else if ("5".equals(type)) {
            states.add(Constants.REFUSED);
            states.add(Constants.DOCTOR_CANCEL);
        }
        Page page = PageHelper.startPage(pageIndex, pageSize);
        PageInfo<PatientDialogVo> pageInfo = new PageInfo<PatientDialogVo>(dialogAppointmentMapper.getMyPatientDialog(patientId,states,signState,appointmentTimeBegin,appointmentTimeEnd));
        pageInfo.getList().stream().forEach(s->genDialogState(s));
        appResult.getBody().put("dialogs", pageInfo.getList());

        return appResult;
    }

    public PatientDialogVo genDialogState(PatientDialogVo p){
        if (null != p.getSignState() && Constants.SIGNED == Byte.valueOf(p.getSignState()).byteValue()) {
            p.setStateName("已完成");
        } else{
            //1-待确认 2已确认 3-拒绝 4-患者取消 5-医生取消
            if ("1".equals(p.getState())) {
                p.setStateName("待确认");
            } else if ("2".equals(p.getState())) {
                if ((DateUtil.setStartDay(new Date()).getTime() - p.getAppointmentTime().getTime()) > 0) {
                    p.setStateName("待签到");
                } else {
                    p.setStateName("待就诊");
                }
            } else if ("3".equals(p.getState())) {
                p.setStateName("已拒绝");
            } else if ("4".equals(p.getState())) {
                p.setStateName("已取消");
            } else if ("5".equals(p.getState())) {
                p.setStateName("已取消");
            }
        }
        return p;
    }

    @Override
    public AppResult getMyPatientTransfer(String token, Long patientId, String type, Integer pageIndex, Integer pageSize) {
        if (null == pageIndex || pageIndex == 0) {
            pageIndex = Constants.PAGEINDEX;
        }
        if (null == pageSize || pageSize == 0) {
            pageSize = Constants.PAGESIZE;
        }
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        List<Byte> states = new ArrayList<Byte>();
        Byte signState = (byte)1;
        if ("1".equals(type)) {
            states.add(Constants.NO_CONFIRM);
            states.add(Constants.CONFIRM);
            states.add(Constants.DEALING);
        } else if ("2".equals(type)) {
            states.add(Constants.TRANS_CONFIRM);
        } else if ("3".equals(type)) {
            states.add(Constants.TRANS_PATIENT_REFUSED);
            states.add(Constants.TRANS_REFUSED);
        }
        Page page = PageHelper.startPage(pageIndex, pageSize);
        PageInfo<PatientTransferVo> pageInfo = new PageInfo<PatientTransferVo>(dialogAppointmentTransferMapper.getMyPatientTransfer(patientId, states));
        pageInfo.getList().stream().forEach(s-> genTransferState(s));
        appResult.getBody().put("transfers", pageInfo.getList());
        return appResult;
    }
    public PatientTransferVo genTransferState(PatientTransferVo p) {
        //1-待确认 2已确认 3-拒绝 4-患者取消 5-医生取消
        if ("1".equals(p.getState()) || "2".equals(p.getState()) || "6".equals(p.getState())) {
            p.setStateName("转诊中");
        } else if ("4".equals(p.getState())) {
            p.setStateName("转诊成功");
        } else if ("3".equals(p.getState()) || "5".equals(p.getState())) {
            p.setStateName("转诊失败");
        }
        return p;
    }

    @Override
    public AppResult overdueUndiagnose() {
        AppResult appResult = new AppResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appointmentTime", DateUtil.setStartDay(new Date()));
        map.put("state", (byte) 2);
        List<DialogAppointment> dialogAppointments = dialogAppointmentMapper.queryOverdueUndiagnose(map);
        dialogAppointments.stream().forEach(s->
            { s.setState((byte)6);
              dialogAppointmentMapper.updateByPrimaryKeySelective(s);
            });
        appResult.setResultCode(ResultCode.SUCCESS);
        return appResult;
    }
}
