package com.yhyt.health.model.query;

import com.yhyt.health.page.BasePage;

import java.io.Serializable;
import java.util.Date;

public class DoctorBlackQuery extends BasePage implements Serializable{

    private static final long serialVersionUID = 3035644687606868423L;
    private String username;
    private Date regiserTimeStart;
    private Date registerTimeEnd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRegiserTimeStart() {
        return regiserTimeStart;
    }

    public void setRegiserTimeStart(Date regiserTimeStart) {
        this.regiserTimeStart = regiserTimeStart;
    }

    public Date getRegisterTimeEnd() {
        return registerTimeEnd;
    }

    public void setRegisterTimeEnd(Date registerTimeEnd) {
        this.registerTimeEnd = registerTimeEnd;
    }
}
