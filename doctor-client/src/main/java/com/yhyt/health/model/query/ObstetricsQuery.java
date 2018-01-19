package com.yhyt.health.model.query;

import com.yhyt.health.page.BasePage;

import java.io.Serializable;
import java.util.Date;

public class ObstetricsQuery extends BasePage implements Serializable{

    private static final long serialVersionUID = 3035644687123868423L;
    private String realname;
    private String username;
    private String isDangerous;
    private String birthTimeStart;
    private String birthTimeEnd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIsDangerous() {
		return isDangerous;
	}

	public void setIsDangerous(String isDangerous) {
		this.isDangerous = isDangerous;
	}

	public String getBirthTimeStart() {
		return birthTimeStart;
	}

	public void setBirthTimeStart(String birthTimeStart) {
		this.birthTimeStart = birthTimeStart;
	}

	public String getBirthTimeEnd() {
		return birthTimeEnd;
	}

	public void setBirthTimeEnd(String birthTimeEnd) {
		this.birthTimeEnd = birthTimeEnd;
	}


}
