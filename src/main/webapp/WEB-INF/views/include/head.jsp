<%@ page import="com.loong.modules.system.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<% User user= (User) session.getAttribute("user");%>
你好<%=user.getUsername()%>
<a href="/logout">登出</a>
<a href="/static/updatePwd.jsp">修改密码</a>
<a href="/static/list.jsp">首页</a>
<shiro:principal></shiro:principal>
<hr>