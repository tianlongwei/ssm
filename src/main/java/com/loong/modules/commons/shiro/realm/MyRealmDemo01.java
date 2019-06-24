package com.loong.modules.commons.shiro.realm;

import com.loong.modules.system.entity.User;
import com.loong.modules.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * @program: ssm
 * @description:检查当前用户不能重复登录的realm
 * @AUTHOR: tlw
 * @create: 2019-06-22 22:40
 */
public class MyRealmDemo01 extends AuthenticatingRealm {
    @Autowired
    UserService userService;

    @Autowired
    EnterpriseCacheSessionDAO enterpriseCacheSessionDAO;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken uptoken= (UsernamePasswordToken) token;
        String username = uptoken.getUsername();
        System.out.println(username);
        //在数据库中查找，检查该用户状态：是否存在、是否锁定等
        User user = userService.getUserByLoginName(username);
        if (user==null){
            throw new UnknownAccountException("该用户不存在");
        }
        //检查登录的session中是否已经登录
        Collection<Session> activeSessions = enterpriseCacheSessionDAO.getActiveSessions();
        for (Session s:activeSessions
             ) {
            User user1 = (User) s.getAttribute("user");
            if (user1!=null&&user1.getUsername().equals(username)){
                System.out.println("当前用户已经登录");
            }
        }


        System.out.println("数据库获取到的账户信息："+"用户名："+username+"密码："+user.getPassword());
        System.out.println(user.getPassword().substring(0,16));
        System.out.println(user.getPassword().substring(16));
        //如果用户存在，则还需要对其密码进行验证
        SimpleAuthenticationInfo info;
        //用户信息
        Object pricipal=username;
        //hash后的密码
        Object hashedCredentials=user.getPassword().substring(16);
        //hash时使用的盐值
        ByteSource credentialsSalt=ByteSource.Util.bytes(Hex.decode(user.getPassword().substring(0,16)));
        //当前realm的名字
        String realmName=getName();
        //创建当前的服务端的账户信息
        info=new SimpleAuthenticationInfo(pricipal,hashedCredentials,credentialsSalt,realmName);
        return info;
    }
}