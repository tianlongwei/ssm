<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<jsp:include page="/WEB-INF/views/include/head.jsp"></jsp:include>


<p><<a href="#" onclick="history.back(-1)">返回</a> <a href="/role/toAddRole">新增角色</a></p>
    <table>
        <tr>
            <td>名称</td>
            <td>名称（英文）</td>
            <td>描述</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${roles}" var="role">
        <tr>
            <td>${role.name}</td>
            <td>${role.enname}</td>
            <td>${role.description}</td>
            <td><a href="/role/delete?id=${role.id}">删除</a></td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
