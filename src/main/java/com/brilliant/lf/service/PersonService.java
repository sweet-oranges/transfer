package com.brilliant.lf.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.brilliant.lf.bean.Person;
import com.brilliant.lf.dao.PersonDao;

@Service
public class PersonService{

    @Resource
    private PersonDao personDao;

    public int insert(Person pojo){
        return personDao.insert(pojo);
    }

    public int insertSelective(Person pojo){
        return personDao.insertSelective(pojo);
    }

    public int insertList(List<Person> pojos){
        return personDao.insertList(pojos);
    }

    public int update(Person pojo){
        return personDao.update(pojo);
    }

    public List<Person> getAll(){
        return personDao.getAll();
    }
}
