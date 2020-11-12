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
public class FeedBack implements Serializable {
    Integer ID;
    String saying ;
    Timestamp sendTime;
    Integer state ;
    String userID ;
    Timestamp processTime ;
    String backsaying ;
}
