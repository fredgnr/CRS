package com.example.demo.domain;

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
public class FeedBack implements Serializable {
    Integer ID;
    String saying ;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp sendTime;
    Integer state ;
    String userID ;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Timestamp processTime ;

    String backsaying ;
}
