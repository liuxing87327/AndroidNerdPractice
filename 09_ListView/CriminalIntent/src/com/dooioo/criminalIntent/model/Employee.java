package com.dooioo.criminalIntent.model;

import java.util.UUID;

/**
 * 功能说明：Employee
 * 作者：liuxing(2014-11-27 23:42)
 */
public class Employee {

    private UUID id;
    private String userCode;
    private String userName;
    private String orgName;
    private String status;
    private String position;
    private Long createdAt;

    public Employee() {
        id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return this.userCode + "-" + this.userName + "-" + this.orgName + "-" + this.position + "-" + "-" + this.status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
