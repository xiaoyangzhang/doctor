package com.yhyt.health.model.dto;

import com.yhyt.health.model.SysBlacklist;

import java.io.Serializable;

public class SysBlacklistDTO implements Serializable {

    private static final long serialVersionUID = 7499170164156387621L;
    private SysBlacklist sysBlacklist;
    private String operateTpye;

    public SysBlacklist getSysBlacklist() {
        return sysBlacklist;
    }

    public void setSysBlacklist(SysBlacklist sysBlacklist) {
        this.sysBlacklist = sysBlacklist;
    }

    public String getOperateTpye() {
        return operateTpye;
    }

    public void setOperateTpye(String operateTpye) {
        this.operateTpye = operateTpye;
    }
}
