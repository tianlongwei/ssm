package com.loong.modules.system.service;

import com.loong.modules.system.dao.RoleDao;
import com.loong.modules.system.dao.UserDao;
import com.loong.modules.system.entity.Role;
import com.loong.modules.system.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;

    public List<User> getAll(){
        List<User> users = userDao.getAll();
        for (int i = 0; i < users.size(); i++) {
            List<Role> roles = roleDao.getRolesByUserId(users.get(i));
            users.get(i).setRoleList(roles);
        }
        return users;
    }

    public User getUserByLoginName(String loginName){
        return userDao.getUserByLoginName(loginName);
    }

    public User getUserById(User user){
        user=userDao.getUserById(user);
        List<Role> roles = roleDao.getRolesByUserId(user);
        user.setRoleList(roles);
        return user;
    }

    public void updatePwd(User user){
        userDao.updatePwd(user);
    }


    public void addUser(User user){
        String id = UUID.randomUUID().toString().replace("-", "");
        user.setId(id);
        user.setDel_flag("0");
        String password = user.getPassword();
        user.setPassword(generatePwd(password));
        if (user.getRoleList().size()>0){
            userDao.insertUserRole(user);
        }
        userDao.addUser(user);
    }

    public void update(User user){
        //先删除当前对象的角色，再添加
        roleDao.deleteAllRolesByUserId(user);
        if (user.getRoleList().size()>0){
            userDao.insertUserRole(user);
        }
        userDao.update(user);
    }

    //生成加密后的密码,使用shiro自己的加密方式
    public String generatePwd(String password){
        SecureRandomNumberGenerator generator=new SecureRandomNumberGenerator();
        ByteSource salt = generator.nextBytes(8);

        HashRequest request=new HashRequest.Builder()
                .setAlgorithmName("SHA-1")
                .setIterations(1024)
                .setSource(password)
                .setSalt(salt)
                .build();

        DefaultHashService service=new DefaultHashService();
        Hash hash = service.computeHash(request);
        return salt.toHex()+hash.toHex();
    }

    public void deleteUser(User user){
        userDao.deleteById(user);
    }

}
