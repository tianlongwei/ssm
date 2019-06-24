package com.loong.modules.system.controler;

import com.loong.modules.system.entity.User;
import com.loong.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: ssm
 * @description:
 * @AUTHOR: tlw
 * @create: 2019-06-22 22:11
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    //添加用户
    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    public String adduser(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        User user=new User();
        user.setUsername(username);
        String generatePwd = userService.generatePwd(password);
        user.setPassword(generatePwd);
        userService.addUser(user);
        return "redirect:/static/list.jsp";
    }

}