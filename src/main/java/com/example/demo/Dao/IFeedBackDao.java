package com.example.demo.Dao;

import com.example.demo.domain.FeedBack;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Mapper
@Repository
public interface IFeedBackDao {

    @Select("select * from FeedBacks")
    List<FeedBack> findAll();

    @Select("select * from FeedBacks WHERE ID=#{ID}")
    FeedBack findByID(@Param("ID") Integer id);

    @Select("<script>"+
            "select * from FeedBacks " +
            "<where> " +
            "<if test='saying!=null'>" +
            " and saying like CONCAT('%',#{saying},'%') " +
            "</if> " +
            "<if test='state!=null'>" +
            " and  state=#{state} " +
            "</if> " +
            "<if test='userID!=null'>" +
            " and userID=#{userID} " +
            "</if>" +
            "<if test='backsaying!=null'>" +
            " and backsaying like CONCAT('%',#{backsaying},'%') " +
            "</if>" +
            "<if test='startleft!=null and startright!=null'>" +
            " and (unix_timestamp(#{startleft})&lt;unix_timestamp(sendTime)" +
            "   and unix_timestamp(sendTime)&lt;unix_timestamp(#{startright})" +
            "</if>" +
            "<if test='endleft!=null and endright!=null'>" +
            " and (unix_timestamp(#{endleft})&lt;unix_timestamp(processTime)" +
            "   and unix_timestamp(processTime)&lt;unix_timestamp(#{endright} " +
            "and state=2)" +
            "</if>" +
            "</where>"+
            "</script>")
    List<FeedBack> findByArgs(
            @Param("saying") String saying ,
            @Param("state") Integer state,
            @Param("userID") String userID,
            @Param("backsaying") String backsaying,
            @Param("startleft") Timestamp startleft,
            @Param("startright") Timestamp startright,
            @Param("endleft") Timestamp endleft,
            @Param("endright") Timestamp endright
    );

    @Options(useGeneratedKeys = true,keyProperty = "ID",keyColumn = "ID")
    @Insert("insert into FeedBacks(saying ,sendTime,state,processTime,userID,backsaying) " +
            "values(#{saying} ,#{sendTime},#{state},#{processTime},#{userID},#{backsaying})")
    void addFeedBack(FeedBack feedBack);

    @Delete("delete from FeedBacks where ID=#{ID}")
    void deleteFeedBack(FeedBack feedBack);

    @Update("update FeedBacks set " +
            "saying=#{saying}," +
            "sendTime=#{sendTime}," +
            "state=#{state}" +
            "processTime=#{processTime}" +
            "userID=#{userID}" +
            "backsaying=#{backsaying}" +
            "where ID=#{ID}")
    void updateFeedBack(FeedBack feedBack);

    @Select("select * from FeedBacks where state=0")
    List<FeedBack> findUndoFeedBack();

    @Select("select * from FeedBacks where userID=#{userID}")
    List<FeedBack> findByUserID(@Param("userID") String userid);

    @Select("select * from FeedBacks " +
            "where unix_timestamp(#{end})<=unix_timestamp(sendTime)" +
            "and unix_timestamp(#{start})>=unix_timestamp(sendTime)")
    List<FeedBack> findBySendTime(@Param("start") Timestamp start,@Param("end") Timestamp end);

    @Select("select * from FeedBacks " +
            "where unix_timestamp(#{end})<=unix_timestamp(processTime)" +
            "and unix_timestamp(#{start})>=unix_timestamp(processTime)" +
            " and state=2")
    List<FeedBack> findByProcessTime(@Param("start") Timestamp start,@Param("end") Timestamp end);
}
