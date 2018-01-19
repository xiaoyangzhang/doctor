package com.yhyt.health.model;

import java.io.Serializable;

public class DoctorKey implements Serializable {

    private static final long serialVersionUID = -211273714568023903L;
    private Long id;

    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}