package com.loong.modules.system.service;

import com.loong.modules.system.dao.UserDao;
import com.loong.modules.system.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public List<User> getAll(){
        return userDao.getAll();
    }

    public User getUserByLoginName(String loginName){
        return userDao.getUserByLoginName(loginName);
    }

    public void updatePwd(User user){
        userDao.updatePwd(user);
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
}
