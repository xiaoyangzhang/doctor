package com.yhyt.health.service;

import com.yhyt.health.model.vo.PatientVisited;
import com.yhyt.health.result.AppResult;

import java.util.List;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月18日 下午4:08:48
 * 类说明
 */
public interface PatientDiagnoseService {

    AppResult updatePatinetDiagnose(String token, long id, String mainDiagnose);

    List<PatientVisited> getDepartPatientsVisited(long departId);

    AppResult getWorkStationCount(String token, Long departmentId, Long doctorId);
}
