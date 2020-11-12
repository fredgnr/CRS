package com.example.demo.Dao;

import com.example.demo.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IUserDao {

    @Select("select *from Users")
    List<User> findAll();


    @Insert("insert into Users(userID,userName,m_password,priority) values(#{userID},#{userName},#{m_password},#{priority})")
    void saveUser(User user);

    @Select("select *from Users where userID=#{userID}")
    User findByID(@Param("userID") String userID);

    @Select("select *from Users where priority=#{priority}")
    List<User> findByPriority(@Param("priority") Integer priority);


    @Select("select *from Users where userName=#{userName}")
    User findByName(@Param("userName") String userName);


    @Delete("delete from Users where userID=#{userID}")
    void delete(User user);

    @Update("update Users set " +
            "userName=#{userName},m_password=#{m_password},priority=#{priority}" +
            " where userID=#{userID}")
    void update(User user);

    @Select("select * from Users where userName like CONCAT('%',#{namepart},'%')")
    List<User> findNamePart(@Param("namepart") String namepart);

}
