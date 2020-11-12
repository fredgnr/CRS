package com.example.demo.service;

import com.example.demo.Dao.IClassRoomDao;
import com.example.demo.domain.ClassRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ClassRoomService {
    @Autowired
    private IClassRoomDao iClassRoomDao;

    public List<ClassRoom> findAll(){
        return iClassRoomDao.findAll();
    }

    public ClassRoom findByID(BigInteger ID){
        return iClassRoomDao.findByID(ID);
    }

    public List<ClassRoom> findByArgs(
            String region,
            String building,
            Integer number,
            Integer type,
            Integer capacity
    ){
        return iClassRoomDao.findByArgs(region,building,number,type,capacity);
    }

    public void addClassRoom(ClassRoom classRoom){
        iClassRoomDao.addClassRoom(classRoom);
    }

    public void updateClassRoom(ClassRoom classRoom){
        iClassRoomDao.updateClassRoom(classRoom);
    }

    public void deleteClassRoom(BigInteger id){
        iClassRoomDao.deleteClassRoom(id);
    }

    public List<ClassRoom> findByCapcity(Integer a){
        return iClassRoomDao.findByCapcity(a);
    }
}
