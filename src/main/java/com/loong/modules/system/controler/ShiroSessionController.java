package com.loong.modules.system.controler;

import com.loong.modules.system.entity.User;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @program: ssm
 * @description:shiro会话管理
 * @AUTHOR: tlw
 * @create: 2019-06-23 09:03
 */
@Controller
public class ShiroSessionController {

    @Autowired
    EnterpriseCacheSessionDAO sessionDAO;


    @RequestMapping("listSession")
    public String listSession(Model model){
        Map<String,Object> sessions=new HashMap<>();
        //获取所有的session
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        Iterator<Session> iterator = activeSessions.iterator();
        while (iterator.hasNext()){
            Session session = iterator.next();
            //获取其中key值为"user"的值
            User user = (User) session.getAttribute("user");
            if (user!=null){
                sessions.put(session.getId().toString(),user);
            }
        }
        model.addAttribute("sessions",sessions);
        return "system/listSession";
    }


    @RequestMapping("deleteSession")
    public String deleteSession(String sessionId){
        //在activesession中删除
        System.out.println("删除的session为："+sessionId);
        //
        List<Session> sessionList = new ArrayList<>();
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        Iterator<Session> iterator = activeSessions.iterator();
        while (iterator.hasNext()){
            Session session = iterator.next();
            System.out.println("保存用户信息的id为："+session.getId());
            if (session.getId().equals(sessionId)){
                //sessionDAO.delete(session);
                System.out.println("找到了该");
                sessionList.add(session);
            }
        }
        for (int i = 0; i < sessionList.size(); i++) {
//            sessionDAO.delete(sessionList.get(i));
            System.out.println("删除了id为："+sessionList.get(i).getId()+"的session");
            sessionList.get(i).setTimeout(0);
        }
        return "redirect:listSession";
    }
}