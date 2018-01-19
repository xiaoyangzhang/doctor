package com.yhyt.health.service;

import com.yhyt.health.mapper.DeptDoctorMapper;
import com.yhyt.health.model.DeptDoctor;
import com.yhyt.health.model.vo.XmppMessageBody;
import com.yhyt.health.util.XmppMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * xmpp消息发送service
 *
 * @author gsh
 * @create 2017-10-16 14:55
 **/
@Service
public class XmppMessageService {

    @Autowired
    private DeptDoctorMapper deptDoctorMapper;


    //发送xmpp信息采用异步的方式（单条发送)
    @Async
    public void sendXmppMessage(String userFromId, String password, String userId, XmppMessageBody body) {
        XmppMessageUtil.sendXmppMessage(userFromId,password,userId,body);
    }

    //发送给患者和科室下所有医生
    @Async
    public void sendXmppMessage(String userFromId, String password, String userId,Long departmentId, XmppMessageBody body) {
        List<DeptDoctor> departDoctorList = deptDoctorMapper.getDepartDoctorList(departmentId);
        for (DeptDoctor deptDoctor : departDoctorList) {
            //发送单条消息给医生
            sendXmppMessage(userFromId,password,"1"+deptDoctor.getDoctorId(),body);
        }
        //给患者发消息
        if (null != userId && !"".equals(userId)) {
            sendXmppMessage(userFromId, password, userId, body);
        }
    }

    //发送给患者和科室下所有医生（接收方和发起方）
    @Async
    public void sendXmppMessage(String userFromId, String password, String userId,Long departmentId,Long launchDepartmentId,XmppMessageBody body) {
        //转诊的所有的科室
        List<DeptDoctor> departDoctorList = deptDoctorMapper.getDepartDoctorList(departmentId);
        for (DeptDoctor deptDoctor : departDoctorList) {
            //发送单条消息给医生
            sendXmppMessage(userFromId,password,"1"+deptDoctor.getDoctorId(),body);
        }
        //发起方的所有科室
        List<DeptDoctor> launchDepartDoctorList = deptDoctorMapper.getDepartDoctorList(departmentId);
        for (DeptDoctor deptDoctor : launchDepartDoctorList) {
            //发送单条消息给医生
            sendXmppMessage(userFromId,password,"1"+deptDoctor.getDoctorId(),body);
        }
        //给患者发消息
        if (null != userId && !"".equals(userId)) {
            sendXmppMessage(userFromId, password, userId, body);
        }
    }
}
