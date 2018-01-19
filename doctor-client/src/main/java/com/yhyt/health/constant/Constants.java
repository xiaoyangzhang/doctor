package com.yhyt.health.constant;

public class Constants {

    public static final byte NOSIGN=1;
    public static final byte SIGNED=2;
    /*患者转诊/预约状态*/
    // 1-待确认 2已确认 3-拒绝 4-患者取消 5-医生取消
    public static final byte  NO_CONFIRM=1;//未确认
    public static final byte CONFIRM=2;//已确认
    public static final byte REFUSED=3;//拒绝
    public static final byte PATIENT_CANCEL=4;//患者取消
    public static final byte DOCTOR_CANCEL=5;//医生取消
    public static final byte DEALING=6;//处理中

//    1-待确认 2-患者同意 3-患者拒绝 4-医生同意 5-医生拒绝 6-处理中
    public static final byte TRANS_PATIENT_REFUSED=3;//患者拒绝
    public static final byte TRANS_CONFIRM=4;//已确认
    public static final byte TRANS_REFUSED=5;//拒绝


    public static final String IM_REGIST = "1001200";
    public static final int PAGEINDEX=1;
    public static final int PAGESIZE=100000;
}
