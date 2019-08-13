package com.brilliant.lf.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.brilliant.lf.bean.Subscribe;

@Mapper
public interface SubscribeDao {
    int insert(@Param("pojo") Subscribe pojo);

    int insertSelective(@Param("pojo") Subscribe pojo);

    int insertList(@Param("pojos") List<Subscribe> pojo);

    int update(@Param("pojo") Subscribe pojo);

    List<Subscribe> getAll();

    int getMaxId();

    void delSubscribeById(int id);

    List<Subscribe> getSubscribeByPort(String port);
}
