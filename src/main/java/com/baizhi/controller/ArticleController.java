package com.baizhi.controller;


import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Article;
import com.baizhi.entity.User;
import com.baizhi.service.ArticleService;
import com.baizhi.util.HttpUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleDao articleDao;

    //分页查
    @RequestMapping("/selectPageArticle")
    public Map selectPageArticle(Integer page,Integer rows){
        Map map = articleService.selectPage(page, rows);
        return map;
    }

    //图片上传
    @RequestMapping("/uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        try {
            String http = HttpUtils.getHttp(imgFile, request, "/upload/articleImg/");
            hashMap.put("error",0);
            hashMap.put("url",http);
        }catch (Exception e){
            hashMap.put("error",1);
            e.printStackTrace();
        }
        return hashMap;
    }

    //查所有图片
    @RequestMapping("/showAllImg")
    public Map showAllImg(HttpServletRequest request,HttpSession session){
        HashMap hashMap = new HashMap();
        hashMap.put("current_url",request.getServletPath()+"/upload/articleImg/");
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            String time = name.split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;

    }

    //添加和修改
    @RequestMapping("/insertArticle")
    public Map insertArticle(Article article, MultipartFile inputfile, HttpServletRequest request){
        if(article.getId()==null||"".equals(article.getId())){
            String http = HttpUtils.getHttp(inputfile, request, "/upload/articleImg/");
            article.setImg(http);
            articleService.insert(article);
        }else{
            article.setImg(null);
            articleService.update(article);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
    //删除
    @RequestMapping("/deleteArticle")
    public void deleteArticle(String id){
        articleService.delete(Arrays.asList(id));
    }

    //文章详情
    @Autowired
    UserDao userDao;
    @RequestMapping("selectAll")
    public Map selectAll(String uid,String id){
        HashMap hashMap = new HashMap();
        User user = userDao.selectByPrimaryKey(uid);
        Article article = articleDao.selectByPrimaryKey(id);
        article.setGuruId(user.getId());
        hashMap.put("status",200);
        hashMap.put("article",article);
        hashMap.put("user",user);
        return hashMap;
    }

}
