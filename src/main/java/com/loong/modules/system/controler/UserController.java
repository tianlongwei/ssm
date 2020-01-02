package com.loong.modules.system.controler;

import com.loong.modules.system.entity.Role;
import com.loong.modules.system.entity.User;
import com.loong.modules.system.service.RoleService;
import com.loong.modules.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @program: ssm
 * @description:
 * @AUTHOR: tlw
 * @create: 2019-06-22 22:11
 */
@Controller
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;


    @RequestMapping(value = "toAddUser")
    public String toAddUser(User user,
                            Model model){
        //获取所有角色信息
        List<Role> roles = roleService.getAll();
        model.addAttribute("roles",roles);
        System.out.println(user);
        return "system/user/addUser";
    }


    @RequestMapping(value = "list")
    public String list(Model model){
        List<User> users = userService.getAll();
        model.addAttribute("users",users);
        return "system/user/listUser";
    }

    //添加用户
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public String save(User user){
        System.out.println("aaaaaaaaaaa:"+user.toString());
        userService.addUser(user);
        return "redirect:list";
    }

    /**
     * @Author tlw
     * @Description 用户删除功能，删除需要管理员权限
     * @Date  2020/1/2-14:07
     * @Param [user]
     * @return java.lang.String
     **/
    @RequiresRoles(value ="admin")
    @RequestMapping(value = "delete")
    public String delete(User user){
        userService.deleteUser(user);
        return "redirect:list";
    }


    @RequestMapping(value = "edit")
    public String edit(User user,
                       Model model){

        user=userService.getUserById(user);
        List<Role> roles = roleService.getAll();
        model.addAttribute("roles",roles);
        model.addAttribute("user",user);
        System.out.println("编辑前通过id查询："+user.toString());
        return "system/user/editUser";
    }


    @RequestMapping(value = "update")
    public String update(User user){
        System.out.println(user.toString());
        userService.update(user);
        return "redirect:list";
    }
}