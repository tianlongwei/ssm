package com.loong.modules.system.dao;


import com.loong.modules.commons.mybatis.annotation.MybatisDao;
import com.loong.modules.system.entity.User;

import java.util.List;

@MybatisDao
public interface UserDao {
    List<User> getAll();
}
