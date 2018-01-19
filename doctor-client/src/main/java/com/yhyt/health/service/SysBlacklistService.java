package com.yhyt.health.service;

import com.yhyt.health.model.SysBlacklist;
import com.yhyt.health.result.AppResult;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月18日 下午2:09:34
 * 类说明
 */
public interface SysBlacklistService {

    AppResult addSysBlacklist(String token, Long patientId,Long doctorId,String state);
}
 