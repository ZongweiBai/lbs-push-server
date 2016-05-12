<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/1/15 0015
  Time: 下午 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取离线消息</title>
</head>
<body>
  <form action="<%=request.getContextPath()%>/GTPushController/getOfflineMessage" method="get">
    account<input type="text" name="account"><br/>
    appId<input type="text" name="appId"><br/>
    <input type="submit" name="获取离线消息"><br/>
  </form>
</body>
</html>
