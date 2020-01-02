package com.loong.modules.system.service;

import com.loong.modules.system.dao.RoleDao;
import com.loong.modules.system.entity.Role;
import com.loong.modules.system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @program: ssm
 * @description:
 * @author: tlw
 * @create: 2019-12-30 16:13
 */
@Service
public class RoleService {
    @Autowired
    RoleDao roleDao;


    public List<Role> getAll(){
        return roleDao.getAll();
    }

    //检查当前角色在数据库中是否已经存在
    public boolean checkRoleExist(Role role){
        //1、判断当前角色是否已经存在
        //必须name与enname都不相同
        Role dbRole = roleDao.getByNameOrEnname(role);
        if (dbRole==null){
            return false;
        }
        return true;
    }

    //添加角色，
    //问：添加过程出错怎么捕获处理
    public void addRole(Role role){
        //自动生成id
        String id = UUID.randomUUID().toString().replace("-","");
        role.setId(id);
        role.setDel_flag("0");
        roleDao.addRole(role);
    }


    public void deleteRole(Role role){
        roleDao.deleteById(role);
    }

    //查询指定用户的所有角色
    public List<Role> getRolesByUserId(User user){
        return roleDao.getRolesByUserId(user);
    }
}