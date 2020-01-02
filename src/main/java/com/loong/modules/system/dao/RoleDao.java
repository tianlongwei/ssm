package com.loong.modules.system.dao;

import com.loong.modules.commons.mybatis.annotation.MybatisDao;
import com.loong.modules.system.entity.Role;
import com.loong.modules.system.entity.User;

import java.util.List;

//该标签将
@MybatisDao
public interface RoleDao {
    //获取所有未删除角色信息
    List<Role> getAll();
    //通过role 名称查询
    Role getByNameOrEnname(Role role);
    void addRole(Role role);
    void deleteById(Role role);
    //查询指定用户的所有角色
    List<Role> getRolesByUserId(User user);
    void deleteAllRolesByUserId(User user);
}
