package com.yhyt.health.constant;

public enum ResultCode {
    //	验证码
    SMS("000", "验证码：%s，有效期%s分钟!"),
    /**
     * 成功
     */
    SUCCESS("200", "success"),

    /**
     * 失败
     */
    FAILED("201", "服务器正在升级，请稍后再试"),

    /**
     * 没有登录
     */
    NOT_LOGIN("400", "没有登录"),
    /**
     * 黑名单校验
     */
    BLACK_LOGIN("406","账号异常，无法登录"),
    /**
     * 系统错误
     */
    SYS_ERROR("402", "系统错误"),

    /**
     * 参数错误
     */
    PARAMS_ERROR("403", "参数错误 "),

    /**
     * 验证码错误
     */
    SECURITY_ERROR("405", "验证码错误"),

    /**
     * 不支持或已经废弃
     */
    NOT_SUPPORTED("410", "不支持或已经废弃"),

    /**
     * AuthCode错误
     */
    INVALID_AUTHCODE("444", "无效的AuthCode"),

    /**
     * 太频繁的调用
     */
    TOO_FREQUENT("445", "太频繁的调用"),

    /**
     * 用户名或密码错误
     */
    UP_ERROR("10004", "用户名或密码错误"),

    /**
     * 用户名不存在
     */
    NO_EXSTIS("10005", "用户不存在"),

    /**
     * 已经注册过
     */
    REGIST_ALREADY("10006", "已经注册过了"),

    /**
     * 已经注册过
     */
    USEREXIST_ALREADY("10007", "用户已存在"),

    /**
     * 未知的错误
     */
    UNKNOWN_ERROR("499", "未知错误"),

    /**
     * 名称已存在
     */
    NAMEEXIST_ALREADY("10012", "名称已存在"),

    /**
     * 名称已存在
     */
    JOIN_ALREADY("10013", "已经加入过"),

    /**
     * 科室签到已存在
     */
    EXISTS_SIGN("202", "已经加入过科室签到"),
    /**
     * 患者已被接诊
     */
    RECEIVED("203", "该患者已被接诊"),
    /**
     * 患者已被其他医生拒绝
     */
    REFUSED("204", "该患者已被拒绝"),
    /**
     * 该疾病已被添加到科室，不能重复添加
     */
    DEPT_DISEASE_EXIST("10014", "该疾病已被添加到科室，不能重复添加"),
    DOCTOR_DISEASE_EXIST("10016", "该医生已关联该疾病，不能重复添加"),
    /**
     * 预约已签到
     */
    ALREAD_SIGN("10015", "该预约已签到，不能撤销"),

    /**
     * 注册IM失败
     */
    IM_REGIST_ERROR("10016", "注册IM失败"),

    /**
     * 患者不在当前科室下
     */
    BLACK_PATIENT_NO_EXIST("10017", "该患者不在当前科室下"),

    /**
     * TOKEN失效了
     */
    TOKEN_INVALID("10018", "TOKEN失效了"),

    /**
     * 安全码超时
     */
    SAFEKEY_INVALID("10019", "操作时间过长，从返回上一步重新操作"),

    /**
     * 发生异常
     */
    EXCEPTION("401", "服务器正在升级，请稍后再试"),
    /**
     * 不需要升级
     */

    NO_UPGRADE("10020","没有可升级版本"),
    /**
     * 不需要升级
     */

    NO_REGIST("10021","此账号没有注册或者没有完成认证"),
    /**
     * 拒绝成功
     */
    REFUSED_SUCCEED("200","");


    private String val;
    private String msg;

    ResultCode(String value, String msg) {
        this.val = value;
        this.msg = msg;
    }

    public String val() {
        return val;
    }

    public String msg() {
        return msg;
    }
} 
