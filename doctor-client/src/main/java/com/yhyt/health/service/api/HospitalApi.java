package com.yhyt.health.service.api;

import com.yhyt.health.result.AppResult;

import java.util.Map;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月25日 下午1:58:17
 * 类说明
 */
public interface HospitalApi {
    AppResult queryHospitalDepartment(Map<String, Object> map);
}
