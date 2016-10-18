<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/1/28 0028
  Time: 下午 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <form action="<%=request.getContextPath()%>/GTPushController/updateLocation" method="post">
    account:<input type="text" name="account"><br/>
    appId:<input type="text" name="appId"><br/>
    latitude:<input type="text" name="latitude"><br/>
    longitude:<input type="text" name="longitude"><br/>
    <input type="submit" name="submit">
  </form>
</body>
</html>
