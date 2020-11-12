package com.example.demo.NewService;

import com.example.demo.Dao.IClassRoomDao;
import com.example.demo.Dao.IFeedBackDao;
import com.example.demo.Dao.IRoomRequestDao;
import com.example.demo.Dao.IUserDao;
import com.example.demo.domain.User;
import lombok.Synchronized;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Semaphore;

@Service
public class Userservices {
    @Autowired
    private IUserDao iUserDao;

    private final Map<String,String> tokenmap=new HashMap<>();

    public static enum mstate{
        WrongAccount,WrongPassword,SuccessLogin,
        InfoChangeSuccess,
        CreateUserSuccess
    };

    private final Object lock=new Object();


    public String getaddtoken(String id){
        synchronized (lock)
        {
            if (tokenmap.containsKey(id))
                return tokenmap.get(id);
            else {
                while (true) {
                    String token = UUID.randomUUID().toString();
                    if (!tokenmap.containsValue(token)) {
                        tokenmap.put(id, token);
                        return token;
                    }
                }
            }
        }
    }



    public boolean verify(String id,String token){
        synchronized (lock){
            if (tokenmap.containsKey(id))
                return tokenmap.get(id).equals(token);
            return false;
        }
    }


    public void logout(String id){
        synchronized (lock) {
            tokenmap.remove(id);
        }
    }



    @Transactional
    public mstate login(String id,String password,User[] users){
        User user=iUserDao.findByID(id);
        if(user==null){
            return mstate.WrongAccount;
        }
        else if(!user.getM_password().equals(password)){
            return mstate.WrongPassword;
        }
        else{
            synchronized (lock){
                getaddtoken(id);
            }
            users[0]=user;
            return mstate.SuccessLogin;
        }
    }

    @Transactional
    public mstate ChangeInfo(User user){
        iUserDao.update(user);
        return mstate.InfoChangeSuccess;
    }

    @Transactional
    public mstate createuser(User user){
        iUserDao.saveUser(user);
        return mstate.CreateUserSuccess;
    }
}
