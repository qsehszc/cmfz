package com.baizhi.service;


import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //分页查
    Map selectPage(Integer curPage, Integer pageSize);
    //添加
    void insert(User user);
    //修改
    void update(User user);
    //删除
    void delete(List ids);
    //根据性别时间查用户数量
    Map showUserTime();
    //根据性别查地区分布
    Map showMap();
}
