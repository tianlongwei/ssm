package com.loong.modules.system.controler;

import com.loong.modules.system.entity.User;
import com.loong.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SystemController {
    @Autowired
    UserService userService;

    @RequestMapping("hello")
    public String hello(Model model){
        model.addAttribute("name","ttl");
        return "hello";
    }


    @RequestMapping("list")
    @ResponseBody
    public List<User> list(){
        return userService.getAll();
    }
}
