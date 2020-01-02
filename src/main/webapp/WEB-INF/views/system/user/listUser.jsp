<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/include/head.jsp"></jsp:include>
<p><<a href="#" onclick="history.back(-1)">返回</a> <a href="/user/toAddUser">新增用户</a></p>
<table>
    <tr>
        <td>名称</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.username}</td>
            <td>
                <shiro:hasPermission name="user:edit">
                    <a href="/user/edit?id=${user.id}">编辑</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="user:delete">
                    <a href="/user/delete?id=${user.id}">删除</a>
                </shiro:hasPermission>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
