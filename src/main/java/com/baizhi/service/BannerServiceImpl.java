package com.baizhi.service;

import com.baizhi.annotation.LogAnnotation;
import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BannerServiceImpl implements BannerService{
    @Autowired
    BannerDao bannerDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> selectAll() {
        List<Banner> banners = bannerDao.selectAll();
        return banners;
    }
    @LogAnnotation(value = "查询轮播图信息")
    public Map getAllBanners(Integer page, Integer rows) {
        // records 总条数 page 当前页 rows 数据 total 总页数
        HashMap hashMap = new HashMap();
        // bannerDao.selectCount(null); 不传入任何条件 查所有
        Integer records = bannerDao.selectCount(null);
        // 三目运算符 求总页数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        List<Banner> banners = bannerDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
        hashMap.put("records", records);
        hashMap.put("page", page);
        hashMap.put("total", total);
        hashMap.put("rows", banners);
        return hashMap;
    }
}
