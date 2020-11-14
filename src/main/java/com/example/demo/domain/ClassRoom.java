package com.example.demo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigInteger;


@ApiModel(description = "教室信息")
public class ClassRoom implements Serializable {
    @ApiModelProperty("教室ID，自增主键")
    BigInteger classroomID;

    @ApiModelProperty("教室所在区域")
    String region;

    @ApiModelProperty("教室所在建筑")
    String building;

    @ApiModelProperty("教室门牌编号")
    Integer m_number;

    @ApiModelProperty("教室类型")
    Integer m_type;

    @ApiModelProperty("教室容量")
    Integer capacity;

    public BigInteger getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(BigInteger classroomID) {
        this.classroomID = classroomID;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Integer getM_number() {
        return m_number;
    }

    public void setM_number(Integer m_number) {
        this.m_number = m_number;
    }

    public Integer getM_type() {
        return m_type;
    }

    public void setM_type(Integer m_type) {
        this.m_type = m_type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "classroomID=" + classroomID +
                ", region='" + region + '\'' +
                ", building='" + building + '\'' +
                ", m_number=" + m_number +
                ", m_type=" + m_type +
                ", capacity=" + capacity +
                '}';
    }
}
