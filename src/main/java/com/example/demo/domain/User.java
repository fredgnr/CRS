package com.example.demo.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.jws.HandlerChain;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.TreeMap;

@ApiModel(description = "用户信息")
public class User implements Serializable {
    @ApiModelProperty(value="账号",required = true)
    String userID;
    @ApiModelProperty(value="姓名",required = true)
    String userName;

    @ApiModelProperty(value = "密码",required = true)
    String m_password;

    @ApiModelProperty(hidden = true)
    Integer priority;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getM_password() {
        return m_password;
    }

    public void setM_password(String m_password) {
        this.m_password = m_password;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", m_password='" + m_password + '\'' +
                ", priority=" + priority +
                '}';
    }
}
