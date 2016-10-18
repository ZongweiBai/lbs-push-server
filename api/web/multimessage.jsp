<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/1/15 0015
  Time: 下午 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发送多人消息</title>
</head>
<body>
    <form action="<%=request.getContextPath()%>/GTPushController/pushTransmissionMessageToMultiPerson" method="post">
        appId:<input type="text" name="appId"><br/>
        content:<input type="text" name="content"><br/>
        latitude:<input type="text" name="latitude"><br/>
        longitude:<input type="text" name="longitude"><br/>
        radius:<input type="text" name="radius"><br/>
        <input type="submit" name="submit"><br/>
    </form>
</body>
</html>
