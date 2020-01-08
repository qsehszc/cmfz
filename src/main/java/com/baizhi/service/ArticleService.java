package com.baizhi.service;


import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //分页查
    Map selectPage(Integer curPage, Integer pageSize);
    //添加
    Map insert(Article article);
    //修改
    void update(Article article);
    //删除
    void delete(List ids);
}
