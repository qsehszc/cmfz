package com.baizhi.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class HttpUtils {
    public static String getHttp(MultipartFile file, HttpServletRequest request,String path){
        //获取项目的绝对路径
        String realPath = request.getSession().getServletContext().getRealPath(path);
        //判断文件夹是否存在
        File file1 = new File(realPath);
        if(!file1.exists()){
            file1.mkdirs();
        }

        //获取文件名
        String originalFilename = file.getOriginalFilename();
        String name = new Date().getTime()+"_"+originalFilename;
        //获取文件网络路径
        //获取协议名称
        String scheme = request.getScheme();
        //获取IP地址
        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().toString().split("/")[1];
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口号
        int localPort = request.getLocalPort();
        //项目名
        String contextPath = request.getContextPath();
        String uri = scheme+"://"+localhost+":"+localPort+contextPath+path+name;
        try {
            file.transferTo(new File(realPath, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
}

