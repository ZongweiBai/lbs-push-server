<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/1/15 0015
  Time: 下午 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发送单人消息</title>
</head>
<body>
  <form action="<%=request.getContextPath()%>/GTPushController/pushTransmissionMessageToPerson" method="post">
    alias:<input type="text" name="alias"><br/>
    appId:<input type="text" name="appId"><br/>
    content:<input type="text" name="content"><br/>
    <input type="submit" value="发送">
  </form>
</body>
</html>
