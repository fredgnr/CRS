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
import io.swagger.annotations.*;
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
    @ApiResponses({
            @ApiResponse(code=100,message = "账号不存在"),
            @ApiResponse(code=102,message = "成功查询信息")
    })
    @Transactional
    public  ResponseResult<List<FeedBack>> userget(@ApiParam(value = "查询账号")@RequestParam String id){
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
            @ApiParam(value = "反馈（模糊搜索）")@RequestParam(required = false) String saying ,
            @ApiParam(value = "状态（1为已处理，2为未处理）")@RequestParam(required = false) Integer state,
            @ApiParam(value = "账号")@RequestParam(required = false) String userID,
            @ApiParam(value = "管理员回复")@RequestParam(required = false) String backsaying,
            @ApiParam(value = "发送时间左界限（格式为yyyy-MM-dd HH:mm:ss）")@RequestParam(required = false) String startleft,
            @ApiParam(value = "发送时间右界限（格式为yyyy-MM-dd HH:mm:ss）")@RequestParam(required = false) String startright,
            @ApiParam(value = "处理时间左界限（格式为yyyy-MM-dd HH:mm:ss）")@RequestParam(required = false) String endleft,
            @ApiParam(value = "处理时间右界限（格式为yyyy-MM-dd HH:mm:ss）")@RequestParam(required = false) String endright
    ){
        List<FeedBack> feedBacks;
        feedBacks=feedBackService.findByArgs(saying,state,userID,backsaying,startleft,startright,endleft,endright);
        return Response.makeOKRsp(feedBacks);

    }

    @ApiOperation(value = "用户提交反馈")
    @PostMapping("/user")
    @ApiResponses({
            @ApiResponse(code=100,message = "账号不存在"),
            @ApiResponse(code=101,message = "账号错误"),
            @ApiResponse(code =102,message = "成功提交反馈")
    })
    @Transactional
    public ResponseResult<FeedBack> postresponse(
            @ApiParam(value = "账号")@RequestParam String userid,
            @ApiParam(value = "密码")@RequestParam String password,
            @ApiParam(value = "反馈")@RequestParam String saying
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
    @ApiResponses({
            @ApiResponse(code=100,message = "账号不存在"),
            @ApiResponse(code=101,message = "密码错误"),
            @ApiResponse(code=104,message = "用户权限不够"),
            @ApiResponse(code=109,message = "反馈ID错误"),
            @ApiResponse(code=102,message = "成功处理反馈")
    })
    @Transactional
    public ResponseResult<FeedBack> putresponse(
            @ApiParam(value = "管理员账号")@RequestParam String id,
            @ApiParam(value = "管理员密码")@RequestParam String password,
            @ApiParam(value = "回复的反馈ID")@RequestParam Integer feedbackid,
            @ApiParam(value = "管理员反馈回复")@RequestParam String backsaying){
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
