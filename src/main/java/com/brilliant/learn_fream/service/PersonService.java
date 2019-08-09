package com.brilliant.learn_fream.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.brilliant.learn_fream.bean.Person;
import com.brilliant.learn_fream.dao.PersonDao;

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
