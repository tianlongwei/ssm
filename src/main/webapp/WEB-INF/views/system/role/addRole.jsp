<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加角色</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/head.jsp"></jsp:include>
<p><<a href="#" onclick="history.back(-1)">返回</a> </p>
<p>${message}</p>
    <form action="addRole" method="post">
        <table>
            <caption>添加角色</caption>
            <tr>
                <td>角色名：</td>
                <td><input type="text" name="name"></td>
            </tr>
            <tr>
                <td>角色名(英文)：</td>
                <td><input type="text" name="enname"></td>
            </tr>
            <tr>
                <td>描述</td>
                <td><textarea name="description" rows="2" cols="22"></textarea></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="确认添加"></td>
            </tr>
        </table>

    </form>

</body>
</html>
