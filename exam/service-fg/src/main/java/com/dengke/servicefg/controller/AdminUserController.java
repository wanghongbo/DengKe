package com.dengke.servicefg.controller;

import com.alibaba.fastjson.JSON;
import com.dengke.entity.common.JsonObjectUtil;
import com.dengke.entity.common.RtnConstants;
import com.dengke.servicefg.service.AdminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/user",produces = "application/json;charset=UTF-8")
public class AdminUserController extends  BaseController{


    @Resource
    private AdminUserService adminUserService;

//    @RequestMapping("/login")
//    public String login(HttpServletRequest request, @RequestParam String id, @RequestParam String password){
//        try{
//            log.info(id+"  "+password+" "+request.getParameter("token"));
//            if(StringUtils.isBlank(id)||StringUtils.isBlank(password)){
//                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"参数为空","");
//            }
//            UserInfo userInfo = userService.getUserById(Long.parseLong(id));
//            if(userInfo == null){
//                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"用户名或密码错误","");
//            }
//            if(!MD5Util.md5(password).equals(userInfo.getPassword())){
//                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"用户名或密码错误","");
//            }
//            String token = getToken(request);
//            log.info(redisTemplate.getExpire(Constants.CACHE_TOKEN+token).toString());
//
//            redisTemplate.opsForHash().put(Constants.CACHE_TOKEN+token,Constants.CACHE_USER_INFO,userInfo);
//            System.out.println(JSON.toJSONString(getUser(request)));
//            log.info(JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"登陆成功",token));
//            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"登陆成功",token);
//        }catch (Exception e){
//            log.error(e.toString());
//            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"用户名或密码错误","");
//
//        }
//    }

//    @RequestMapping("/logout")
//    public String logout(HttpServletRequest request){
//        String token = getExistToken(request);
//        if(StringUtils.isNotBlank(token)) {
//            redisTemplate.delete(Constants.CACHE_TOKEN + token);
//        }
//        return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"","");
//    }

    @RequestMapping("/noLogin")
    public String noLogin(){
        return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.NO_LOGIN,"您未登录","");
    }

    @RequestMapping("/getUser")
    public String getUser(HttpServletRequest request){
        String userName =request.getParameter("userName");
        return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",adminUserService.getUser(userName));
    }
}
