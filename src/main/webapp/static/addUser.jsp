<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/22
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>addUser</title>
</head>
<body>
    <form action="/addUser" method="post">
        <table>
            <caption>添加用户</caption>
            <tr>
                <td>用户名：</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" name="提交"></td>
            </tr>
        </table>

    </form>
</body>
</html>
