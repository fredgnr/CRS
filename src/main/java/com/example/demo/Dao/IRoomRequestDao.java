package com.example.demo.Dao;

import com.example.demo.domain.FeedBack;
import com.example.demo.domain.RoomRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface IRoomRequestDao {

    @Select("select * from RoomRequests")
    List<RoomRequest> findAll();


    @Select("<script>"+
            "select * from RoomRequests " +
            "<where>" +
            "<if test='RequstID!=null'>" +
            " and RequstID=#{RequstID} " +
            "</if>" +
            "<if test='userID!=null'>" +
            " and userID=#{userID} " +
            "</if>" +
            "<if test='classroomID!=null'>" +
            " and classroomID=#{classroomID} " +
            "</if>" +
            "<if test='state!=null'>" +
            " and state=#{state} " +
            "</if>" +
            "</where>"+
            "</script>")
    List<RoomRequest> findByArgs(
           @Param("RequstID") BigInteger RequstID,
           @Param("userID") String userID,
           @Param("classroomID") BigInteger classroomID ,
           @Param("state") Integer state
    );

  @Insert("insert into roomrequests(RequstID ,userID,classroomID ,requestTime ,startTime ,endTime, state)"
          + "  values(#{RequstID} ,#{userID},#{classroomID} ,#{requestTime} ,#{startTime} ,#{endTime}, #{state})")
  void addRoomReqeust(RoomRequest roomRequest);

    @Delete("delete from roomrequests where RequstID=#{RequstID}")
    void deleteRoomReqeust(RoomRequest roomRequest);

    @Update("update roomrequests set " +
            "userID=#{userID}," +
            "classroomID=#{classroomID} ," +
            "requestTime=#{requestTime} ," +
            "startTime=#{startTime} ," +
            "endTime=#{endTime}," +
            "state=#{state}" +
            "where RequstID=#{RequstID}")
    void updateRoomReqeust(RoomRequest roomRequest);

    @Select("select * FROM RoomRequests " +
            "where unix_timestamp(startTime)>=unix_timestamp(#{timestamp})")
    List<RoomRequest> findByStartTime(@Param("timestamp") Timestamp timestamp);

    @Select("select * FROM RoomReqeusts " +
            "where unix_timestamp(endTime)<=unix_timestamp(#{timestamp})")
    List<RoomRequest> findByEndTime(@Param("timestamp") Timestamp timestamp);

    @Select("<script>" +
            "select * FROM  RoomReqeusts " +
            "<where>" +
            "<if test='startt!=null'>" +
            " and  unix_timestamp(requestTime)&lt;=unix_timestamp(#{startt})" +
            "</if>" +
            "<if test='endt!=null'>" +
            " and unix_timestamp(requestTime)&lt;=unix_timestamp(#{endt})" +
            "</if>" +
            "</where>" +
            "</script>")
    List<RoomRequest> findbyRequestTime(@Param("startt") Timestamp start,@Param("endt") Timestamp end);

}

