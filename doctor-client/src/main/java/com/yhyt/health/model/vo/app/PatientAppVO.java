package com.yhyt.health.model.vo.app;

import com.yhyt.health.model.PatientDiagnosePics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientAppVO implements Serializable {

    private static final long serialVersionUID = -4949236794747658522L;
    private Long id;//患者 id
    private Long requestPatientId;//求诊/转诊id
    private String roomId;
    private Integer idType;//转诊 id：1求诊 id:2预约id:3转诊
    private Long deptSignId;//诊后签到 id
    private String headImage;
    private String name;
    private Byte sex;
    private Integer age;
    private String cardNo;//身份证号码
    private Date diagnoseDate;//就诊日期
    private String mainDoctor;//主治医生
    private byte type;//就医类型1-门诊 2-住院 3-特需
    private byte isRepeat;//初诊/复诊 1-初诊 2复诊
    private String diagnoseResult;// 诊断结果
    private List<PatientDiagnosePics> diseaseInfo = new ArrayList<PatientDiagnosePics>();//病情资料
    private Date applyTime;
    private String operator;
    private String state;//数据的状态
    private String resideLocation;//居住地
    private Date birthday;
    private String city;
    private String mainDisease;//疑似疾病
    private String sourceHospital;//转诊(转出医院)、求诊(医院名)
    private String sourceDepartment;//转出科室、求诊(科室名)
    private Long departmentId;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestPatientId() {
        return requestPatientId;
    }

    public void setRequestPatientId(Long requestPatientId) {
        this.requestPatientId = requestPatientId;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public Long getDeptSignId() {
        return deptSignId;
    }

    public void setDeptSignId(Long deptSignId) {
        this.deptSignId = deptSignId;
    }


    public List<PatientDiagnosePics> getDiseaseInfo() {
        return diseaseInfo;
    }

    public void setDiseaseInfo(List<PatientDiagnosePics> diseaseInfo) {
        this.diseaseInfo = diseaseInfo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Date getDiagnoseDate() {
        return diagnoseDate;
    }

    public void setDiagnoseDate(Date diagnoseDate) {
        this.diagnoseDate = diagnoseDate;
    }

    public String getMainDoctor() {
        return mainDoctor;
    }

    public void setMainDoctor(String mainDoctor) {
        this.mainDoctor = mainDoctor;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(byte isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getDiagnoseResult() {
        return diagnoseResult;
    }

    public void setDiagnoseResult(String diagnoseResult) {
        this.diagnoseResult = diagnoseResult;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMainDisease() {
        return mainDisease;
    }

    public void setMainDisease(String mainDisease) {
        this.mainDisease = mainDisease;
    }

    public String getSourceHospital() {
        return sourceHospital;
    }

    public void setSourceHospital(String sourceHospital) {
        this.sourceHospital = sourceHospital;
    }

    public String getSourceDepartment() {
        return sourceDepartment;
    }

    public void setSourceDepartment(String sourceDepartment) {
        this.sourceDepartment = sourceDepartment;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResideLocation() {
        return resideLocation;
    }

    public void setResideLocation(String resideLocation) {
        this.resideLocation = resideLocation;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
