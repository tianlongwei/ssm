<%@ page import="com.loong.modules.system.entity.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/7
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>list</title>
</head>
<body>
<% User user= (User) session.getAttribute("user");%>
你好<%=user.getUsername()%>
<a href="/logout">登出</a>
<a href="/static/updatePwd.jsp">修改密码</a>
</body>
</html>
