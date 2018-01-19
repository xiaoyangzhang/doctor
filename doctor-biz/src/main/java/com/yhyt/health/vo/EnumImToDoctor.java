package com.yhyt.health.vo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 患者向医生推送消息，内容枚举
 */
public enum EnumImToDoctor {

    WORD((byte)1, "您收到一条来自医生的消息"),
    PICTURE((byte)2, "您收到一条来自医生的图片消息"),
    VOICE((byte)3, "您收到一条来自医生的语音消息");

    public static ConcurrentHashMap<Byte,EnumImToDoctor> map = new ConcurrentHashMap<>();

    EnumImToDoctor(byte code, String message){
        this.code = code;
        this.message = message;
    }

    static{
        map.put((byte)1,WORD);
        map.put((byte)2, PICTURE);
        map.put((byte)3, VOICE);
    }

    private byte code;
    private String message;

    public byte getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    public static EnumImToDoctor getEnumImToDoctor(byte code){
        return map.get(code);
    }
}
