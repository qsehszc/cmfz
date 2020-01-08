package com.baizhi.controller;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminDao adminDao;


    //登陆
    @RequestMapping("AdminLogin")
    public Map AdminLogin(String yan, String username, String password, HttpSession session, String msg) {
        HashMap hashMa = new HashMap();
        Admin admin = new Admin();
        admin.setUsername(username);
        Admin admin1 = adminDao.selectOne(admin);
        session.setAttribute("Admin",admin1);

        String code = session.getAttribute("serviceCode").toString();

        if(admin1==null){
            hashMa.put("status",400);
            hashMa.put("msg","用户名错误");
        }else if (!admin1.getPassword().equals(password)){
            hashMa.put("status",400);
            hashMa.put("msg","密码错误");
        }else if (!code.equals(yan)){
            hashMa.put("status",400);
            hashMa.put("msg","验证码错误");
        }else {
            hashMa.put("status",200);
        }
        return hashMa;
    }


    //验证码
    @RequestMapping("createImg")
    public void createImg(HttpServletResponse response, HttpSession session) throws IOException {
        CreateValidateCode vcode = new CreateValidateCode();
        String code = vcode.getCode();//随机验证码
        vcode.write(response.getOutputStream());
        //把生产的验证码存入session
        session.setAttribute("serviceCode",code);
    }
}
