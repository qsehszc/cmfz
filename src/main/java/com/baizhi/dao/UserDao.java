package com.baizhi.dao;

import com.baizhi.entity.MapUserDto;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    Integer queryUserByTime(@Param("sex") String sex, @Param("day") Integer day);
    List<MapUserDto> queryLocationBySex(String sex);
}

