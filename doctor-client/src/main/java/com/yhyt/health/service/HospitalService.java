package com.yhyt.health.service;

import com.github.pagehelper.PageInfo;
import com.yhyt.health.model.Hospital;
import com.yhyt.health.model.query.HospitalQuery;
import com.yhyt.health.model.vo.HospitalVO;

public interface HospitalService {

    PageInfo<HospitalVO> getHospitalListPage(HospitalQuery hospitalQuery);

    int addHospital(Hospital hospital);

    int updateHospital(Hospital hospital);

    Hospital viewHospital(long id);
}
