package com.loong.modules.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//必须要试下序列化接口才能使用cache工具持久化到磁盘中
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    //逻辑删除标志 0正常 1删除 2冻结,默认为0
    private String del_flag;

    //用户角色
    private List<Role> roleList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }


    //用于注入List<Role>对象
    public List<String> getRoleIdList() {
        List<String> roleIdList = new ArrayList<>();

        if (roleList != null) {
            for (int i = 0; i < roleList.size(); i++) {
                roleIdList.add(i, roleList.get(i).getId());
            }
        }
        return roleIdList;
    }


    public void setRoleIdList(List<String> roleIdList) {
        if (roleIdList == null) {

        } else {
            for (int i = 0; i < roleIdList.size(); i++) {
                Role role = new Role();
                role.setId(roleIdList.get(i));
                roleList.add(role);
            }
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", del_flag='" + del_flag + '\'' +
                ", roleList=" + roleList +
                '}';
    }
}
