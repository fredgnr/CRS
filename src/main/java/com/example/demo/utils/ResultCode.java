package com.example.demo.utils;

public enum ResultCode {
    USER_NOT_EXSIT(100),
    PASSWORD_WRONG(101),
    SUCCESS(102),
    ID_DUPLICATED(103),
    PRIORITY_NOT_ENOUGH(104),
    USER_TO_BE_DELETED_NOT_EXSIT(105),
    SUPERUSER_CAN_NOT_BE_DELETED(106),
    CLASSROOM_EXIST(107),
    CLASSROOM_NOT_EXIST(108),
    FEEDBACK_NOT_EXIST(109),


    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
