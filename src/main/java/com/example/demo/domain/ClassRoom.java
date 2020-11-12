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

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
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
}
