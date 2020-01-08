package com.baizhi.service;


import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("guruService")
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    GuruDao guruDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Guru> selectAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }
}
