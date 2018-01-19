package com.yhyt.health.vo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 时间间隔枚举类
 */
public enum EnumTimeInterval {

    TIME5((byte)1, 5),
    TIME12((byte)2, 12),
    TIME24((byte)3, 24);

    public static ConcurrentHashMap<Byte,EnumTimeInterval> map = new ConcurrentHashMap<>();

    EnumTimeInterval(byte code, Integer message){
        this.code = code;
        this.message = message;
    }

    static{
        map.put((byte)1,TIME5);
        map.put((byte)2, TIME12);
        map.put((byte)3, TIME24);
    }

    private byte code;
    private Integer message;

    public byte getCode() {
        return code;
    }
    public Integer getMessage() {
        return message;
    }

    public static EnumTimeInterval getEnumImToDoctor(byte code){
        return map.get(code);
    }
}
