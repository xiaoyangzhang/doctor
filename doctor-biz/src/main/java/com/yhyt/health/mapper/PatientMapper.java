package com.yhyt.health.mapper;

import com.yhyt.health.model.Patientl;
import com.yhyt.health.model.vo.app.AppointmentTransferPatientVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PatientMapper {
    List<Patientl> searchpatient(Map<String, Object> map);

    List<Patientl> searchdepartmentpatient(Map<String, Object> map);

    List<AppointmentTransferPatientVO> transferpatient(Map<String, Object> map);

    List<Patientl> dialogsignlist(Map<String, Object> map);

    List<Patientl> dialogsigndetail(Map<String, Object> map);

    Patientl getPatientl(Map<String, Object> map);

    Patientl selectPatientlById(Long id);

    int updatePatientIsauthenticated(Patientl patientl);

}