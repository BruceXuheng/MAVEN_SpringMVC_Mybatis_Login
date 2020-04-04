package com.kunlun.chen.control;

import com.kunlun.chen.bean.ResponseData;
import com.kunlun.chen.bean.SpringmvcUser;
import com.kunlun.chen.bean.User;
import com.kunlun.chen.utils.LoginDataHelper;
import com.kunlun.chen.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resources;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.List;

@Controller
public class LoginControl {

    @PostMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData login(@RequestBody User user, HttpServletResponse response) {
        System.out.println(user.toString());
        String resCode = LoginDataHelper.checkUserName(user);
        System.out.println(resCode);
        String resDesc = "";
        switch (resCode) {
            case "00":
                resDesc = "登录成功";
                break;
            case "01":
                resDesc = "密码错误";
                break;
            case "02":
                resDesc = "无此账号，请确认账号填写";
                break;
            default:
                break;
        }
        return new ResponseData(resCode, resDesc, "");
//        return new ResponseData("00", "登陆成功", "");
    }


    @PostMapping(value = "/register", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String register(@RequestParam("uname") String uname,
                           @RequestParam("upwd") String upwd,
//                           @DateTimeFormat(pattern = "yyyyMMdd") Date date){
                           Date date) {
        System.out.println("uname = " + uname);
        System.out.println("upwd = " + upwd);
        System.out.println("date = " + date);
        if(uname.equals("")||"".equals(upwd)){
            return uname + "信息不正确";
        }
        boolean a = LoginDataHelper.registerUser(new User(uname, upwd));
        if (a) {
            return uname + "注册成功";
        } else {
            return uname + "注册失败";
        }
    }

    @PostMapping(value = "/select")
    @ResponseBody
    public String select(User user) {
        List<SpringmvcUser> list = LoginDataHelper.squeryAllUser();
        StringBuilder sb = new StringBuilder();
        if (list == null) {
            return "无数据";
        }
        for (SpringmvcUser user1 : list) {
            sb.append("<h1>" + user1.getUname() + "  " + user1.getUpwd() + "</h1>");
        }
        return sb.toString();
    }


    @GetMapping("/view")
    public ModelAndView showView() {
        ModelAndView modelAndView = new ModelAndView("/index.html");
        return modelAndView;
    }

}
