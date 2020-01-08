package com.baizhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.HttpUtil;
import com.baizhi.util.SmsUtil;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    //根据性别时间查数量
    @RequestMapping("showUserTime")
    public Map showUserTime(){
        Map map = userService.showUserTime();
        return map;
    }

    //根据性比额查地区
    @RequestMapping("showMap")
    public Map showMap(){
        Map map = userService.showMap();
        return map;
    }


    //分页查
    @RequestMapping("selectPageUser")
    public Map selectPageUser(Integer page,Integer rows){
        Map map = userService.selectPage(page, rows);
        return map;
    }
    //编辑判断是修改/删除/增加
    @RequestMapping("/saveUser")
    public Map saveUser(User user, String oper, String[] id){
        HashMap hashMap = new HashMap();
        if ("add".equals(oper)){
            userService.insert(user);
            hashMap.put("userId",user.getId());
        } else if ("edit".equals(oper)){
            user.setPhoto(null);
            userService.update(user);
            hashMap.put("userId",user.getId());
        } else {
            userService.delete(Arrays.asList(id));
        }
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-374e6325f91646eeb2bec6f977717e53");
        Map map = showUserTime();
        String s = JSONObject.toJSONString(map);
        goEasy.publish("cmfz", s);
        return hashMap;
    }

    //文件上传
    @RequestMapping("/uploadUser")
    public Map uploadUser(MultipartFile photo, String userId, HttpServletRequest request, HttpSession session){
        HashMap hashMap = new HashMap();
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/user/");
        // 判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(photo, request, "/upload/user/");
        //将文件存放到指定目录
        User user = new User();
        user.setId(userId);
        user.setPhoto(http);
        userService.update(user);


        hashMap.put("status",200);
        return hashMap;
    }


    //登陆接口
    @RequestMapping("login")
    public Map login(String phone, String password) {
        HashMap hashMap = new HashMap();
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        User user1 = userDao.selectOne(user);
        System.out.println(user1);
        if (user1 == null) {
            hashMap.put("status",-200);
            hashMap.put("message","错误");
        }
        hashMap.put("status",200);
        hashMap.put("user",user1);
        return hashMap;
    }

    //发送验证码
    @RequestMapping("sendCode")
    public Map sendCode(String phone){
        String s = UUID.randomUUID().toString();
        String code = s.substring(0, 3);
        SmsUtil.send(phone,code);
        // 将验证码保存值Redis  Key phone_186XXXX Value code 1分钟有效
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("message","短信发送成功");
        return hashMap;
    }
    //注册接口文档
    @RequestMapping("sendAdd")
    public Map sendAdd(User user,String salt) {
        HashMap hashMap = new HashMap();
        String s = UUID.randomUUID().toString();
        user.setId(s);
        user.setRigestDate(new Date());
        user.setSalt("12312");
        user.setSex("1");
        user.setPhone("1243413");
        user.setNickName("1351");
        userDao.insert(user);
        hashMap.put("status",200);
        hashMap.put("message","");
        return hashMap;
    }
    //补充信息接口
    @RequestMapping("sendAdd1")
    public Map sendAdd1(User user){
        HashMap hashMap = new HashMap();
        String s = UUID.randomUUID().toString();
        user.setId(s);
        int insert = userDao.insert(user);
        if (insert==0){
            hashMap.put("status", -200);
            hashMap.put("message","");
        }
        hashMap.put("status",200);
        hashMap.put("user",user);
        return hashMap;
    }
}
