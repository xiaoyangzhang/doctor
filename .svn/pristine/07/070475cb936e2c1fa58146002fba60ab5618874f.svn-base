package com.yhyt.health.service;

import com.yhyt.health.model.Dialog;
import com.yhyt.health.model.DialogAppointmentNotice;
import com.yhyt.health.result.AppResult;

import java.util.List;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月17日 下午4:14:15
 * 类说明
 */
public interface ImService {

    AppResult addDialog(Dialog dialog);

    AppResult sendGroupMessage(String token, String message, String roomIds);

    String sendGroupMessage(Long doctorId,Long patientId,String type, String message);

    AppResult getDialogRecord(String token, String roomId, String messageId);

    List<DialogAppointmentNotice> selectByUserId(int rows, int userType, String roomId, String token);

    int updateDialogNotice(DialogAppointmentNotice dialogAppointmentNotice);

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
    AppResult doctorPushMessageToPatient(final String roomId,final String type,final String token,final String sendCrowd,final String isAit,final String doctors);

    /**
     * 定时任务每天晚间9点发送预约提醒
     * @return
     */
    AppResult reservationReminding();

}
