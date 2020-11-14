package com.example.demo.Controler;

import com.example.demo.Dao.IFeedBackDao;
import com.example.demo.Dao.IUserDao;
import com.example.demo.domain.FeedBack;
import com.example.demo.domain.User;
import com.example.demo.service.FeedBackService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Response;
import com.example.demo.utils.ResponseResult;
import com.example.demo.utils.ResultCode;
import com.example.demo.utils.timemapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Api(tags="发送处理反馈")
@RestController("/feedback")
public class FeedBackController {
    private enum StateCode{
        PROCESSED(1),
        UNPROCESSED(2);
        public int code;
        StateCode(int code) {
            this.code=code;
        }
    }

    private enum PriorityCode{
        NORMAL_USER(1),
        SUPER_USER(3);
        public int code;
        PriorityCode(int code) {
            this.code=code;
        }
    }

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private UserService userService;

    @GetMapping("/feedbacks")
    @ApiOperation(value = "获取所有反馈")
    @Transactional
    public ResponseResult<List<FeedBack>> getresponses(){
        return Response.makeOKRsp(feedBackService.findAll());
    }

    @GetMapping("/user")
    @ApiOperation(value = "用户获取自己的反馈")
    @Transactional
    public  ResponseResult<List<FeedBack>> userget(@RequestParam String id){
        User user=userService.findByID(id);
        if(user==null)
            return Response.makeRsp(ResultCode.USER_NOT_EXSIT.code,"不存在此账号");
        else{
            return Response.makeOKRsp(feedBackService.findByUserID(id));
        }
    }


    @GetMapping("/byargs")
    @ApiOperation(value="多参数查找反馈")
    @Transactional
    public ResponseResult<List<FeedBack>> findbyArgs(
            @RequestParam(required = false) String saying ,
            @RequestParam(required = false) Integer state,
            @RequestParam(required = false) String userID,
            @RequestParam(required = false) String backsaying,
            @RequestParam(required = false) String startleft,
            @RequestParam(required = false) String startright,
            @RequestParam(required = false) String endleft,
            @RequestParam(required = false) String endright
    ){
        List<FeedBack> feedBacks;
        feedBacks=feedBackService.findByArgs(saying,state,userID,backsaying,startleft,startright,endleft,endright);
        return Response.makeOKRsp(feedBacks);

    }

    @ApiOperation(value = "用户提交反馈")
    @PostMapping("/user")
    @Transactional
    public ResponseResult<FeedBack> postresponse(
            @RequestParam String userid,
            @RequestParam String password,
            @RequestParam String saying
    ){
        User superuser=userService.findByID(userid);
        if(superuser==null){
            return Response.makeRsp(ResultCode.USER_NOT_EXSIT.code,"账号错误");
        }
        else if(!superuser.getM_password().equals(password)){
            return Response.makeRsp(ResultCode.PASSWORD_WRONG.code,"密码错误");
        }
        else{
            FeedBack feedBack=new FeedBack();
            feedBack.setSaying(saying);
            feedBack.setUserID(userid);
            feedBack.setSendTime(new Timestamp(System.currentTimeMillis()));
            feedBack.setProcessTime(null);
            feedBack.setState(StateCode.UNPROCESSED.code);
            feedBack.setBacksaying("");
            feedBackService.addFeedBack(feedBack);
            return Response.makeOKRsp(feedBack);
        }
    }

    @ApiOperation(value="管理员回复")
    @PutMapping("/manager")
    @Transactional
    public ResponseResult<FeedBack> putresponse(
            @RequestParam String id,
            @RequestParam String password,
            @RequestParam Integer feedbackid,
            @RequestParam String backsaying){
        User superuser=userService.findByID(id);
        if(superuser==null){
            return Response.makeRsp(ResultCode.USER_NOT_EXSIT.code,"账号错误");
        }
        else if(!superuser.getM_password().equals(password)){
            return Response.makeRsp(ResultCode.PASSWORD_WRONG.code,"密码错误");
        }
        else if(superuser.getPriority()!= PriorityCode.SUPER_USER.code){
            return Response.makeRsp(ResultCode.PRIORITY_NOT_ENOUGH.code,"用户权限不够");
        }
        else{
            FeedBack feedBack=feedBackService.findByID(feedbackid);
            if(feedBack==null)
                return Response.makeRsp(ResultCode.FEEDBACK_NOT_EXIST.code,"id为"+feedbackid+"的请求不存在");
            feedBack.setBacksaying(backsaying);
            feedBack.setProcessTime(new Timestamp(System.currentTimeMillis()));
            feedBack.setState(StateCode.PROCESSED.code);
            feedBackService.updateFeedBack(feedBack);
            return Response.makeOKRsp(feedBack);
        }
    }
}
