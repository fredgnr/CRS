package com.example.demo.service;

import com.example.demo.Dao.IUserDao;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserDao iUserDao;

    public List<User> findAll(){
        return iUserDao.findAll();
    }

    public User findByID(String id){
        return iUserDao.findByID(id);
    }

    public User findByName(String name){
        return iUserDao.findByName(name);
    }

    public  void update(User user){
        iUserDao.update(user);
    }

    public  void delete(User user){
        iUserDao.delete(user);
    }

    public List<User> findByPartName(String name){
        return iUserDao.findNamePart(name);
    }

    public void saveuser(User user){
        iUserDao.saveUser(user);
    }

    public List<User> findByPriority(Integer priority){
        return iUserDao.findByPriority(priority);
    }
}
