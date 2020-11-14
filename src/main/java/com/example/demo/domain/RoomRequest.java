package com.example.demo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;


public class RoomRequest implements Serializable {
    BigInteger RequstID;
    String userID;
    BigInteger classroomID ;
    Timestamp requestTime ;
    Timestamp startTime;
    Timestamp endTime;
    Integer state    ;

    public BigInteger getRequstID() {
        return RequstID;
    }

    public void setRequstID(BigInteger requstID) {
        RequstID = requstID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public BigInteger getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(BigInteger classroomID) {
        this.classroomID = classroomID;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "RoomRequest{" +
                "RequstID=" + RequstID +
                ", userID='" + userID + '\'' +
                ", classroomID=" + classroomID +
                ", requestTime=" + requestTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                '}';
    }
}
