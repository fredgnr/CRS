package com.example.demo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;



@ApiModel(description = "反馈")
public class FeedBack implements Serializable {
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getSaying() {
        return saying;
    }

    public void setSaying(String saying) {
        this.saying = saying;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Timestamp getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Timestamp processTime) {
        this.processTime = processTime;
    }

    public String getBacksaying() {
        return backsaying;
    }

    public void setBacksaying(String backsaying) {
        this.backsaying = backsaying;
    }

    @ApiModelProperty(value = "反馈编号，主键")
    Integer ID;
    @ApiModelProperty(value = "反馈内容")
    String saying ;

    @ApiModelProperty(value = "反馈时间")
    Timestamp sendTime;

    @ApiModelProperty(value = "反馈处理状态")
    Integer state ;

    @ApiModelProperty(value = "反馈者账号")
    String userID ;

    @ApiModelProperty(value = "处理时间")
    Timestamp processTime ;

    @ApiModelProperty(value = "处理时间")
    String backsaying ;

    @Override
    public String toString() {
        return "FeedBack{" +
                "ID=" + ID +
                ", saying='" + saying + '\'' +
                ", sendTime=" + sendTime +
                ", state=" + state +
                ", userID='" + userID + '\'' +
                ", processTime=" + processTime +
                ", backsaying='" + backsaying + '\'' +
                '}';
    }
}
