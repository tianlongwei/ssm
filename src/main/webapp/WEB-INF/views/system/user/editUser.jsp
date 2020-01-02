<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/head.jsp"></jsp:include>
<p><<a href="#" onclick="history.back(-1)">返回</a></p>

${roleList.size}
<form:form modelAttribute="user" action="update" method="post">
<input type="hidden" name="id" value="${user.id}">
    <table>
        <caption>编辑用户</caption>
        <tr>
            <td>用户名：</td>
            <td><form:input path="username" type="text"/></td>
        </tr>
            <%--角色单选框行--%>
        <tr>
            <td>
                <form:checkboxes path="roleIdList"
                                 items="${roles}"
                                 itemLabel="name"
                                 itemValue="id"
                                 htmlEscape="true"/>
            </td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" value="修改">
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
