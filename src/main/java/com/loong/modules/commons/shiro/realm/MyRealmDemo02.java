package com.loong.modules.commons.shiro.realm;

import com.loong.modules.commons.shiro.bytesource.MyByteSource;
import com.loong.modules.system.entity.Role;
import com.loong.modules.system.entity.User;
import com.loong.modules.system.service.RoleService;
import com.loong.modules.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: ssm
 * @description:认证授权,继承自AuthorizingRealm，而AuthorizingRealm继承了AuthenticatingRealm
 * 所以该类可以完成认证和授权两个功能
 * @author: tlw
 * @create: 2020-01-01 14:46
 */
public class MyRealmDemo02 extends AuthorizingRealm {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    //完成授权功能
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Set<String> roles=new HashSet<>();
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.getUserByLoginName(username);
        List<Role> roleList = roleService.getRolesByUserId(user);

        for (int i = 0; i < roleList.size(); i++) {
            String enname = roleList.get(i).getEnname();
            roles.add(enname);
        }

        //参数为由用户角色组成的set集合
        //设置角色信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        //还可以设置当前用户所有的资源权限信息
        Set<String> stringPermissions=new HashSet<>();
        //每个用户都添加用户查看权限
        if (username.equals("admin")){
            stringPermissions.add("user:edit");
            stringPermissions.add("user:delete");
        }
        info.setStringPermissions(stringPermissions);
        return info;
    }



    //完成认证功能
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //转化成其实现类
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        //前端登录时输入的明文用户名
        String username = token.getUsername();
        //在数据库查询是否存在该对象
        User user = userService.getUserByLoginName(username);
        if (user==null){
            //登录用户不存在，或者已经删除、冻结。抛出异常
            throw new UnknownAccountException("当前用户不存在");
        }
        //已经查到该用户则获取数据库保存的密码，交给shiro进行验证
        //1、用户对象
        Object principal=username;
        //2、数据库保存的经过散列算法计算后的密码
        Object hashedCredentials=user.getPassword().substring(16);
        //3、盐值
        ByteSource credentialsSalt=new MyByteSource(Hex.decode(user.getPassword().substring(0,16)));
        //4、当前realm的名称
        String realmName=getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,hashedCredentials,credentialsSalt,realmName);
        return info;
    }
}