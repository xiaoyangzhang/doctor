package com.yhyt.health.service;

import com.yhyt.health.result.AppResult;

/**
 * Created by shensh on 2017/9/6.
 */
public interface DeptPatientSignService {
    AppResult agreeCheck(Long replenishSignId);

    AppResult agree(String token, Long id);

    AppResult refuse(Long id, String operator,String refuseReason);
}
