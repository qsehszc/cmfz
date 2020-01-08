package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    //分页
    Map getAllBanners(Integer page, Integer rows);
    List<Banner> selectAll();
}
