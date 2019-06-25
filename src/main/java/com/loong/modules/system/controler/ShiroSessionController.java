package com.loong.modules.system.controler;

import com.loong.modules.system.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.Subject;
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
/*            User user = (User) session.getAttribute("user");
            if (user!=null){
                sessions.put(session.getId().toString(),user);
            }*/
        }
        model.addAttribute("sessions",activeSessions);
        return "system/listSession";
    }


    @RequestMapping("deleteSession")
    public String deleteSession(String sessionId){
        //在activesession中删除
        List<Session> sessionList = new ArrayList<>();
        Collection<Session> activeSessions = sessionDAO.getActiveSessions();
        Iterator<Session> iterator = activeSessions.iterator();
        while (iterator.hasNext()){
            Session session = iterator.next();
            System.out.println("保存用户信息的id为："+session.getId());
            if (session.getId().equals(sessionId)){
                //sessionDAO.delete(session);
                System.out.println("找到了当前需要删除的sessionID:"+session.getId());
                sessionList.add(session);
            }
        }

        //不能删除当前对象的session
        Subject subject = SecurityUtils.getSubject();
        Session currentSession = subject.getSession();

        for (int i = 0; i < sessionList.size(); i++) {
            Session session = sessionList.get(i);
            //跳过当前会话session
            if (currentSession.getId().equals(session.getId())){
                System.out.println("当前会话session不会删除："+session.getId());
                continue;
            }
            System.out.println("删除了id为："+sessionList.get(i).getId()+"的session");
            sessionDAO.delete(session);//直接删除session
//            session.setTimeout(0);
//            sessionList.get(i).stop();
        }
        return "redirect:listSession";
    }
}