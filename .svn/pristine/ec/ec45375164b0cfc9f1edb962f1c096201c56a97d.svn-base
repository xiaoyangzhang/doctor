package com.yhyt.health.app.controller;

import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.api.DictionaryApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * 查询字典信息
 *
 * @author gsh
 * @create 2017-12-06 18:46
 **/
@RestController
@RequestMapping("/dictionary")
@Api(value = "", description = "字典接口")
public class DictionaryAppController {

    @Autowired
    private DictionaryApi dictionaryApi;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dictCode", value = "字典编码", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/dictionary")
    public AppResult getDictionaryByDictCode(
            @RequestHeader HttpHeaders httpHeaders,
            @RequestParam String dictCode
            ) {
        String token = httpHeaders.getFirst("token");
        return dictionaryApi.getDictionaryByDictCode(token, dictCode);
    }
}
