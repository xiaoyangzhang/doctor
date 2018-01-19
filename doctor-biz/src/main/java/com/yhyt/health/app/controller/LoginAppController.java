package com.yhyt.health.app.controller;


import com.yhyt.health.constant.DoctorConstant;
import com.yhyt.health.constant.ResultCode;
import com.yhyt.health.model.Device;
import com.yhyt.health.model.Doctor;
import com.yhyt.health.result.AppResult;
import com.yhyt.health.service.DoctorAddService;
import com.yhyt.health.service.DoctorService;
import com.yhyt.health.service.RedisService;
import com.yhyt.health.service.SMSService;
import com.yhyt.health.service.api.DoctorApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆、注册、找回密码接口
 *
 * @author localadmin
 */
@Api(value = "", description = "用户登录相关操作")
@RestController
public class LoginAppController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private DoctorAddService doctorAddService;
    @Autowired
    private DoctorConstant doctorConstant;
    @Autowired
    private DoctorApi doctorApi;
    @Autowired
    private SMSService smsService;

    private Logger logger = LoggerFactory.getLogger(LoginAppController.class);

    @ApiOperation(value = "登录接口", notes = "登录时调用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientType", value = "客户端类型", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "appVersion", value = "客户端版本号", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "mobileId", value = "设备唯一标识", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "deviceName", value = "设备名称", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jiguangId", value = "极光推送Id", paramType = "header", required = false, dataType = "String"),
            @ApiImplicitParam(name = "iosId", value = "iosId", paramType = "header", required = false, dataType = "String"),
            @ApiImplicitParam(name = "huaweiId", value = "华为手机推送id", paramType = "header", required = false, dataType = "String"),
            @ApiImplicitParam(name = "xiaomiId", value = "小米手机推送id", paramType = "header", required = false, dataType = "String"),
            @ApiImplicitParam(name = "appName", value = "新健康newhealth,新医疗newmedical,平板smart", paramType = "header", required = false, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "用户密码", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/doctors")
    public AppResult doctorLogin(@RequestParam String username
            , @RequestParam String password
            , @RequestHeader HttpHeaders httpHeaders
    ) {
        String clientType = httpHeaders.getFirst("clientType");
        String appVersion = httpHeaders.getFirst("appVersion");
        String mobileId = httpHeaders.getFirst("mobileId");
        String deviceName = httpHeaders.getFirst("deviceName");
        String iosId = httpHeaders.getFirst("iosId");
        String jiguangId = httpHeaders.getFirst("jiguangId");
        String huaweiId = httpHeaders.getFirst("huaweiId");
        String xiaomiId = httpHeaders.getFirst("xiaomiId");
        String appName = httpHeaders.getFirst("appName");
        //组装设备信息
        Device device = new Device();
        device.setIosId(null == iosId ? "" : iosId);
        device.setJiguangId(null == jiguangId ? "" : jiguangId);
        device.setAppName(null == appName ? "" : appName);
        device.setHuaweiId(huaweiId);
        device.setXiaomiId(xiaomiId);
        Doctor doctorApp = new Doctor();
        doctorApp.setUsername(username);
        doctorApp.setPassword(password);
        return doctorService.login(device, doctorApp);//调用登陆接口,返回app
    }

    /**
     * 注册接口
     *
     * @param username
     * @param password
     * @param verificationCode
     * @return
     */
    @ApiOperation(value = "注册接口", notes = "注册时调用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "verificationCode", value = "验证码", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "用户密码", paramType = "query", required = true, dataType = "String")
    })
    @PostMapping("/doctors")
    public AppResult doctorRegist(@RequestParam String username
            , @RequestParam String password
            , @RequestParam String verificationCode
            , @RequestHeader HttpHeaders httpHeaders) {
        AppResult appResult = new AppResult();
        String sericutyCode = (String) redisService.get("doctorsyzm" + username);
        //校验验证码
        if ("".equals(verificationCode) || null == verificationCode || !verificationCode.equals(sericutyCode)) {
            appResult.setResultCode(ResultCode.SECURITY_ERROR);
            return appResult;
        }
        String clientType = httpHeaders.getFirst("clientType");
        String appVersion = httpHeaders.getFirst("appVersion");
        String mobileId = httpHeaders.getFirst("mobileId");
        String deviceName = httpHeaders.getFirst("deviceName");
        String iosId = httpHeaders.getFirst("iosId");
        String jiguangId = httpHeaders.getFirst("jiguangId");
        String huaweiId = httpHeaders.getFirst("huaweiId");
        String xiaomiId = httpHeaders.getFirst("xiaomiId");
        String appName = httpHeaders.getFirst("appName");

        //组装设备信息
        Device device = new Device();
        device.setIosId(null == iosId ? "" : iosId);
        device.setJiguangId(null == jiguangId ? "" : jiguangId);
        device.setAppName(null == appName ? "" : appName);
        device.setHuaweiId(huaweiId);
        device.setXiaomiId(xiaomiId);



        Doctor doctor = new Doctor();
        doctor.setUsername(username);
        doctor.setPassword(password);
        return doctorService.regists(device,doctor);//调用注册接口,返回app
    }

    @ApiOperation(value = "忘记密码", notes = "忘记密码找回密码时调用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "verificationCode", value = "验证码", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "新密码", paramType = "query", required = true, dataType = "String")
    })
    @PatchMapping("/doctors/username/{username}")
    public AppResult forgetPassWord(@PathVariable String username
            , @RequestParam String password, @RequestParam String verificationCode) {
        AppResult appResult = new AppResult();
        //校验验证码
        if ("".equals(verificationCode) || null == verificationCode) {
            appResult.setResultCode(ResultCode.NO_EXSTIS);
            return appResult;
        }
        Doctor doctor = new Doctor();
        doctor.setUsername(username);
        doctor.setPassword(password);
        return doctorService.forgetPassWord(doctor,verificationCode);//调用忘记密码接口,返回app
    }

    @ApiOperation(value = "获取验证码", notes = "发送验证码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type",value = "验证码类型1:注册 2：修改密码 3：忘记密码 4:修改手机号",paramType = "query",required = false,dataType = "String")
    })

    @GetMapping("/verificationCodes/username/{username}")
    public AppResult sericutyCode(@PathVariable String username,@RequestParam(required = false) String type) {
        //返回app成功信息
        AppResult appResult = new AppResult();
        appResult.setResultCode(ResultCode.SUCCESS);
        //注册
        if ("1".equals(type)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("username", username);
            if (null != doctorService.getDoctor(map)) {
                appResult.setResultCode(ResultCode.REGIST_ALREADY);
                return appResult;
            }
        }

        //产生4位随机验证码
    	String randCode =String.format("%04d", (int)(Math.random()*10000));
        //发送短信(采用异步请求的方式)
//        smsService.executeAsyncTaskPlus(username, randCode, "2");
        //验证码存入缓存
        redisService.put(doctorConstant.getRedis().get("global")+"yzm" + username, "1234", 120);
        return appResult;
    }

    @ApiOperation(value = "修改密码", notes = "2.修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户注册账号（手机号）", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "originpassword", value = "原用户密码（6-16字母或数字）", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newpassword", value = "新用户密码（6-16字母或数字）", paramType = "query", required = true, dataType = "String")
    })
    @GetMapping("/updatepassword")
    public AppResult updatepassword(@RequestParam String username, @RequestParam String originpassword, @RequestParam String newpassword) {
        return doctorAddService.updatepassword(username, originpassword, newpassword);
    }

    @ApiOperation(value = "注销登录", notes = "注销登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "登录 token", paramType = "header", required = true, dataType = "String"),
//			@ApiImplicitParam(name = "userId",value = "用户 id",paramType = "query",required = false,dataType = "String"),
            @ApiImplicitParam(name = "appName", value = "小米手机推送id", paramType = "query", required = false, dataType = "String"),
    })
    @PatchMapping("/logout")
    public AppResult logout(@RequestHeader("token") String token
            , @RequestHeader(required = false) String appName
            , @RequestParam(required = false) String userId) {
        Device device = new Device();
        device.setAppName(appName);
        AppResult result = null;
        result = doctorApi.logout(token, device, null == userId ? null : Long.valueOf(userId));

        return result;
    }

    @ApiIgnore
    @GetMapping("/checkToken")
    public String checkToken(@RequestParam("token") String token) {
        return doctorApi.checkToken(token);
    }
}
