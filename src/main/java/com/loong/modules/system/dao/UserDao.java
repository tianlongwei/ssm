package com.loong.modules.system.dao;


import com.loong.modules.commons.mybatis.annotation.MybatisDao;
import com.loong.modules.system.entity.User;

import java.util.List;

@MybatisDao
public interface UserDao {
    List<User> getAll();
    User getUserByLoginName(String loginName);
    User getUserById(User user);
    void updatePwd(User user);
    void addUser(User user);
    void deleteById(User user);
    //插入用户角色关系表
    void insertUserRole(User user);
    void update(User user);
}
