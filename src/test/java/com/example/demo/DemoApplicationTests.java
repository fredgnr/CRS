package com.example.demo;

import com.example.demo.Dao.IClassRoomDao;
import com.example.demo.Dao.IFeedBackDao;
import com.example.demo.Dao.IRoomRequestDao;
import com.example.demo.Dao.IUserDao;
import com.example.demo.domain.ClassRoom;
import com.example.demo.domain.FeedBack;
import com.example.demo.domain.RoomRequest;
import com.example.demo.domain.User;
import org.joda.time.DateTime;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWarDeployment;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.Data;
import java.lang.ref.ReferenceQueue;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.example.demo.utils.timemapper;
@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoApplicationTests {
    private List<String> getstrlist(int totalnum,int length){
        List<String> stringList=new ArrayList<>();
        String str="0123456789";
        Random random=new Random(System.currentTimeMillis());
    for (int i = 0; i < totalnum; i++) {
        StringBuilder stringBuilder=new StringBuilder();
        for(int j=0;j<length;j++)
            stringBuilder.append(str.charAt(random.nextInt(str.length())));
        stringList.add(stringBuilder.toString());
    }
        return stringList;
    }
    private static List<User> users;
    private static   List<ClassRoom> classRooms;
    private static  List<RoomRequest> requests;
    private  static List<FeedBack> feedBacks;
    static int i=0;

    @Autowired
    private IUserDao iUserDao;

    @Autowired
    private IClassRoomDao iClassRoomDao;

    @Autowired
    private IFeedBackDao iFeedBackDao;

    @Autowired
    private IRoomRequestDao iRoomRequestDao;


    @BeforeAll
    @Test
    static void fuck(){
        i=3;
        users = new ArrayList<>();
        classRooms = new ArrayList<>();
        requests = new ArrayList<>();
        feedBacks = new ArrayList<>();
    }

    @Test
    public void test1(){
        Date date=new Date();
        Time time=new Time(System.currentTimeMillis());
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        System.out.println(date);
        System.out.println(time);
        System.out.println(timestamp);

    }
    @Test
    @Order(1)
    public void testUser1(){
        i++;
        User user1=new User();
        User user2=new User();
        User user3=new User();
        //设置用户信息

        user1.setUserName("zjw");
        user1.setM_password("zz434370");
        user1.setUserID("2017302590226");
        user1.setPriority(1);

        user2.setUserID("2017302590227");
        user2.setUserName("fred");
        user2.setM_password("zhu963852741");
        user2.setPriority(2);

        user3.setUserID("2017302590228");
        user3.setUserName("zjwnb");
        user3.setM_password("zz434370");
        user3.setPriority(1);

        users.add(user1);iUserDao.saveUser(user1);
        users.add(user2);iUserDao.saveUser(user2);
        users.add(user3);iUserDao.saveUser(user3);


        System.out.println("after init");
        List<User> users1=iUserDao.findAll();
        for(User user:users1)
            System.out.println(user);

        System.out.println("after update");
        user2.setUserName("jdks");
        iUserDao.update(user2);

        List<User> tmp;
        tmp=iUserDao.findAll();
        for(User user:tmp)
            System.out.println(user);

        System.out.println("select by id");
        System.out.println("original: "+user2);
        User usertmp=iUserDao.findByID(user2.getUserID());
        System.out.println("result: "+usertmp);

        System.out.println("select by name");
        System.out.println("original: "+user2);
        usertmp=iUserDao.findByName(user2.getUserName());
        System.out.println("result: "+usertmp);

        System.out.println("select by name part");
        tmp=iUserDao.findNamePart("jw");
        for(User user:tmp)
            System.out.println(user);
    }

    @Test
    @Order(2)
    public void testClassRooms() {
        ClassRoom classRoom1=new ClassRoom();
        classRoom1.setM_type(0);
        classRoom1.setM_number(310);
        classRoom1.setBuilding("教一");
        classRoom1.setCapacity(50);
        classRoom1.setRegion("信息学部");

        ClassRoom classRoom2=new ClassRoom();
        classRoom2.setM_type(1);
        classRoom2.setM_number(320);
        classRoom2.setBuilding("教三");
        classRoom2.setCapacity(60);
        classRoom2.setRegion("文理学部");

        ClassRoom classRoom3=new ClassRoom();
        classRoom3.setM_type(0);
        classRoom3.setM_number(425);
        classRoom3.setBuilding("教三");
        classRoom3.setCapacity(80);
        classRoom3.setRegion("文理学部");

        iClassRoomDao.addClassRoom(classRoom1);
        iClassRoomDao.addClassRoom(classRoom2);
        iClassRoomDao.addClassRoom(classRoom3);

        classRooms.add(classRoom1);
        classRooms.add(classRoom2);
        classRooms.add(classRoom3);

        List<ClassRoom> classRooms1=iClassRoomDao.findAll();
        for(ClassRoom classRoom:classRooms1)
            System.out.println(classRoom);

        System.out.println("findByID");
        ClassRoom classRoom=iClassRoomDao.findByID(classRoom1.getClassroomID());
        System.out.println(classRoom);

        System.out.println("findByArgs");
        List<ClassRoom> classRooms2=iClassRoomDao.findByArgs(classRoom3.getRegion(),
               null,null,null,null);
        for(ClassRoom item:classRooms2)
            System.out.println(item);

        System.out.println("findByArgs");
        classRooms2=iClassRoomDao.findByCapcity(55);
        for(ClassRoom item:classRooms2)
            System.out.println(item);
    }

    @Test
    @Order(3)
    void feedbacktest(){
        System.out.println(users.size());
        FeedBack feedBack1=new FeedBack();
        feedBack1.setSaying("王高飞早点死");
        feedBack1.setSendTime(new Timestamp(System.currentTimeMillis()));
        feedBack1.setState(0);
        feedBack1.setUserID(users.get(0).getUserID());

        FeedBack feedBack2=new FeedBack();
        feedBack2.setSaying("王高飞nmsl啊");
        feedBack2.setSendTime(new Timestamp(System.currentTimeMillis()));
        feedBack2.setState(0);
        feedBack2.setUserID(users.get(1).getUserID());

        iFeedBackDao.addFeedBack(feedBack1);
        iFeedBackDao.addFeedBack(feedBack2);
        System.out.println("findAll");
        List<FeedBack> feedBacks1=iFeedBackDao.findAll();
        for(FeedBack item:feedBacks1)
            System.out.println(item);
        System.out.println("findByArgs");

       //feedBacks1=iFeedBackDao.findByArgs("nmsl",null,null,null);
        for(FeedBack item:feedBacks1)
            System.out.println(item);
        System.out.println("findUndoFeedBack");
        feedBacks1=iFeedBackDao.findUndoFeedBack();
        for(FeedBack item:feedBacks1)
            System.out.println(item);
        DateTime dateTime=new DateTime();
        Timestamp timestamp=new Timestamp(dateTime.getMillis());
        System.out.println(timestamp);
    }

    @Test
    @Order(4)
    void roomrequesttest()  {
        Calendar calendar=Calendar.getInstance();

        RoomRequest request1=new RoomRequest();
        request1.setUserID(users.get(0).getUserID());
        request1.setClassroomID(classRooms.get(0).getClassroomID());
        request1.setRequestTime(new Timestamp(System.currentTimeMillis()));
        calendar.set(2020, Calendar.NOVEMBER,30);
        request1.setStartTime( timemapper.date2timestamp(calendar.getTime()));
        calendar.set(2020, Calendar.DECEMBER,5);
        request1.setEndTime( timemapper.date2timestamp(calendar.getTime()));
        request1.setState(0);
        System.out.println(request1);

        RoomRequest request2=new RoomRequest();
        request2.setUserID(users.get(1).getUserID());
        request2.setClassroomID(classRooms.get(1).getClassroomID());
        request2.setRequestTime(new Timestamp(System.currentTimeMillis()));
        calendar.set(2020, Calendar.NOVEMBER,28);
        request2.setStartTime( timemapper.date2timestamp(calendar.getTime()));
        calendar.set(2020, Calendar.DECEMBER,4);
        request2.setEndTime( timemapper.date2timestamp(calendar.getTime()));
        request2.setState(0);
        System.out.println(request2);

        requests.add(request1);requests.add(request2);
        iRoomRequestDao.addRoomReqeust(request1);iRoomRequestDao.addRoomReqeust(request2);

        System.out.println("findAll");
        List<RoomRequest>lists=iRoomRequestDao.findAll();
        for(RoomRequest item:lists)
            System.out.println(item);

        System.out.println("findByStartTime");
        calendar.set(2020, Calendar.NOVEMBER,29);
        lists=iRoomRequestDao.findByStartTime(timemapper.date2timestamp(calendar.getTime()));
        for(RoomRequest item:lists)
            System.out.println(item);
    }

    @Test
    @Order(5)
    void roomrequestdelete(){
        List<RoomRequest>lists=iRoomRequestDao.findAll();
        for(RoomRequest request:lists)
            iRoomRequestDao.deleteRoomReqeust(request);
    }

    @Test
    @Order(6)
    void deletefeedback(){
        List<FeedBack> feedBacks=iFeedBackDao.findAll();
        for (FeedBack item : feedBacks) {
            System.out.println(item);
            iFeedBackDao.deleteFeedBack(item);
        }
    }
    @Test
    @Order(7)
    void deleteUsers()
    {
        List<User> users=iUserDao.findAll();
        for(User user:users)
            iUserDao.delete(user);
    }

    @Test
    @Order(8)
    void deleteClassRooms(){
        List<ClassRoom> classRoomtmp=iClassRoomDao.findAll();
        for(ClassRoom classRoom:classRoomtmp)
            iClassRoomDao.deleteClassRoom(classRoom.getClassroomID());
    }

}
