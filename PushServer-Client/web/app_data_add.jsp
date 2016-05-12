<%--
  Created by IntelliJ IDEA.
  User: lizhengbin
  Date: 2015/5/21
  Time: 9:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <form action="./appDataController/addAppData" method="post">
    appId:<input type="text" name="appId"><br/>
    appKey:<input type="text" name="appKey"><br/>
    masterSecret:<input type="text" name="master"><br/>
    <input type="submit" value="addAppData">
  </form>
</body>
</html>
