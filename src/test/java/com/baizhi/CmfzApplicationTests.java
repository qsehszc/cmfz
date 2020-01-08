/*package com.baizhi;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Banner;
import com.baizhi.service.AdminService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class CmfzApplicationTests {
@Autowired
    AdminService adminService;
@Autowired
    BannerService bannerService;
    @Test
    void contextLoads() {
        Admin admin = adminService.AdminLogin("admin");
        System.out.println(admin);
    }
    @Test
    public void queryAllbanner(){
        List<Banner> banners = bannerService.BannerQueryAll();
        for (Banner banner : banners) {
            System.out.println(banner);
        }
    }

}*/
