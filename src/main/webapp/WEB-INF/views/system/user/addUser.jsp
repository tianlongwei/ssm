<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>addUser</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/head.jsp"></jsp:include>
<p><<a href="#" onclick="history.back(-1)">返回</a></p>

${roleList.size}
<form:form modelAttribute="user" action="save" method="post">
    <table>
        <caption>添加用户</caption>
        <tr>
            <td>用户名：</td>
            <td><form:input path="username" type="text"/></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><form:input path="password" type="password"/></td>
        </tr>
            <%--角色单选框行--%>
        <tr>
            <td>
                <form:checkboxes path="roleIdList"
                                 items="${roles}"
                                 itemLabel="name"
                                 itemValue="id"
                                 htmlEscape="true"></form:checkboxes>
            </td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="确认添加"></td>
        </tr>


    </table>
</form:form>
</body>
</html>
