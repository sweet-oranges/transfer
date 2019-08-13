package com.brilliant.lf.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.brilliant.lf.bean.Link;

@Mapper
public interface LinkDao {
    int insert(@Param("pojo") Link pojo);

    int insertSelective(@Param("pojo") Link pojo);

    int insertList(@Param("pojos") List<Link> pojo);

    int update(@Param("pojo") Link pojo);

    List<Link> getAll();

    //根据端口获取所有主题
    List<Link> getTopicByPort(String port);

    void addTopic(String port,String topic);

    int getMaxId();
}
