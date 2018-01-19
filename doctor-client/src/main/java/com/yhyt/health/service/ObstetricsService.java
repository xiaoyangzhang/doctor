package com.yhyt.health.service;

import com.github.pagehelper.PageInfo;
import com.yhyt.health.model.*;
import com.yhyt.health.model.dto.SysBlacklistDTO;
import com.yhyt.health.model.query.*;
import com.yhyt.health.model.vo.*;
import com.yhyt.health.result.AppResult;

import java.util.List;
import java.util.Map;

public interface ObstetricsService {


    /**
     *
     * @param doctorQuery
     * @return
     */
    PageInfo<PatientlObstetrics> getObstetricsList(ObstetricsQuery doctorDiseaseQuery);

	List<Object> getObstetricsDetailList(ObstetricsQuery doctorQuery);

	PageInfo<PatientlObstetrics> getObstetricsListExport(ObstetricsQuery doctorQuery);

	List getObstetricsDetailListMail();

	PageInfo<PatientlObstetrics> getObstetricsListSingle(ObstetricsQuery doctorQuery);

}
