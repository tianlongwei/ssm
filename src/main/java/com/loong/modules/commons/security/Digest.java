package com.loong.modules.commons.security;

import org.apache.shiro.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @program: ssm
 * @description:加密类
 * @AUTHOR: tlw
 * @create: 2019-06-09 20:54
 */
public class Digest {
    private static String SHA_1="SHA-1";
    private static String MD5="MD5";
    private static Integer SALT_SIZE=8;
    private static Integer HASH_ITERATIONS=1024;

    public static Integer getSaltSize() {
        return SALT_SIZE;
    }

    public static void setSaltSize(Integer saltSize) {
        SALT_SIZE = saltSize;
    }

    public static Integer getHashIterations() {
        return HASH_ITERATIONS;
    }

    public static void setHashIterations(Integer hashIterations) {
        HASH_ITERATIONS = hashIterations;
    }

    public static byte[] digest(String hashAlgorithmName, byte[] source, byte[] salt, int hashIterations){
        byte[] res=null;
        try {
            MessageDigest digest=MessageDigest.getInstance(hashAlgorithmName);
            if (salt!=null){
                digest.update(salt);
            }
            res = digest.digest(source);
            for (int i = 1; i < hashIterations; i++) {
                digest.reset();
                res=digest.digest(res);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
    *@Description:生成随机盐值
    */
    public static byte[] generateSalt(){
        byte[] salt=new byte[SALT_SIZE];
        SecureRandom random=new SecureRandom(salt);
        random.nextBytes(salt);
        return salt;
    }

    /**
    *@Description: MD5加密
    */
    public static byte[] MD5(byte[] source,byte[] salt,int hashIterations){
        return digest("MD5",source,salt,hashIterations);
    }
    public static byte[] MD5(byte[] source,byte[] salt){
        return digest("MD5",source,salt,1);
    }
    public static byte[] MD5(byte[] source){
        return digest("MD5",source,null,1);
    }
    
    /**
    *@Description:SHA-1
    */
    public static byte[] SHA1(byte[] source,byte[] salt,int hashIterations){
        return digest("SHA-1",source,salt,hashIterations);
    }
    public static byte[] SHA1(byte[] source,byte[] salt){
        return digest("SHA-1",source,salt,1);
    }
    public static byte[] SHA1(byte[] source){
        return digest("SHA-1",source,null,1);
    }

    /**
    *@Description: 生成密码
    */
    public static String generatePwd(String passowrd){
        //生成随机盐值
        byte[] salt=generateSalt();
        byte[] res = SHA1(passowrd.getBytes(), salt, HASH_ITERATIONS);
        String pwd = Hex.encodeToString(salt) + Hex.encodeToString(res);
        return pwd;
    }

}