package com.baizhi.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import com.baizhi.util.HttpUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerDao bannerDao;
    @Autowired
    BannerService bannerService;

    @RequestMapping("findByPage")
    public Map findByPage(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        Map map = bannerService.getAllBanners(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public Map edit(String oper, Banner banner, String[] id) {
        HashMap hashMap = new HashMap();
        // 添加逻辑
        if (oper.equals("add")) {
            String bannerId = UUID.randomUUID().toString();
            banner.setId(bannerId);
            bannerDao.insert(banner);
            hashMap.put("bannerId", bannerId);
            // 修改逻辑
        } else if (oper.equals("edit")) {
            bannerDao.updateByPrimaryKeySelective(banner);
            hashMap.put("bannerId", banner.getId());
            // 删除
        } else {
            bannerDao.deleteByIdList(Arrays.asList(id));
        }
        return hashMap;
    }

    @RequestMapping("upload")
    public Map upload(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) throws UnknownHostException {

        HashMap hashMap = new HashMap();
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(url, request, "/upload/img/");
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setUrl(http);
        bannerDao.updateByPrimaryKeySelective(banner);
        hashMap.put("status", 200);
        return hashMap;
    }
    //EasyExcel导出轮播图信息
    @RequestMapping("/outBannerInformation")
    public Map outBannerInformation(){
        List<Banner> banners = bannerService.selectAll();
        for (Banner banner : banners) {
            String[] split = banner.getUrl().split("/");
            String url = split[split.length-1];
            banner.setUrl(url);
        }
        String fileName = "E:\\dd"+new Date().getTime()+".xls";
        EasyExcel.write(fileName,Banner.class).sheet("banner").doWrite(banners);
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
    //Excel模板下载
    @RequestMapping("/outBannerModel")
    public Map outBannerModel(){
        List<Banner> banners = bannerService.selectAll();
        //创建excel表格对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //工作簿对象
        HSSFSheet sheet = workbook.createSheet();
        //行对象
        HSSFRow row = sheet.createRow(0);
        //表格的头部分
        String[] str = {"ID","标题","图片","超链接","创建时间","描述","状态"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            //单元格对象
            row.createCell(i).setCellValue(s);
        }

        try {
            workbook.write(new File("E:\\dd"+new Date().getTime()+".xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
}
