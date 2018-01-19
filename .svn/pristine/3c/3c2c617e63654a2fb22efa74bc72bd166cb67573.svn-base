package com.yhyt.health.service.api;

import com.yhyt.health.model.dto.QuestionnaireItemAnswer;
import com.yhyt.health.result.AppResult;

import java.util.List;

/**
 * api接口
 * @author gsh
 * @create 2017-11-16 15:09
 **/
public interface QuestionnaireApi {

   AppResult getQuestionnaireRepertory(String token,Long userId, String type, String moreFlag);

   AppResult sendQuestionnaireDoctor(String token,String roomId,Long questionnaireId);

   AppResult getSendQuestionnaireDoctor(Long questionnaireDoctorPatientId, Long questionnaireId);

   AppResult submitQuestionnairePatient(Long questionnaireDoctorPatientId, Long questionnaireId, String questionnaireItemAnswerList);

   AppResult getQuestionnairePatient(Long questionnaireDoctorPatientId);

   AppResult getMyQuestionnairePatient(String token,Long userId, String type);

   AppResult searchQuestionnaire(String token,String keyWord,Long userId);

   AppResult getMyPatientQuestionnaire(String token, Long patientId,String type,Integer pageIndex,Integer pageSize);
}
