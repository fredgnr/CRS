package com.example.demo.Controler;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.Response;
import com.example.demo.utils.ResponseResult;
import com.example.demo.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags="用户操作")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    private enum PriorityCode{
        NORMAL_USER(1),
        SUPER_USER(3);
        public int code;
        PriorityCode(int code) {
            this.code=code;
        }
    }

    @GetMapping("/id")
    @ApiOperation(value = "根据id获取用户信息")
    @Transactional
    public ResponseResult<User> getuser(
            @RequestParam String id,
            @RequestParam String password
    ){
        User user=userService.findByID(id);
        if(user==null){
            return Response.makeRsp(ResultCode.USER_NOT_EXSIT.code,
                    "账号错误");
        }
        else if(!user.getM_password().equals(password)){
            return Response.makeRsp(ResultCode.PASSWORD_WRONG.code,
                    "密码错误");
        }
        else{
            return Response.makeOKRsp(user);
        }

    }

    @GetMapping("/login")
    @ApiOperation(value = "用户登录")
    @Transactional
    public ResponseResult<Boolean> login(@RequestParam String id,@RequestParam String password){
        User user1=userService.findByID(id);
        if(user1==null){
            return Response.makeRsp(ResultCode.USER_NOT_EXSIT.code,
                    "账号错误",false);
        }
        else if(!user1.getM_password().equals(password)){
            return Response.makeRsp(ResultCode.PASSWORD_WRONG.code,
                    "密码错误",false);
        }
        else{
            return Response.makeOKRsp("成功登录");
        }
    }

    @PostMapping("/singup")
    @ApiOperation(value = "用户注册")
    @Transactional
    public ResponseResult<Boolean> singup(@RequestBody User user){
        if(userService.findByID(user.getUserID())!=null){
            return Response.makeRsp(ResultCode.ID_DUPLICATED.code,
                    "id\t"+user.getUserID()+"已被注册",false);
        }
        else{
            user.setPriority(PriorityCode.NORMAL_USER.code);
            userService.saveuser(user);
            return Response.makeOKRsp("成功注册");
        }
    }

    @PutMapping("/changename")
    @ApiOperation(value="修改姓名")
    @Transactional
    public  ResponseResult<Boolean> changename(
            @RequestParam String id,
            @RequestParam String password,
            @RequestParam String newname){
        User user1=userService.findByID(id);
        if(user1==null){
            return Response.makeRsp(ResultCode.USER_NOT_EXSIT.code,
                    "账号错误",true);
        }
        else if(!user1.getM_password().equals(password)){
            return Response.makeRsp(ResultCode.PASSWORD_WRONG.code,
                    "密码错误",false);
        }
        else{
            user1.setUserName(newname);
            userService.update(user1);
            return Response.makeOKRsp("成功修改用户信息");
        }
    }

    @PutMapping("/changepassword")
    @ApiOperation(value="修改密码")
    @Transactional
    public  ResponseResult<Boolean> changepassword(
            @RequestParam String id,
            @RequestParam String password,
            @RequestParam String newpassword){
        User user1=userService.findByID(id);
        if(user1==null){
            return Response.makeRsp(ResultCode.USER_NOT_EXSIT.code,
                    "id为\t"+id+"\t的用户不存在",true);
        }
        else if(!user1.getM_password().equals(password)){
            return Response.makeRsp(ResultCode.PASSWORD_WRONG.code,
                    "密码错误",false);
        }
        else{
            user1.setM_password(password);
            userService.update(user1);
            return Response.makeOKRsp("成功修改密码");
        }
    }

    @GetMapping("/users")
    @Transactional
    @ApiOperation("管理员获取所有普通用户")
    public ResponseResult<List<User>> getusers(
            @RequestParam String id,
            @RequestParam String password
    ){
        User superuser=userService.findByID(id);
        if(superuser==null){
            return Response.makeRsp(ResultCode.USER_NOT_EXSIT.code,"账号错误");
        }
        else if(!superuser.getM_password().equals(password)){
            return Response.makeRsp(ResultCode.PASSWORD_WRONG.code,"密码错误");
        }
        else if(superuser.getPriority()!=PriorityCode.SUPER_USER.code){
            return Response.makeRsp(ResultCode.PRIORITY_NOT_ENOUGH.code,"用户权限不够");
        }
        else{
            List<User>users=userService.findByPriority(PriorityCode.NORMAL_USER.code);
            return  Response.makeOKRsp(users);
        }
    }


    @DeleteMapping("/deleteuser")
    @Transactional
    @ApiOperation("管理员删除普通用户")
    public ResponseResult<List<User>> deleteUser(
            @RequestParam String id,
            @RequestParam String password,
            @RequestParam String userid_tobedeleted
    ){
        User superuser=userService.findByID(id);
        if(superuser==null){
            return Response.makeRsp(ResultCode.USER_NOT_EXSIT.code,"账号错误");
        }
        else if(!superuser.getM_password().equals(password)){
            return Response.makeRsp(ResultCode.PASSWORD_WRONG.code,"密码错误");
        }
        else if(superuser.getPriority()!=PriorityCode.SUPER_USER.code){
            return Response.makeRsp(ResultCode.PRIORITY_NOT_ENOUGH.code,"用户权限不够");
        }
        else{
           User user=userService.findByID(userid_tobedeleted);
           if(user==null){
               return Response.makeRsp(ResultCode.USER_TO_BE_DELETED_NOT_EXSIT.code,
                       "需要被删除的账号不存在");
           }
           else if(user.getPriority()==PriorityCode.SUPER_USER.code){
               return Response.makeRsp(ResultCode.SUPERUSER_CAN_NOT_BE_DELETED.code,
                       "管理员账号无法删除");
           }
           else{
               userService.delete(user);
               return Response.makeOKRsp("用户删除成功");
           }
        }
    }


}