package my.loong.modules;

import com.loong.modules.commons.security.Digest;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.BlowfishCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @program: ssm
 * @description:
 * @AUTHOR: tlw
 * @create: 2019-06-11 22:03
 */
public class TestShiro {
//https://blog.csdn.net/zt_fucker/article/details/72364959
//https://blog.csdn.net/u012437781/article/details/78366305
//https://www.cnblogs.com/maofa/p/6407102.html *****

    /**
     * *********************************散列算法***********************************************
    */

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
        System.out.println(Hex.encodeToString(byteSource.getBytes()));//8ccd6a9afda380a7
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


    /**
     *  **********************************对称加密**************************************************
    */


    //https://jinnianshilongnian.iteye.com/blog/2021439
    /**
     * AES 高级加密标准（Advanced Encryption Standard） 对称加密  区块加密算法
     * AES的区块长度固定为128 比特，密钥长度则可以是128，192或256比特,密文长度固定为256位
     * 特点：速度快 适用于大数据量加密
     * 前任标准des   高级版 3des
     *
    */
    @Test
    public void testAesCipherService(){
        AesCipherService service=new AesCipherService();
        service.setKeySize(128);
        //1、生成key
        Key key = service.generateNewKey();
        System.out.println("秘钥为："+Hex.encodeToString(key.getEncoded()));//0511151c7623ffa250a9a09e15c37e71(32)
        System.out.println("秘钥长度为："+key.getEncoded().length*8);//128,192,256
        System.out.println(key.getAlgorithm());//aes
        //2、明文
        String text="admin";
        //3、加密
        String plainText = service.encrypt(text.getBytes(), key.getEncoded()).toHex();
        System.out.println("密文为:"+plainText);//2c499806f75701182d48a44ff222fd40e69da6e1701bc8dec15c376653c8dbd8(64)
        System.out.println("密文长度为："+Hex.decode(plainText).length*8);//256,256,256
        //4、解密
        String pass = new String(service.decrypt(Hex.decode(plainText), key.getEncoded()).getBytes());
        System.out.println(pass);//admin
    }

    //https://www.cnblogs.com/dengpengbo/p/10325930.html
    /**
     * blowfish 区块加密算法  对称加密 分组加密算法
     * 特点：秘钥可变长(32-256) 速度优于des 加密64位字符串 免费
     * 过程：
     *      加密：秘钥预处理 信息加密
     *      解密：秘钥预处理 信息解密
    */
    @Test
    public void testBlowfishCipherService(){
        BlowfishCipherService service=new BlowfishCipherService();
        //生成加密秘钥
        Key key = service.generateNewKey(256);
        System.out.println("秘钥为："+Hex.encodeToString(key.getEncoded()));//538cd1fa20389f1510e662643384d2c7
        System.out.println("秘钥长度为："+key.getEncoded().length*8);//32,64,128,256
        //明文
        String text="admin";
        //加密
        ByteSource encrypt = service.encrypt(text.getBytes(), key.getEncoded());
        System.out.println("密文为:"+encrypt.toHex());//9dc968c566b3f475054eeca6abae3bee(32)
        System.out.println("密文长度为："+encrypt.getBytes().length*8);//128,128,128,128
        //解密
        ByteSource decrypt = service.decrypt(encrypt.getBytes(), key.getEncoded());
        System.out.println(new String(decrypt.getBytes()));
}

