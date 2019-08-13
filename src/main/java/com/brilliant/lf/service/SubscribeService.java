package com.brilliant.lf.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.brilliant.lf.bean.Subscribe;
import com.brilliant.lf.dao.SubscribeDao;

@Service
public class SubscribeService{

    @Resource
    private SubscribeDao subscribeDao;

    public int insert(Subscribe pojo){
        return subscribeDao.insert(pojo);
    }

    public int insertSelective(Subscribe pojo){
        return subscribeDao.insertSelective(pojo);
    }

    public int insertList(List<Subscribe> pojos){
        return subscribeDao.insertList(pojos);
    }

    public int update(Subscribe pojo){
        return subscribeDao.update(pojo);
    }

    public List<Subscribe> getAll(){
        return subscribeDao.getAll();
    }

    public int getMaxId(){
        return subscribeDao.getMaxId();
    }

    public void deleteSubscribeById(int id){
        subscribeDao.delSubscribeById(id);
    }

    public List<Subscribe> getSubscribeByPort(String port){
       return subscribeDao.getSubscribeByPort(port);
    }
}
