package com.yhyt.health.service.api;

import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.mapper.DepartmentMapper;
import com.yhyt.health.mapper.HospitalMapper;
import com.yhyt.health.model.Department;
import com.yhyt.health.model.Hospital;
import com.yhyt.health.result.AppResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月25日 下午1:59:29
 * 类说明
 */
@Service
public class HospitalApiImpl implements HospitalApi {

    @Autowired
    private HospitalMapper hospitalMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    private static Logger logger = LoggerFactory.getLogger(HospitalApiImpl.class);

    @Override
    @Transactional
    public AppResult queryHospitalDepartment(Map<String, Object> map) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        //如果是查询医院
        if ("1".equals(map.get("type"))) {
            List<Hospital> hospitals = hospitalMapper.queryHospitals(map);
//            Hospital hospital = new Hospital();
//            hospitals.add(hospital);
//            hospital.setName("其他");
            appResult.getBody().put("list", hospitalMapper.queryHospitals(map));
        }
        if ("2".equals(map.get("type"))) {//如果是科室
            if (null == map.get("id")) {
                appResult.setResultCode(ResultCode.PARAMS_ERROR);
                return appResult;
            }
            List<Department> departments = departmentMapper.getDepartmentList((long) map.get("id"));
//            Department department = new Department();
//            department.setName("其他");
//            departments.add(department);
            appResult.getBody().put("list", departments);
        }
        return appResult;
    }

}