    /**
     * *****************************使用jdk自带的工具进行非对称加密解密**********************************************************
     */
    //https://blog.csdn.net/qy20115549/article/details/83105736
    //RSA 算法 非对称算法
    @Test
    public void testRsa(){
        try {
            KeyPairGenerator generator= KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024,new SecureRandom());
            KeyPair keyPair = generator.genKeyPair();
            PrivateKey aPrivate = keyPair.getPrivate();
            PublicKey aPublic = keyPair.getPublic();
            System.out.println("私钥为："+Base64.encodeToString(aPrivate.getEncoded()));
            //MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJtEFXK57d7eIFffwECeP8OYGjkmcHiZoe4/dcD/+xu15yxQyiiFrxxG4hagtptvHFMXuT2MBixs/35GYoh8nZAoVmcnbMWGW7ds7j2cDvs7k9u/7sNdMY/MtGLBqCQxHLyTINQxWDaWz+PZ/4uVsF0Y4vi8BgGsZmd6uVzs1RQtAgMBAAECgYAe/jYx0n9zD2/xWo6r12rvBjul0NB+olw6jJxazD45x9O/etj/wb+0q0mSLr6ExGcpBwFJEdxhndhaQn6EtCtXPFoavm5qRehrMivtYrOckeh0B9c2eCEl3c5Om04kCL5e4NVeZOa+uShpMPPRyLDtlpUeY6Z4VepQS2YrubXRwQJBAPBFbsZD5cpsm8Z7WaF6gSrG2cooQhgpt2hlyT18F4s3GMFTEAn4xM3nGQog7ZCQIEIwm/PC+w99ilIsXQkPBpUCQQClbhi8Z/1G3Aa9NHl5A7h16aheNbBxiapO2yykhjxW8sIquEtynMfZwWmvP4IGy3+8EbjbvFBhSkYNnWpYw+k5AkEAiFS9UB5CiqMpmGtxJiDenakK9pv1NMSm3sOydgKtUT7H+xnoZeATu0k/efHJGlfNFgGC28NstRNkos+tUnCGKQJBAIx2hO5EZZuq6tXwHkwQSqljsh/7+ksNFtxJ4Qf5KnaxPQhE31r7n/1W6kNw6xiV5Wlsqsd4h89z3zNkO44PGpECQEV6Q9csCsGSuVC1clpArleSSNfdGBquQM9qSxeu92p6tLgvmhOWin33udg3A0s6zuAbq49gOIMZ931S8KYN52o=
            System.out.println("公钥为:"+Base64.encodeToString(aPublic.getEncoded()));
            //MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCF5YYzX7SaWpn3lzWwRCNEF987G0bSCjCWK5bCBhfFRSaN0Ey/35BDQTnBoGU3MXsAeAr83thIv5Ah0FxkBACmpMWS4ZRw+t5hqZvz65IZbbV8u/thbdtookJZm6gzAtodGU7Ypy/nqmvGOgYp8KGN0bGPVkE9TWz91psw5xE6eQIDAQAB

            //生成公钥
            PublicKey rsa = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(aPublic.getEncoded()));
            Cipher cipher=Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,rsa);
            byte[] b = cipher.doFinal("admin".getBytes());
            String s = Base64.encodeToString(b);
            //生成密文
            System.out.println("密文为："+s);//X8MeNwavIVixMICiazD9zgHfiQ+l4hOMyEWbKBXGgu1L4rSgJrafqouvNRPuRLnWo6p9cAE79DZ09POwQ55lSbbQDe+tshBVeK9daxJsQ5dcnw0gPQ97owUuuD3xLSxDNycR20ITnnKOyj7Wl2QFrWJnTnQb8QjWjTp2FVcOu8o=


            //解密
            PrivateKey rsa1 = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(aPrivate.getEncoded()));
            Cipher cipher1=Cipher.getInstance("RSA");
            cipher1.init(Cipher.DECRYPT_MODE,rsa1);
            byte[] bytes = cipher1.doFinal(b);
            System.out.println(new String(bytes));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    //  DH （密钥交换协议/算法(Diffie-Hellman)简称）

    //  PBE算法（   Password Based Encryption，基于口令加密）是一种基于口令的加密算法，其特点是使用口令代替了密钥，而口令由用户自己掌管，采用随机数杂凑多重加密等方法保证数据的安全性。
    //  RC2是由著名密码学家Ron Rivest设计的一种传统对称分组加密算法，它可作为DES算法的建议替代算法。它的输入和输出都是64比特。密钥的长度是从1字节到128字节可变，但目前的实现是8字节（1998年）。
    //  安全传输层协议（TLS）用于在两个通信应用程序之间提供保密性和数据完整性。
    //  HMAC是密钥相关的哈希运算消息认证码，HMAC运算利用哈希算法，以一个密钥和一个消息为输入，生成一个消息摘要作为输出。
    //  PBE算法（Password Based Encryption，基于口令加密）是一种基于口令的加密算法，其特点是使用口令代替了密钥，而口令由用户自己掌管，采用随机数杂凑多重加密等方法保证数据的安全性。
    //  SunJCE
}