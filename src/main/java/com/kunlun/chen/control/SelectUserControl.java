package com.kunlun.chen.control;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kunlun.chen.bean.ResponseData;
import com.kunlun.chen.bean.SpringmvcUser;
import com.kunlun.chen.utils.LoginDataHelper;
import com.kunlun.chen.utils.MyBatisUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SelectUserControl {

    private static ObjectMapper MAPPER = new ObjectMapper();

    @PostMapping(value = "/fromSelect")
    @ResponseBody
    public String selctFromBackString() {
        List<SpringmvcUser> list = LoginDataHelper.squeryAllUser();
        if (list == null) {
            return "无数据";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (SpringmvcUser user : list) {
            stringBuilder.append("<h1>" + user.getId() +"  "+ user.getUname() +"  "+ user.getUpwd()+"  " + "</h1>");
        }
        return stringBuilder.toString();
    }


    @PostMapping(value = "/ajxaSelect", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public ResponseData selctAjaxBackJson() {
        List<SpringmvcUser> list = LoginDataHelper.squeryAllUser();
        if (list == null) {
            return new ResponseData("er", "无数据", "");
        }
        try {
            System.out.println(new ResponseData("00", "查询成功", MAPPER.writeValueAsString(list)).toString());
            return new ResponseData("00", "查询成功", MAPPER.writeValueAsString(list));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseData("er", "系统异常", "");
    }

}
