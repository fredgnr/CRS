package com.example.demo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RoomRequest implements Serializable {
    BigInteger RequstID;
    String userID;
    BigInteger classroomID ;
    Timestamp requestTime ;
    Timestamp startTime;
    Timestamp endTime;
    Integer state    ;
}
