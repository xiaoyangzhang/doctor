package com.yhyt.health.service.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.DepartmentMapper;
import com.yhyt.health.mapper.DeptLabelMapper;
import com.yhyt.health.mapper.DeptPatientLableMapper;
import com.yhyt.health.model.DeptLabel;
import com.yhyt.health.model.DeptPatientLable;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.model.vo.DepartmentHospitalVO;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.RedisService;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月25日 下午3:31:30
 * 类说明
 */
@Service
public class DepartmentLabelApiImpl implements DepartmentLabelApi {

    @Autowired
    private DeptLabelMapper deptLabelMapper;
    @Autowired
    private DeptPatientLableMapper deptPatientLableMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private DepartmentMapper departmentMapper;

    private static Logger logger = LoggerFactory.getLogger(DepartmentLabelApiImpl.class);

    @Override
    @Transactional
    public AppResult queryDepartmentLabels(String token, DeptLabel deptLabel) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);

        DepartmentHospitalVO departmentHospitalVO = departmentMapper.selectByPrimaryKeyForApp(deptLabel.getDepartmentId());
        appResult.getBody().put("departmentId", departmentHospitalVO.getId());
        appResult.getBody().put("departmentName", departmentHospitalVO.getName());
        appResult.getBody().put("hospitalName", departmentHospitalVO.getHospital());
        List<DeptLabel> deptLabels = deptLabelMapper.queryDeptLabels(deptLabel);
        appResult.getBody().put("list", deptLabels);
        appResult.getBody().put("labelCount", deptLabels.size());
        return appResult;
    }

    @Override
    public AppResult queryDepartmentPatientLabels(String token, DeptPatientLable deptPatientLable) {

        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        deptPatientLable.setDepartmentId(doctor.getDepartmentId());
        appResult.getBody().put("list", deptPatientLableMapper.queryDeptPatientLabels(deptPatientLable));
        return appResult;
    }

    @Override
    public AppResult addDepartmentLabels(String token, DeptLabel deptLabel) {

        AppResult appResult = new AppResult();
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        if (null == deptLabel.getDepartmentId()) {
            deptLabel.setDepartmentId(doctor.getDepartmentId());
        }
        appResult.setResultCode(ResultCode.SUCCESS);
        deptLabel.setCreateTime(new Date());
        deptLabelMapper.insert(deptLabel);
        appResult.getBody().put("id", deptLabel.getId());
        return appResult;
    }

    @Override
    public AppResult deleteDepartmentLabels(String token, DeptLabel deptLabel) {

        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        deptLabelMapper.deleteDeptLabel(deptLabel);
        return appResult;
    }

    @Override
    @Transactional
    public AppResult updateDepartmentLabels(String token, DeptLabel deptLabel) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        deptLabelMapper.updateByDeptLabelId(deptLabel);
        return appResult;
    }

    @Override
    @Transactional
    public AppResult updateDepartmentPatientLabels(String token, String ids, Long departmentId, Long patientId) {
        AppResult appResult = new AppResult();
//		if(null == ids || "".equals(ids)) {
//			appResult.setResultCode(ResultCode.PARAMS_ERROR);
//			return appResult;
//		}
        Doctor doctor = (Doctor) redisService.get(doctorConstant.getRedis().get("global"), token);
        appResult.setResultCode(ResultCode.SUCCESS);
        //删除科室患者下所有标签
        deptPatientLableMapper.deleteDeptPatientByDepartmentId(doctor.getDepartmentId());
        if (null == ids || "".equals(ids)) {
            return appResult;
        }
        //批量添加
        List<DeptPatientLable> DeptPatientLables = new ArrayList<DeptPatientLable>();
        String[] stateStr = ids.split(",");
        for (int i = 0; i < stateStr.length; i++) {
            DeptPatientLable deptPatientLable = new DeptPatientLable();
            deptPatientLable.setCreateTime(new Date());
            deptPatientLable.setDepartmentId(departmentId);
            deptPatientLable.setDeptLabelId(Long.valueOf(stateStr[i]));
            deptPatientLable.setPatientId(patientId);
            DeptPatientLables.add(deptPatientLable);
        }
        deptPatientLableMapper.insertDeptPatientLableBatch(DeptPatientLables);
        return appResult;
    }
}
