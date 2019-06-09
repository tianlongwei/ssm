<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/7
  Time: 20:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<form action="/login" method="post">
    <table>
        <caption>登陆</caption>
        <tr>
            <td>用户名：</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密码：</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>
                <input type="submit" name="提交">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
