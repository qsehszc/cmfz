package com.baizhi.service;


import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map selectPage(Integer curPage, Integer pageSize) {
        HashMap hashMap = new HashMap();
        // 设置当前页 page
        hashMap.put("page",curPage);
        // 设置总行数 records
        int selectCount = userDao.selectCount(null);
        hashMap.put("records",selectCount);
        // 设置总页 total
        hashMap.put("total",selectCount%pageSize==0? selectCount/pageSize:selectCount/pageSize+1);
        // 设置当前页的数据行 rows
        hashMap.put("rows",userDao.selectByRowBounds(null,new RowBounds((curPage-1)*pageSize,pageSize)));
        return hashMap;
    }

    @Override
    public void insert(User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.updateByPrimaryKeySelective(user);

    }

    @Override
    public void delete(List ids) {
        userDao.deleteByIdList(ids);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map showUserTime(){
        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userDao.queryUserByTime("0",1));
        manList.add(userDao.queryUserByTime("0",7));
        manList.add(userDao.queryUserByTime("0",30));
        manList.add(userDao.queryUserByTime("0",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.queryUserByTime("1",1));
        womenList.add(userDao.queryUserByTime("1",7));
        womenList.add(userDao.queryUserByTime("1",30));
        womenList.add(userDao.queryUserByTime("1",365));
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map showMap(){
        HashMap hashMap = new HashMap();
        hashMap.put("man",userDao.queryLocationBySex("0"));
        hashMap.put("women",userDao.queryLocationBySex("1"));
        return hashMap;
    }
}
