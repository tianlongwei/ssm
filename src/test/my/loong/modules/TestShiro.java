package my.loong.modules;

import com.loong.modules.commons.security.Digest;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;

import java.security.Key;

/**
 * @program: ssm
 * @description:
 * @AUTHOR: tlw
 * @create: 2019-06-11 22:03
 */
public class TestShiro {
//https://blog.csdn.net/zt_fucker/article/details/72364959
//https://blog.csdn.net/u012437781/article/details/78366305
//https://www.cnblogs.com/maofa/p/6407102.html

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
        System.out.println(hashRequest.getAlgorithmName());//
    }

    @Test
    public void testDefaultHashService(){
        DefaultHashService service=new DefaultHashService();
        service.setHashAlgorithmName("SHA-512");
        service.setHashIterations(1);
        service.setPrivateSalt(new SimpleByteSource("123456"));
        service.setGeneratePublicSalt(true);
        service.setRandomNumberGenerator(new SecureRandomNumberGenerator());

        HashRequest hashRequest=new HashRequest.Builder()
                .setAlgorithmName("SHA-1")
                .setIterations(1024)
                .setSalt(Hex.decode("f822a594c3d50454"))
                .setSource("admin")
                .build();
        Hash hash = service.computeHash(hashRequest);
        System.out.println(hash);//35392f7a4377a3e9949a31f5b191ace8025e506e
        System.out.println(service.getHashAlgorithmName());//SHA-512
        System.out.println(service.getHashIterations());//1
        System.out.println(new String(service.getPrivateSalt().getBytes()));//123456
        System.out.println(service.getRandomNumberGenerator());
        System.out.println("-0-----------------");
        System.out.println(hash.getAlgorithmName());//SHA-1
        System.out.println(hash.getIterations());//1024
        System.out.println(hash.getSalt().toHex());//f822a594c3d50454
    }


    //https://jinnianshilongnian.iteye.com/blog/2021439
    @Test
    public void testAesCipherService(){
        AesCipherService service=new AesCipherService();
        service.setKeySize(128);
        //1、生成key
        Key key = service.generateNewKey();
        System.out.println(Hex.encodeToString(key.getEncoded()));//0511151c7623ffa250a9a09e15c37e71(32)
        System.out.println(key.getAlgorithm());//aes
        //2、明文
        String text="admin";
        //3、加密
        String plainText = service.encrypt(text.getBytes(), key.getEncoded()).toHex();
        System.out.println(plainText);//2c499806f75701182d48a44ff222fd40e69da6e1701bc8dec15c376653c8dbd8
        //4、解密
        String pass = new String(service.decrypt(Hex.decode(plainText), key.getEncoded()).getBytes());
        System.out.println(pass);//admin
    }
}