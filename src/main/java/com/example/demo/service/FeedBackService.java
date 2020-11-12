package com.example.demo.service;

import com.example.demo.Dao.IFeedBackDao;
import com.example.demo.domain.FeedBack;
import com.example.demo.utils.timemapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class FeedBackService {
    @Autowired
    private IFeedBackDao iFeedBackDao;

    public List<FeedBack> findAll(){
        return iFeedBackDao.findAll();
    }


    public List<FeedBack> findByUserID(String id) {
        return iFeedBackDao.findByUserID(id);
    }

    public FeedBack findByID(int id){
        return iFeedBackDao.findByID(id);
    }

    public List<FeedBack> findByArgs(String saying, Integer state, String userID, String backsaying,
                                     Date startleft,
                                     Date startright,
                                     Date endleft,
                                     Date endright) {
        return iFeedBackDao.findByArgs(saying,state,userID,backsaying,
                startleft!=null? timemapper.date2timestamp(startleft):null,
                startright!=null? timemapper.date2timestamp(startright):null,
                endleft!=null? timemapper.date2timestamp(endleft):null,
                endright!=null? timemapper.date2timestamp(endright):null);
    }

    public void addFeedBack(FeedBack feedBack){

    }

    public void updateFeedBack(FeedBack feedBack){
        iFeedBackDao.updateFeedBack(feedBack);
    }
}
