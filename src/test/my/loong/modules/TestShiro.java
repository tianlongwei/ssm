package my.loong.modules;

import com.loong.modules.commons.security.Digest;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

/**
 * @program: ssm
 * @description:
 * @AUTHOR: tlw
 * @create: 2019-06-11 22:03
 */
public class TestShiro {

    @Test
    public void testMD5(){
        byte[] salt = Digest.generateSalt();
        System.out.println("盐值为："+Hex.encodeToString(salt));//f822a594c3d50454
        byte[] result = Digest.MD5("admin".getBytes(), salt, 1024);
        System.out.println("密码为："+Hex.encodeToString(result));//f2c5049f0f3ef0e4d6e6e786c7a570bb
    }


    @Test
    public void testSHA1(){
        byte[] salt = Digest.generateSalt();
        System.out.println("盐值为："+Hex.encodeToString(salt));//f822a594c3d50454
        byte[] result = Digest.SHA1("admin".getBytes(), salt, 1024);
        System.out.println("密码为："+Hex.encodeToString(result));//d13836d980f2bc31e3b5786a299a6fdb66405647
    }



    /**
     *@Description:使用shiro自带的Cryptography来获取随机盐值
     */
    @Test
    public void testShiro(){
        SecureRandomNumberGenerator generator=new SecureRandomNumberGenerator();
        ByteSource byteSource = generator.nextBytes(8);
        System.out.println(Hex.encodeToString(byteSource.getBytes()));
        System.out.println(byteSource);
    }
    /**
     *@Description: shiro自带的工具来加密
     */
    @Test
    public void testShiro1(){
        Md5Hash md5Hash=new Md5Hash("admin",Hex.decode("f822a594c3d50454"),1024);
        System.out.println(md5Hash.toHex());//f2c5049f0f3ef0e4d6e6e786c7a570bb
    }

    @Test
    public void testShiroSha1(){
        Sha1Hash sha1Hash=new Sha1Hash("admin",Hex.decode("f822a594c3d50454"),1024);
        System.out.println(sha1Hash.toHex());//d13836d980f2bc31e3b5786a299a6fdb66405647
    }

    /**
     *
     *
    */

    @Test
    public void testHashRequest(){
        HashRequest hashRequest=new HashRequest.Builder()
                .setAlgorithmName("SHA-1")
                .setIterations(1024)
                .setSalt(Hex.decode("f822a594c3d50454"))
                .setSource("admin")
                .build();

        DefaultHashService service=new DefaultHashService();

        Hash hash = service.computeHash(hashRequest);
        System.out.println(hash);//d13836d980f2bc31e3b5786a299a6fdb66405647
        System.out.println("私钥:"+service.getPrivateSalt());//null
        System.out.println("公钥:"+service.getPrivateSalt());//null
        System.out.println(hashRequest.getAlgorithmName());
    }

}