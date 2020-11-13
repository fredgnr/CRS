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

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@ApiModel(description = "反馈")
public class FeedBack implements Serializable {
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
}
