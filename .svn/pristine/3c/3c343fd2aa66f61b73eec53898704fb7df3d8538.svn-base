package com.yhyt.health.constant;

public enum OperateType {
    ADD("add",(byte) 1),
    REMOVE("remove",(byte) 2);

    private String desc;
    private byte code;

    OperateType(String desc, byte code) {
        this.desc = desc;
        this.code = code;
    }

    public static OperateType getCodeByDesc(String desc) {
        OperateType[] values = OperateType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].desc.equalsIgnoreCase(desc)){
                return values[i];
            }

        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public byte getCode() {
        return code;
    }
}
