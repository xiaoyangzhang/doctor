package com.yhyt.health.service.api;

import com.yhyt.health.config.PathConfiguration;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.util.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gsh
 * @create 2017-12-06 18:48
 **/
@Service
public class DictionaryApiImpl implements  DictionaryApi {

    @Autowired
    @Qualifier("loadBalanced")
    private RestTemplate restTemplate;

    @Autowired
    private PathConfiguration pathConfiguration;

    @Override
    public AppResult getDictionaryByDictCode(String token, String dictCode) {
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        Map<String, Object> mapPara = new HashMap<String, Object>();
        mapPara.put("dictCode", dictCode);
        com.yhyt.health.spring.AppResult result
                =restTemplate.getForObject(pathConfiguration.getSystemUrl()+"/dictionary/dictionary/?dictCode={dictCode}", com.yhyt.health.spring.AppResult.class, mapPara);
        if (null != result && "200".equals(result.getStatus().getCode())) {
            appResult.setResultCode(ResultCode.SUCCESS);
            appResult.getBody().put("dicts", result.getBody());
        } else {
            throw new BusinessException(ResultCode.EXCEPTION.val(), "调用字典接口失败");
        }
        return appResult;
    }
}
