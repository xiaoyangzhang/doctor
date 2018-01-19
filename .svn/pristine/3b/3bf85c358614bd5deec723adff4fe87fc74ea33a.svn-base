package com.yhyt.health.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhyt.health.mapper.HospitalMapper;
import com.yhyt.health.model.Hospital;
import com.yhyt.health.model.query.HospitalQuery;
import com.yhyt.health.model.vo.HospitalVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalMapper hospitalMapper;

    private static Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);

    @Override
    public PageInfo<HospitalVO> getHospitalListPage(HospitalQuery hospitalQuery) {
        Page page = PageHelper.startPage(hospitalQuery.getPageIndex(), hospitalQuery.getPageSize());
        List<HospitalVO> hospitalListPage = hospitalMapper.getHospitalListPage(hospitalQuery);
        PageInfo<HospitalVO> pageInfo = new PageInfo<>(hospitalListPage);
        return pageInfo;
    }

    @Override
    public int addHospital(Hospital hospital) {
        return hospitalMapper.insertSelective(hospital);
    }

    @Override
    public int updateHospital(Hospital hospital) {
        return hospitalMapper.updateByPrimaryKeySelective(hospital);
    }

    @Override
    public Hospital viewHospital(long id) {
        return hospitalMapper.selectByPrimaryKey(id);
    }
}
