package com.loong.modules.system.service;

import com.loong.modules.system.dao.UserDao;
import com.loong.modules.system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public List<User> getAll(){
        return userDao.getAll();
    }
}
