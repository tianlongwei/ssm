package com.loong.modules.system.controler;

import com.loong.modules.commons.security.Digest;
import com.loong.modules.system.entity.User;
import com.loong.modules.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SystemController {
    @Autowired
    UserService userService;

    @RequestMapping("hello")
    public String hello(Model model){
        model.addAttribute("name","ttl");
        return "/static/hello.jsp";
    }


    @RequestMapping("list")
    @ResponseBody
    public List<User> list(){
        return userService.getAll();
    }


    /**
    *@Description: 登陆操作
    *@Param: [username, password, request]
    *@return: java.lang.String
    *@Author: tlw
    *@Created: 2019/6/9-21:24 
    */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request){
        System.out.println("登陆使用的用户名与密码为："+username+":"+password);
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            UsernamePasswordToken token=new UsernamePasswordToken(username,password);
            try {
                subject.login(token);
            }catch (AuthenticationException e){
                //登陆失败时
                System.out.println(e.getMessage());
            }
        }
        //登陆成功时
        User user = userService.getUserByLoginName(username);
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        return "redirect:static/list.jsp";
    }


    @RequestMapping("/updatePwd")
    public String updatePwd(@RequestParam("pwd") String pwd,
                            HttpServletRequest request){
        String pass= Digest.generatePwd(pwd);
        System.out.println("加密后的密码为："+pass);
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");
        user.setPassword(pass);
        userService.updatePwd(user);
        return "redirect:static/list.jsp";
    }
}
