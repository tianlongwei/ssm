# shirodemo
在ssm框架基础上，增加shiro权限认证功能，以各种加密算法简单demo


20190624

1、验证shiro自带的各个过滤器作用

2、在第一次登录成功后，ehcache将登录成功的认证信息保存在cache中时，SimpleAuthenticationInfo对象中的
SimpleByteSource没有实现序列化接口，无法缓存到磁盘中。（待解决）   
    
    自定义com.loong.modules.commons.shiro.bytesource.MyByteSource解决
    shiro的SimpleByteSource没有实现序列化接口问题。
3、tomcat关闭时异常
    
    严重: The web application [] appears to have started a thread named [SessionValidationThread-1] but has failed to stop it. This is very likely to create a memory leak.

参考https://blog.csdn.net/wcy_1011/article/details/50524272
