package com.baizhi.service;


import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map selectPage(Integer curPage, Integer pageSize) {
        HashMap hashMap = new HashMap();
        // 设置当前页 page
        hashMap.put("page",curPage);
        // 设置总行数 records
        int selectCount = articleDao.selectCount(null);
        hashMap.put("records",selectCount);
        // 设置总页 total
        hashMap.put("total",selectCount%pageSize==0? selectCount/pageSize:selectCount/pageSize+1);
        // 设置当前页的数据行 rows
        hashMap.put("rows",articleDao.selectByRowBounds(null,new RowBounds((curPage-1)*pageSize,pageSize)));
        return hashMap;
    }

    @Override
    public Map insert(Article article) {
        String id = UUID.randomUUID().toString();
        article.setId(id);
        article.setCreateDate(new Date());
        article.setPublishDate(new Date());
        articleDao.insert(article);
        HashMap hashMap = new HashMap();
        hashMap.put("articleId",id);
        return hashMap;
    }

    @Override
    public void update(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public void delete(List ids) {
        articleDao.deleteByIdList(ids);
    }
}
