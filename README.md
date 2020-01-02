# shirodemo
在ssm框架基础上，增加shiro权限认证功能，以各种加密算法简单demo


20190624

1、验证shiro自带的各个过滤器作用

2、在第一次登录成功后，ehcache将登录成功的认证信息保存在cache中时，SimpleAuthenticationInfo对象中的
SimpleByteSource没有实现序列化接口，无法缓存到磁盘中。（待解决）   
    
    自定义com.loong.modules.commons.shiro.bytesource.MyByteSource解决
    shiro的SimpleByteSource没有实现序列化接口问题。
3、tomcat关闭时异常(已解决)
    
    严重: The web application [] appears to have started a thread named [SessionValidationThread-1] but has failed to stop it. This is very likely to create a memory leak.

spring容器中创建线程的类的生命周期需要交给spring容器管理。即添加init-method、destroy-method方法
参考https://blog.csdn.net/wcy_1011/article/details/50524272

20191230-20200102

结合shiro权限框架简单的添加了用户角色管理功能，并在页面中加入shiro标签。在方法中使用shiro注解进行配置。

使用shiro标签在页面加入

    <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

使用shiro注解需要在springMvc配置文件中配置开启。在spring容器中配置没有生效。可能是加载顺序导致的。spring容器是由
listener加载的，最先加载。而shiro加载是由过滤器加载的。因此shiro注解配置并不生效。所以选择在springMvc配置文件中加载
springMvc配置文件由servlet加载。在filter后加载。因此生效。ServletContext -> listener -> filter -> servlet

        <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>

        <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator" depends-on="lifecycleBeanPostProcessor">
            <property name="proxyTargetClass" value="true"/>
        </bean>

        <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
            <property name="securityManager" ref="defaultWebSecurityManager"/>
        </bean>
