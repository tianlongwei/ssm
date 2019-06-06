package my.loong.modules;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestSystem {


    @Test
    public void testMd5(){
        byte[] res=null;
        try {
            MessageDigest digest=MessageDigest.getInstance("MD5");
            //MD5($salt.$pass) 先添加盐值再加密码
            digest.update("123".getBytes());
            System.out.println("加盐值时长度："+digest.getDigestLength());//16
            res=digest.digest("admin".getBytes());//按照UTF-8转成字节数组
            System.out.println("加盐值后长度："+digest.getDigestLength());//16
            System.out.println(Hex.encodeToString(res));//d829b843a6550a947e82f2f38ed6b7a7
            System.out.println("----------------------");
            //MD5($pass.$salt) 先添加密码在添加盐值
            digest.reset();
            System.out.println("重置后长度："+digest.getDigestLength());
            digest.update("admin".getBytes());
            System.out.println(Hex.encodeToString(digest.digest("123".getBytes())));//0192023a7bbd73250516f069df18b500
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test(){
        try {
            MessageDigest digest=MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest();
            System.out.println(Hex.encodeToString(bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSimpleHash(){
        String algorithmName="MD5";
        Object source="admin";
        Object salt="123";
        int hashIterations=1;
        SimpleHash hash=new SimpleHash(algorithmName,source,salt,hashIterations);
        System.out.println(hash.toString());//d829b843a6550a947e82f2f38ed6b7a7
    }


    @Test
    public void testSHA1(){
        try {
            byte[] res=null;
            MessageDigest digest=MessageDigest.getInstance("SHA-1");
            //SHA1($salt.$pass)
            digest.update("123".getBytes("UTF-8"));
            System.out.println("添加盐值后长度："+digest.getDigestLength());
            res=digest.digest("admin".getBytes("UTF-8"));
            System.out.println("添加密码后长度："+digest.getDigestLength());
            System.out.println(Hex.encodeToString(res));//28dca2a7b33b7413ad3bce1d58c26dd679c799f1
            //SHA1($pass.$salt)
            digest.reset();
            digest.update("admin".getBytes());
            res=digest.digest("123".getBytes());
            System.out.println(Hex.encodeToString(res));//f865b53623b121fd34ee5426c792e5c33af8c227
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSHa(){
        String algorithmName="SHA-1";
        Object source="admin";
        Object salt="123";
        int hashIterations=1;
        SimpleHash hash=new SimpleHash(algorithmName,source,salt,hashIterations);
        System.out.println(hash.toString());//28dca2a7b33b7413ad3bce1d58c26dd679c799f1
    }
}
