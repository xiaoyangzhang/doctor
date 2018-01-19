package com.yhyt.health.mapper;

import com.yhyt.health.model.DeptPatientSign;
import com.yhyt.health.model.vo.app.PatientAppVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptPatientSignMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeptPatientSign record);

    int insertSelective(DeptPatientSign record);

    DeptPatientSign selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeptPatientSign record);

    int updateByPrimaryKey(DeptPatientSign record);

    DeptPatientSign selectByPatientIdAndDeptId(long patientId, long deptId);

    /**
     * 诊后扫码签到申请
     *
     * @param deptId
     * @return
     */
    List<PatientAppVO> getSignApplyWithScanCode(long deptId);

    PatientAppVO getSignDetail(long deptId, long patientId);

    PatientAppVO getSignDetailBydeptSignId(long deptSignId);

    int agree(Long id, String operator);

    int refuse(Long id, String operator);

    Long getSignApplyWithScanCodeCount(Long departmentId);
}