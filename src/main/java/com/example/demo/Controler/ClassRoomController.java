package com.example.demo.Controler;


import com.example.demo.domain.ClassRoom;
import com.example.demo.domain.User;
import com.example.demo.service.ClassRoomService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Response;
import com.example.demo.utils.ResponseResult;
import com.example.demo.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;

@Api(tags = "对教室操作")
@RestController("/classroom")
public class ClassRoomController {

    private enum PriorityCode{
        NORMAL_USER(1),
        SUPER_USER(3);
        public int code;
        PriorityCode(int code) {
            this.code=code;
        }
    }

    @Autowired
    ClassRoomService classRoomService;

    @Autowired
    UserService userService;

    @GetMapping("/classrooms")
    @ApiOperation(value = "查询所有教室")
    @Transactional
    public ResponseResult<List<ClassRoom>> findall(){
        return Response.makeOKRsp(classRoomService.findAll());
    }

    @PostMapping("/addclassroom")
    @ApiOperation(value="管理员添加教室")
    @Transactional
    public ResponseResult<ClassRoom> add(
            @RequestParam String id,
            @RequestParam String password,
            @RequestBody ClassRoom classRoom){
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
           if(classRoomService.findByArgs(
                   classRoom.getRegion(),classRoom.getBuilding(),classRoom.getM_number()
           ,null,null).size()!=0){
               return Response.makeRsp(ResultCode.CLASSROOM_EXIST.code,"该教室已存在");
           }
           else{
               classRoomService.addClassRoom(classRoom);
               return Response.makeOKRsp(classRoom);
           }
        }
    }

    @PutMapping("/putclassroom")
    @ApiOperation(value="管理员修改教室信息")
    @Transactional
    public ResponseResult<ClassRoom> changeinfo(
            @RequestParam String id,
            @RequestParam String password,
            @RequestBody ClassRoom classRoom){
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
            if(classRoomService.findByID(classRoom.getClassroomID())==null){
                return Response.makeRsp(ResultCode.CLASSROOM_NOT_EXIST.code,"该教室不存在");
            }
            else{
                classRoomService.addClassRoom(classRoom);
                return Response.makeOKRsp(classRoom);
            }
        }
    }

    @DeleteMapping("/deleteclassroom")
    @ApiOperation(value="管理员修改教室信息")
    @Transactional
    public ResponseResult<ClassRoom> delete(
            @RequestParam String id,
            @RequestParam String password,
            @RequestBody ClassRoom classRoom){
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
            if(classRoomService.findByID(classRoom.getClassroomID())==null){
                return Response.makeRsp(ResultCode.CLASSROOM_NOT_EXIST.code,"该教室不存在");
            }
            else{
                classRoomService.deleteClassRoom(classRoom.getClassroomID());
                return Response.makeOKRsp(classRoom);
            }
        }
    }
}
