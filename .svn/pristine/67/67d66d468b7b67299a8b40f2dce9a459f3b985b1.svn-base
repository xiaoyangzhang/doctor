package com.yhyt.health.vo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 预约表状态枚举
 */
public enum EnumAppointmentState {

    TOCONFIRM((byte)1, "待确认"),
    HAVECONFIRM((byte)2, "已确认"),
    REFUSE((byte)3, "拒绝"),
    PATIENTCANCEL((byte)4, "患者取消"),
    DOCTORCANCEL((byte)5, "医生取消");

    public static ConcurrentHashMap<Byte,EnumAppointmentState> map = new ConcurrentHashMap<>();

    EnumAppointmentState(byte code, String message){
        this.code = code;
        this.message = message;
    }
    private byte code;
    private String message;

    public byte getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    public static EnumAppointmentState getEnumImToDoctor(byte code){
        return map.get(code);
    }

    static{
        map.put((byte)1,TOCONFIRM);
        map.put((byte)2, HAVECONFIRM);
        map.put((byte)3, REFUSE);
        map.put((byte)4, PATIENTCANCEL);
        map.put((byte)5, DOCTORCANCEL);
    }
}
