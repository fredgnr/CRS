package com.example.demo.Dao;

import com.example.demo.domain.ClassRoom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Repository
public interface IClassRoomDao {
    @Select("select * from ClassRooms")
    List<ClassRoom> findAll();

    @Select("select * from ClassRooms where classroomID=#{ID}")
    ClassRoom findByID(BigInteger ID);

    @Select("<script>"+
            "select * from ClassRooms " +
            "<where>"+
            "<if test='region!=null'>"+
            " and region like CONCAT('%',#{region},'%')"
            +"</if>"+
            "<if test='building!=null'>"+
            " and building like CONCAT('%',#{building},'%')"
            +"</if>" +
            "<if test='capacity!=null'>"+
            " and capacity>=#{capacity}"
            +"</if>" +
            "<if test='m_type!=null'>"+
            " and m_type=#{m_type}"
            +"</if>" +
            "<if test='m_number!=null'>"+
            " and m_number=#{m_number}"
            +"</if>" +
            "</where>"+
        "</script>")
    List<ClassRoom> findByArgs(
            @Param("region") String region,
            @Param("building") String building,
            @Param("m_number") Integer number,
            @Param("m_type") Integer type,
            @Param("capacity") Integer capacity
    );


    @Insert("insert into ClassRooms(region,building,m_number,m_type,capacity) " +
            "values(#{region},#{building},#{m_number},#{m_type},#{capacity})")
    @Options(useGeneratedKeys = true,keyProperty = "classroomID",keyColumn = "ID")
    void addClassRoom(ClassRoom classRoom);

    @Update("update ClassRooms set " +
            "region=#{region},building=#{building},m_number=#{m_number},m_type=#{m_type},capacity=#{capacity}" +
            "where classroomID=#{classroomID}")
    void updateClassRoom(ClassRoom classRoom);

    @Delete("delete from ClassRooms where classroomID=#{id}")
    void deleteClassRoom(@Param("id") BigInteger id);

    @Select("select * from ClassRooms" +
            " where capacity>=#{a} ")
    List<ClassRoom> findByCapcity(@Param("a") Integer a);

}
