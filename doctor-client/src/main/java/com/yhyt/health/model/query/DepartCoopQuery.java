package com.yhyt.health.model.query;

import com.yhyt.health.page.BasePage;

import java.io.Serializable;

public class DepartCoopQuery extends BasePage implements Serializable{

    private static final long serialVersionUID = 983912916849078863L;
    private Long departId;
    private Byte local;//本科室
    private Byte initiator;//发起科室
    private byte type;//1合作方2发起方

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Long getDepartId() {
        return departId;
    }

    public void setDepartId(Long departId) {
        this.departId = departId;
    }

    public Byte getLocal() {
        return local;
    }

    public void setLocal(Byte local) {
        this.local = local;
    }

    public Byte getInitiator() {
        return initiator;
    }

    public void setInitiator(Byte initiator) {
        this.initiator = initiator;
    }
}
