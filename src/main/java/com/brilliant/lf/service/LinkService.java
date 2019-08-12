package com.brilliant.lf.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.brilliant.lf.bean.Link;
import com.brilliant.lf.dao.LinkDao;

@Service
public class LinkService{

    @Resource
    private LinkDao linkDao;

    public int insert(Link pojo){
        return linkDao.insert(pojo);
    }

    public int insertSelective(Link pojo){
        return linkDao.insertSelective(pojo);
    }

    public int insertList(List<Link> pojos){
        return linkDao.insertList(pojos);
    }

    public int update(Link pojo){
        return linkDao.update(pojo);
    }
    public List<Link> getAll(){
        return linkDao.getAll();
    }
}
