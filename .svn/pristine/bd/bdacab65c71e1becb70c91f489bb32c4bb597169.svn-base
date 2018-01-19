package com.yhyt.health.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhyt.health.config.PathConfiguration;
import com.yhyt.health.constant.Constants;
import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.*;
import com.yhyt.health.model.*;
import com.yhyt.health.model.vo.app.AppointmentVo;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.util.BusinessException;
import com.yhyt.health.util.HttpUtils;
import com.yhyt.health.vo.EnumAppointmentState;
import com.yhyt.health.vo.EnumImToDoctor;
import com.yhyt.health.vo.EnumTimeInterval;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.print.Doc;
import java.util.*;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月17日 下午4:14:42
 * 类说明
 */
@Service
public class ImServiceImpl implements ImService {
    @Autowired
    private ImMapper imMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private HospitalMapper hospitalMapper;
    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DialogMapper dialogMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private DialogAppointmentNoticeMapper dialogAppointmentNoticeMapper;

    @Autowired
    private PathConfiguration pathConfiguration;

    @Resource
    private DialogAppointmentMapper dialogAppointmentMapper;

    @Autowired
    @Qualifier("loadBalanced")
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ImServiceImpl.class);

    @Override
    public AppResult addDialog(Dialog dialog) {

        AppResult appResult = new AppResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("patientid", dialog.getPatientId());
        map.put("departmentid", dialog.getDepartmentId());
        try {
            imMapper.addDialog(map);
            Patientl patientl = patientMapper.getPatientl(map);
            Long id = dialog.getDepartmentId();
            Department department = departmentMapper.selectByPrimaryKey(id);
            Long deptId = id;
            List<Doctor> doctor = doctorMapper.queryDoctorsByDeptId(deptId);
            Hospital hospital = hospitalMapper.queryHospitalsBydeptid(map);
            appResult.getBody().put("patient", patientl);
            appResult.getBody().put("department", department);
            appResult.getBody().put("doctor", doctor);
            appResult.getBody().put("hospital", hospital);
            appResult.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            appResult.setResultCode(ResultCode.SYS_ERROR);
        }

        return appResult;
    }

    @Override
    @Transactional
    public AppResult sendGroupMessage(String token, String message, String roomIds) {
        AppResult appResult = new AppResult();
        try {
            //获取当前医生信息
            Doctor doctor = null;
            if (null != token) {
                doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("username", doctor.getUsername());
                doctor = doctorMapper.getDoctor(map);
            }
            JSONArray roomIdss = JSONArray.parseArray(roomIds);
            for (int i = 0; i < roomIdss.size(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                if (null == doctor) {//im要求写的
                    map.put("userId", "100666");
                    map.put("passWord", "123456");
                    map.put("roomId", roomIdss.getString(i));
                    map.put("body", message);
                    map.put("NickName", "100666");
                } else {
                    map.put("userId", "1" + doctor.getId());
                    map.put("passWord", doctor.getImPassword());
                    map.put("roomId", roomIdss.getString(i));
                    map.put("body", message);
                    map.put("NickName", doctor.getRealname());
                }
                logger.info("发送群消息请求:" + JSON.toJSONString(map));
                String result = HttpUtils.sendGet(doctorConstant.getIm().get("imOperUrl") + "/groupSend", map);
                logger.info("发送群消息返回:" + result);
                if (!Constants.IM_REGIST.equals(JSONObject.parse(result))) {
                    logger.info("发送失败");
                    appResult.setResultCode(ResultCode.FAILED);
                    appResult.getStatus().setMessage("信息发送失败");
                    return appResult;
                }
                //发起诊后随访
                Dialog dialog = dialogMapper.selectByRoomId(roomIdss.getString(i));
                if (null != dialog) {
                    Map<String, Object> mapPara = new HashMap<String, Object>();
                    mapPara.put("patientId", dialog.getPatientId());
                    mapPara.put("departId", dialog.getDepartmentId());
                    mapPara.put("doctorId", doctor.getId());
                    mapPara.put("type", 2);
                    com.yhyt.health.spring.AppResult resultDialog
                            =restTemplate.getForObject(pathConfiguration.getDialogUrl()+"/dialog/launchdialog?patientId={patientId}" +
                                    "&departId={departId}&type={type}" +
                                    "&doctorId={doctorId}"
                            , com.yhyt.health.spring.AppResult.class, mapPara);
                    if (null != resultDialog && "200".equals(resultDialog.getStatus().getCode())) {
                        appResult.setResultCode(ResultCode.SUCCESS);
                        appResult.getBody().put("questionnaires",resultDialog.getBody());
                    } else {
                        throw new BusinessException(ResultCode.EXCEPTION.val(), "发起随访接口调用失败");
                    }
                }
            }
            appResult.setResultCode(ResultCode.SUCCESS);
            return appResult;
        } catch (Exception ex) {
            logger.error("群发 im 消息失败 : ", ex);
            appResult.setResultCode(ResultCode.FAILED);
            return appResult;
        }
    }


    @Override
    public AppResult getDialogRecord(String token, String roomId, String messageId) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Map<String, String> map = new HashMap<String, String>();
        map.put("roomId", roomId);
        map.put("messageId", messageId);
        String result = HttpUtils.sendGet(doctorConstant.getIm().get("imOperUrl") + "/mucHistory", map);
        appResult.getBody().put("message", result);
        /*if (null != result) {
           JSONArray jsonArray = JSONArray.parseArray(StringEscapeUtils.unescapeJava(result));
            appResult.getBody().put("message", jsonArray);
        }{
            appResult.getBody().put("message", new JSONArray());
        }*/
        return appResult;
    }

    @Override
    public List<DialogAppointmentNotice> selectByUserId(int rows, int userType, String roomId, String token) {

        if (userType == 1) {//医生
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
            return dialogAppointmentNoticeMapper.selectByUserIdForDoctor(doctor.getId(), roomId, rows);
        }
        if (userType == 2) {//患者
            return dialogAppointmentNoticeMapper.selectByUserIdForPatient(roomId, rows);
        }
        return Collections.emptyList();
    }

    @Override
    public int updateDialogNotice(DialogAppointmentNotice dialogAppointmentNotice) {
        return dialogAppointmentNoticeMapper.updateByPrimaryKeySelective(dialogAppointmentNotice);
    }

    /**
     * 患者向医生推送消息，包括文字、图片、语音
     * @param roomId 房间id
     * @param type   推送类型 1－>文字 2－>图片 3－>语音
     * @param token
     * @param sendCrowd 1医生 2患者
     * @param isAit 是否@推送  1是2否
     * @param doctors @推送状态下的医生列表
     * @return
     */
    @Override
    public AppResult doctorPushMessageToPatient(final String roomId,final String type,final String token,final String sendCrowd,final String isAit,final String doctors) {

        if(StringUtils.equals(isAit,"1")){
            //@推送
            return PatientsPushMessageToDoctorsAit(roomId,type,token,sendCrowd,isAit,doctors);
        }else{
            //非@推送
            return doctorPushMessageToPatients(roomId,type);
        }
    }



    /**
     * 医生向医生&患者通过@形式推送消息，包括文字、图片、语音
     * @param roomId 房间id
     * @param type   推送类型 1－>文字 2－>图片 3－>语音
     * @param token
     * @param sendCrowd 1医生 2患者
     * @param isAit 是否@推送  1是2否
     * @param doctorList @推送状态下的医生列表
     * @return
     */
    public AppResult PatientsPushMessageToDoctorsAit(final String roomId,final String type,final String token,final String sendCrowd,final String isAit,final String doctorList){
        //返回app成功信息
        AppResult appResult=new AppResult();
        try {
            //接口必填项校验   --> 参数错误
            if (StringUtils.isEmpty(roomId) || StringUtils.isEmpty(type) || StringUtils.isEmpty(doctorList)) {
                appResult.getStatus().setCode(ResultCode.PARAMS_ERROR.val());
                appResult.getStatus().setMessage(ResultCode.PARAMS_ERROR.msg());
                return appResult;
            }
            Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        //    Doctor doctor = doctorMapper.selectByPrimaryKey(192);

            Map<String, Object> map = new HashMap<>();
            map.put("field", "room_id");
            map.put("val", roomId);

            Dialog dialog = dialogMapper.findDialogByAppointFiel(map);
            List<Doctor> doctorLists = new ArrayList<>();
            if (null != dialog) {
                Long departmentId = dialog.getDepartmentId();
                doctorLists = doctorMapper.getDoctorByDepartmentId(departmentId);
            } //查询出医生列表
            String message = "";
            /**
             * 17年12月14日与董产品沟通的文案，以及不推送@场景
             * @文案
                患者端@医生
                    指定接受的医生获取的推送信息
                        您科室有一条患者张三@你的消息
                    部门其他医生获取的推送信息
                        您科室有一条患者张三@小李医生的消息
                医生端@患者
                    指定患者获取的推送信息
                        您有一条医生小李@你的消息
                    部门其他医生获取的推送信息
                        不推送
                医生@医生
                    指定医生获取的推送信息
                        您科室有一条医生小李@你的消息
                    部门其他医生获取的推送信息
                        不推送
             */
            if (StringUtils.equals(sendCrowd, "2")) {
                //医生向患者发送推送，支持多@推送
                List<String> patients = new ArrayList<>();
                patients = Arrays.asList(doctorList.split("_"));
                StringBuffer patientBuffer = new StringBuffer();
                StringBuffer patientNameBuffer = new StringBuffer();
                for (String str : patients) {
                    patientBuffer.append(str);
                    patientNameBuffer.append(patientMapper.selectPatientlById(Long.parseLong(str)).getRealname() + " ");
                }
                for (String str : patients) {
                    message = "您有一条医生" + doctor.getRealname() + "@你的消息 ";
                    logger.info("医生端向患者端发送@方式的推送消息,患者id为{},接受类型{},消息内容:{},消息类型{},", patientBuffer, "2", message, "9");
                    messageService.sendMessage(Long.parseLong(str), "2", message, message, "9");
                   /* for (Doctor vo : doctorLists) {
                        long id = vo.getId();
                        if (id == doctor.getId()) {
                            continue;
                        }
                        Patientl patientl = patientMapper.selectPatientlById(Long.parseLong(str));
                        message = "您科室有一条医生" + doctor.getRealname() + "@患者" + patientl.getRealname() + "的消息 ";
                        logger.info("医生端向医生端发送@方式的推送消息,医生id为{},接受类型{},消息内容:{},消息类型{},", patientBuffer, "1", message, "9");
                        messageService.sendMessage(vo.getId(), "1", message, message, "9");
                    }*/
                }
            } else {
                //医生向医生发送
                List<String> doctorsList = new ArrayList<>();
                doctorsList = Arrays.asList(doctorList.split("_"));
                StringBuffer doctorBuffer = new StringBuffer();
                StringBuffer doctorsNameBuffer = new StringBuffer();
                for (String doctorId : doctorsList) {
                    doctorBuffer.append(doctorId + " ");
                    doctorsNameBuffer.append(doctorMapper.selectByPrimaryKey(Long.parseLong(doctorId)).getRealname() + " ");
                }
                for (Doctor vo : doctorLists) {
                        long id = vo.getId();
                    if (id == doctor.getId()) {
                        continue;
                    }
                    if (doctorsList.contains(vo.getId().toString())) {
                        //说明当前医生是@的接收
                        message = "您科室有一条医生" + doctor.getRealname() + "@你的消息";
                        logger.info("医生端向医生端发送@方式的推送消息,医生id为{},接受类型{},消息内容:{},消息类型{},", vo.getId(), "1", message, "9");
                        messageService.sendMessage(vo.getId(), "1", message, message, "9");
                    }else{
                        continue;
                    }/* else {
                        message = "您科室有一条医生" + doctor.getRealname() + "@" + doctorsNameBuffer + "医生的消息";
                    }
                    logger.info("医生端向医生端发送@方式的推送消息,医生id为{},接受类型{},消息内容:{},消息类型{},", vo.getId(), "1", message, "9");
                    messageService.sendMessage(vo.getId(), "1", message, message, "9");*/
                }
            }
            appResult.getStatus().setCode(ResultCode.SUCCESS.val());
            appResult.getStatus().setMessage(ResultCode.SUCCESS.msg());
            return appResult;
        }catch (Exception e){
            e.printStackTrace();
            appResult.getStatus().setCode(ResultCode.SYS_ERROR.val());
            appResult.getStatus().setMessage(ResultCode.SYS_ERROR.msg());
            return appResult;
        }
    }

    /**
     * 医生向患者推送消息，包括文字、图片、语音
     * @param roomId 房间id
     * @param type   推送类型 1－>文字 2－>图片 3－>语音
     * @return
     */
    public AppResult doctorPushMessageToPatients(final String roomId,final String type){
        //返回app成功信息
        AppResult appResult=new AppResult();

        //接口必填项校验   --> 参数错误
        if(StringUtils.isEmpty(roomId) || StringUtils.isEmpty(type)){
            appResult.getStatus().setCode(ResultCode.PARAMS_ERROR.val());
            appResult.getStatus().setMessage(ResultCode.PARAMS_ERROR.msg());
            return appResult;
        }

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("field","room_id");
            map.put("val",roomId);

            Dialog dialog = dialogMapper.findDialogByAppointFiel(map);
            Long patientId; //患者id
            if(null != dialog){
                patientId = dialog.getPatientId();
                String message = "";
                message = EnumImToDoctor.getEnumImToDoctor(Byte.parseByte(type)).getMessage();
                /**
                 * 推送给谁(接收人)id          receiverId
                 * 接收人类型   是推送给医生端的 为1  推送给患者的 为2
                 * 标题
                 * 消息内容
                 * 消息类型 如果是新的推送 不要和以前的推送编号重复  目前系统有从1- 8的推送号  此处设置为9
                 */
                logger.info("************************{}****************************************",new Date().toString());
                logger.info("医生端向患者端发送推送消息,患者id为{},接受类型{},消息内容:{},消息类型{},",patientId,"2",message,"9");
                messageService.sendMessage(patientId,"2",message,message,"9");

                appResult.getStatus().setCode(ResultCode.SUCCESS.val());
                appResult.getStatus().setMessage(ResultCode.SUCCESS.msg());
                return appResult;

            }else{
                appResult.getStatus().setCode(ResultCode.SYS_ERROR.val());
                appResult.getStatus().setMessage(ResultCode.SYS_ERROR.msg());
                return appResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            appResult.getStatus().setCode(ResultCode.SYS_ERROR.val());
            appResult.getStatus().setMessage(ResultCode.SYS_ERROR.msg());
            return appResult;
        }
    }

    @Override
    @Transactional
    public String sendGroupMessage(Long doctorId,Long patientId,String type, String message) {
        //返回app成功信息
        AppResult appResult=new AppResult();
        try {
            //查询当前医生
            Doctor doctor = doctorMapper.selectByPrimaryKey(doctorId.longValue());
            Dialog dialog = dialogMapper.queryBydeptIdAndpatientId(doctor.getDepartmentId(),patientId);
            Map<String, String> map = new HashMap<String, String>();
            if ("1".equals(type)) {//医生向患者发送卡片
                map.put("userId", "1" + doctor.getId());
                map.put("passWord", doctor.getImPassword());
                map.put("roomId", dialog.getRoomId());
                map.put("body", message);
                map.put("NickName", doctor.getRealname());
            }
            if ("2".equals(type)) {//患者向医生发送卡片
                Patientl patientl = patientMapper.selectPatientlById(patientId.longValue());
                map.put("userId", "2" + patientl.getId());
                map.put("passWord", patientl.getImPassword());
                map.put("roomId", dialog.getRoomId());
                map.put("body", message);
                map.put("NickName", patientl.getRealname());
            }
            logger.info("发送群消息请求:" + JSON.toJSONString(map));
            String result = HttpUtils.sendGet(doctorConstant.getIm().get("imOperUrl") + "/groupSend", map);
            logger.info("发送群消息返回:" + result);
            if (!Constants.IM_REGIST.equals(JSONObject.parse(result))) {
                logger.info("发送失败");
            }
            return  "200";
        } catch (Exception ex) {
            logger.error("群发 im 消息失败 : ", ex);
            return "201";
        }
    }


    /**
     * 定时任务每天晚间9点发送预约提醒
     * @return
     */
    @Override
    public AppResult reservationReminding() {

        //返回app成功信息
        AppResult appResult=new AppResult();
        try {
            //查询规则  预约时间的前一天21:00  同时状态是已预约的
            List<AppointmentVo> list = dialogAppointmentMapper.getAppointmentVo(
                    EnumTimeInterval.TIME24.getMessage(),
                    EnumAppointmentState.HAVECONFIRM.getCode());

            if (null != list && list.size() > 0) {
                for (AppointmentVo vo : list) {
                    //您预约了2017年7月14日 上午到XXX医院XX科的XX医生就诊，请如期赴诊
                    StringBuffer message = new StringBuffer();
                    message.append("您预约了").append(vo.getAppointmentTime() + " ").
                            append(vo.getLastAfternoon() + "到").
                            append(vo.getHospitalName()).
                            append(vo.getDepartmentName() + "的").
                            append(vo.getDoctorName() + "医生就诊，").
                            append("请如期赴诊。");
                    String title = "预约就诊提醒";
                    logger.info("定时任务发送预约推送消息,医生id为{},医生名称为{},接受类型{},标题为{},消息内容:{},消息类型{},",
                            vo.getDoctorId(), vo.getDoctorName(), "1", title, message, "8");
                    messageService.sendMessage(vo.getPatientId(), "2", title, message.toString(), "1",String.valueOf(vo.getAppointmentId()));
                }
            }
            appResult.getStatus().setCode(ResultCode.SUCCESS.val());
            appResult.getStatus().setMessage(ResultCode.SUCCESS.msg());
            return appResult;
        } catch (Exception e) {
            e.printStackTrace();
            appResult.getStatus().setCode(ResultCode.SYS_ERROR.val());
            appResult.getStatus().setMessage(ResultCode.SYS_ERROR.msg());
            return appResult;
        }
    }
}
