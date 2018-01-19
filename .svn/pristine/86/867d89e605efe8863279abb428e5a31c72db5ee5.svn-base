package com.yhyt.health.controller;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.model.Hospital;
import com.yhyt.health.model.query.HospitalQuery;
import com.yhyt.health.model.vo.HospitalVO;
import com.yhyt.health.service.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class HospitalController {

    private static Logger logger = LoggerFactory.getLogger(HospitalController.class);
    @Autowired
    private HospitalService hospitalService;
    @GetMapping("/hospitalList")
    public PageInfo<HospitalVO> getHospitalListPage(@RequestParam("queryStr") String queryStr) {
        HospitalQuery hospitalQuery = JSON.parseObject(queryStr, HospitalQuery.class);
        PageInfo<HospitalVO> pageInfo = hospitalService.getHospitalListPage(hospitalQuery);
        return pageInfo;
    }

    @PostMapping("/addHospital")
    public int addHospital(@RequestBody Hospital hospital) {
        hospital.setCreateTime(new Date());
        hospital.setUpdateTime(new Date());
        return hospitalService.addHospital(hospital);
    }

    @PutMapping("/updateHospital")
    public int updateHospital(@RequestBody Hospital hospital) {
        /*Hospital hospital = new Hospital();

        hospital.setId(Long.parseLong(hospitalMap.get("id")));
        hospital.setDictCityIdProvince(hospitalMap.get("dictCityIdProvince"));
        hospital.setDictCityIdCity(hospitalMap.get("dictCityIdCity"));
        if (!StringUtils.isEmpty(hospitalMap.get("dictHospitalLevelId"))) {

        hospital.setDictHospitalLevelId(Long.parseLong(hospitalMap.get("dictHospitalLevelId")));
        }
        if (!StringUtils.isEmpty(hospitalMap.get("dictHospitalCategoryId"))) {

        hospital.setDictHospitalCategoryId(Long.parseLong(hospitalMap.get("dictHospitalCategoryId")));
        }
        hospital.setName(hospitalMap.get("name"));
        hospital.setLogo(hospitalMap.get("logo"));
        hospital.setSummary(hospitalMap.get("summary"));
        hospital.setOperator(hospitalMap.get("operator"));*/
        hospital.setUpdateTime(new Date());
        return hospitalService.updateHospital(hospital);
    }

    @GetMapping("/viewHospital/{id}")
    public Hospital viewHospital(@PathVariable("id") long id) {
        return hospitalService.viewHospital(id);
    }
}
