package com.kunlun.chen.utils;

import com.kunlun.chen.bean.SpringmvcUser;
import com.kunlun.chen.bean.User;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LoginDataHelper {


    public static String checkUserName(User user) {
        SqlSession sqlSession = null;
        SpringmvcUser springmvcUser = null;
        try {
            sqlSession = MyBatisUtils.openSqlSession();
            springmvcUser = sqlSession.selectOne("springmvcUser.findByName", user.getUname());
            System.out.println(springmvcUser.toString());
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSqlSession(sqlSession);
            if (springmvcUser == null) {
                return "02";
            }
            if (springmvcUser.getUpwd().equals(user.getUpwd())) {
                return "00";
            }
            return "01";
        }
    }


    public static boolean registerUser(User user) {
        SpringmvcUser user1 = null;
        SqlSession session = null;
        int num = 0;
        try {
            session = MyBatisUtils.openSqlSession();
            System.out.println(user.getUname()+user.getUpwd());
            user1 = new SpringmvcUser(user.getUname(), user.getUpwd());
            num = session.insert("springmvcUser.insertUser", user1);
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSqlSession(session);
            if (num == 1) {
                return true;
            } else {
                return false;
            }
        }

    }

    public static List<SpringmvcUser> squeryAllUser() {
        SqlSession sqlSession = null;
        SpringmvcUser springmvcUser = null;
        List<SpringmvcUser> list = null;
        try {
            sqlSession = MyBatisUtils.openSqlSession();
            list = sqlSession.selectList("springmvcUser.findAll");
            for (SpringmvcUser user : list) {
                System.out.println(user.toString());
            }
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            MyBatisUtils.closeSqlSession(sqlSession);
            return list;
        }
    }


}
