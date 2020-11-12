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

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
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
}
