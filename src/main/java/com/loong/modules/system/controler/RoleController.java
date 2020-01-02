package com.loong.modules.system.controler;

import com.loong.modules.system.entity.Role;
import com.loong.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @program: ssm
 * @description:
 * @author: tlw
 * @create: 2019-12-30 17:01
 */
@Controller
@RequestMapping(value = "role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String getAllRoles(Model model){
        List<Role> roles = roleService.getAll();
        model.addAttribute("roles",roles);
        return "system/role/listRole";
    }

    /*
     * @Author tlw
     * @Description 去添加角色页面
     * @Date  2019/12/30-17:45
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping(value = "toAddRole",method = RequestMethod.GET)
    public String toAddRole(){
        return "system/role/addRole";
    }

    @RequestMapping(value = "list")
    public String list(Model model){
        List<Role> roles = roleService.getAll();
        model.addAttribute("roles",roles);
        return "system/role/listRole";
    }

    @RequestMapping(value = "addRole")
    public String addRole(Role role,
                          Model model){
        //缺少对象验证功能，后台验证是否为空
        boolean isexist = roleService.checkRoleExist(role);
        String message="";
        if (isexist){
            //已经存在，提醒
            message="该角色已经存在";
            model.addAttribute("message",message);
            return "system/role/addRole";
        }else {
            //不存在直接添加该角色
            roleService.addRole(role);
        }
        //无论是否添加成功，都返回添加页面.并提示添加成功或者失败消息
        return "redirect:list";
    }

    @RequestMapping(value = "delete")
    public String delete(Role role){
        roleService.deleteRole(role);
        return "redirect:list";
    }



    @RequestMapping(value = "testa")
    public String test(){
        return "system/hello";
    }

}