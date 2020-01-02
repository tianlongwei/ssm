package com.loong.modules.system.controler;

import com.loong.modules.system.entity.User;
import com.loong.modules.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
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
import java.util.*;

@Controller
public class SystemController {
    @Autowired
    UserService userService;

    @Autowired
    EnterpriseCacheSessionDAO dao;

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
        //System.out.println("servlet中session中的user值为："+session.getAttribute("user"));//null
        return "redirect:static/list.jsp";
    }


    @RequestMapping("/updatePwd")
    public String updatePwd(@RequestParam("pwd") String pwd,
                            HttpServletRequest request){
        //生成密码
        String generatePwd = userService.generatePwd(pwd);
        System.out.println("加密后的密码为："+generatePwd);
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");
        user.setPassword(generatePwd);
        userService.updatePwd(user);
        return "redirect:static/list.jsp";
    }

    /**
    *@Description: 显示shiro管理的所有session值
    */
    @RequestMapping("showSession")
    @ResponseBody
    public Map<String,Object> showSession(){
        Map<String,Object> map=new HashMap<>();
        //获取EnterpriseCacheSessionDAO中所有的session对象
        Collection<Session> activeSessions = dao.getActiveSessions();
        Iterator<Session> sessionIterator = activeSessions.iterator();
/*        while (sessionIterator.hasNext()){
            Session s= sessionIterator.next();
            System.out.println("session的id为："+s.getId());
            //获取属性中的值
            Collection<Object> attributeKeys = s.getAttributeKeys();
            Iterator<Object> iterator = attributeKeys.iterator();
            while (iterator.hasNext()){
                //获取到所有的key值
                Object key=iterator.next();
                System.out.println("当前session对象的key值为："+key);//shiroSavedRequest
                //获取key值对应的值
                Object attribute = s.getAttribute(key);
                System.out.println("当前session对象的值为："+attribute);
                if (attribute instanceof SavedRequest){
                    System.out.println("*********************************************************************");
                    System.out.println("SavedRequest为key值时");
                    //如果值是SavedRequest类型
                    SavedRequest request= (SavedRequest) attribute;
                    System.out.println(request.getMethod());
                    System.out.println(request.getQueryString());
                    System.out.println(request.getRequestURI());
                    System.out.println(request.getRequestUrl());
                    System.out.println("*********************************************************************");
                }
            }
        }*/
/*        while (sessionIterator.hasNext()){
            Session session = sessionIterator.next();
            System.out.println("id:"+session.getId());
            System.out.println("登陆主机为："+session.getHost());
            System.out.println("用户为："+session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));//admin
            System.out.println(session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY));//true
        }*/

        //保存user对象到map中
/*        while (sessionIterator.hasNext()){
            Session session = sessionIterator.next();
            Object user = session.getAttribute("user");
            if (user!=null){
                map.put(session.getId().toString(),user);
            }
        }*/
        //获取当前会话的认证对象
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());//admin，与获取的对象有关
        //获取当前会话的session
        Session session = subject.getSession();
        System.out.println(session.getId());
        return map;
    }

}
