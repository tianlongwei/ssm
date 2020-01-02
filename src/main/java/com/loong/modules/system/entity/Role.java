package com.loong.modules.system.entity;

import java.io.Serializable;

/**
 * @program: ssm
 * @description: 角色
 * @author: tlw
 * @create: 2019-12-30 15:03
 */
public class Role implements Serializable{

    //角色id
    private String id;
    //角色名称
    private String name;
    //角色英文名，用于判断用户角色
    private String enname;
    //角色描述
    private String description;
    //逻辑删除标志 0正常 1删除 2冻结,默认为0
    private String del_flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", enname='" + enname + '\'' +
                ", description='" + description + '\'' +
                ", del_flag='" + del_flag + '\'' +
                '}';
    }
}