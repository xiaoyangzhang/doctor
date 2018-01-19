package com.yhyt.health.service;

import com.yhyt.health.result.AppResult;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * localadmin 作者:
 *
 * @version 创建时间：2017年8月15日 上午9:42:13
 * 类说明
 */
//@FeignClient(name="patient01") 
public interface PatientService {

    //	@GetMapping("/deptGroupPatients/deptGroupId/{deptGroupId}")
    AppResult helloWord(@PathVariable("deptGroupId") Long deptGroupId);

}
