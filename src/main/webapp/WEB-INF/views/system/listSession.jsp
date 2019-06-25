<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>listSession</title>
</head>
<body>
<% %>
<table>
    <tr>
        <td>sessionID</td>
        <td>用户名</td>
        <td>登录时间</td>
        <td>最后访问</td>
        <td>操作</td>
    </tr>
    <c:forEach var="s" items="${sessions}">
    <tr>
        <td>${s.id}</td>
        <td>${s.attributes.user.username}</td>
        <td><fmt:formatDate value="${s.startTimestamp}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td><fmt:formatDate value="${s.lastAccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        <td><a href="deleteSession?sessionId=${s.id}">踢出用户</a></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
