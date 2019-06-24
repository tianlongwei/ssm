<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>listSession</title>
</head>
<body>
<table>
    <tr>
        <td>sessionID</td>
        <td>用户名</td>
        <td>操作</td>
    </tr>
    <c:forEach var="s" items="${sessions}">
    <tr>
        <td>${s.key}</td>
        <td>${s.value.username}</td>
        <td><a href="deleteSession?sessionId=${s.key}">踢出用户</a></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
