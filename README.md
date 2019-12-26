# shirodemo
在ssm框架基础上，增加shiro权限认证功能，以各种加密算法简单demo


20190624

1、验证shiro自带的各个过滤器作用

2、在第一次登录成功后，ehcache将登录成功的认证信息保存在cache中时，SimpleAuthenticationInfo对象中的
SimpleByteSource没有实现序列化接口，无法缓存到磁盘中。（待解决）   
    
    自定义com.loong.modules.commons.shiro.bytesource.MyByteSource解决
    shiro的SimpleByteSource没有实现序列化接口问题。
3、tomcat关闭时异常（已解决）
    
    严重: The web application [] appears to have started a thread named [SessionValidationThread-1] but has failed to stop it. This is very likely to create a memory leak.

20191226

主要是相关对象在spring销毁时未销毁，导致tomcat容器在关闭应用后无法关闭。

1)数据源创建于销毁都需要交由spring容器进行，使用druid连接池，如果有多个数据源，会为每个数据源分配一个初始化线程与销毁监视线程

        <bean id="datasource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
            <property name="url" value="${jdbc.url}"/>
            <property name="driverClassName" value="${jdbc.driverClassName}"/>
            <property name="username" value="${jdbc.username}"/>
            <property name="password" value="${jdbc.password}"/>
        </bean>

2)由于开启了sessionValidation,开启了一个验证线程，该对象必须交由spring容器销毁

        <bean id="defaultWebSessionManager" class="org.apache.shiro.web.session.mgt.DefaultWebSessionManager" destroy-method="destroy">

3)同样ecache也开启了缓存监视线程，同样销毁时需要交由spring容器一起销毁

        <bean id="ehCacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager" init-method="init" destroy-method="destroy">

4)注销pom文件中的mysql连接驱动依赖，而是将jar包放到tomcat中的lib下。可解决销毁时警告问题


参考https://blog.csdn.net/wcy_1011/article/details/50524272
