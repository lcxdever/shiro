<%@page import="java.util.Enumeration"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
Enumeration<String> it=request.getAttributeNames();
while( it.hasMoreElements()){
	System.out.println(it.nextElement());
} %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
     OAuth2登录失败了，如错误的auth code。<br/>
    <c:if test="${not empty param.error}">
        错误码：
        ${param.error}
        ${param.error_description}
    </c:if>
</body>
</html>